package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunxiaohang.root.ctimes.interfaces.PostExecuteUpdate;
import com.sunxiaohang.root.ctimes.pojo.Book;
import com.sunxiaohang.root.ctimes.pojo.Music;
import com.sunxiaohang.root.ctimes.pojo.Video;
import com.sunxiaohang.root.ctimes.utils.RequestJson;
import com.sunxiaohang.root.ctimes.utils.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaViewPagerAdapter extends PagerAdapter implements PostExecuteUpdate{
    private final int MEDIAVIEWPAGERCOUNT = 3;
    private AVLoadingIndicatorView loadingIndicatorView;
    private RecyclerView.Adapter mediaAdapters[] = new RecyclerView.Adapter[3];
    private ArrayList<Book> bookLists = new ArrayList<>();
    private ArrayList<Music> musicLists = new ArrayList<>();
    private ArrayList<Video> videoLists = new ArrayList<>();
    public MediaViewPagerAdapter(AVLoadingIndicatorView loadingIndicatorView) {
        this.loadingIndicatorView = loadingIndicatorView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return MEDIAVIEWPAGERCOUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();
        if(position == 0){
            View rootView = View.inflate(context,R.layout.book_subject_content,null);
            Button fictionButton = rootView.findViewById(R.id.fiction_book_switcher);
            Button nonfictionButton = rootView.findViewById(R.id.nonfiction_book_switcher);
            final PostExecuteUpdate updater = this;
            fictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new RequestJson(Utils.REQUEST_BOOKS_URL+"虚构类"+Utils.REQUEST_BOOKS_URL_KEY,0,updater).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                }
            });
            nonfictionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new RequestJson(Utils.REQUEST_BOOKS_URL+"非虚构类"+Utils.REQUEST_BOOKS_URL_KEY,0,updater).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                }
            });
            RecyclerView mediaContentRecycleView = rootView.findViewById(R.id.subject_content_recycle_view);
            new RequestJson(Utils.REQUEST_BOOKS_URL+"虚构类"+Utils.REQUEST_BOOKS_URL_KEY,0,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
            BookOverviewRecycleViewAdapter adapter = new BookOverviewRecycleViewAdapter(context,bookLists);
            mediaAdapters[0] = adapter;
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            mediaContentRecycleView.setLayoutManager(layoutManager);
            mediaContentRecycleView.setNestedScrollingEnabled(false);
            mediaContentRecycleView.setAdapter(adapter);
            container.addView(rootView);
            return rootView;
        }else if(position == 1){
            View rootView = View.inflate(context,R.layout.subject_content,null);
            RecyclerView musicRecycleView = rootView.findViewById(R.id.subject_content_recycle_view);
            new RequestJson(Utils.TOPMUSICREQUESTADDRESS,1,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
            MusicRecycleViewAdapter musicRecycleViewAdapter = new MusicRecycleViewAdapter(context,musicLists);
            mediaAdapters[1] = musicRecycleViewAdapter;
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            musicRecycleView.setLayoutManager(layoutManager);
            musicRecycleView.setNestedScrollingEnabled(false);
            musicRecycleView.setAdapter(musicRecycleViewAdapter);
            container.addView(rootView);
            return rootView;
        }else{
            View rootView = View.inflate(context,R.layout.subject_content,null);
            RecyclerView videoRecycleView = rootView.findViewById(R.id.subject_content_recycle_view);
            new RequestJson(Utils.TOPMVLISTREQUESTADDRESS,2,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
            VideoRecycleViewAdapter adapter = new VideoRecycleViewAdapter(context,videoLists);
            mediaAdapters[2] = adapter;
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            videoRecycleView.setLayoutManager(layoutManager);
            videoRecycleView.setNestedScrollingEnabled(false);
            videoRecycleView.setAdapter(adapter);
            container.addView(rootView);
            return rootView;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
    // request json has obtain a result
    @Override
    public void update(String resultJson, int position) {
        if(position == 0) {
            bookLists.clear();
            if(bookLists.size()>0)return;
            bookLists.addAll(Utils.processBookJsonResult(resultJson));
            mediaAdapters[0].notifyDataSetChanged();
            loadingIndicatorView.hide();
        }
        if(position == 1) {
            loadingIndicatorView.hide();
            if(musicLists.size()>0)return;
            musicLists.clear();
            musicLists.addAll(Utils.processMusicJsonResult(resultJson));
            mediaAdapters[1].notifyDataSetChanged();
        }
        if(position == 2) {
            loadingIndicatorView.hide();
            if(videoLists.size()>0)return;
            videoLists.clear();
            videoLists.addAll(Utils.processVideoJsonResult(resultJson));
            mediaAdapters[2].notifyDataSetChanged();
        }

    }
}
