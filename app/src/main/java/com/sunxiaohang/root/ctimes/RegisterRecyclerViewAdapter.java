package com.sunxiaohang.root.ctimes;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.sunxiaohang.root.ctimes.utils.Utils;

import java.util.List;

class RegisterRecyclerViewAdapter extends RecyclerView.Adapter{
    private String[] subjects;
    private List<String> subscribedSubject;
    public RegisterRecyclerViewAdapter(String[] subjects,List<String> registeredSubjects) {
        this.subjects = subjects;
        subscribedSubject = registeredSubjects;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.register_item_card,viewGroup,false);
        GridViewHolder holder = new GridViewHolder(rootView);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        GridViewHolder holder = (GridViewHolder) viewHolder;
        final int tempIndex =i;
        holder.textView.setText(subjects[i]);
        holder.textView.setTextColor(Color.parseColor(Utils.getRandomColor()));
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    subscribedSubject.add(subjects[tempIndex]);
                }else {
                    subscribedSubject.remove(subjects[tempIndex]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }
    static class GridViewHolder extends RecyclerView.ViewHolder{
        final TextView textView;
        final Switch aSwitch;
        public GridViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.register_text_view);
            aSwitch = itemView.findViewById(R.id.register_switch);
        }
    }
}
