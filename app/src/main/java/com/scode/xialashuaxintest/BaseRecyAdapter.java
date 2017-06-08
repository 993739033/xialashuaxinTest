package com.scode.xialashuaxintest;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 知らないのセカイ on 2017/6/7.
 */

public abstract class BaseRecyAdapter<T> extends RecyclerView.Adapter {
    protected List<T> dataSet = new ArrayList<>();//用于存储数据

    public void updataData(List data) {//更新数据
        if (dataSet != null&&!dataSet.isEmpty()) {//判断list不为空引用且不为空
            dataSet.clear();
            appendData(data);
        }else{
            appendData(data);
        }
    }

    public void appendData(List data) {//添加数据到原有数据的下面
        if (dataSet!=null){
            dataSet.addAll(data);
        }
    }

    public List<T> getDataSet(){//返回当前的数据
        return dataSet;
    }

    @Override
    public int getItemCount() {//获取当前数据的长度

        return dataSet.size();//内容加footview？？
    }
}
