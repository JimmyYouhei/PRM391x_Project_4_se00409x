package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity;

import android.content.ContentValues;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.UserAccountTable;

// class to represent User Account

public class UserAccount {
    private String username;
    private String password;

    // Constructor
    public UserAccount() {
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // setter and getter

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


    // ContenValues for inserting to the database
    public ContentValues toValues(){
        ContentValues values = new ContentValues(2);

        values.put(UserAccountTable.COLUMN_NAME, username);
        values.put(UserAccountTable.COLUMN_PASSWORD, password);

        return values;
    }

}
