package com.platformyun.plus.business.controller;

import com.platformyun.plus.commons.dto.ResponseResult;
import com.platformyun.plus.provider.api.UmsAdminService;
import com.platformyun.plus.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "reg")
public class RegController {

    @Reference(version = "0.0.1")
    private UmsAdminService umsAdminService;

    /**
     * 注册
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "")
    public ResponseResult<UmsAdmin> reg(@RequestBody UmsAdmin umsAdmin) {
        String message = validateReg(umsAdmin);

        // 通过验证
        if (message == null) {
            int result = umsAdminService.insert(umsAdmin);

            // 注册成功
            if (result > 0) {
                UmsAdmin admin = umsAdminService.get(umsAdmin.getUsername());
                return new ResponseResult<UmsAdmin>(HttpStatus.OK.value(), "用户注册成功", admin);
            }
        }

        return new ResponseResult<UmsAdmin>(HttpStatus.NOT_ACCEPTABLE.value(), message != null ? message : "用户注册失败");
    }

    /**
     * 注册信息验证
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return 验证结果
     */
    private String validateReg(UmsAdmin umsAdmin) {
        UmsAdmin admin = umsAdminService.get(umsAdmin.getUsername());

        if (admin != null) {
            return "用户名已存在，请重新输入";
        }

        return null;
    }

}

