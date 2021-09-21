package pro.haichuang.tzs144.activity;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.kongzue.dialog.v3.BottomMenu;
import com.next.easynavigation.view.EasyNavigationBar;
import com.kongzue.dialog.v3.WaitDialog;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pro.haichuang.tzs144.R;

import pro.haichuang.tzs144.fragment.AccountFragment;
import pro.haichuang.tzs144.fragment.ClientFragment;
import pro.haichuang.tzs144.fragment.InventoryFragment;
import pro.haichuang.tzs144.fragment.MainActivityPresenter;
import pro.haichuang.tzs144.fragment.OrderFragment;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import pro.haichuang.tzs144.view.ShowMoreDialog;

/**
 * 主页面
 */
@RuntimePermissions
public class MainActivity extends BaseActivity {


    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    private boolean isLocation = false;
    private MainActivityPresenter mainActivityPresenter;

    private String[] tabText = {"订单", "库存", "账务", "客户"};
    private int[] selectIcon = {R.mipmap.order_p, R.mipmap.inventory_p, R.mipmap.account_p, R.mipmap.client_p};
    private int[] normalIcon = {R.mipmap.order_n, R.mipmap.inventory_n, R.mipmap.account_n, R.mipmap.client_n};

    private List<Fragment> fragments;
    private List<String> tabList;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        WaitDialog.show(this, "加载中...");
        Observable.just("")
                .delay(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        WaitDialog.dismiss();
                    }
                });
    }

    @Override
    protected void setUpData() {
        MainActivityPermissionsDispatcher.allplyPermissionWithPermissionCheck(this);
        mainActivityPresenter = new MainActivityPresenter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainActivityPresenter.findKhTypes();
                mainActivityPresenter.getAllClient();
                mainActivityPresenter.findAreas();
                mainActivityPresenter.findStockMainList();
            }
        }).start();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Utils.showCenterTomast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA})
    public void allplyPermission() {
        initView();
    }

    public void initView() {
        fragments = new ArrayList<>();
        fragments.add(new OrderFragment());
        fragments.add(new InventoryFragment());
        fragments.add(new AccountFragment());
        fragments.add(new ClientFragment());

        tabList = new ArrayList<>();
        tabList.add("直接销售");
        if (Config.AUTHORITY.contains("5")){
            tabList.add("补录订单");
        }
        tabList.add("开押");
        tabList.add("退押");

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .centerImageRes(R.mipmap.tab)
                .centerTextStr("")
                .centerLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .centerLayoutBottomMargin(0)
                .centerAlignBottom(true)
                .fragmentManager(getSupportFragmentManager())
                .setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabSelectEvent(View view, int position) {
                        return false;
                    }

                    @Override
                    public boolean onTabReSelectEvent(View view, int position) {
                        return false;
                    }

                })
                .setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
                    @Override
                    public boolean onCenterTabSelectEvent(View view) {
                        ShowMoreDialog showMoreDialog = new ShowMoreDialog(MainActivity.this);
                        showMoreDialog.show(getSupportFragmentManager(), "");
                        return false;
                    }
                })
                .canScroll(false)
                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 申请权限被拒绝时
     */
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA})
    void onWriteAndReadDenied() {
        Utils.showCenterTomast("写入和读取以及摄像头权限被拒，有可能导致无法使用");
    }

    /**
     * 申请权限被拒绝并勾选不再提醒时
     */
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA})
    void onInstallNeverAskAgain() {
        Utils.showCenterTomast("写入和读取以及摄像头权限被拒，有可能导致无法使用");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
