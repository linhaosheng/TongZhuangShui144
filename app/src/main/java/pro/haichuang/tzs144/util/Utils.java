package pro.haichuang.tzs144.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.application.MyApplication;

public class Utils {

    private static final double EARTH_RADIUS = 6378137;//赤道半径
    public static final String YMDHMS_BREAK = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS_BREAK2 = "yyyy-MM-dd HH:mm";
    public static final String YMD = "yyyy-MM-dd";
    private static SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS_BREAK);// 格式化时间
    private static SimpleDateFormat sdf2 = new SimpleDateFormat(YMDHMS_BREAK2);// 格式化时间
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMD);// 格式化时间
    private static Gson gson;

    public static Gson gsonInstane() {
        if (gson == null) {
            synchronized (Utils.class){
                if (gson==null){
                    gson = new Gson();
                }
            }
        }
        return gson;
    }


    public static String formatSelectTime(Date date){
      return   simpleDateFormat.format(date);
    }

    public static String transformTime(Date date){
        String formatTime = sdf.format(date);
        return formatTime;
    }

    public static String transformTime2(Date date){
        String formatTime = sdf2.format(date);
        return formatTime;
    }


    /**
     * 像素转换
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 封装Toast
     * @param message
     */
    public static void showCenterTomast(String message) {

//        Toast toast = Toast.makeText(MyApplication.getApplication(),
//                message, Toast.LENGTH_LONG);
//        LinearLayout linearLayout = (LinearLayout) toast.getView();
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();

        // 传入 Activity 对象表示设置成局部的，不需要有悬浮窗权限
// 传入 Application 对象表示设置成全局的，但需要有悬浮窗权限
        ToastUtils.show(message);
    }

    public static void showCenterTomast(int message) {
        Toast toast = Toast.makeText(MyApplication.getApplication(),
                message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void showImage(ImageView imageView,String url){
        Glide.with(MyApplication.getApplication()).load(url).error(R.mipmap.bottled_water).into(imageView);
    }

    public static void showAvatorImage(ImageView imageView,String url){
        Glide.with(MyApplication.getApplication()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).error(R.mipmap.avator).into(imageView);
    }

    public static void showRectangleImage(ImageView imageView,String url){
        Glide.with(MyApplication.getApplication()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(50))).error(R.mipmap.bottled_water).into(imageView);//四周都是圆角的圆角矩形图片
    }

    public static void showTvImage(ImageView imageView,String url){
        Glide.with(MyApplication.getApplication()).load(url).error(R.mipmap.tv_bg).into(imageView);
    }

    public static void showExhitionDetailImage(ImageView imageView,String url){
        Glide.with(MyApplication.getApplication()).load(url).error(R.mipmap.exhibiton_bg).into(imageView);
    }

    public static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(35);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    public static String getCurrentPageName(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName();
    }

    public static float GetDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return (float) s;//单位米
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 拨打电话
     * @param phoneNum
     */
    public static void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        MyApplication.getApplication().startActivity(intent);
    }

    /**
     * 自动关闭软键盘
     *
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    /**
     * 判断允许通知，是否已经授权
     * 返回值为true时，通知栏打开，false未打开。
     *@param context 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 跳转到app的设置界面--开启通知
     * @param context
     */
    public static void goToNotificationSetting(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 获取当前时间的上两个个月
     * @return
     */
    public static String formatLastMonthTime(){

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return formatSelectTime(m);
    }

}
