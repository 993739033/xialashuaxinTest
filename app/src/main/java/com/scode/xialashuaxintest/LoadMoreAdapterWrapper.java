package com.scode.xialashuaxintest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 声明装饰类
 *
 * Created by 知らないのセカイ on 2017/6/7.
 */

public class LoadMoreAdapterWrapper extends BaseRecyAdapter<String> {
    private BaseRecyAdapter mAdapter;//被装饰的类
    private int pagePosition=0;
    private int pageSize=10;
    private OnLoad onLoad;
    private Boolean hasMore=true;//是否还有数据

    public LoadMoreAdapterWrapper(BaseRecyAdapter mAdapter, OnLoad onLoad) {
        this.mAdapter = mAdapter;//获取被包装类
        this.onLoad = onLoad;//实例化接口用于回调
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            if(!hasMore){
                hasMore=true;//以为不会进入oncreateviewholder方法中所以直接在gettype中重置
                return !hasMore ? R.layout.list_item_loading : R.layout.list_item_failure;
            }
            return hasMore ? R.layout.list_item_loading : R.layout.list_item_failure;
        }else{
            return mAdapter.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //分别返回相应的ViewHolder
       if (viewType==R.layout.list_item_failure){
           //已经绑定过一次的view第二次不会再绑定？
           return new NomoreItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_failure,parent,false));
       }else if (viewType==R.layout.list_item_loading){
           return new LoadingItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_loading,parent,false));
       }else{
           return mAdapter.createViewHolder(parent, viewType);//调用被装饰者的viewholder
       }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreAdapter.ContentVH){
            mAdapter.onBindViewHolder(holder,position);//调用被装饰者的onbindviewholder方法
        }else if (holder instanceof LoadingItemVH){
            RequsetData();
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+1;//这里一定要调用mAdapter的方法
    }



    private void RequsetData(){
        if (onLoad != null) {
            onLoad.load(pagePosition, pageSize, new CallBack() {
                @Override
                public void onSuccess() {
                    pagePosition=(pagePosition+1)*pageSize;
                    hasMore=true;
                    notifyDataSetChanged();//这个可以重新绑定view
                }
                @Override
                public void onFailure() {
                    hasMore=false;
                    notifyDataSetChanged();//这个可以重新绑定view
                }
            });
        }
    }


    //读取中的footview
    static class LoadingItemVH extends RecyclerView.ViewHolder {
        public LoadingItemVH(View itemView) {
            super(itemView);
        }
    }

    //读取失败的footview
    static class NomoreItemVH extends RecyclerView.ViewHolder {
        public NomoreItemVH(View itemView) {
            super(itemView);
        }
    }


    public interface OnLoad{
        void load(int pageposition, int pagesize, CallBack callBack);
    }
    public interface CallBack{
        //成功时回调
        void onSuccess();

        //失败时回调
        void onFailure();
    }
}
