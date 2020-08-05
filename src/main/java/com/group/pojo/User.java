package com.group.pojo;

public class User extends MyPage {
    private Integer userid;

    private String username;

    private String password;

    private String sex;

    private String tel;

    private String role;

    private Integer total;

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", role='" + role + '\'' +
                ", total=" + total +
                '}';
    }

    public User() {
    }

    public User(Integer userid, String username, String password, String sex, String tel, String role, Integer total) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.tel = tel;
        this.role = role;
        this.total = total;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}