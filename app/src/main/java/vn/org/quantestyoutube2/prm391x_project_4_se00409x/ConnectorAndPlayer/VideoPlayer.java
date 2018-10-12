package vn.org.quantestyoutube2.prm391x_project_4_se00409x.ConnectorAndPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Adapter.VideoAdapter;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Database.VideoRoom;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.FirstVideoScreen;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;

public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {
    private static final String TAG = "VideoPlayer";
    public static final int RESULT_TO_BACK = 10;

    private YouTubePlayerView youTubePlayerView;
    private TextView txtTilte;
    private TextView txtDescription;
    private VideoRoom videoRoom;
    private List<VideoEntity> videoHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoRoom = VideoRoom.getInstance(this);
        if (videoRoom.videoDao().countVideos() != 0) {
            videoHistoryList = videoRoom.videoDao().getVideoDatabase();
        }


        youTubePlayerView = findViewById(R.id.videoPlayer);

        txtTilte = findViewById(R.id.txtTitlePlayer);
        txtDescription = findViewById(R.id.txtDescriptionPlayer);
        txtTilte.setText(getIntent().getStringExtra(VideoAdapter.VIDEO_TITLE));
        txtDescription.setText(getIntent().getStringExtra(VideoAdapter.VIDEO_DESCRIPTION));

        youTubePlayerView.initialize(YoutubeConnector.API_KEY , this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean isRestored) {


        if(!isRestored){
            youTubePlayer.loadVideo(getIntent().getStringExtra(VideoAdapter.VIDEO_ID) , 0);
            VideoEntity videoPlaying = Command.getVideoFromIntent(getIntent());

            if (!Command.isVideoDuplicate(videoHistoryList , videoPlaying)){
                videoRoom.videoDao().insert(videoPlaying);
            }

            Log.d(TAG, "onInitializationSuccess: " + videoRoom.videoDao().countVideos());
        }



    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        Toast.makeText(this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra(FirstVideoScreen.REQUEST_HISTORY) == null){

            Intent backToFirstScreen = new Intent(this , FirstVideoScreen.class);
            backToFirstScreen.putExtra(SignIn.USERNAME_KEY ,getIntent().getStringExtra(SignIn.USERNAME_KEY));
            startActivity(backToFirstScreen);

        } else if(getIntent().getStringExtra(FirstVideoScreen.REQUEST_HISTORY).equals(FirstVideoScreen.REQUEST_HISTORY)){
            Intent backToFirstScreen = new Intent(this , FirstVideoScreen.class);
            backToFirstScreen.putExtra(SignIn.USERNAME_KEY ,getIntent().getStringExtra(SignIn.USERNAME_KEY));
            backToFirstScreen.putExtra(FirstVideoScreen.REQUEST_HISTORY , FirstVideoScreen.REQUEST_HISTORY);
            startActivity(backToFirstScreen);
        }
    }

    @Override
    protected void onDestroy() {
        VideoRoom.destroyInstance();
        super.onDestroy();
    }

}
