package com.aaa.lsjdemo.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaa.lsjdemo.R;

/**
 * Created by Administrator on 2018/6/16 0016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    public MyAdapter(Context context){
        this.context= context;
    }

    @Override
    public MyHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder( MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
