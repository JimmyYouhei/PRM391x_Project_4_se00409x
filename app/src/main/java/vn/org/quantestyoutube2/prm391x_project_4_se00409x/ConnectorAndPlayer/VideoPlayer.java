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


    private YouTubePlayerView youTubePlayerView;
    private TextView txtTilte;
    private TextView txtDescription;

    //Room and List to manage the video history database
    private VideoRoom videoRoom;
    private List<VideoEntity> videoHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Initialize or acquired the database
        videoRoom = VideoRoom.getInstance(this);
        //if acquired transfer all video entity from database to the List
        if (videoRoom.videoDao().countVideos() != 0) {
            videoHistoryList = videoRoom.videoDao().getVideoDatabase();
        }


        // tied View to its place in xml
        youTubePlayerView = findViewById(R.id.videoPlayer);

        txtTilte = findViewById(R.id.txtTitlePlayer);
        txtDescription = findViewById(R.id.txtDescriptionPlayer);
        // set Text of video's title and description
        txtTilte.setText(getIntent().getStringExtra(VideoAdapter.VIDEO_TITLE));
        txtDescription.setText(getIntent().getStringExtra(VideoAdapter.VIDEO_DESCRIPTION));

        // play video
        youTubePlayerView.initialize(YoutubeConnector.API_KEY , this);


    }

    // case success
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean isRestored) {

        //case video is not restored meaning new playing
        if(!isRestored){
            // load and autoplay video
            youTubePlayer.loadVideo(getIntent().getStringExtra(VideoAdapter.VIDEO_ID) , 0);
            // make Video Entity of the playing video object from the intent data
            VideoEntity videoPlaying = Command.getVideoFromIntent(getIntent());

            // check whether  the video already exist in the database , if not exist , will add to the database
            if (!Command.isVideoDuplicate(videoHistoryList , videoPlaying)){
                videoRoom.videoDao().insert(videoPlaying);
            }
        }

    }

    // case failure
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        Toast.makeText(this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();

    }

    // override Hard back button get intent data to know where the intent come from
    @Override
    public void onBackPressed() {
        // case come from the channel will come back to channel ans preserve the username data
        if (getIntent().getStringExtra(FirstVideoScreen.REQUEST_HISTORY) == null){

            Intent backToFirstScreen = new Intent(this , FirstVideoScreen.class);
            backToFirstScreen.putExtra(SignIn.USERNAME_KEY ,getIntent().getStringExtra(SignIn.USERNAME_KEY));
            startActivity(backToFirstScreen);
            overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);

            //case from the history mode will come back to the history and preserve username and REQUEST_HISTORY
        } else if(getIntent().getStringExtra(FirstVideoScreen.REQUEST_HISTORY).equals(FirstVideoScreen.REQUEST_HISTORY)){
            Intent backToFirstScreen = new Intent(this , FirstVideoScreen.class);
            backToFirstScreen.putExtra(SignIn.USERNAME_KEY ,getIntent().getStringExtra(SignIn.USERNAME_KEY));
            backToFirstScreen.putExtra(FirstVideoScreen.REQUEST_HISTORY , FirstVideoScreen.REQUEST_HISTORY);
            startActivity(backToFirstScreen);
            overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
        }
    }

    // avoid database leak
    @Override
    protected void onDestroy() {
        VideoRoom.destroyInstance();
        super.onDestroy();
    }

}
