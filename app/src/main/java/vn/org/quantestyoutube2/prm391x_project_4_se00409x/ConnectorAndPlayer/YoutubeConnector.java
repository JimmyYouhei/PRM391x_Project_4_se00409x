package vn.org.quantestyoutube2.prm391x_project_4_se00409x.ConnectorAndPlayer;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;

public class YoutubeConnector {
    private static final String TAG = "YoutubeConnector";

    public static final String API_KEY = "AIzaSyATyXrHrTFjcKxx9XGRgz9tdKVo0RFWGyg";

    private static final long MAX_VIDEO = 10;

    private YouTube youTube;
    private YouTube.Search.List list;

    public YoutubeConnector(Context context){

        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            list = youTube.search().list("id,snippet");
            list.setKey(API_KEY);
            list.setType("video");
            list.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "YoutubeConnector: "+ e.getMessage());
        }
    }

    public List<VideoEntity> getListForAdapter (@Nullable String keywords) {

        if (keywords == null){
            list.setChannelId("UCkxo65nH5wfg7z1XxQ_Hb3Q");
        } else {
            list.setQ(keywords);
        }

        list.setMaxResults(MAX_VIDEO);

        try{
            SearchListResponse response = list.execute();
            List<SearchResult> results = response.getItems();
            List<VideoEntity> videoList = new ArrayList<>();

            if (results!= null){
                videoList = getList(results.iterator());
            }

            return videoList;
        } catch (IOException e){
            Log.d(TAG, "setupFirstScreen: "+ e.getMessage());
            return null;
        }

    }

    private static List<VideoEntity> getList(Iterator<SearchResult> iterator){

        List<VideoEntity> videoList = new ArrayList<>();

        if (!iterator.hasNext()){
            Log.d(TAG, "setList: no result????");
        }

        while (iterator.hasNext()){
            SearchResult result = iterator.next();

            ResourceId id = result.getId();

            if (id.getKind().equals("youtube#video")){
                VideoEntity video = new VideoEntity();
                Thumbnail thumbnail = result.getSnippet().getThumbnails().getHigh();

                video.setId(result.getId().getVideoId());
                video.setTitle(result.getSnippet().getTitle());
                video.setDescription(result.getSnippet().getDescription());
                video.setImageURL(thumbnail.getUrl());

                videoList.add(video);

            }

        }

        return videoList;

    }


}
