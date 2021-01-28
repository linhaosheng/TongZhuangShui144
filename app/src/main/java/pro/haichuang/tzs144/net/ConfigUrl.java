package pro.haichuang.tzs144.net;

import rxhttp.wrapper.annotation.DefaultDomain;

public class ConfigUrl {


    public static final int GET = 0X110;
    public static final int POST = 0X111;


    /**
     * base url
     */
    @DefaultDomain
    public static final String BASE_URL = "https://yapi.haichuang.pro/mock/576";

    /**
     * 登录
     */
    public static final String LOGIN  = "/api/app/login/login";

    /**
     * 获取主体列表
     */
    public static final String SUBJECT_LIST  = "/api/app/login/findBindMainList";

    /**
     * [首页]-订单列表
     */
    public static final String HOME_ORDER = "/api/app/order/findHomeOrders";

    /**
     * [首页]-订单接单
     */
    public static final String TAKE_ORDER = "/api/app/order/takeOrder";

    /**
     * [首页]-订单详情
     */
    public static final String HOME_ORDER_INFO = "/api/app/order/getHomeOrderInfo";

    /**
     * [实时库存]查询商品列表
     */
    public static final String FIND_GOODS = "/api/app/kc/findGoods";

    /**
     * [直接销售]-客户类型列表
     */
    public static final String FIND_CUSTOME_TYPE = "/api/app/order/findCustomerType";

    /**
     * [直接销售]-列表数据
     */
    public static final String FIND_DIRECT_SALES = "/api/app/order/findDirectSales";

    /**
     * [实时库存]录入需求单
     */
    public static final String DEMAND = "/api/app/kc/demand";

    /**
     * [实时库存]查询需求单
     */
    public static final String FIND_DEMAND = "/api/app/kc/findDemands";
}
