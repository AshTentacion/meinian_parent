package com.atguigu.pojo;

/**
 * @作者 靖雪阳
 * @时间 2021-8-28 下午 3:37
 * @类名 User
 * @描述
 */

public class User {
    private String username;
    private String password;
    private String telephone;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public User() {
    }

    public User(String username, String password, String telephone) {
        this.username = username;
        this.password = password;
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
