package com.asset.training.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer userId;
    private String name;
    private String username;
    private Integer profileId;
    private Integer lockFlag;
    private String creationDate;

    private String password;

    public UserModel(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
