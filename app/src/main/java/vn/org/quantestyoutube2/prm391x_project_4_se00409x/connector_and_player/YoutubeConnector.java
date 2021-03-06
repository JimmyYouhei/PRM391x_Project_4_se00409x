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

package vn.org.quantestyoutube2.prm391x_project_4_se00409x.connector_and_player;

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

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;

public class YoutubeConnector {
    private static final String TAG = "YoutubeConnector";

    // API key from google
    public static final String API_KEY = "AIzaSyATyXrHrTFjcKxx9XGRgz9tdKVo0RFWGyg";

    // max Video
    private static final long MAX_VIDEO = 10;

    private YouTube.Search.List list;

    public YoutubeConnector(Context context){

        // build new youtube object
        YouTube youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            // get only id and snippet part that is send back
            list = youTube.search().list("id,snippet");
            // set API key
            list.setKey(API_KEY);
            // only take video back not channel or playlist
            list.setType("video");
            // field to get back : kind , id , title , description , thumbnail url
            list.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "YoutubeConnector: "+ e.getMessage());
        }
    }

    // make List that can be used for Adapter
    public List<VideoEntity> getListForAdapter (@Nullable String keywords) {

        // if no key word mean see the video from Funix channel
        if (keywords == null){
            // id of Funix channel
            list.setChannelId("UCkxo65nH5wfg7z1XxQ_Hb3Q");
        } else {
            // setQ to search for the video with keyword
            list.setQ(keywords);
        }

        // set max Result as above
        list.setMaxResults(MAX_VIDEO);

        // get a List<SearchResult> back and if not null convert to List<VideoEntity> for Adapter
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

    // method to convert as above . Use Iterator
    private static List<VideoEntity> getList(Iterator<SearchResult> iterator){

        List<VideoEntity> videoList = new ArrayList<>();

        // no search result!!!!
        if (!iterator.hasNext()){

        }

        // there are search result
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
