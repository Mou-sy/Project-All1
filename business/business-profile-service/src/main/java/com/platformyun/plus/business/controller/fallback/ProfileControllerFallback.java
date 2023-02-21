package com.platformyun.plus.business.controller.fallback;

import com.platformyun.plus.business.dto.UmsAdminDTO;
import com.platformyun.plus.business.feign.fallback.ProfileFeignFallback;
import com.platformyun.plus.commons.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ProfileControllerFallback {

    private static final Logger logger = LoggerFactory.getLogger(ProfileControllerFallback.class);

    /**
     * 熔断方法
     *
     * @param username {@code String} 用户名
     * @return {@link ResponseResult<UmsAdminDTO>}
     */
    public static ResponseResult<UmsAdminDTO> infoFallback(String username, Throwable ex) {
        logger.warn("Invoke infoFallback: " + ex.getClass().getTypeName());
        ex.printStackTrace();
        return new ResponseResult<UmsAdminDTO>(ResponseResult.CodeStatus.BREAKING, ProfileFeignFallback.BREAKING_MESSAGE);
    }

}
