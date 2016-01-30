package cc.haoduoyu.demoapp.grecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by XP on 2016/1/27.
 */
public class GRecyclerViewHolder extends RecyclerView.ViewHolder {

    //用于保存findViewById加载过的view
    private final SparseArray<View> views;
    private View convertView;

    public GRecyclerViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
    }

    //findViewById过的view会被缓存下来，以供下次find相同的view
    public <T extends View> T findViewById(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
