package com.platformyun.plus.cloud.feign.fallback;

import com.platformyun.plus.cloud.feign.UploadFeign;
import com.platformyun.plus.commons.ResponseResult;
import com.platformyun.plus.commons.utils.MapperUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class UploadFeignFallback implements UploadFeign {

    private static final String BREAKING_MESSAGE = "请求失败了，请检查您的网络";

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            return MapperUtils.obj2json(new ResponseResult<Void>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}