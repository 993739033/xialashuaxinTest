package com.scode.xialashuaxintest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyview);
        final LoadMoreAdapter adapter=new LoadMoreAdapter();
        LoadMoreAdapterWrapper adapterWrapper=new LoadMoreAdapterWrapper(adapter, new LoadMoreAdapterWrapper.OnLoad() {
            @Override
            public void load(int pageposition, int pagesize, final LoadMoreAdapterWrapper.CallBack callBack) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count++%3==1){
                            callBack.onFailure();
                            return;
                        }
                        List<String> loadData = new ArrayList<>();
                        for (int i=0;i<30;i++) {
                            loadData.add("loadData:" + i);
                        }
                        adapter.appendData(loadData);
                        callBack.onSuccess();
                    }
                }, 2000);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterWrapper);

    }
}
