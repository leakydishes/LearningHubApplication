package com.example.learninghubapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// model class to get user information and hold data
// id is primary key
@Entity(tableName = "user_table")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userId")
    private String name;
    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;


    // constructor
    public User(String userName, String name, String password, String phoneNumber) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    //getter setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}



