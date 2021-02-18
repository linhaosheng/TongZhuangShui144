package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountOrderModel;
import pro.haichuang.tzs144.model.ClientEvent;
import pro.haichuang.tzs144.model.ClientListModel;
import pro.haichuang.tzs144.model.ClientTrendModel;
import pro.haichuang.tzs144.model.RealAccountEvent;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Utils;

public class ClientFragmentPresenter {

    private ILoadDataView iLoadDataView;

    public ClientFragmentPresenter(ILoadDataView mILoadDataView){
          this.iLoadDataView = mILoadDataView;
    }

    /**
     * [客户]列表统计
     */
    public void countKh(){

        HttpRequestEngine.postRequest(ConfigUrl.COUNT_KH, null, new HttpRequestResultListener() {
            @Override
            public void start() {
                iLoadDataView.startLoad();
            }

            @Override
            public void success(String result) {
                 try {
                     ClientTrendModel clientTrendModel = Utils.gsonInstane().fromJson(result, ClientTrendModel.class);
                     if (clientTrendModel!=null){
                         if (clientTrendModel.getResult()==1){
                             iLoadDataView.successLoad(clientTrendModel);
                         }else {
                             iLoadDataView.errorLoad("加载错误");
                         }
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

    /**
     * [客户]获取客户列表 列表搜索
     * @param khName
     * @param startTime
     * @param endTime
     * @param khTypeId
     * @param khStatus
     * @param page
     */
    public void findKhList(String khName,String startTime,String endTime,String khTypeId,String khStatus,int page){

        Map<String,Object>params = new ArrayMap<>();
        params.put("khName",khName);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("khTypeId",khTypeId);
        params.put("khStatus",khStatus);
        params.put("page",page);

        HttpRequestEngine.postRequest(ConfigUrl.FIND_KHLIST, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                ClientListModel clientListModel = Utils.gsonInstane().fromJson(result, ClientListModel.class);
                if (clientListModel.getResult()==1){
                    EventBus.getDefault().post(new ClientEvent(clientListModel));
                }else {
                    EventBus.getDefault().post(new ClientEvent(null));
                }
            }

            @Override
            public void error(String error) {
                EventBus.getDefault().post(new ClientEvent(null));
            }
        });
    }
}
