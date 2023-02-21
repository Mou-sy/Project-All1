package com.platformyun.plus.business.dto.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordParam implements Serializable {

    private String username;
    private String oldPassword;
    private String newPassword;

}
