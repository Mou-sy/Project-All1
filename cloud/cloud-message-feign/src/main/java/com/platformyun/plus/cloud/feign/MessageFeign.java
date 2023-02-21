package com.platformyun.plus.cloud.feign;

import com.platformyun.plus.cloud.feign.fallback.MessageFeignFallback;
import com.platformyun.plus.configuration.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "cloud-message", path = "message", configuration = FeignRequestConfiguration.class, fallback = MessageFeignFallback.class)
public interface MessageFeign {

}
