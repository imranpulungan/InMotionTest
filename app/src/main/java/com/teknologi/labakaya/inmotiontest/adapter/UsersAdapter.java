package com.teknologi.labakaya.inmotiontest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teknologi.labakaya.inmotiontest.R;
import com.teknologi.labakaya.inmotiontest.entity.User;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class UsersAdapter extends
        RecyclerView.Adapter<UsersAdapter.MyViewHolder>{

    Context context;
    List<User> usersList;

    public UsersAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.productName.setText(usersList.get(position).getLogin());
    }

    public void setUser(List<User> usersList){
        this.usersList = usersList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


    static final class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name)
        TextView productName;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}