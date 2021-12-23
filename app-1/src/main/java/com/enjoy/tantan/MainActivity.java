package com.enjoy.tantan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enjoy.tantan.adapter.UniversalAdapter;
import com.enjoy.tantan.adapter.ViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UniversalAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new SlideCardLayoutManager());

        mDatas = SlideCardBean.initDatas();
        adapter = new UniversalAdapter<SlideCardBean>(this, mDatas, R.layout.item_swipe_card) {
            @Override
            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
                viewHolder.setText(R.id.tvName, slideCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, slideCardBean.getPostition() + "/" + mDatas.size());
                Glide.with(MainActivity.this)
                        .load(slideCardBean.getUrl())
                        .into((ImageView) viewHolder.getView(R.id.iv));
            }
        };
        recyclerView.setAdapter(adapter);
        // 初始化数据
        CardConfig.initConfig(this);

        SlideCallback slideCallback = new SlideCallback(adapter, mDatas);
        ItemTouchHelper helper = new ItemTouchHelper(slideCallback);
        helper.attachToRecyclerView(recyclerView);
    }
}
