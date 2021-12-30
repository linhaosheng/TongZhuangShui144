package pro.haichuang.tzs144.presenter;

import android.util.ArrayMap;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.Map;

import pro.haichuang.tzs144.iview.IUpLoadFileView;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.AddOrderStatusEvent;
import pro.haichuang.tzs144.model.OrderStatusModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.UploadFileModel;
import pro.haichuang.tzs144.net.ConfigUrl;
import pro.haichuang.tzs144.net.HttpRequestEngine;
import pro.haichuang.tzs144.net.HttpRequestResultListener;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import rxhttp.wrapper.entity.UpFile;

public class EnterOrderActivityPresenter {


    private IUpLoadFileView iUpLoadFileView;


    public EnterOrderActivityPresenter( IUpLoadFileView iUpLoadFileView){
      this.iUpLoadFileView = iUpLoadFileView;
    }

    /**
     * [直接销售]-录入订单
     */
    public void enterOrder(AddOrderModel addOrderModel,String totalPrice,String receivablePrice,String realPrice,int select){

        if (addOrderModel==null){
            return;
        }
        Map<String,Object> params = new ArrayMap<>();
        params.put("orderType",addOrderModel.getOrderType());
        params.put("customerId",addOrderModel.getCustomerId());
        params.put("addressId",addOrderModel.getAddressId());
        params.put("goodsList",addOrderModel.getGoodsList());
        params.put("totalPrice",totalPrice);
        params.put("receivablePrice",receivablePrice);
        params.put("realPrice",realPrice);


//        String s = Utils.gsonInstane().toJson(params);
//        Log.i("JSON==",s);
//        if (true){
//            return;
//        }

        HttpRequestEngine.postRequest(ConfigUrl.ENTER_ORFER, params, new HttpRequestResultListener() {
            @Override
            public void start() {
                iUpLoadFileView.startLoad();
            }

            @Override
            public void success(String result) {
                try {
                    OrderStatusModel orderStatusModel = Utils.gsonInstane().fromJson(result, OrderStatusModel.class);
                    if (orderStatusModel.getResult()==1){
                        orderStatusModel.setSelect(select);
                        EventBus.getDefault().post(new AddOrderStatusEvent(Config.LOAD_SUCCESS,orderStatusModel.getData().getId(),select));
                    }else {
                        iUpLoadFileView.errorLoad(orderStatusModel.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iUpLoadFileView.errorLoad(error);
            }
        });
    }



    /**
     * [押金]提交退押
     * @param ids
     */
    public void returnDeposits(String ids,String returnCount,String returnPrice){
        Map<String,Object>params = new ArrayMap<>();
        params.put("ids",ids);
        params.put("returnCount",returnCount);
        params.put("returnPrice",returnPrice);

        HttpRequestEngine.postRequest(ConfigUrl.RETURN_DEPOSIT, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");
                    if (jsonObject.getInt("result")==1){
                        EventBus.getDefault().post(new StatusEvent(Config.LOAD_SUCCESS,9));
                    }else {
                        StatusEvent statusEvent = new StatusEvent(Config.LOAD_FAIL,9);
                        statusEvent.setResult(message);
                        EventBus.getDefault().post(statusEvent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                EventBus.getDefault().post(new StatusEvent(Config.LOAD_FAIL,9));
            }
        });
    }


    /**
     *
     */
    public void getCustomerPrice(int customerId,int goodsId){

        Map<String,Object> params = new ArrayMap<>();
        params.put("goodsId",goodsId);
        params.put("customerId",customerId);

        HttpRequestEngine.postRequest(ConfigUrl.GET_CUSTOMER_PRICE, params, new HttpRequestResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void success(String result) {
                try {
                    CustomerPriceModel customerPriceModel = Utils.gsonInstane().fromJson(result, CustomerPriceModel.class);
                    EventBus.getDefault().post(customerPriceModel);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                iUpLoadFileView.errorLoad(error);
            }
        });
    }


    /**
     * 上传文件
     * @param key
     * @param path
     */
    public void uploadFile(String key,String path){

        Log.i("uploadFile====",path);
        UpFile upFile = new UpFile("file",new File(path));

        HttpRequestEngine.uploadFile(ConfigUrl.UPLOAD_FILE, upFile, new HttpRequestResultListener() {
            @Override
            public void start() {
                iUpLoadFileView.startLoad();
            }

            @Override
            public void success(String result) {
                UploadFileModel uploadFileModel = Utils.gsonInstane().fromJson(result, UploadFileModel.class);
                if (uploadFileModel.getResult()!=1){
                    iUpLoadFileView.errorLoad("上传失败");
                }else {
                    uploadFileModel.getData().setKey(key);
                    iUpLoadFileView.successLoad(uploadFileModel.getData());
                }
            }

            @Override
            public void error(String error) {
                iUpLoadFileView.errorLoad(error);
            }
        });
    }

    public static class CustomerPriceModel{
        private int result;
        private String message;
        private DataBean data;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public class DataBean{

            private String price;
            private boolean isEdit;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public boolean isEdit() {
                return isEdit;
            }

            public void setEdit(boolean edit) {
                isEdit = edit;
            }
        }
    }

}
