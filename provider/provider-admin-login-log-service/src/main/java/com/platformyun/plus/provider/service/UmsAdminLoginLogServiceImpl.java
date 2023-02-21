package com.platformyun.plus.provider.service;

import com.platformyun.plus.provider.api.UmsAdminLoginLogService;
import com.platformyun.plus.provider.domain.UmsAdminLoginLog;
import com.platformyun.plus.provider.mapper.UmsAdminLoginLogMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service(version = "0.0.1")
public class UmsAdminLoginLogServiceImpl implements UmsAdminLoginLogService {

    @Resource
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;

    @Override
    public int insert(UmsAdminLoginLog umsAdminLoginLog) {
        return umsAdminLoginLogMapper.insert(umsAdminLoginLog);
    }
}