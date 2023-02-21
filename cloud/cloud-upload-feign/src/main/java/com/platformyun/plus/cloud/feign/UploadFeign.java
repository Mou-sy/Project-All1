package com.platformyun.plus.cloud.feign;

import com.platformyun.plus.cloud.feign.fallback.UploadFeignFallback;
import com.platformyun.plus.configuration.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "cloud-upload", path = "upload", configuration = FeignRequestConfiguration.class, fallback = UploadFeignFallback.class)
public interface UploadFeign {

    /**
     * 文件上传
     *
     * @param multipartFile {@code MultipartFile}
     * @return {@code String} 文件上传路径
     */
    @PostMapping(value = "")
    String upload(@RequestPart(value = "multipartFile") MultipartFile multipartFile);

}