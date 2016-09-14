package cc.haoduoyu.demoapp.dagger.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.grecyclerview.GRecyclerViewAdapter;
import cc.haoduoyu.demoapp.grecyclerview.GRecyclerViewHolder;
import cc.haoduoyu.demoapp.utils.Log;

/**
 * Created by xiepan on 2016/9/14.
 */
public class ImageAdapter extends GRecyclerViewAdapter {
    private Context mContext;

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_dagger};
    }

    @Inject
    public ImageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindRecycleViewHolder(GRecyclerViewHolder viewHolder, int position) {
        String url = getItem(position);
        TextView textView = viewHolder.findViewById(R.id.textView);
        ImageView imageView = viewHolder.findViewById(R.id.imageView);
        textView.setText(url);
        Picasso.with(mContext).load(url).resize(150,150).into(imageView);
        Log.d("ImageAdapter", "loading...");
    }

    @Override
    public int getRecycleViewItemType(int position) {
        return 0;
    }
}
