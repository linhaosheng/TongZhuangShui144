package pro.haichuang.tzs144.net;

import pro.bilibili.boxing_impl.crop.util.ProviderUtil;
import rxhttp.wrapper.annotation.DefaultDomain;

public class ConfigUrl {


    public static final int GET = 0X110;
    public static final int POST = 0X111;


    /**
     * base url
     */
    @DefaultDomain
    public static final String BASE_URL = "https://api-tzs144.haichuang.pro";

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
     * [实时库存]获取商品品类
     */
    public static final String FIND_GOODS_CATEGORY = "/api/app/kc/findGoodsCategory";

    /**
     * [实时库存]查询商品列表
     */
    public static final String FIND_GOODS = "/api/app/kc/findGoods";

    /**
     * [实时库存]调拨商品列表
     */
    public static final String FIND_ALLOT_GOODS = "/api/app/kc/findAllotGoods";

    /**
     * [实时库存]调拨出库
     */
    public static final String ALLOT_GOODS = "/api/app/kc/allotGoods";

    /**
     * [实时库存]查询调拨记录
     */
    public static final String FIND_ALLOT_GOODS_LOG = "/api/app/kc/findAllotGoodsLog";

    /**
     * [实时库存]获取需求单商品列表
     */
    public static final String FIND_DEMAND_GOODS = "/api/app/kc/findDemandGoods";

    /**
     * [实时库存]查询取水收捅明细
     */
    public static final String FIND_QSST_LOGS = "/api/app/kc/findQsstLogs";


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

    /**
     * 添加押金本
     */
    public static final String ADD_DESPOSIT_BOOK = "/api/app/depositManage/addDepositBook";

    /**
     * [客户]列表统计
     */
    public static final String COUNT_KH = "/api/app/kh/countKh";

    /**
     * [客户]获取客户列表
     */
    public static final String FIND_KHLIST = "/api/app/kh/findKhList";

    /**
     * [客户]获取客户类型
     */
    public static final String FIND_KH_TYPES = "/api/app/kh/findKhTypes";

    /**
     * [客户]获取所在片区列表
     */
    public static final String FIND_AREAS = "findAreas";

    /**
     * [客户]新增客户
     */
    public static final String ADD_KH = "/api/app/kh/addKh";

    /**
     * [客户]获取客户详情
     */
    public static final String GET_CUSTOMER_INFO = "/api/app/kh/getCustomerInfo";

    /**
     * [客户]修改地址
     */
    public static final String UPDATE_ADDRESS = "/api/app/kh/updateAddress";

    /**
     * [客户]删除地址
     */
    public static final String DEL_ADDRESS = "/api/app/kh/delAddress";

    /**
     * [客户]获取客户订单记录
     */
    public static final String FINF_CUSTOMER_ORDERS = "/api/app/kh/findCustomerOrders";


}
