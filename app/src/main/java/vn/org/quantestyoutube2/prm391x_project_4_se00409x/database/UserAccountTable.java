package vn.org.quantestyoutube2.prm391x_project_4_se00409x.database;

// class for SQLite account database
public class UserAccountTable {
    public static final String TABLE_NAME = "userAccount";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String[] ALL_COLUMN = {COLUMN_NAME , COLUMN_PASSWORD};

    public static final String CREATE_SQL =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_NAME + " TEXT PRIMARY KEY," +
                    COLUMN_PASSWORD + " TEXT" + ");";

    public static final String DELETE_SQL =
            "DROP TABLE " + TABLE_NAME;



}
