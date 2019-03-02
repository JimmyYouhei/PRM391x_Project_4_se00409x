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

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;

// each username have unique data base;
// Room for video database
@Database(entities = {VideoEntity.class} , version = 1)
public abstract class VideoRoom extends RoomDatabase {

    private static VideoRoom instance;

    public abstract VideoDao videoDao();

    public static VideoRoom getInstance (Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , VideoRoom.class ,
                    // the name will include username in its name so to make unique database for each username
                    "videos-history-"  +( (Activity)context).getIntent().getStringExtra(SignIn.USERNAME_KEY) + ".db")
                    // eliminate running room on Main Thread for performance and avoid freezing
//                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }


}
