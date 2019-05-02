package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxiaohang.root.ctimes.pojo.Music;
import com.sunxiaohang.root.ctimes.utils.ACache;
import com.sunxiaohang.root.ctimes.utils.ObtainImageDrawable;
import com.sunxiaohang.root.ctimes.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MusicRecycleViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Music> musicArrayList;
    private MediaPlayer musicMediaPlayer = new MediaPlayer();
    private Bundle bundle = new Bundle();

    public MusicRecycleViewAdapter(Context context, ArrayList<Music> musicArrayList) {
        this.context = context;
        this.musicArrayList = musicArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        System.out.println("TAG:music recycle view holder");
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_item,viewGroup,false);
        RecyclerView.ViewHolder holder = new MusicRecycleViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        System.out.println("TAG:onBind view holder");
        final MusicRecycleViewHolder holder = (MusicRecycleViewHolder) viewHolder;
        final Music music = musicArrayList.get(i);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MusicPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("music",music);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.musicName.setText(music.getName());
        holder.musicSinger.setText(music.getSinger());
        holder.musicDurationTextView.setText(Utils.processSecondTime(music.getMusicDuration()));
        new ObtainImageDrawable(music.getImageUrl(),holder.musicImageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
//        LyricsRecycleViewAdapter lyricsRecycleViewAdapter = new LyricsRecycleViewAdapter(context,bundle);
//        new ObtainLyricsTask(music.getLrcUrl(),lyricsRecycleViewAdapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        holder.musicPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicMediaPlayer.isPlaying()){
                    musicMediaPlayer.stop();
                    holder.musicPlayButton.setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_white_24dp));
                }else {
                    musicMediaPlayer.reset();
                    try {
                        musicMediaPlayer.setDataSource(music.getMusicUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new MusicPlayAsyncTask(musicMediaPlayer).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                    holder.musicPlayButton.setImageDrawable(context.getDrawable(R.drawable.ic_pause_white_24dp));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("TAG:get Item Count:"+musicArrayList.size());
        if(musicArrayList.size()>0)return musicArrayList.size();
        else return 0;
    }
    /**
     * release media player resource
     * */
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if(musicMediaPlayer.isPlaying())musicMediaPlayer.stop();
        musicMediaPlayer.reset();
        musicMediaPlayer.release();
    }
    /**
     * obtain lyrics
     * */
    class ObtainLyricsTask extends AsyncTask<Void,String,String>{
        private String requestAddress;
        private RecyclerView.Adapter updateRecycleViewAdapter;

        public ObtainLyricsTask(String requestAddress, RecyclerView.Adapter updateRecycleViewAdapter) {
            this.requestAddress = requestAddress;
            this.updateRecycleViewAdapter = updateRecycleViewAdapter;
        }

        @Override
        protected String doInBackground(Void... voids) {
            ACache writeACache = Utils.getACacheInstance();
            String cacheResult;
            if((cacheResult=writeACache.getAsString(Utils.processLyricsRequestAddress(requestAddress))) != null) {
                return cacheResult;
            }
            URL url = null;
            StringBuffer buffer = new StringBuffer();
            try {
                url = new URL(requestAddress);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5*1000);
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(reader);
                String temp="";
                while ((temp=bufferedReader.readLine())!=null){
                    buffer.append(temp);
                }
                inputStream.close();
                reader.close();
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = buffer.toString();
            if(result != null) {
                writeACache.put(Utils.processLyricsRequestAddress(requestAddress), result, 1 * ACache.TIME_DAY);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String o) {
            bundle.putAll(Utils.processMusicLyrics(o));
            updateRecycleViewAdapter.notifyDataSetChanged();
        }
    }

    static class MusicRecycleViewHolder extends RecyclerView.ViewHolder{
        final View rootView;
        final ImageView musicImageView;
        final ImageButton musicPlayButton;
        final TextView musicName;
        final TextView musicSinger;
        final TextView musicDurationTextView;
        final ImageButton musicMore;
        public MusicRecycleViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            musicImageView = itemView.findViewById(R.id.music_item_image);
            musicName = itemView.findViewById(R.id.music_item_name);
            musicPlayButton = itemView.findViewById(R.id.music_item_play_button);
            musicDurationTextView = itemView.findViewById(R.id.music_item_duration);
            musicSinger = itemView.findViewById(R.id.music_item_singer);
            musicMore = itemView.findViewById(R.id.item_more);
        }
    }
}
class LyricsRecycleViewAdapter extends RecyclerView.Adapter{
    private ArrayList<String> lyricsList;
    private Bundle bundle;
    //duration list has never been used

    public LyricsRecycleViewAdapter(Context context, Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyrics_item_single_line,viewGroup,false);
        RecyclerView.ViewHolder holder = new LyricsRecycleViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(lyricsList == null)return;
        System.out.println("TAG:onBindViewHolder:"+lyricsList.size());
        LyricsRecycleViewHolder holder = (LyricsRecycleViewHolder) viewHolder;
        holder.textView.setText(lyricsList.get(i));
    }

    @Override
    public int getItemCount() {
        if(bundle.isEmpty())return 0;
        else if(lyricsList == null ||lyricsList.size() == 0){
            lyricsList = bundle.getStringArrayList("lyricsList");
            System.out.println("TAG:bundle update length:"+lyricsList.size());
            return lyricsList.size();
        }else{
            System.out.println("TAG:bundle update length:"+lyricsList.size());
            return lyricsList.size();
        }
    }
    static class LyricsRecycleViewHolder extends RecyclerView.ViewHolder{
        final TextView textView;
        public LyricsRecycleViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_lyrics_display_text);
        }
    }
}
class MusicPlayAsyncTask extends AsyncTask<Void,Integer,Integer> {
    private MediaPlayer mediaPlayer;

    public MusicPlayAsyncTask(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    protected Integer doInBackground(Void[] object) {
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        while (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() != -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mediaPlayer.getCurrentPosition();
        }
        return -1;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}