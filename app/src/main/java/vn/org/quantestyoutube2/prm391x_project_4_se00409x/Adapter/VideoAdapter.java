package vn.org.quantestyoutube2.prm391x_project_4_se00409x.Adapter;

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

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.ConnectorAndPlayer.VideoPlayer;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.Entity.VideoEntity;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.R;
import vn.org.quantestyoutube2.prm391x_project_4_se00409x.SignIn;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    public static final String VIDEO_ID = "video , id";
    public static final String VIDEO_TITLE = "video , title";
    public static final String VIDEO_DESCRIPTION = "video , description";
    public static final String VIDEO_IMAGE_URL = "video , image , url";
    public static final int TO_PLAY_REQUEST_CODE = 10010;

    List<VideoEntity> videoList;
    private Context context;

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
        final VideoEntity video = videoList.get(position);

        holder.txtVideoTitle.setText(video.getTitle());
        holder.txtVideoSummary.setText(video.getDescription());

        Picasso.get().load(video.getImageURL()).into(holder.videoImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toVideoPlayer = new Intent(context , VideoPlayer.class);

                toVideoPlayer.putExtra(VIDEO_ID , video.getId());
                toVideoPlayer.putExtra(VIDEO_TITLE , video.getTitle());
                toVideoPlayer.putExtra(VIDEO_DESCRIPTION , video.getDescription());
                toVideoPlayer.putExtra(VIDEO_IMAGE_URL , video.getImageURL());
                toVideoPlayer.putExtra(SignIn.USERNAME_KEY , ((Activity)context).getIntent().getStringExtra(SignIn.USERNAME_KEY));

                ((Activity)context).startActivity(toVideoPlayer);

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

