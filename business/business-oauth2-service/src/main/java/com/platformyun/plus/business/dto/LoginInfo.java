package com.platformyun.plus.business.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInfo implements Serializable {
    private String name;
    private String avatar;
    private String nickName;
}
