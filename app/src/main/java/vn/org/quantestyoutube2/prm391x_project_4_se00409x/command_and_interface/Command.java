package vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.adapter.VideoAdapter;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.UserAccount;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;


// Central class to manage methods
// the class is aim for easily read , maintain and reusable and only included methods that can be used in multi class not class specific methods
public abstract class Command {

    // check for whether username is exist in the database , return true if exist
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

    // check for if the password of the username match with the database, return true if password match
    // the method is here because it may be use when an Activity re-ask for password (eg: change password)
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

    // method that is to setup toolbar.xml on the Activity that want to reuse this toolbar
    // Required a placeholder that include toolbar.xml (eg: framelayout)
    public static void setupToolbar (Context context) {

        //
        Toolbar toolbar = ((AppCompatActivity)context).findViewById(R.id.toolbarResuseable);

        // make toolbar become Action bar so can easily create menu
        ((AppCompatActivity)context).setSupportActionBar(toolbar);
        // disable generated title so to create custom welcome text
        ((AppCompatActivity)context).getSupportActionBar().setDisplayShowTitleEnabled(false);

        // make welcome text according to the username
        Intent intentFromPreviousActivity = ((AppCompatActivity) context).getIntent();
        TextView txtWelcomeText = ((AppCompatActivity) context).findViewById(R.id.txtToolbarWelcome);

        // setup Welcome Text
        txtWelcomeText.setText("Hi "+ intentFromPreviousActivity.getStringExtra(SignIn.USERNAME_KEY)+
                ", Welcome to Funix!");
    }

    // method to inflate the menu for activity
    public static void createMenu (Context context , Menu menu){
        ((Activity)context).getMenuInflater().inflate(R.menu.videolist_menu , menu);
    }

    //make object VideoEntity from intent data
    public static VideoEntity getVideoFromIntent (Intent intent){

        VideoEntity video= new VideoEntity();
        video.setId(intent.getStringExtra(VideoAdapter.VIDEO_ID));
        video.setTitle(intent.getStringExtra(VideoAdapter.VIDEO_TITLE));
        video.setDescription(intent.getStringExtra(VideoAdapter.VIDEO_DESCRIPTION));
        video.setImageURL(intent.getStringExtra(VideoAdapter.VIDEO_IMAGE_URL));

        return video;

    }

    // check whether the video is already exist in the database , return true if already exist
    public static boolean isVideoDuplicate(List<VideoEntity> videos , VideoEntity video){

        boolean result = false;

        for (VideoEntity anVideo : videos){
            if (video.getId().equals(anVideo.getId())){
                result = true;
            }
        }

        return result;

    }

    // method when click on the logout menu item
    public static void logOut (Context context) {
        Intent logOut = new Intent(context , SignIn.class);
        context.startActivity(logOut);
        ((Activity)context).overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
    }

}
