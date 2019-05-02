package com.sunxiaohang.root.ctimes;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunxiaohang.root.ctimes.utils.Utils;

class GridRecyclerViewAdapter extends RecyclerView.Adapter{
    private String[] subjects;
    private LayoutInflater inflater;
    public GridRecyclerViewAdapter(String[] subjects) {
        this.subjects = subjects;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(inflater == null)inflater = LayoutInflater.from(viewGroup.getContext());
        View rootView = inflater.inflate(R.layout.item_card_view,viewGroup,false);
        GridViewHolder holder = new GridViewHolder(rootView);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GridViewHolder holder = (GridViewHolder) viewHolder;
        holder.textView.setText(subjects[i]);
        holder.textView.setTextSize((Utils.getRandomNumber(25)+5)*2);
        holder.textView.setTextColor(Color.parseColor(Utils.getRandomColor()));
    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }
    static class GridViewHolder extends RecyclerView.ViewHolder{
        final TextView textView;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text_view);
        }
    }
}
