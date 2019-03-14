package com.teknologi.labakaya.inmotiontest.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teknologi.labakaya.inmotiontest.R;
import com.teknologi.labakaya.inmotiontest.adapter.UsersAdapter;
import com.teknologi.labakaya.inmotiontest.api.response.ApiResponse;
import com.teknologi.labakaya.inmotiontest.api.response.UserResponse;
import com.teknologi.labakaya.inmotiontest.entity.User;
import com.teknologi.labakaya.inmotiontest.presenter.Presenter;
import com.teknologi.labakaya.inmotiontest.presenter.iPresenterResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* Created by Imran Zulfri Pulungan
* 14 December 2018*/

public class MainActivity extends AppCompatActivity implements iPresenterResponse, SearchView.OnQueryTextListener {

    UsersAdapter usersAdapter;
    Presenter mPresenter;

    @BindView(R.id.rv_users)
    protected RecyclerView rvUsersData;
    @BindView(R.id.btn_load_more)
    protected FloatingActionButton btnLoadMore;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.layout_not_found)
    protected LinearLayout layoutNotFound;
    @BindView(R.id.img_err)
    protected ImageView imgErr;
    @BindView(R.id.tv_err)
    protected TextView tvErr;

    private List<User> mUsers;

    int load = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new Presenter(this, this);
        progressBar.setVisibility(View.VISIBLE);
        mPresenter.getDataUser(load);
        initEvent();
    }

    private void initEvent() {
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                load+=10;
                mPresenter.getDataUser(load);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<User> filteredUserList = filterUsers(mUsers, query);
        usersAdapter.setUser(filteredUserList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<User> filterUsers(List<User> users, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<User> filteredModelList = new ArrayList<>();
        for (User user : users) {
            final String text = user.getLogin().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(user);
            }
        }
        return filteredModelList;
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        UserResponse users = (UserResponse) response;
        if (!users.incomplete_results){
            if (users.total_count > 0){
                mUsers = users.items;
                usersAdapter = new UsersAdapter(this, mUsers);
                rvUsersData.setLayoutManager(new LinearLayoutManager(this));
                rvUsersData.setAdapter(usersAdapter);
                rvUsersData.setHasFixedSize(true);

                rvUsersData.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (!recyclerView.canScrollVertically(1)) {
                            btnLoadMore.setVisibility(View.VISIBLE);
                        }else{
                            btnLoadMore.setVisibility(View.GONE);
                        }
                    }
                });
                layoutNotFound.setVisibility(View.GONE);
                rvUsersData.setVisibility(View.VISIBLE);
            }else{
                layoutNotFound.setVisibility(View.VISIBLE);
                rvUsersData.setVisibility(View.GONE);
            }
        }else{
            tvErr.setText(R.string.server_error);
            layoutNotFound.setVisibility(View.VISIBLE);
            rvUsersData.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void doFail(String message) {
    }

    @Override
    public void doFailClient(int message) {
    }

    @Override
    public void doConnectionError() {
    }
}
