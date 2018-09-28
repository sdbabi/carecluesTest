package com.careclues.app.activity;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.careclues.app.R;
import com.careclues.app.adapter.DoctorListAdapter;
import com.careclues.app.constraints.Common;
import com.careclues.app.model.DataModel;
import com.careclues.app.model.DoctorModel;
import com.careclues.app.service.ApiInterface;
import com.careclues.app.service.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    RecyclerView doctorList;
    DoctorListAdapter doctorListAdapter;
    ProgressBar pBar;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    LinearLayoutManager linearLayoutManager;
    List<DataModel> docList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        initWidgets();
        //populateDoctorList();

    }

    private void initWidgets() {
        doctorList = findViewById(R.id.doctorList);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        doctorListAdapter = new DoctorListAdapter(MainActivity.this, docList);
        doctorList.setAdapter(doctorListAdapter);
        doctorList.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        pBar = findViewById(R.id.pBar);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateDoctorList();
            }
        });

        doctorList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling=true;

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling == true && (currentItems + scrollOutItems > totalItems)) {
                    isScrolling = false;
                    populateDoctorList();
                }
            }
        });
        populateDoctorList();
    }


    private void populateDoctorList() {

        if (Common.checkNetworkConnection(this)) {
            ApiInterface apiService =
                    ApiService.getClient().create(ApiInterface.class);
            swipeRefreshLayout.setRefreshing(true);
            Map<String, String> data = new HashMap<>();
            data.put("expand", "qualifications,reviews_count,specializations");
            data.put("page_size", "10");
            data.put("page_no", "1");

            Call<DoctorModel> retrofitApiCall = apiService.getDoctor(data);
            retrofitApiCall.enqueue(new Callback<DoctorModel>() {
                @Override
                public void onResponse(Call<DoctorModel> call, Response<DoctorModel> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.code() == 200) {
                        List<DataModel> doctorlist = response.body().getDataList();
                        if (doctorlist.size() != 0) {
                            if (Common.isDebug)
                                Toast.makeText(MainActivity.this, "List size is : " + doctorlist.size(), Toast.LENGTH_SHORT).show();

                            docList.addAll(doctorlist);
                            doctorListAdapter.notifyDataSetChanged();


                        } else {
                            Toast.makeText(MainActivity.this, "No doctors found", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(MainActivity.this, "List not Fetched", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DoctorModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "List not Fetched", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false).setTitle("Sorry...").setMessage("Please check your internet connection").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    if (Common.checkNetworkConnection(MainActivity.this)) {
                        try {
                            populateDoctorList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            AlertDialog dlg = builder.create();
            dlg.show();
        }

    }
}
