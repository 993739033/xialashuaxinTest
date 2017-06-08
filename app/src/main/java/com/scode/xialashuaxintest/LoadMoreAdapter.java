package com.scode.xialashuaxintest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 知らないのセカイ on 2017/6/7.
 */

public class LoadMoreAdapter extends BaseRecyAdapter<String> {
    @Override
    public ContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_content, parent,false);
        return new ContentVH(view) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ContentVH)holder).textView.setText(getDataSet().get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item_content;
    }

     static class ContentVH extends RecyclerView.ViewHolder {
        public TextView textView;
        public ContentVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_content_tv);
        }
    }
}
