package cc.haoduoyu.demoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.haoduoyu.demoapp.asynctask.AsyncTaskActivity;
import cc.haoduoyu.demoapp.base.Demo;
import cc.haoduoyu.demoapp.canvas.CanvasActivity;
import cc.haoduoyu.demoapp.device.DeviceActivity;
import cc.haoduoyu.demoapp.dialog.DialogActivity;
import cc.haoduoyu.demoapp.downloadservice.DownloadActivity;
import cc.haoduoyu.demoapp.dropdownlistview.DropDownListViewActivity;
import cc.haoduoyu.demoapp.rxjava.RxJavaActivity;
import cc.haoduoyu.demoapp.sort.SortActivity;
import cc.haoduoyu.demoapp.span.SpanActivity;
import cc.haoduoyu.demoapp.stickylayout.StickyLayoutActivity;
import cc.haoduoyu.demoapp.utils.ToastUtils;
import cc.haoduoyu.demoapp.viewdraghelper.ViewDragHelperActivity;
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
        checkPermission();
        initData();
        setupRecyclerView();
    }


    private void initData() {
        mDemos = new ArrayList<>();
        mDemos.add(new Demo("DropDownListViewActivity", new Intent(this, DropDownListViewActivity.class)));
        mDemos.add(new Demo("SpanActivity", new Intent(this, SpanActivity.class)));
        mDemos.add(new Demo("DeviceActivity", new Intent(this, DeviceActivity.class)));
        mDemos.add(new Demo("CanvasActivity", new Intent(this, CanvasActivity.class)));
        mDemos.add(new Demo("WebViewActivity", new Intent(this, WebViewActivity.class)));
        mDemos.add(new Demo("DownloadActivity", new Intent(this, DownloadActivity.class)));
        mDemos.add(new Demo("SortActivity", new Intent(this, SortActivity.class)));
        mDemos.add(new Demo("AsyncTaskActivity", new Intent(this, AsyncTaskActivity.class)));
        mDemos.add(new Demo("ViewDragHelperActivity", new Intent(this, ViewDragHelperActivity.class)));
        mDemos.add(new Demo("StickyLayoutActivity", new Intent(this, StickyLayoutActivity.class)));
        mDemos.add(new Demo("DialogActivity", new Intent(this, DialogActivity.class)));
        mDemos.add(new Demo("RxJavaActivity", new Intent(this, RxJavaActivity.class)));

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

    private void checkPermission() {
        final List<String> permissionsList = new ArrayList<>();
        //需要添加的权限
        addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE);
        addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE);
        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                    1);
        } else {

        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            Map<String, Integer> perms = new HashMap<>();
            //在只有某些权限需要处理时防止NullPointerException，因为这些权限已经被允许不在permissions中
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);

            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // 检查
            if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                //Denied

            }
        }
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
            ToastUtils.showToast(MainActivity.this, getString(R.string.select));
            Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
