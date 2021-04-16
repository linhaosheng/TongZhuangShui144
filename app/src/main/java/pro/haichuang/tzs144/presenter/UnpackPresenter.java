package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.json.JSONObject;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;

public class UnpackPresenter {

    private ILoadDataView iLoadDataView;

    public UnpackPresenter(ILoadDataView iLoadDataView){
        this.iLoadDataView = iLoadDataView;
    }

    public void unpack(String mainId,String unpackGoodsId,String unpackNum,String goodsId,String num){

        Map<String,Object> params = new ArrayMap<>();
        params.put("mainId",mainId);
        params.put("unpackGoodsId",unpackGoodsId);
        params.put("unpackNum",unpackNum);
        params.put("goodsId",goodsId);
        params.put("num",num);

        HttpRequestEngine.postRequest(ConfigUrl.UNPACK, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("result")==1){
                        iLoadDataView.successLoad("拆包成功");
                    }else {
                        String error = jsonObject.getString("message");
                        iLoadDataView.errorLoad(error);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iLoadDataView.errorLoad(error);
            }
        });
    }
}
