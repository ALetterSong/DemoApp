package cc.haoduoyu.demoapp.dagger.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.dagger.injector.components.DaggerImageComponent;
import cc.haoduoyu.demoapp.dagger.injector.modules.ImageModule;
import cc.haoduoyu.demoapp.grecyclerview.GRecyclerView;

/**
 * a very simple dagger2 example
 * <p/>
 * Created by xiepan on 2016/9/14.
 */
public class DaggerActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView)
    GRecyclerView mRecyclerView;

    ImageAdapter mAdapter;

    @Inject
    List<String> urls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);

        DaggerImageComponent.builder()
                .imageModule(new ImageModule(this))
                .build()
                .inject(this);

        mAdapter = new ImageAdapter(this);
        mAdapter.setList(urls);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);


    }
}
