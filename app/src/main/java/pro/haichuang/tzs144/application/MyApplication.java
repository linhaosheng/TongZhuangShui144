package pro.haichuang.tzs144.application;

import android.app.Application;
import android.content.Context;

import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.kongzue.dialog.v2.DialogSettings;
import com.tencent.bugly.crashreport.CrashReport;

import pro.bilibili.boxing.BoxingMediaLoader;
import pro.bilibili.boxing.loader.IBoxingMediaLoader;
import pro.haichuang.tzs144.view.CustomLoadMoreView;
import pro.haichuang.tzs144.util.BoxingGlideLoader;


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
        DialogSettings.style = DialogSettings.STYLE_IOS;
        DialogSettings.tip_theme = DialogSettings.THEME_LIGHT;
        DialogSettings.use_blur = true;
        CrashReport.initCrashReport(getApplicationContext(), "98a2abb9a8", true);
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());

    }
    public static Context getInstance() {
        return context;
    }

    public static Application getApplication() {
        return application;
    }

}
