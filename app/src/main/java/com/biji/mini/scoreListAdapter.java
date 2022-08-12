package com.biji.mini;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biji.mini.database.insertSHigh;

import java.util.List;

public class scoreListAdapter extends RecyclerView.Adapter<scoreListAdapter.scoreViewHolder> {
    private final Context context;
    private List<insertSHigh> scoreList;

    public scoreListAdapter(Context context){ this.context =context;}
    @SuppressLint("NotifyDataSetChanged")
    public void setScoreList(List<insertSHigh> list){
        this.scoreList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public scoreListAdapter.scoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_score,parent,false);
        return new scoreListAdapter.scoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull scoreListAdapter.scoreViewHolder holder, int position) {
        holder.score_recycle_text.setText(this.scoreList.get(position).scorestring);
    }

    @Override
    public int getItemCount() {
        if(scoreList != null){
            return scoreList.size();
        }
        return 0;
    }
    public class scoreViewHolder extends RecyclerView.ViewHolder{
        TextView score_recycle_text;
        public scoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_recycle_text =itemView.findViewById(R.id.score_recycle_text);
        }
    }
}
