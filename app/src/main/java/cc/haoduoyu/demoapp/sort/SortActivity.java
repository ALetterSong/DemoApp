package cc.haoduoyu.demoapp.sort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/2/1.
 */
public class SortActivity extends AppCompatActivity {

    @Bind(R.id.sortTv1)
    TextView sortTv1;
    @Bind(R.id.sortTv2)
    TextView sortTv2;
    @Bind(R.id.sortTv3)
    TextView sortTv3;
    @Bind(R.id.sortTv4)
    TextView sortTv4;
    final int MAX = 5000;
    Integer sort[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.selection)
    void selection() {
        sort = new Integer[MAX];
        for (int i = 0; i < MAX; i++) {
            sort[i] = (int) (Math.random() * 10000);
        }
        long time = System.currentTimeMillis();
        Log.i("selection", show(sort));
        Sort.selection(sort);
        Log.i("selection", show(sort));
        sortTv1.setText("selection排序时间: " + (System.currentTimeMillis() - time) + show(sort));
    }

    @OnClick(R.id.insertion)
    void insertion() {
        sort = new Integer[MAX];
        for (int i = 0; i < MAX; i++) {
            sort[i] = (int) (Math.random() * 10000);
        }
        long time = System.currentTimeMillis();
        Log.i("insertion", show(sort));
        Sort.insertion(sort);
        Log.i("insertion", show(sort));
        sortTv2.setText("insertion排序时间: " + (System.currentTimeMillis() - time) + show(sort));
    }

    @OnClick(R.id.bubble)
    void bubble() {
        sort = new Integer[MAX];
        for (int i = 0; i < MAX; i++) {
            sort[i] = (int) (Math.random() * 10000);
        }
        long time = System.currentTimeMillis();
        Log.i("bubble", show(sort));
        Sort.bubble(sort);
        Log.i("bubble", show(sort));
        sortTv3.setText("bubble排序时间: " + (System.currentTimeMillis() - time) + show(sort));
    }

    @OnClick(R.id.quick)
    void quick() {
        sort = new Integer[MAX];
        for (int i = 0; i < MAX; i++) {
            sort[i] = (int) (Math.random() * 10000);
        }
        long time = System.currentTimeMillis();
        Log.i("quick", show(sort));
        Sort.quick(sort);
        Log.i("quick", show(sort));
        sortTv4.setText("quick排序时间: " + (System.currentTimeMillis() - time) + show(sort));
    }

    private String show(Comparable[] a) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < a.length / 1000; i++) {
            if (i == 0) {
                stringBuilder.append("  数组：");
            }
            stringBuilder.append(a[i]).append("  ");
        }
        return String.valueOf(stringBuilder);
    }

}
