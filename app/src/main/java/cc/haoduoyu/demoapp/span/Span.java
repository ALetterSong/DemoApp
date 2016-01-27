package cc.haoduoyu.demoapp.span;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XP on 2016/1/27.
 */
public class Span implements Parcelable {

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    public Span() {
    }

    protected Span(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<Span> CREATOR = new Parcelable.Creator<Span>() {
        public Span createFromParcel(Parcel source) {
            return new Span(source);
        }

        public Span[] newArray(int size) {
            return new Span[size];
        }
    };
}

