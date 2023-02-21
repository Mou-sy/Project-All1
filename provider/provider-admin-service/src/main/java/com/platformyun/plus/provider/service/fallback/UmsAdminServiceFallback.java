package com.platformyun.plus.provider.service.fallback;

import com.platformyun.plus.provider.domain.UmsAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UmsAdminServiceFallback {
    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceFallback.class);

    /**
     * 熔断方法
     *
     * @param username {@code String} 用户名
     * @param ex       {@code Throwable} 异常信息
     * @return {@link UmsAdmin} 熔断后的固定结果
     */
    public static UmsAdmin getByUsernameFallback(String username, Throwable ex) {
        logger.warn("Invoke getByUsernameFallback: " + ex.getClass().getTypeName());
        ex.printStackTrace();
        return null;
    }

}
