package com.youliao.news.java;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youliao.news.R;
import com.youliao.sdk.news.ui.NewsFragment;

public class JavaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private FragmentManager fragmentManager;

    public JavaRecyclerViewAdapter(Context context,FragmentManager fragmentManager2) {
        mContext = context;
        fragmentManager = fragmentManager2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i < 10) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_item_image, viewGroup, false);
            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_item_framelayout, viewGroup, false);
            return new FrameLayoutViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        if (viewHolder instanceof FrameLayoutViewHolder) {
            FrameLayoutViewHolder frameLayoutViewHolder = (FrameLayoutViewHolder) viewHolder;
            JavaFragmentData javaFragmentData = frameLayoutViewHolder.javaFragmentData;
            int containerViewId = frameLayoutViewHolder.itemView.getId();
            JavaFrameLayout frameLayout = (JavaFrameLayout) frameLayoutViewHolder.itemView;
            Fragment fragment = javaFragmentData.fragment;
            if (fragment == null) {
                javaFragmentData.setContainerViewId(containerViewId);
                fragment = getFragment(javaFragmentData);
                if (fragment.isAdded()) {
                    fragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
                }
                fragmentManager.beginTransaction().add(containerViewId, fragment, javaFragmentData.getTag()).commitNowAllowingStateLoss();
                javaFragmentData.setFragment(fragment);
            }
            frameLayout.setFragment((NewsFragment) fragment);
            fragment.setUserVisibleHint(true);
            fragment.setMenuVisibility(true);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        if (viewHolder instanceof FrameLayoutViewHolder) {
            FrameLayoutViewHolder frameLayoutViewHolder = (FrameLayoutViewHolder) viewHolder;
            Fragment fragment = frameLayoutViewHolder.javaFragmentData.getFragment();
            if (fragment != null) {
                fragment.setUserVisibleHint(false);
                fragment.setMenuVisibility(false);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ImageViewHolder) {
            ((ImageViewHolder) viewHolder).imageView.setBackgroundResource(R.drawable.restaurant);
        }
    }

    private Fragment getFragment(JavaFragmentData javaFragmentData) {
        if (javaFragmentData.fragment != null) {
            return javaFragmentData.fragment;
        }
        Fragment fragment = instantiate(javaFragmentData);
//        Fragment fragment = fragmentManager.findFragmentByTag(javaFragmentData.getTag());
//        if (fragment == null) {
//            fragment = instantiate(javaFragmentData);
//            fragment.setMenuVisibility(false);
//            fragment.setUserVisibleHint(false);
//        }
        return fragment;
    }

    private Fragment instantiate(JavaFragmentData javaFragmentData) {
        return NewsFragment.newInstance("news", true);
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    class FrameLayoutViewHolder extends RecyclerView.ViewHolder {
        private JavaFragmentData javaFragmentData;

        public FrameLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            javaFragmentData = new JavaFragmentData("NewsFragment");
        }
    }
}
