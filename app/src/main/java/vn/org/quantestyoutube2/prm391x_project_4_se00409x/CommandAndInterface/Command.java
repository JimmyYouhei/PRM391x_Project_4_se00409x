package vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Adapter.VideoAdapter;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.UserAccount;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;

public abstract class Command {

    public static boolean isUsernameAlreadyExist(List<UserAccount> userAccountList, String username) {
        boolean result = false;

        if (userAccountList.size() > 0) {
            for (UserAccount account : userAccountList) {
                if (account.getUsername().equals(username)) {
                    result = true;
                }
            }
        }

        return result;
    }

    public static boolean isPasswordCorrect(List<UserAccount> userAccountList,
                                            String username, String password) {
        boolean result = false;

        for (UserAccount anAccount : userAccountList) {
            if (anAccount.getUsername().equals(username)) {
                if (anAccount.getPassword().equals(password)) {
                    result = true;
                }
            }
        }

        return result;
    }

    public static void setupToolbar (Context context) {

        Toolbar toolbar = ((AppCompatActivity)context).findViewById(R.id.toolbarResuseable);

        ((AppCompatActivity)context).setSupportActionBar(toolbar);
        ((AppCompatActivity)context).getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intentFromPreviousActivity = ((AppCompatActivity) context).getIntent();
        TextView txtWelcomeText = ((AppCompatActivity) context).findViewById(R.id.txtToolbarWelcome);

        txtWelcomeText.setText("Hi "+ intentFromPreviousActivity.getStringExtra(SignIn.USERNAME_KEY)+
                ", Welcome to Funix!");
    }

    public static void createMenu (Context context , Menu menu){
        ((Activity)context).getMenuInflater().inflate(R.menu.videolist_menu , menu);
    }

    public static void setupVideoRecyclerViewDefault(Context context ,  List<VideoEntity> videos , RecyclerView recyclerView){

        VideoAdapter videoAdapter = new VideoAdapter(context , videos);
        recyclerView.setAdapter(videoAdapter);

    }

    public static VideoEntity getVideoFromIntent (Intent intent){

        VideoEntity video= new VideoEntity();
        video.setId(intent.getStringExtra(VideoAdapter.VIDEO_ID));
        video.setTitle(intent.getStringExtra(VideoAdapter.VIDEO_TITLE));
        video.setDescription(intent.getStringExtra(VideoAdapter.VIDEO_DESCRIPTION));
        video.setImageURL(intent.getStringExtra(VideoAdapter.VIDEO_IMAGE_URL));

        return video;

    }

    public static boolean isVideoDuplicate(List<VideoEntity> videos , VideoEntity video){

        boolean result = false;

        for (VideoEntity anVideo : videos){
            if (video.getId().equals(anVideo.getId())){
                result = true;
            }
        }

        return result;

    }

    public static void logOut (Context context) {
        Intent logOut = new Intent(context , SignIn.class);
        context.startActivity(logOut);
    }

}
