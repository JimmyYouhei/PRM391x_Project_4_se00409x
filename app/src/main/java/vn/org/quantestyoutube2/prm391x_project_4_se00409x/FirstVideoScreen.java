/*
 * MIT License
 *
 * Copyright (c) 2019.  Jimmy Youhei (Quan Nguyen)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package vn.org.quantestyoutube2.prm391x_project_4_se00409x;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.adapter.VideoAdapter;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface.Command;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface.SetupRecyclerView;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.database.VideoRoom;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.event_for_eventbus.ContextListRecyclerView;


public class FirstVideoScreen extends AppCompatActivity {
    private static final String TAG = "FirstVideoScreen";
    public static final String REQUEST_HISTORY = "history , request";

    // List and Room for database management
    private VideoRoom videoRoom;
    private List<VideoEntity> videoHistoryList = new ArrayList<>();

    // Executor for background task
    private Executor executor = Executors.newSingleThreadExecutor();

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        // setup reusable toolbar
        Command.setupToolbar(this);

        recyclerView = findViewById(R.id.firstScreenRecyclerView);

        // if received intent does not carry REQUEST_HISTORY setup RecyclerView as channel
        if (getIntent().getStringExtra(REQUEST_HISTORY) == null){

            SetupRecyclerView setupRecyclerView = new SetupRecyclerView();
            setupRecyclerView.execute(recyclerView , null);

            // if received intent does carry REQUEST_HISTORY setup RecyclerView as videos history
        }else if (getIntent().getStringExtra(REQUEST_HISTORY).equals(REQUEST_HISTORY)){

            // Room to initialize or acquire database
            videoRoom = VideoRoom.getInstance(this);


            // if database already exist transfer all to the List

            // background task for setup RecyclerView
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // check for history of video, if any will retrieve to a List to setup for the RecyclerView
                    if (videoRoom.videoDao().countVideos() > 0){
                        videoHistoryList = videoRoom.videoDao().getVideoDatabase();
                    }

                    // wrap the 3 class for EventBus usage
                    ContextListRecyclerView wrapper = new ContextListRecyclerView(FirstVideoScreen.this, videoHistoryList , recyclerView);

                    // use EventBus to tranfer to main thread (also called UI thread ) to setup recycler view
                    EventBus.getDefault().post(wrapper);
                }


            });
        }
        // register the method
        EventBus.getDefault().register(this);
    }

    // create the 3 dot Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Command.createMenu(this , menu);
        return super.onCreateOptionsMenu(menu);
    }

//when Menu Item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // case click video history
            // if currently in channel switch to history mode else notify the user that they already at it
            case R.id.videoHistory:
                if (getIntent().getStringExtra(REQUEST_HISTORY) == null){
                    getIntent().putExtra(REQUEST_HISTORY , REQUEST_HISTORY);
                    this.recreate();
                } else {
                    Toast.makeText(this, "You are at it", Toast.LENGTH_LONG).show();
                }
            break;

                // case click logout go to SignIn activity
            case R.id.accountLogOut:
                Command.logOut(this);
                break;

                // case click search video will go to search video Activity
            case R.id.searchVideo:
                Intent toSearch = new Intent(this , SearchVideo.class);
                toSearch.putExtra(SignIn.USERNAME_KEY , getIntent().getStringExtra(SignIn.USERNAME_KEY));
                startActivity(toSearch);
                overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


// avoid database leak
    @Override
    protected void onDestroy() {
        VideoRoom.destroyInstance();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // depend on channel or history mode to make the hard back button work
    @Override
    public void onBackPressed() {

        if (getIntent().getStringExtra(REQUEST_HISTORY) == null){
            Command.logOut(this);
        } else if (getIntent().getStringExtra(REQUEST_HISTORY).equals(REQUEST_HISTORY)){
            getIntent().removeExtra(REQUEST_HISTORY);
            this.recreate();
        }
    }

    // Setup the RecyclerView on the Main Thread using the EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setupVideoRecyclerViewDefault(ContextListRecyclerView wrapper){

        Context context = wrapper.getContext();
        List<VideoEntity> videos = wrapper.getVideoEntityList();
        RecyclerView recyclerView = wrapper.getRecyclerView();

        VideoAdapter videoAdapter = new VideoAdapter(context , videos);
        recyclerView.setAdapter(videoAdapter);

    }

}
