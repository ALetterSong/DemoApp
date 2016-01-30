package cc.haoduoyu.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.haoduoyu.demoapp.base.Demo;
import cc.haoduoyu.demoapp.canvas.CanvasActivity;
import cc.haoduoyu.demoapp.device.DeviceActivity;
import cc.haoduoyu.demoapp.dropdownlistview.DropDownListViewActivity;
import cc.haoduoyu.demoapp.span.SpanActivity;
import cc.haoduoyu.demoapp.utils.ToastUtils;
import cc.haoduoyu.demoapp.webview.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ArrayList<Demo> mDemos;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setupRecyclerView();
    }


    private void initData() {
        mDemos = new ArrayList<>();
        mDemos.add(new Demo("DropDownListViewActivity", new Intent(this, DropDownListViewActivity.class)));
//        mDemos.add(new Demo("RxJavaActivity", new Intent(this, RxJavaActivity.class)));
        mDemos.add(new Demo("SpanActivity", new Intent(this, SpanActivity.class)));
        mDemos.add(new Demo("DeviceActivity", new Intent(this, DeviceActivity.class)));
        mDemos.add(new Demo("CanvasActivity", new Intent(this, CanvasActivity.class)));
        mDemos.add(new Demo("WebViewActivity", new Intent(this, WebViewActivity.class)));



    }


    private void setupRecyclerView() {
        mAdapter = new MyAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//!
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setListener(new OnClickItemListener() {
            @Override
            public void onTouch(View v, TextView titleView, TextView enterView, Demo demo) {
                if (demo == null) return;
                if (v == titleView) {
                    ToastUtils.showToast(MainActivity.this, demo.title);
                } else if (v == enterView) {
                    startActivity(demo.intent);
                }
            }
        });

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private OnClickItemListener listener;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.demo = mDemos.get(position);
            holder.titleTv.setText(mDemos.get(position).title);
        }

        @Override
        public int getItemCount() {
            return mDemos.size();
        }

        public void setListener(OnClickItemListener onClickItemListener) {
            this.listener = onClickItemListener;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @Bind(R.id.tv_title)
            TextView titleTv;
            @Bind(R.id.tv_enter)
            TextView enterTv;
            Demo demo;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                titleTv.setOnClickListener(this);
                enterTv.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
//                startActivity(mDemos.get(getAdapterPosition()).intent);
                listener.onTouch(v, titleTv, enterTv, demo);
            }
        }
    }

    interface OnClickItemListener {
        void onTouch(View v, TextView titleView, TextView enterView, Demo demo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
