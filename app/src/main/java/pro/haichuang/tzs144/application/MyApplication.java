package pro.haichuang.tzs144.application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.kongzue.dialog.util.DialogSettings;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;
import pro.bilibili.boxing.BoxingMediaLoader;
import pro.bilibili.boxing.loader.IBoxingMediaLoader;
import pro.haichuang.tzs144.view.CustomLoadMoreView;
import pro.haichuang.tzs144.util.BoxingGlideLoader;

import static com.kongzue.dialog.util.DialogSettings.STYLE.STYLE_IOS;


public class MyApplication extends Application {

    private static Context context;
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        this.application = this;
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        DialogSettings.style = STYLE_IOS;
        CrashReport.initCrashReport(getApplicationContext(), "01918d3053", true);
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());

        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        // 初始化SDK
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
    public static Context getInstance() {
        return context;
    }

    public static Application getApplication() {
        return application;
    }

}
