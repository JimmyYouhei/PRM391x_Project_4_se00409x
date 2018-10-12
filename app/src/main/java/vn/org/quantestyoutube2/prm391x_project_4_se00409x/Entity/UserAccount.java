package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity;

import android.content.ContentValues;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.UserAccountTable;

public class UserAccount {
    private String username;
    private String password;

    public UserAccount() {
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
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

    public ContentValues toValues(){
        ContentValues values = new ContentValues(2);

        values.put(UserAccountTable.COLUMN_NAME, username);
        values.put(UserAccountTable.COLUMN_PASSWORD, password);

        return values;
    }

}
