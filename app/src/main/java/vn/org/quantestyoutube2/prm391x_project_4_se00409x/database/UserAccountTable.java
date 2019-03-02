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
