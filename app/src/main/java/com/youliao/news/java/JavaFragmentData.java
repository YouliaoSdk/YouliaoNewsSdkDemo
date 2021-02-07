package com.youliao.news.java;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * JavaFragmentData，Fragment数据 实现Parcelable，可以将Fragment的状态保存下来，以便于恢复
 * Created by LuckyJayce on 2016/8/9.
 */
public class JavaFragmentData implements Parcelable {

    Bundle arguments = new Bundle();
    Fragment fragment;
    String tag;
    int containerViewId = View.NO_ID;

    public JavaFragmentData(String fragmentTag) {
        this.tag = fragmentTag;
    }

    public String getTag() {
        return tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    void setContainerViewId(int containerViewId) {
        this.containerViewId = containerViewId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.arguments);
        dest.writeString(this.tag);
    }

    protected JavaFragmentData(Parcel in) {
        this.arguments = in.readBundle();
        this.tag = in.readString();
    }

    public static final Creator<JavaFragmentData> CREATOR = new Creator<JavaFragmentData>() {
        @Override
        public JavaFragmentData createFromParcel(Parcel source) {
            return new JavaFragmentData(source);
        }

        @Override
        public JavaFragmentData[] newArray(int size) {
            return new JavaFragmentData[size];
        }
    };
}