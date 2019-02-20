package vn.org.quantestyoutube2.prm391x_project_4_se00409x.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;

//Dao for video database
@Dao
public interface VideoDao {

    @Insert
    void insertAll(List<VideoEntity> videos);

    @Insert
    void insert(VideoEntity...video);

    @Query("SELECT COUNT(*) from videoentity")
    int countVideos();

    @Query("SELECT * FROM videoentity")
    List<VideoEntity> getVideoDatabase();

    @Query("DELETE FROM videoentity")
    void nukeVideoDatabase();
}
