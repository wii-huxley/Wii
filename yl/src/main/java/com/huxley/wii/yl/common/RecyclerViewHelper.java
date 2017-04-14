package com.huxley.wii.yl.common;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.huxley.wii.yl.R;
import com.zhy.adapter.recyclerview.CommonAdapter;

/**
 * Created by huxley on 2017/3/20.
 */
public class RecyclerViewHelper {

    public static void setList(RecyclerView recyclerView, CommonAdapter adapter, final Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        setListSpan(recyclerView, adapter, context.getResources().getDimension(R.dimen.dp_8_p));
    }

    public static void setGrid(RecyclerView recyclerView, CommonAdapter adapter, final Context context, final int spanCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        setSpanCount(recyclerView, adapter, spanCount, context.getResources().getDimension(R.dimen.dp_8_p));
        recyclerView.setAdapter(adapter);
    }

    private static void setSpanCount(RecyclerView recyclerView, final CommonAdapter adapter, final int spanCount, final float spanWidth) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childLayoutPosition = parent.getChildLayoutPosition(view);
                int childCount = adapter.getItemCount();
                float elevation = view.findViewById(R.id.card_view).getElevation();
                if (childLayoutPosition % spanCount == 0) {
                    outRect.left = (int) (spanWidth - elevation);
                    outRect.right = (int) (spanWidth - (elevation * 2));
                } else if (childLayoutPosition % spanCount == (spanCount - 1)) {
                    outRect.right = (int) (spanWidth - elevation);
                } else {
                    outRect.right = (int) (spanWidth - (elevation * 2));
                }
                if (childLayoutPosition < spanCount) {
                    outRect.top = (int) (spanWidth - elevation);
                    outRect.bottom = (int) (spanWidth - (elevation * 2));
                } else if (childLayoutPosition >= ((childCount / spanCount) * spanCount)) {
                    outRect.bottom = (int) (spanWidth - elevation);
                } else {
                    outRect.bottom = (int) (spanWidth - (elevation * 2));
                }
            }
        });
    }

    private static void setListSpan(RecyclerView recyclerView, final CommonAdapter adapter, final float spanWidth) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childLayoutPosition = parent.getChildLayoutPosition(view);
                float elevation = view.findViewById(R.id.card_view).getElevation();
                if (childLayoutPosition == 0) {
                    outRect.top = (int) (spanWidth - elevation);
                    outRect.bottom = (int) (spanWidth - (elevation * 2));
                } else if (childLayoutPosition == (adapter.getItemCount() - 1)) {
                    outRect.bottom = (int) (spanWidth - elevation);
                } else {
                    outRect.bottom = (int) (spanWidth - (elevation * 2));
                }
                outRect.left = (int) (spanWidth - elevation);
                outRect.right = (int) (spanWidth - elevation);
            }
        });
    }
}
