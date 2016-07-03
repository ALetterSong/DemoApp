package cc.haoduoyu.demoapp.itemtouchhelper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.itemtouchhelper.helper.ITouchHelperAdapter;
import cc.haoduoyu.demoapp.itemtouchhelper.helper.ITouchHelperViewHolder;
import cc.haoduoyu.demoapp.itemtouchhelper.helper.OnStartDragListener;

/**
 * Created by XP on 2016/6/29.
 */
public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.IViewHolder>
        implements ITouchHelperAdapter {

    private final List<String> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;

    public RecyclerViewListAdapter(Context context, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
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

        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class IViewHolder extends RecyclerView.ViewHolder implements
            ITouchHelperViewHolder {

        public final TextView textView;
        public final ImageView handleView;

        public IViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
