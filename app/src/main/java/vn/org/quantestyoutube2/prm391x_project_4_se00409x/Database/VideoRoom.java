package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;

// each username have unique data base;
@Database(entities = {VideoEntity.class} , version = 1)
public abstract class VideoRoom extends RoomDatabase {

    private static VideoRoom instance;

    public abstract VideoDao videoDao();

    public static VideoRoom getInstance (Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , VideoRoom.class ,
                    "videos-history-"  +( (Activity)context).getIntent().getStringExtra(SignIn.USERNAME_KEY) + ".db")
                    // since the database will be small so MainThread is somewhat ok but I know it is best to move to other thread
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }


}
