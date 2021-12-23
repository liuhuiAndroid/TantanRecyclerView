package com.enjoy.tantan;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoy.tantan.adapter.UniversalAdapter;

import java.util.List;

public class SlideCallback extends ItemTouchHelper.SimpleCallback {

    private UniversalAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> dataList;

    public SlideCallback(UniversalAdapter<SlideCardBean> adapter, List<SlideCardBean> dataList) {
        super(0, 15);
        this.adapter = adapter;
        this.dataList = dataList;
    }

    /**
     * 当用户拖拽列表某个 item 时会回调
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * 当用户滑动列表某个 item 时会回调
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        SlideCardBean remove = dataList.remove(viewHolder.getLayoutPosition());
        dataList.add(0, remove);
        adapter.notifyDataSetChanged();// onMeasure, onlayout
    }

    // onDraw
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = distance / maxDistance;
        if (fraction > 1) {
            fraction = 1;
        }
        // 显示的个数  4个
        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View view = recyclerView.getChildAt(i);
            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                }
            }
        }
    }

    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 1500;
    }
}
