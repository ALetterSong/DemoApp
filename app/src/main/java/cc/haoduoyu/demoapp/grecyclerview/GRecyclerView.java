package cc.haoduoyu.demoapp.grecyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by XP on 2016/1/27.
 */
public class GRecyclerView extends RecyclerView {
    public GRecyclerView(Context context) {
        super(context);
        initRecyclerView(context);
    }

    public GRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRecyclerView(context);
    }

    public GRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRecyclerView(context);
    }

    /**
     * 初始化RecyclerView
     *
     * @param context context
     */
    private void initRecyclerView(Context context) {
        //初始化LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        //设置垂直布局
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        setLayoutManager(linearLayoutManager);
        //设置Item增加、移除动画
        setItemAnimator(new DefaultItemAnimator());
        //使RecyclerView保持固定的大小（如果可以确定每个item的高度是固定的，设置这个选项可以提高性能）
        setHasFixedSize(true);
    }
}
