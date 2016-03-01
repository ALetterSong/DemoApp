package cc.haoduoyu.demoapp.stickylayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.LinkedList;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/3/1.
 */
public class StickyLayoutActivity extends Activity implements StickyLayout.OnGiveUpTouchEventListener {
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

        listItems = new LinkedList<String>();
        listItems.addAll(Arrays.asList(mStrings));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

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
