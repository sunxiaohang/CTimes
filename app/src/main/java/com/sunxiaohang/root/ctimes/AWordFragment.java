package com.sunxiaohang.root.ctimes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.sunxiaohang.root.ctimes.interfaces.PostExecuteUpdate;
import com.sunxiaohang.root.ctimes.utils.RequestJson;
import com.sunxiaohang.root.ctimes.utils.Utils;
import java.util.ArrayList;
import java.util.HashSet;

public class AWordFragment extends Fragment implements PostExecuteUpdate {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private TextView randomTextView;
    private TextView randomFromTextView;
    private ImageButton collectionImageButton;
    private ArrayList<String> aWordsArrayList;
    private FloatingActionButton floatingActionButton;
    private View changeClickView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("AWordSharePreference",getContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        HashSet<String> referenceHashSet = (HashSet<String>) sharedPreferences.getStringSet("AWordShare",null);
        if (referenceHashSet!=null){
            aWordsArrayList = new ArrayList<>(referenceHashSet);
        }else {
            aWordsArrayList = new ArrayList<>();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_aword, container, false);
        recyclerView = rootView.findViewById(R.id.a_word_recycle_view);
        randomTextView = rootView.findViewById(R.id.random_a_word_generator);
        changeClickView = rootView.findViewById(R.id.change_click_view);
        collectionImageButton = rootView.findViewById(R.id.btn_collection_aWord);
        floatingActionButton = rootView.findViewById(R.id.save_sharePreference);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putStringSet("AWordShare",new HashSet(aWordsArrayList));
                editor.apply();
            }
        });
        final PostExecuteUpdate obtainAWordTask = this;
        changeClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RequestJson(Utils.AWORDRANDOMREQUESTADDRESS,0,obtainAWordTask).execute();
                collectionImageButton.setImageDrawable(container.getResources().getDrawable(R.drawable.ic_star_black_stroke_24dp));
            }
        });
        randomFromTextView = rootView.findViewById(R.id.random_a_word_generator_from);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        final AWardRecycleViewAdapter aWardRecycleViewAdapter = new AWardRecycleViewAdapter(aWordsArrayList);
        recyclerView.setAdapter(aWardRecycleViewAdapter);
        collectionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectionString = randomTextView.getText().toString()+"&&&"+randomFromTextView.getText().toString();
                if(!aWordsArrayList.contains(collectionString))aWordsArrayList.add(collectionString);
                collectionImageButton.setImageDrawable(container.getResources().getDrawable(R.drawable.ic_star_black_24dp));
                aWardRecycleViewAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public void update(String newsLists, int position) {
        String[] award = Utils.processAWordJsonResult(newsLists).split("&&&");
        randomTextView.setText(award[0]);
        randomFromTextView.setText(award[1]);
    }
}
class AWardRecycleViewAdapter extends RecyclerView.Adapter{
    private ArrayList<String> aWordArrayList;
    private final AWardRecycleViewAdapter adapter;
    public AWardRecycleViewAdapter(ArrayList<String> aWordArrayList) {
        this.aWordArrayList = aWordArrayList;
        adapter = this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.a_word_item,viewGroup,false);
        AWardRecycleViewHolder holder = new AWardRecycleViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,final int i) {
        AWardRecycleViewHolder holder = (AWardRecycleViewHolder) viewHolder;
        String[] result = aWordArrayList.get(i).split("&&&");
        holder.hitokotoTextView.setText(result[0]);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aWordArrayList.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
        if(result[1]!="")holder.fromTextView.setText("--"+result[1]);
        else holder.fromTextView.setText("--unKnown");
    }
    @Override
    public int getItemCount() {
        return aWordArrayList.size();
    }
    static class AWardRecycleViewHolder extends RecyclerView.ViewHolder{
        final TextView hitokotoTextView;
        final TextView fromTextView;
        final ImageButton imageButton;
        public AWardRecycleViewHolder(View itemView) {
            super(itemView);
            hitokotoTextView = itemView.findViewById(R.id.a_word_hitokoto_text_view);
            fromTextView = itemView.findViewById(R.id.a_word_from_text_view);
            imageButton = itemView.findViewById(R.id.btn_unCollection_aWord);
        }
    }
}
