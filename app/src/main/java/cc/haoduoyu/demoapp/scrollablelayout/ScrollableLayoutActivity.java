package cc.haoduoyu.demoapp.scrollablelayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/7/3.
 */
public class ScrollableLayoutActivity extends AppCompatActivity implements ScrollableHelper.ScrollableContainer {

    private RecyclerView recyclerView;
    private ScrollableLayout scrollLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollablelayout);
        scrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SimpleAdapter(this));

        scrollLayout.getHelper().setCurrentScrollableContainer(this);
    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }
}
