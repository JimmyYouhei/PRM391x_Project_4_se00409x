package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_FILE_NAME = "account.db";
    public static final int DATABASE_VERSION = 1;

    public AccountDatabaseOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserAccountTable.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserAccountTable.DELETE_SQL);
        onCreate(db);

    }
}
