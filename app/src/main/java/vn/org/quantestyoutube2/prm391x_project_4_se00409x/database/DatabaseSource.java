/*
 * MIT License
 *
 * Copyright (c) 2019.  Jimmy Youhei (Quan Nguyen)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package vn.org.quantestyoutube2.prm391x_project_4_se00409x.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.UserAccount;

// class that include and manage account database
public class DatabaseSource {

    private Context mContext;
    private SQLiteDatabase mAccountDatabase;
    SQLiteOpenHelper mOpenHelper;

    // contructor
    public DatabaseSource (Context context){
        mContext = context;
        mOpenHelper = new AccountDatabaseOpenHelper(mContext);
        mAccountDatabase = mOpenHelper.getWritableDatabase();
    }

    // initialize or acquire database
    public void open(){
        mAccountDatabase = mOpenHelper.getWritableDatabase();
    }

    // close to avoid leak
    public void close() {
        mOpenHelper.close();
    }

    // insert method for database
    public void insert(UserAccount account){
        ContentValues values = account.toValues();
        mAccountDatabase.insert(UserAccountTable.TABLE_NAME , null , values);
    }

    // count item method in database
    public long countAccount(){
        return DatabaseUtils.queryNumEntries(mAccountDatabase , UserAccountTable.TABLE_NAME);
    }

    // method to transfer all account to a List using Cursor
    public List<UserAccount> getAllAccount (){
        List<UserAccount> userAccountList = new ArrayList<>();

        Cursor cursor = mAccountDatabase.query(UserAccountTable.TABLE_NAME , UserAccountTable.ALL_COLUMN ,
                null,null,null,null, UserAccountTable.COLUMN_NAME);

        while (cursor.moveToNext()){
            UserAccount account = new UserAccount();
            account.setUsername(cursor.getString(cursor.getColumnIndex(UserAccountTable.COLUMN_NAME)));
            account.setPassword(cursor.getString(cursor.getColumnIndex(UserAccountTable.COLUMN_PASSWORD)));

            userAccountList.add(account);
        }

        cursor.close();
        return userAccountList;
    }

}
