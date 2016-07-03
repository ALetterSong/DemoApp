package cc.haoduoyu.demoapp.itemtouchhelper.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by XP on 2016/6/29.
 */
public interface ITouchHelperViewHolder {
    /**
     * {@link SimpleItemTouchHelperCallBack#onSelectedChanged(RecyclerView.ViewHolder, int)}
     */
    void onItemSelected();

    /**
     * {@link SimpleItemTouchHelperCallBack#clearView(RecyclerView, RecyclerView.ViewHolder)}
     */
    void onItemClear();
}
