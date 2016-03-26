package cc.haoduoyu.demoapp.autoscrollviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by XP on 2016/3/26.
 */
public class MyPageAdapter extends PagerAdapter {

    private List<Integer> list;
    private Context context;

    public MyPageAdapter(Context context, List<Integer> List) {
        this.list = List;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(context);
        item.setScaleType(ImageView.ScaleType.FIT_XY);

        if (position != -1) {
            item.setImageResource(list.get(position));
        }

        container.addView(item);
        return item;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
