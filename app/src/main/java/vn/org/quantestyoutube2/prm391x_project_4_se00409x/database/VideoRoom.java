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
