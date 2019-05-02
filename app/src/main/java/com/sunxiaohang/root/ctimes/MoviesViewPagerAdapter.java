package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sunxiaohang.root.ctimes.interfaces.PostExecuteUpdate;
import com.sunxiaohang.root.ctimes.pojo.Movies;
import com.sunxiaohang.root.ctimes.pojo.Role;
import com.sunxiaohang.root.ctimes.utils.ObtainImageDrawable;
import com.sunxiaohang.root.ctimes.utils.RequestJson;
import com.sunxiaohang.root.ctimes.utils.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;

public class MoviesViewPagerAdapter extends PagerAdapter implements PostExecuteUpdate {
    private Context context;
    private ArrayList<String> viewPagerString;
    private ArrayList<String> viewPagerRequestUrl = new ArrayList<>();
    private ArrayList<ArrayList<Movies>> allCatalogMovies = new ArrayList<>();
    private ArrayList<RecyclerView.Adapter> adapters = new ArrayList<>();
    private AVLoadingIndicatorView indicatorView;

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public MoviesViewPagerAdapter(Context context, ArrayList<String> viewPagerString,AVLoadingIndicatorView indicatorView) {
        this.context = context;
        this.viewPagerString = viewPagerString;
        viewPagerRequestUrl.add(Utils.REQUEST_MOVIES_URL_NOW_PLAYING);
        viewPagerRequestUrl.add(Utils.REQUEST_MOVIES_URL_COMING);
        viewPagerRequestUrl.add(Utils.REQUEST_MOVIES_URL_TOP);
        this.indicatorView = indicatorView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        View rootView = View.inflate(context,R.layout.subject_content,null);
        RecyclerView recyclerView = rootView.findViewById(R.id.subject_content_recycle_view);
        ArrayList moviesList = new ArrayList();
        allCatalogMovies.add(moviesList);
        new RequestJson(viewPagerRequestUrl.get(position),position,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        MoviesRecycleViewAdapter adapter = new MoviesRecycleViewAdapter(context,allCatalogMovies.get(position));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        container.addView(rootView);
        adapters.add(adapter);
        return rootView;
    }

    @Override
    public int getCount() {
        return viewPagerString.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void update(String resultArrayList, int position) {
        if(position == 0){
            allCatalogMovies.get(0).clear();
            allCatalogMovies.get(0).addAll(Utils.processMoviesJsonResult(resultArrayList));
            adapters.get(0).notifyDataSetChanged();
            indicatorView.hide();
        }else if(position == 1){
            allCatalogMovies.get(1).clear();
            allCatalogMovies.get(1).addAll(Utils.processMoviesJsonResult(resultArrayList));
            adapters.get(1).notifyDataSetChanged();
        }else if(position == 2){
            allCatalogMovies.get(2).clear();
            allCatalogMovies.get(2).addAll(Utils.processMoviesJsonResult(resultArrayList));
            adapters.get(2).notifyDataSetChanged();
        }
    }
}
class MoviesRecycleViewAdapter extends RecyclerView.Adapter{
    private ArrayList<Movies> moviesArrayList;
    private Context context;
    public MoviesRecycleViewAdapter(Context context,ArrayList<Movies> moviesArrayList) {
        this.moviesArrayList = moviesArrayList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.movies_item_layout,viewGroup,false);
        RecyclerView.ViewHolder holder = new MoviesRecycleViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MoviesRecycleViewHolder holder = (MoviesRecycleViewHolder) viewHolder;
        final Movies movies = moviesArrayList.get(i);
        holder.moviesName.setText(movies.getTitle());
        holder.moviesDetails.setText(movies.getGenres()+"/"+movies.getYears()+"/"+movies.getDuration());
        holder.ratingReader.setText(movies.getCollectionCount());
        holder.moviesScore.setText(movies.getRatingScore());
        new ObtainImageDrawable(movies.getImgUrl(),holder.moviesImageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View rootView = LayoutInflater.from(context).inflate(R.layout.movies_datails_dialog_layout,null,false);

                ImageView moviesImageView = rootView.findViewById(R.id.movies_image);
                TextView moviesName = rootView.findViewById(R.id.movies_name);
                TextView moviesDetails = rootView.findViewById(R.id.movies_details);
                TextView ratingReader = rootView.findViewById(R.id.rating_star_reader);
                TextView ratingScore = rootView.findViewById(R.id.rating_star_score);
                RecyclerView directorRecycleView = rootView.findViewById(R.id.directors_recycle_view);
                RecyclerView rolesRecycleView = rootView.findViewById(R.id.roles_recycle_view);
                moviesName.setText(movies.getTitle());
                moviesDetails.setText(movies.getGenres()+"/"+movies.getYears()+"/"+movies.getDuration());
                ratingReader.setText(movies.getCollectionCount());
                ratingScore.setText(movies.getRatingScore());
                new ObtainImageDrawable(movies.getImgUrl(),moviesImageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                directorRecycleView.setAdapter(new RoleRecycleViewAdapter(movies.getDirectors(),context));
                directorRecycleView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                rolesRecycleView.setAdapter(new RoleRecycleViewAdapter(movies.getRoles(),context));
                rolesRecycleView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                bottomSheetDialog.setContentView(rootView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }
    static class MoviesRecycleViewHolder extends RecyclerView.ViewHolder{
        final View rootView;
        final ImageView moviesImageView;
        final TextView moviesName;
        final TextView moviesDetails;
        final TextView moviesScore;
        final TextView ratingReader;
        public MoviesRecycleViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            moviesImageView = itemView.findViewById(R.id.movies_image);
            moviesName = itemView.findViewById(R.id.movies_name);
            moviesDetails = itemView.findViewById(R.id.movies_details);
            moviesScore = itemView.findViewById(R.id.rating_star_score);
            ratingReader = itemView.findViewById(R.id.rating_star_reader);
        }
    }
}
class RoleRecycleViewAdapter extends RecyclerView.Adapter{
    private ArrayList<Role> roles;
    private Context context;

    public RoleRecycleViewAdapter(ArrayList<Role> roles, Context context) {
        this.roles = roles;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.role_item_layout,viewGroup,false);
        RoleRecycleViewHolder holder = new RoleRecycleViewHolder(rootView);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        RoleRecycleViewHolder holder = (RoleRecycleViewHolder) viewHolder;
        Role role = roles.get(i);
        holder.textView.setText(role.getName());
        new ObtainImageDrawable(role.getAvatarsUrl(),holder.imageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
    }
    @Override
    public int getItemCount() {
        return roles.size();
    }
    static class RoleRecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public RoleRecycleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roles_image_view);
            textView = itemView.findViewById(R.id.roles_name_text_view);
        }
    }
}
