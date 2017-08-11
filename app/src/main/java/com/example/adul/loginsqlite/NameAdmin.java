package com.example.adul.loginsqlite;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by adul on 18/04/17.
 */

public class NameAdmin {
    private String id;
    private String username;
    private String password;

//    public NameAdmin(int id, String username, String password) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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



    public static final String SQL_CREATE = "create table admin_name " +
            "(_id integer primary key autoincrement, "+
            "username text, password text)";
    public static final String SQL_DELETE = "drop table if exists admin_name";
}
