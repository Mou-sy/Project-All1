package com.platformyun.plus.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {
    /**
     * 文件路径
     */
    private String path;
}
