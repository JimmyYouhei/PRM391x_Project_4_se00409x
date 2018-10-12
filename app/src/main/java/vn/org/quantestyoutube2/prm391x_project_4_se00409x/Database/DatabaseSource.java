package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.UserAccount;

public class DatabaseSource {

    private Context mContext;
    private SQLiteDatabase mAccountDatabase;
    SQLiteOpenHelper mOpenHelper;

    public DatabaseSource (Context context){
        mContext = context;
        mOpenHelper = new AccountDatabaseOpenHelper(mContext);
        mAccountDatabase = mOpenHelper.getWritableDatabase();
    }

    public void open(){
        mAccountDatabase = mOpenHelper.getWritableDatabase();
    }

    public void close() {
        mOpenHelper.close();
    }

    public UserAccount insert(UserAccount account){
        ContentValues values = account.toValues();
        mAccountDatabase.insert(UserAccountTable.TABLE_NAME , null , values);
        return account;
    }

    public long countAccount(){
        return DatabaseUtils.queryNumEntries(mAccountDatabase , UserAccountTable.TABLE_NAME);
    }

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
