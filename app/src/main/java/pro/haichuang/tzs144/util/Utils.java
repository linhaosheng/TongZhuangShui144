package pro.haichuang.tzs144.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
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

        Toast toast = Toast.makeText(MyApplication.getApplication(),
                message, Toast.LENGTH_LONG);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
        Glide.with(MyApplication.getApplication()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(50))).error(R.mipmap.avator).into(imageView);//四周都是圆角的圆角矩形图片
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
}
