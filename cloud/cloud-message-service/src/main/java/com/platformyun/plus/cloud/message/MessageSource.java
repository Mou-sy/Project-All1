package com.platformyun.plus.cloud.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {

    @Output("admin-login-log-topic")
    MessageChannel adminLoginLog();

}