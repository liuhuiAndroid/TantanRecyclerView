package com.enjoy.tantan;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class SlideCardLayoutManager extends RecyclerView.LayoutManager {

    /**
     * 搬运 LinearLayoutManager
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 实现 ItemView 布局
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 先移除所有view
        removeAllViews();
        // 在布局之前，将所有的子 View 先 Detach 掉，放入到 Scrap 缓存中
        detachAndScrapAttachedViews(recycler);
        int bottomPosition;
        int itemCount = getItemCount();
        if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            // 布局了四张卡片 --- 4，5，6，7
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }
        for (int i = bottomPosition; i < itemCount; i++) {
            // 复用
            View view = recycler.getViewForPosition(i);
            // 将 ItemView 加入到 RecyclerView 中
            addView(view);
            // 测量 ItemView
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            // 布局 ---draw -- onDraw ,onDrawOver, onLayout
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else {
                    // 最下面的那个view 与前一个view 布局一样
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleX(1 - CardConfig.SCALE_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }
        }
    }
}
