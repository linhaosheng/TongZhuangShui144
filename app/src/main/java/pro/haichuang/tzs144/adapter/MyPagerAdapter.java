package pro.haichuang.tzs144.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

//如果数据量大，推荐使用 FragmentStatePagerAdapter   而 FragmentPagerAdapter 适用于静态数据和数量比较小得页面
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mViewList;
    private List<String> mTitleList;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> viewList, List<String> titleList) {
        super(fm);
        this.mViewList = viewList;
        this.mTitleList = titleList;
    }


    @Override
    public int getCount() {
        return mViewList.size();//页卡数
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);//页卡标题

    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }
}
