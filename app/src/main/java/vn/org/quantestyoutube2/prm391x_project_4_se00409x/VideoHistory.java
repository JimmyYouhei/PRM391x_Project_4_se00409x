package vn.org.quantestyoutube2.prm391x_project_4_se00409x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.VideoRoom;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;

public class VideoHistory extends AppCompatActivity {
    private static final String TAG = "VideoHistory";

    private RecyclerView videoHistoryRecyclerView;
    private VideoRoom videoRoom;
    private List<VideoEntity> videoHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_history);

        videoRoom = VideoRoom.getInstance(this);

        if (videoRoom.videoDao().countVideos() > 0){
            videoHistoryList = videoRoom.videoDao().getVideoDatabase();
        }

        videoHistoryRecyclerView = findViewById(R.id.videoHistoryRecyclerView);

        Command.setupVideoRecyclerViewDefault(this , videoHistoryList , videoHistoryRecyclerView);

        Command.setupToolbar(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Command.createMenu(this , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.accountLogOut:
                Command.logOut(this);
                break;

            case R.id.videoHistory:
                Toast.makeText(this, "You are at it!!!", Toast.LENGTH_LONG).show();
                break;

            case R.id.searchVideo:
                Intent toSearch = new Intent(this , SearchVideo.class);
                toSearch.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
                startActivity(toSearch);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        VideoRoom.destroyInstance();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent backToFirstScreen = new Intent(this , FirstVideoScreen.class);
        backToFirstScreen.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
        startActivity(backToFirstScreen);
        super.onBackPressed();
    }
}
