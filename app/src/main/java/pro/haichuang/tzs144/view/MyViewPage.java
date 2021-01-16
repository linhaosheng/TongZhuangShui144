package pro.haichuang.tzs144.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPage extends ViewPager {


    public MyViewPage(@NonNull Context context) {
        super(context);
    }

    public MyViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager) {   //判断当前的View是不是ViewPager
            Log.e("view_pager", "canScroll: ------------------1    "+dx );
            int currentItem = ((ViewPager) v).getCurrentItem();   //当前的条目
            int countItem = ((ViewPager) v).getAdapter().getCount();  //总的条目
            Log.e("view_pager", "canScroll: ------------------1 "+dx );
            if ((currentItem == (countItem - 1) && dx < 0) || (currentItem == 0 && dx > 0)) {   //判断当前条目以及滑动方向
                Log.e("dong", "canScroll perform");
                Log.e("view_pager", "canScroll: ------------------2     "+dx  );
                return false;
            }
            return true;
        }
        Log.e("view_pager", "canScroll: ------------------3     "+dx );
        return super.canScroll(v, checkV, dx, x, y);

    }
}
