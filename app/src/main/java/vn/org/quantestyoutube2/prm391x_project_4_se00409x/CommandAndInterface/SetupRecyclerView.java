package vn.org.quantestyoutube2.prm391x_project_4_se00409x.CommandAndInterface;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Adapter.VideoAdapter;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.ConnectorAndPlayer.YoutubeConnector;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;

public class SetupRecyclerView extends AsyncTask <Object , Void, Void> {
    private YoutubeConnector youtubeConnector;
    private List<VideoEntity> videos;
    private VideoAdapter videoAdapter;
    private RecyclerView recyclerView;
    private String keyword;

    @Override
    protected Void doInBackground(Object... objects) {
        recyclerView = (RecyclerView) objects[0];
        keyword = (String) objects[1];
        youtubeConnector = new YoutubeConnector(recyclerView.getContext());
        videos = youtubeConnector.getListForAdapter(keyword);
        videoAdapter = new VideoAdapter(recyclerView.getContext() , videos);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }
}
