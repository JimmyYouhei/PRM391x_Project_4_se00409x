package vn.org.quantestyoutube2.prm391x_project_4_se00409x.command_and_interface;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.adapter.VideoAdapter;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.connector_and_player.YoutubeConnector;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;

// AsyncTask to play on other thread (not main thread)
// a Combine of new Thread () and Handler which is easier to use and is much better in the term of reusable code
public class SetupRecyclerView extends AsyncTask <Object , Void, Void> {
    private YoutubeConnector youtubeConnector;
    private List<VideoEntity> videos;
    private VideoAdapter videoAdapter;
    private RecyclerView recyclerView;
    private String keyword;

    // the work that do on the new thread ( also call background thread)
    @Override
    protected Void doInBackground(Object... objects) {
        // will pass 2 arguments to the Asynctask first is the context's RecyclerView and the other is the String keyword(nullable and is null if want to get to the original channel)
        recyclerView = (RecyclerView) objects[0];
        keyword = (String) objects[1];

        // setup adapter
        youtubeConnector = new YoutubeConnector(recyclerView.getContext());
        videos = youtubeConnector.getListForAdapter(keyword);
        videoAdapter = new VideoAdapter(recyclerView.getContext() , videos);
        return null;
    }

    // there is the task which is doing on the main thread . Avoid the problem that Android required the RecyclerView must be initialize and setup on a same thread
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // setup the RecyclerView
        recyclerView.setAdapter(videoAdapter);
        // notify the List is change for the RecyclerView to re-setup
        videoAdapter.notifyDataSetChanged();
    }
}
