package cc.haoduoyu.demoapp.grecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XP on 2016/1/27.
 */
public abstract class GRecyclerViewAdapter extends RecyclerView.Adapter<GRecyclerViewHolder> {

    /**
     * 返回RecyclerView加载的布局Id数组
     *
     * @return 布局Id数组
     */
    public abstract int[] getItemLayouts();

    /**
     * onBindRecycleViewHolder
     *
     * @param viewHolder viewHolder
     * @param position   position
     */
    public abstract void onBindRecycleViewHolder(GRecyclerViewHolder viewHolder, int position);

    /**
     * 多布局写判断逻辑
     * 单布局可以不写
     *
     * @param position Item position
     * @return 布局Id数组中的index
     */
    public abstract int getRecycleViewItemType(int position);

    private ArrayList mList;


    public GRecyclerViewAdapter() {
        this.mList = new ArrayList();
    }

    //创建新View，被LayoutManager所调用,viewType由getItemViewType提供
    @Override
    public GRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0) return null;
        if (this.getItemLayouts() == null) return null;
        int[] layoutIds = this.getItemLayouts();
        if (layoutIds.length < 1) return null;

        int itemLayoutId;
        if (layoutIds.length == 1) {
            itemLayoutId = layoutIds[0];
        } else {
            itemLayoutId = layoutIds[viewType];
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, null);
        return new GRecyclerViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(GRecyclerViewHolder holder, int position) {
        try {
            this.onBindRecycleViewHolder(holder, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    /**
     * 根据position获取ItemType
     *
     * @param position Item position
     * @return 默认itemType等于0
     */
    @Override
    public int getItemViewType(int position) {
        return this.getRecycleViewItemType(position);
    }

    public <T> T getItem(int position) {
        return (T) this.mList.get(position);
    }

    public <T> T getItemByPosition(int position) {
        return this.getItem(position);
    }

    public void setList(List list) {
        this.mList.clear();
        if (list == null) return;
        this.mList.addAll(list);
    }

    public List getList() {
        return this.mList;
    }

    public int getListSize() {
        return this.mList.size();
    }
}
