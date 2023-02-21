package com.platformyun.plus.cloud.producer;

import com.platformyun.plus.cloud.api.MessageService;
import com.platformyun.plus.cloud.dto.UmsAdminLoginLogDTO;
import com.platformyun.plus.cloud.message.MessageSource;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@Service(version = "0.0.1")
public class MessageProducer implements MessageService {

    @Resource
    private MessageSource source;

    /**
     * 管理登录日志
     *
     * @param dto {@link UmsAdminLoginLogDTO}
     * @return {@code boolean}
     */
    @Override
    public boolean sendAdminLoginLog(UmsAdminLoginLogDTO dto) {
        return source.adminLoginLog().send(MessageBuilder.withPayload(dto).build());
    }
}