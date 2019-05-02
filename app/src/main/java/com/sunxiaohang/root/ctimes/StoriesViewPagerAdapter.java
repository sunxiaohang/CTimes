package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sunxiaohang.root.ctimes.interfaces.PostExecuteUpdate;
import com.sunxiaohang.root.ctimes.pojo.News;
import com.sunxiaohang.root.ctimes.utils.RequestJson;
import com.sunxiaohang.root.ctimes.utils.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.List;

public class StoriesViewPagerAdapter extends PagerAdapter implements PostExecuteUpdate{
    private List<String> subscribedSubjects;
    private List<ArrayList<News>> newsListsLists;
    private ArrayList<StoriesRecycleViewAdapter> adapters;
    private AVLoadingIndicatorView indicatorView;

    public StoriesViewPagerAdapter(List<String> subscribedSubjects, AVLoadingIndicatorView indicatorView) {
        this.subscribedSubjects = subscribedSubjects;
        newsListsLists = new ArrayList<>();
        adapters = new ArrayList<>();
        this.indicatorView = indicatorView;
    }

    @Override
    public int getCount() {
        return subscribedSubjects.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        View rootView = View.inflate(context,R.layout.subject_content,null);
        RecyclerView recyclerView = rootView.findViewById(R.id.subject_content_recycle_view);
        ArrayList newsLists = new ArrayList();
        newsListsLists.add(newsLists);
        StoriesRecycleViewAdapter adapter = new StoriesRecycleViewAdapter(newsListsLists.get(position));
        new RequestJson(Utils.getRequestAddress(subscribedSubjects.get(position)),position,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        container.addView(rootView);
        adapters.add(adapter);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void update(String requestResult,int position) {
        if(newsListsLists.get(position).size()>5)return;
        newsListsLists.get(position).addAll(Utils.processResultToList(requestResult));
        adapters.get(position).notifyDataSetChanged();
        indicatorView.hide();
    }
}
