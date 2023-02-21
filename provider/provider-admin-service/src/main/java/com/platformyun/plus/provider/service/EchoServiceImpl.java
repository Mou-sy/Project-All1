package com.platformyun.plus.provider.service;

import com.platformyun.plus.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "0.0.1")
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String string) {
        return "Hello Dubbo" + string;
    }
}