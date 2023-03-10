package com.platformyun.plus.business.controller;

import com.google.common.collect.Maps;


import com.platformyun.plus.business.BusinessException;
import com.platformyun.plus.business.BusinessStatus;
import com.platformyun.plus.business.dto.LoginInfo;
import com.platformyun.plus.business.dto.LoginParam;
import com.platformyun.plus.business.feign.ProfileFeign;
import com.platformyun.plus.cloud.api.MessageService;
import com.platformyun.plus.cloud.dto.UmsAdminLoginLogDTO;
import com.platformyun.plus.commons.ResponseResult;
import com.platformyun.plus.commons.utils.MapperUtils;
import com.platformyun.plus.commons.utils.OkHttpClientUtil;
import com.platformyun.plus.commons.utils.UserAgentUtils;
import com.platformyun.plus.provider.api.UmsAdminService;
import com.platformyun.plus.provider.domain.UmsAdmin;
import eu.bitwalker.useragentutils.Browser;
import okhttp3.Response;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
public class LoginController {

//    private static final String URL_OAUTH_TOKEN = "http://localhost:9001/oauth/token";
    @Value("${server.port}")
    public String port;
    @Value("${business.oauth2.grant_type}")
    public String oauth2GrantType;

    @Value("${business.oauth2.client_id}")
    public String oauth2ClientId;

    @Value("${business.oauth2.client_secret}")
    public String oauth2ClientSecret;

    @Resource(name = "userDetailsServiceBean")
    public UserDetailsService userDetailsService;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @Resource
    public TokenStore tokenStore;

    @Resource
    private ProfileFeign profileFeign;

    @Reference(version = "0.0.1")
    private UmsAdminService umsAdminService;

    @Reference(version = "0.0.1")
    private MessageService messageService;

    /**
     * ??????
     *
     * @param loginParam ????????????
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam, HttpServletRequest request) throws Exception {
        // ????????????????????????
        Map<String, Object> result = Maps.newHashMap();

        // ????????????????????????
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            throw new BusinessException(BusinessStatus.ADMIN_PASSWORD);
        }

        // ?????? HTTP ???????????????????????????
        Map<String, String> params = Maps.newHashMap();
        params.put("username", loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("grant_type", oauth2GrantType);
        params.put("client_id", oauth2ClientId);
        params.put("client_secret", oauth2ClientSecret);

        try {
            // ?????????????????????????????????
            Response response = OkHttpClientUtil.getInstance().postData("http://localhost:"+port+"/oauth/token", params);

            String jsonString = Objects.requireNonNull(response.body()).string();
            System.out.printf("========================"+jsonString);
            Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);

            String token = String.valueOf(jsonMap.get("access_token"));

            result.put("token", token);

            // ??????????????????
            sendAdminLoginLog(userDetails.getUsername(), request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK, "????????????", result);
    }

    /**
     * ??????????????????
     *
     * @return {@link ResponseResult}
     */
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/user/info")
    public ResponseResult<LoginInfo> info() throws Exception {
        // ??????????????????
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // ??????????????????
        String jsonString = profileFeign.info(authentication.getName());
        UmsAdmin umsAdmin = MapperUtils.json2pojoByTree(jsonString, "data", UmsAdmin.class);

        // ???????????????????????????????????????
        if (umsAdmin == null) {
            return MapperUtils.json2pojo(jsonString, ResponseResult.class);
        }

        // ?????????????????????
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(umsAdmin.getUsername());
        loginInfo.setAvatar(umsAdmin.getIcon());
        loginInfo.setNickName(umsAdmin.getNickName());
        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.OK, "??????????????????", loginInfo);
    }

    /**
     * ??????
     *
     * @return {@link ResponseResult}
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        // ?????? token
        String token = request.getParameter("access_token");
        // ?????? token ?????????
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "???????????????");
    }

    /**
     * ??????????????????
     *
     * @param request {@link HttpServletRequest}
     */
    private void sendAdminLoginLog(String username, HttpServletRequest request) {
        UmsAdmin umsAdmin = umsAdminService.get(username);

        if (umsAdmin != null) {
            // ?????????????????????????????????
            Browser browser = UserAgentUtils.getBrowser(request);
            String ip = UserAgentUtils.getIpAddr(request);
            String address = UserAgentUtils.getIpInfo(ip).getCity();

            UmsAdminLoginLogDTO dto = new UmsAdminLoginLogDTO();
            dto.setAdminId(umsAdmin.getId());
            dto.setCreateTime(new Date());
            dto.setIp(ip);
            dto.setAddress(address);
            dto.setUserAgent(browser.getName());

            messageService.sendAdminLoginLog(dto);
        }
    }
}

