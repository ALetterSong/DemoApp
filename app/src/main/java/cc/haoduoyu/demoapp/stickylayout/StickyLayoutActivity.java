package cc.haoduoyu.demoapp.stickylayout;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.LinkedList;

import cc.haoduoyu.demoapp.R;

/**
 * http://blog.csdn.net/singwhatiwanna/article/details/25546871
 * Created by XP on 2016/3/1.
 */
public class StickyLayoutActivity extends AppCompatActivity implements StickyLayout.OnGiveUpTouchEventListener {
    private StickyLayout stickyLayout;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private LinkedList<String> listItems = null;

    private String[] mStrings = {"Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee", "Ffffff",
            "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm", "Nnnnnn",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickylayout);
        stickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);
        listView = (ListView) findViewById(R.id.list_view);
        listItems = new LinkedList<>();
        listItems.addAll(Arrays.asList(mStrings));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        stickyLayout.setOnGiveUpTouchEventListener(this);

    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (listView.getFirstVisiblePosition() == 0) {
            View view = listView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }
}
