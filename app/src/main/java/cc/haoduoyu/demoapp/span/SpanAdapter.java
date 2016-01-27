package cc.haoduoyu.demoapp.span;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.widget.GRecyclerViewAdapter;
import cc.haoduoyu.demoapp.widget.GRecyclerViewHolder;

/**
 * Created by XP on 2016/1/27.
 */
public class SpanAdapter extends GRecyclerViewAdapter {

    private Context mContext;

    public SpanAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_span_content};
    }

    @Override
    public void onBindRecycleViewHolder(GRecyclerViewHolder viewHolder, int position) {
//        holder.contentView.setText(spans.get(position).getContent());//normal
        Span span = getItem(position);
        TextView labelView = viewHolder.findViewById(R.id.span_label_tv);
        TextView contentView = viewHolder.findViewById(R.id.span_content_tv);

        setSpanContent(labelView, contentView, span.getContent(), position);
    }

    @Override
    public int getRecycleViewItemType(int position) {
        return 0;//itemType需要时写在实体类中span.getType();
    }

    public void setSpanContent(TextView labelTv, TextView contentTv, String content, int position) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        int start = 2;
        int end = "Nice".length() + start;
        switch (position) {
            case 0:
                labelTv.setText("URLSpan");
                ssb.setSpan(new URLSpan("https://github.com/bigggge"), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);
                contentTv.setMovementMethod(LinkMovementMethod.getInstance());
                contentTv.setHighlightColor(Color.YELLOW);
                break;
            case 1:
                labelTv.setText("SuperscriptSpan + SubscriptSpan");
                ssb.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.setSpan(new SubscriptSpan(), end + 1, end + 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);

                break;
            case 2:
                labelTv.setText("UnderlineSpan + StrikethroughSpan");
                ssb.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.setSpan(new StrikethroughSpan(), end + 1, end + 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);
                break;
            case 3:
                labelTv.setText("BackgroundColorSpan");
                ssb.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);
                break;
            case 4:
                labelTv.setText("ScaleXSpan");
                ssb.setSpan(new ScaleXSpan(2.0f), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);
                break;
            case 5:
                labelTv.setText("StyleSpan");
                ssb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.setSpan(new StyleSpan(Typeface.ITALIC), end + 1, end + 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                contentTv.setText(ssb);
                break;
            case 6:
                labelTv.setText("ImageSpan");
                ssb.setSpan(new ImageSpan(mContext, R.mipmap.ic_launcher, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);
                break;
            case 7:
                labelTv.setText("TypefaceSpan ( Examples include \"monospace\", \"serif\", and \"sans-serif\". )");
                ssb.setSpan(new TypefaceSpan("serif"), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                contentTv.setText(ssb);
                break;
            default:
                labelTv.setText("???");
                contentTv.setText("???");

        }
    }
}
