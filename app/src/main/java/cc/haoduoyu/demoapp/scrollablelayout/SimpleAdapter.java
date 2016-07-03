package cc.haoduoyu.demoapp.scrollablelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/6/29.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.IViewHolder> {

    private final List<String> mItems = new ArrayList<>();

    public SimpleAdapter(Context context) {
        mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.items)));
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items, parent,
                false);
        IViewHolder itemViewHolder = new IViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final IViewHolder holder, int position) {
        holder.textView.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class IViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;

        public IViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
