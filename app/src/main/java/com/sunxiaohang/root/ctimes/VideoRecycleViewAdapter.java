package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.sunxiaohang.root.ctimes.pojo.Video;
import com.sunxiaohang.root.ctimes.utils.ObtainVideoFirstFrame;
import java.util.ArrayList;

public class VideoRecycleViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Video> videoLists;

    public VideoRecycleViewAdapter(Context context, ArrayList<Video> videoLists) {
        this.context = context;
        this.videoLists = videoLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item,viewGroup,false);
        VideoRecycleViewHolder holder = new VideoRecycleViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final VideoRecycleViewHolder holder = (VideoRecycleViewHolder) viewHolder;
        if(videoLists.size() == 0)return;
        final Video video = videoLists.get(i);
        holder.videoName.setText(video.getName());
        holder.playCount.setText(video.getPlayCount()+"times");
        holder.videoView.setVideoURI(Uri.parse(video.getVideoUrl()));
        holder.singer.setText(video.getSinger());
        new ObtainVideoFirstFrame(video.getVideoUrl(),holder.videoViewFirstFrame).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        holder.videoPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.videoView.isPlaying()) {
                    holder.videoView.start();
                    holder.videoViewFirstFrame.setVisibility(View.GONE);
                    holder.videoPlayButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pause_white_24dp));
                }else {
                    holder.videoView.pause();
                    holder.videoPlayButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(videoLists.size() == 0)return 0;
        return videoLists.size();
    }
    static class VideoRecycleViewHolder extends RecyclerView.ViewHolder{
        final VideoView videoView;
        final ImageView videoViewFirstFrame;
        final ImageButton videoPlayButton;
        final TextView playCount;
        final TextView videoName;
        final TextView singer;
        public VideoRecycleViewHolder(View itemView) {
            super(itemView);
            videoPlayButton = itemView.findViewById(R.id.item_video_play_button);
            playCount = itemView.findViewById(R.id.item_video_play_times);
            videoName = itemView.findViewById(R.id.item_video_name);
            videoView = itemView.findViewById(R.id.video_view);
            videoViewFirstFrame = itemView.findViewById(R.id.video_view_first_frame);
            singer = itemView.findViewById(R.id.video_singer);
        }
    }
}
