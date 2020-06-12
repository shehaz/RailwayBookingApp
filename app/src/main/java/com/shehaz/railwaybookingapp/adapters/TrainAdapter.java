package com.shehaz.railwaybookingapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shehaz.railwaybookingapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shehaz.railwaybookingapp.models.Train;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {
    private ArrayList<Train> mTrainList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class TrainViewHolder extends RecyclerView.ViewHolder {
        public TextView mNoView;
        public TextView mNameView;
        public TextView mDayView;
        public TrainViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mNoView = itemView.findViewById(R.id.trainNo);
            mNameView = itemView.findViewById(R.id.trainName);
            mDayView = itemView.findViewById(R.id.serviceDay);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public TrainAdapter(ArrayList<Train> trainList) {
        mTrainList = trainList;
    }
    @Override
    public TrainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item_layout, parent, false);
        TrainViewHolder tvh = new TrainViewHolder(v, mListener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TrainViewHolder holder, int position) {
        Train currentTrain = mTrainList.get(position);
        holder.mNoView.setText("Train No : "+currentTrain.getTrainNo());
        holder.mNameView.setText(currentTrain.getTrainName());
        holder.mDayView.setText("Service Day : "+currentTrain.getServiceDay());
    }

    @Override
    public int getItemCount() {
        return mTrainList.size();
    }
}