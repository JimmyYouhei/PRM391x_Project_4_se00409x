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

package vn.org.quantestyoutube2.prm391x_project_4_se00409x.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.FirstVideoScreen;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.connector_and_player.VideoPlayer;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;

// Adapter class for Video's RecyclerView
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    // Constant key to store data in the intent
    public static final String VIDEO_ID = "video , id";
    public static final String VIDEO_TITLE = "video , title";
    public static final String VIDEO_DESCRIPTION = "video , description";
    public static final String VIDEO_IMAGE_URL = "video , image , url";

    List<VideoEntity> videoList;
    private Context context;

    // Constructor
    public VideoAdapter(Context context , List<VideoEntity> videoEntities) {
        this.videoList = videoEntities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View videoView = layoutInflater.inflate(R.layout.for_recyclerview , parent , false);

        return new ViewHolder(videoView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // make VideoEntity object based on the View
        final VideoEntity video = videoList.get(position);

        // setup the video 's title , description and Image thumbnail
        holder.txtVideoTitle.setText(video.getTitle());
        holder.txtVideoSummary.setText(video.getDescription());

        // use Picasso to load video's Thumbnail
        Picasso.get().load(video.getImageURL()).into(holder.videoImage);

        // case click on the View will play the video
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toVideoPlayer = new Intent(context , VideoPlayer.class);

                // put video information into the intent
                toVideoPlayer.putExtra(VIDEO_ID , video.getId());
                toVideoPlayer.putExtra(VIDEO_TITLE , video.getTitle());
                toVideoPlayer.putExtra(VIDEO_DESCRIPTION , video.getDescription());
                toVideoPlayer.putExtra(VIDEO_IMAGE_URL , video.getImageURL());
                // put information about the username
                toVideoPlayer.putExtra(SignIn.USERNAME_KEY , ((Activity)context).getIntent().getStringExtra(SignIn.USERNAME_KEY));
                // put the REQUEST_HISTORY to the intent to know from channel or from history
                // from channel will be null
                // from history will not be null
                toVideoPlayer.putExtra(FirstVideoScreen.REQUEST_HISTORY , ((Activity)context).getIntent().getStringExtra(FirstVideoScreen.REQUEST_HISTORY));

                context.startActivity(toVideoPlayer);
                ((Activity)context).overridePendingTransition(R.animator.push_left_in , R.animator.push_left_out);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView videoImage;
        public TextView txtVideoTitle;
        public TextView txtVideoSummary;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.videoImage);
            txtVideoTitle = itemView.findViewById(R.id.txtTitle);
            txtVideoSummary = itemView.findViewById(R.id.txtSummary);

            mView = itemView;

        }
    }

}

