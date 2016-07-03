package cc.haoduoyu.demoapp.itemtouchhelper.helper;

import android.support.v7.widget.RecyclerView;

/**
 * 将ItemTouchHelper.Callback回调方法传递出去的接口
 *
 * Created by XP on 2016/6/29.
 */
public interface ITouchHelperAdapter {

    /**
     * {@link SimpleItemTouchHelperCallBack#onMove(RecyclerView, RecyclerView.ViewHolder, RecyclerView.ViewHolder)}
     *
     * @param fromPosition
     * @param toPosition
     * @return
     */

    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * {@link SimpleItemTouchHelperCallBack#onSwiped(RecyclerView.ViewHolder, int)}
     *
     * @param position
     */
    void onItemDismiss(int position);
}
