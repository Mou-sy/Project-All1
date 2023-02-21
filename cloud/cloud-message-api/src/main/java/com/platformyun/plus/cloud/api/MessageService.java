package com.platformyun.plus.cloud.api;

import com.platformyun.plus.cloud.dto.UmsAdminLoginLogDTO;

public interface MessageService {

    boolean sendAdminLoginLog(UmsAdminLoginLogDTO umsAdminLoginLogDTO);

}
