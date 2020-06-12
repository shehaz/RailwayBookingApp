package com.shehaz.railwaybookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shehaz.railwaybookingapp.R;
import com.shehaz.railwaybookingapp.adapters.TrainAdapter;
import com.shehaz.railwaybookingapp.models.Train;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class TrainListActivity extends AppCompatActivity {

    String dayOfWeek;
    private ArrayList<Train> mTrainList;
    private ArrayList<Train> filteredList;
    private RecyclerView mRecyclerView;
    private TrainAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);

        Intent intent1 = getIntent();
        dayOfWeek = intent1.getStringExtra("day");
        createTrainList();
        filterTheList();
        buildRecyclerView();
    }



    public void createTrainList()
    {
        mTrainList = new ArrayList<>();
        mTrainList.add(new Train("10001","Maveli Express","Monday"));
        mTrainList.add(new Train("10002","Malabar Express","Monday"));
        mTrainList.add(new Train("10003","Antyodaya Express","Monday"));
        mTrainList.add(new Train("10004","Netravati Express","Tuesday"));
        mTrainList.add(new Train("10005","Kannur Janashatabdi","Tuesday"));
        mTrainList.add(new Train("10006","Westcoast Superfast Express","Tuesday"));
        mTrainList.add(new Train("10007","Vivek Express","Wednesday"));
        mTrainList.add(new Train("10008","Kerala Sampark Kranti Express","Wednesday"));
        mTrainList.add(new Train("10009","Okha Express","Wednesday"));
        mTrainList.add(new Train("10010","Nizamuddin Express","Thursday"));
        mTrainList.add(new Train("10011","Kerala Express","Thursday"));
        mTrainList.add(new Train("10012","Azad Hind Express","Thursday"));
        mTrainList.add(new Train("10013","Rajdhani Express","Friday"));
        mTrainList.add(new Train("10014","Duronto Express","Friday"));
        mTrainList.add(new Train("10015","Garib Rath Express","Friday"));
        mTrainList.add(new Train("10016","Amritsar Express","Saturday"));
        mTrainList.add(new Train("10017","Gatimaan Express","Saturday"));
        mTrainList.add(new Train("10018","Mumbai Rajdhani Express","Saturday"));
        mTrainList.add(new Train("10019","Subidha Express","Sunday"));
        mTrainList.add(new Train("10020","Maharaja","Sunday"));
        mTrainList.add(new Train("10021","Venad Express","Sunday"));

    }
    //to show only those trains that provides service on the selected day
    private void filterTheList() {
        filteredList = new ArrayList<>();
        for (Train train: mTrainList
             ) {

            if(train.getServiceDay().toUpperCase().equals(dayOfWeek))
                filteredList.add(train);

        }
    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TrainAdapter(filteredList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new TrainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent listIntent = new Intent();
                listIntent.putExtra("train", filteredList.get(position));
                setResult(RESULT_OK, listIntent);
                finish();

            }
        });

    }


}