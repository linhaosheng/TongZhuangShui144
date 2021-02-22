package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AllocationRecordModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class AllocationRecordActivityPresenter {

    private ILoadDataView iLoadDataView;

    public AllocationRecordActivityPresenter(ILoadDataView iLoadDataView){
       this.iLoadDataView = iLoadDataView;
    }

    /**
     * [实时库存]查询调拨记录
     * @param page
     */
    public void findAllotGoodsLog(int page){

        Map<String,Object>params = new ArrayMap<>();
        params.put("page",page);
        HttpRequestEngine.postRequest(ConfigUrl.FIND_ALLOT_GOODS_LOG, params, new HttpRequestResultListener() {
            @Override
            public void start() {
              iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                AllocationRecordModel allocationRecordModel = Utils.gsonInstane().fromJson(result, AllocationRecordModel.class);
                if (allocationRecordModel.getResult()==1){
                    iLoadDataView.successLoad(allocationRecordModel.getData());
                }else {
                    iLoadDataView.errorLoad("加载失败");
                }
            }

            @Override
            public void error(String error) {
              iLoadDataView.errorLoad(error);
            }
        });
    }
}
