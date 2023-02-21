package com.platformyun.plus.provider.api;

import com.platformyun.plus.provider.domain.UmsAdminLoginLog;

public interface UmsAdminLoginLogService {

    /**
     * 新增日志
     *
     * @param umsAdminLoginLog {@link UmsAdminLoginLog}
     * @return {@code int} 大于 0 则表示添加成功
     */
    int insert(UmsAdminLoginLog umsAdminLoginLog);

}