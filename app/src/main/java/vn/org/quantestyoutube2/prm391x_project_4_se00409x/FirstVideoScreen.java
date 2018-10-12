package vn.org.quantestyoutube2.prm391x_project_4_se00409x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.SetupRecyclerView;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.VideoRoom;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;


public class FirstVideoScreen extends AppCompatActivity {
    private static final String TAG = "FirstVideoScreen";
    public static final String REQUEST_HISTORY = "history , request";

    private VideoRoom videoRoom;
    private List<VideoEntity> videoHistoryList = new ArrayList<>();


    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Command.setupToolbar(this);

        recyclerView = findViewById(R.id.firstScreenRecyclerView);

        if (getIntent().getStringExtra(REQUEST_HISTORY) == null){

            SetupRecyclerView setupRecyclerView = new SetupRecyclerView();
            setupRecyclerView.execute(recyclerView , null);

        }else if (getIntent().getStringExtra(REQUEST_HISTORY).equals(REQUEST_HISTORY)){

            videoRoom = VideoRoom.getInstance(this);


            if (videoRoom.videoDao().countVideos() > 0){
                videoHistoryList = videoRoom.videoDao().getVideoDatabase();
            }

            Command.setupVideoRecyclerViewDefault(this , videoHistoryList , recyclerView);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Command.createMenu(this , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.videoHistory:
                getIntent().putExtra(REQUEST_HISTORY , REQUEST_HISTORY);
                this.recreate();

            break;

            case R.id.accountLogOut:
                Command.logOut(this);
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

        if (getIntent().getStringExtra(REQUEST_HISTORY) == null){
            Command.logOut(this);
        } else if (getIntent().getStringExtra(REQUEST_HISTORY).equals(REQUEST_HISTORY)){
            getIntent().removeExtra(REQUEST_HISTORY);
            this.recreate();
        }
    }
}
