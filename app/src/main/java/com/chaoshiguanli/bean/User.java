package com.chaoshiguanli.bean;

public class User {

    private String userid;
    private String password;
    private String username;
    private boolean sex;
    private String question;
    private String aswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAswer() {
        return aswer;
    }

    public void setAswer(String aswer) {
        this.aswer = aswer;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", sex=" + sex +
                ", question='" + question + '\'' +
                ", aswer='" + aswer + '\'' +
                '}';
    }

    public User() {
    }

    public User(String userid, String password, String username, boolean sex, String question, String aswer) {
        this.userid = userid;
        this.password = password;
        this.username = username;
        this.sex = sex;
        this.question = question;
        this.aswer = aswer;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
