package com.example.spring_boot_mybatis.entity;

import com.example.spring_boot_mybatis.enumer.UserSexEnum;

import java.io.Serializable;

public class UserEntity  implements Serializable{
    private  static  final long serialVersionUID = 1L;
    private  Long id;
    private String userName;
    private  String passWord ;
    private UserSexEnum userSex;
    private  String nickName;
    public UserEntity(){
        super();
    }
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

    public UserEntity(String userName, String passWord, UserSexEnum user_sex){
        super();
                      this.userName = userName;
                      this.passWord =passWord;
                      this.userSex = user_sex;
     }
     @Override
    public  String toString(){
        return  "userName: "+this.userName+",password: "+ this.passWord +",sex: "+ userSex.name();
     }
}
