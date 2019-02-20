package vn.org.quantestyoutube2.prm391x_project_4_se00409x.event_for_eventbus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;


// Wrapper class to wrap Context , List and RecyclerView for EventBus usage
public class ContextListRecyclerView {

    // 3 class to wrap
    private RecyclerView recyclerView;
    private Context context;
    private List<VideoEntity> videoEntityList;


    // Constructor
    public ContextListRecyclerView(Context context, List<VideoEntity> videoEntityList, RecyclerView recyclerView ) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.videoEntityList = videoEntityList;
    }

    // getter and setter
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<VideoEntity> getVideoEntityList() {
        return videoEntityList;
    }

    public void setVideoEntityList(List<VideoEntity> videoEntityList) {
        this.videoEntityList = videoEntityList;
    }
}
