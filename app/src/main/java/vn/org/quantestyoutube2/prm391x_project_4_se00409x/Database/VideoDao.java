package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;

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
