package pro.haichuang.tzs144.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    // the sliding page switch
    private boolean isSlidingEnable = true ;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }


    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写此函数
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return  this.isSlidingEnable;
    }
    //重写此函数
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isSlidingEnable;
    }

    public void setSlidingEnable(boolean slidingEnable) {
        isSlidingEnable = slidingEnable;
    }
}
