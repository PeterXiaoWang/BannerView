package com.afun.bannerviewdemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final int MSG_AUTO_FLIP = 1001;
    private static final int AUTO_FLIP_INTERVAL = 5000;

    //多添加的2个item
    protected int FAKE_ITEM_COUNT = 0;

    protected List<View> mImageViews = new ArrayList<>();
    protected ViewPager mPager;
    protected long mLastTouchUpTime;
    private int pageIndex;
    private Handler mHandler;

    public BannerView(Context context) {
        super(context);
        initView();
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoFlip();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoFlip();
    }


    private void initView() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_AUTO_FLIP:
                        autoFlip();
                        startAutoFlip();
                        break;

                    default:
                        break;
                }
            }
        };

        mPager = new MyPager(getContext());
        mPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mPager.setAdapter(new MyPagerAdapter());
        mPager.addOnPageChangeListener(this);

        addView(mPager, 0);
    }

    public void updateBannerView(ArrayList<View> bannerViews, boolean isLoopable) {
        FAKE_ITEM_COUNT = isLoopable ? 2 : 0;

        if (bannerViews == null) {
            mImageViews.clear();
        } else {
            mImageViews = (ArrayList) bannerViews.clone();
        }

        mPager.getAdapter().notifyDataSetChanged();
        mPager.setCurrentItem(isLoopable && mImageViews.size() > 1 ? 1 : 0);
    }

    void autoFlip() {
        // 距离用户最近一次触摸松开的时间在自动滑动间隔之内
        if ((SystemClock.elapsedRealtime() - mLastTouchUpTime) < AUTO_FLIP_INTERVAL) {
            return;
        }

        int position = mPager.getCurrentItem() + 1;
        if (position >= mPager.getAdapter().getCount()) { // 滑到底就再滑到开头
            position = 0;
        }
        mPager.setCurrentItem(position);
    }

    public void startAutoFlip() {
        stopAutoFlip();
        if (mImageViews.size() < 2) {
            return;
        }

        mHandler.sendEmptyMessageDelayed(MSG_AUTO_FLIP, AUTO_FLIP_INTERVAL);
    }

    public void stopAutoFlip() {
        mHandler.removeMessages(MSG_AUTO_FLIP);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        pageIndex = i;
        final int imageViewSize = mImageViews.size();
        if (FAKE_ITEM_COUNT == 2 && imageViewSize > 1) {
            if (i == 0) {// 当视图在第一个时，将页面号设置为图片的最后一张
                pageIndex = imageViewSize - FAKE_ITEM_COUNT;
            } else if (i == mImageViews.size() - 1) {// 当视图在最后一个时,将页面号设置为图片的第一张
                pageIndex = 1;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if (i == ViewPager.SCROLL_STATE_IDLE) {
            if (mPager.getCurrentItem() != pageIndex) {
                mPager.setCurrentItem(pageIndex, false);
            }
        }
    }

    public class MyPager extends ViewPager {
        private GestureDetector mGestureDetector;

        public MyPager(Context context) {
            this(context, null);
        }

        public MyPager(Context context, AttributeSet attrs) {
            super(context, attrs);

            mGestureDetector = new GestureDetector(context, new MyGestureListener());
            setFadingEdgeLength(0);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            mLastTouchUpTime = SystemClock.elapsedRealtime();

            if (mGestureDetector.onTouchEvent(ev)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            return super.dispatchTouchEvent(ev);
        }

        class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (Math.abs(distanceY) < Math.abs(distanceX)) {
                    return true;
                }
                return false;
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mImageViews.get(position).getParent() == null) {
                container.addView(mImageViews.get(position));
            }

            return mImageViews.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
