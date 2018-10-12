package vn.org.quantestyoutube2.prm391x_project_4_se00409x.ConnectorAndPlayer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        VideoRoom.destroyInstance();
        super.onDestroy();
    }


}
