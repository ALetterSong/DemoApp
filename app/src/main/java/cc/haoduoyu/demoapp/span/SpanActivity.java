package cc.haoduoyu.demoapp.span;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.widget.GRecyclerView;

/**
 * Created by XP on 2016/1/27.
 */
public class SpanActivity extends AppCompatActivity {

    @Bind(R.id.spanRecyclerView)
    GRecyclerView mRecyclerView;

    SpanAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);

        ButterKnife.bind(this);
        mAdapter = new SpanAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        List<Span> spans = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Span span = new Span();
            span.setContent(i + " Nice to Meet You");
            spans.add(span);
        }

        mAdapter.setList(spans);
        mAdapter.notifyDataSetChanged();
    }
}
