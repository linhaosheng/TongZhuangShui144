package pro.haichuang.tzs144.net;

import pro.bilibili.boxing_impl.crop.util.ProviderUtil;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import rxhttp.wrapper.annotation.DefaultDomain;

public class ConfigUrl {


    public static final int GET = 0X110;
    public static final int POST = 0X111;

    public final static String DEFAULT_SERVER_URL = "http://119.188.90.126:9090";  // https://api-tzs144.haichuang.pro  //http://114.215.137.110:9090


    /**
     * base url
     */
    @DefaultDomain
    public static  String BASE_URL = SPUtils.getString(Config.SERVER_URL,DEFAULT_SERVER_URL);//http://124.70.96.225:9090";
    // https://api.ssssedc.com; "http://114.215.137.110:9090"

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

    public static final String SET_IS_KNOW  = "/api/app/order/setIsKnow";

    /**
     * [首页]-订单详情
     */
    public static final String HOME_ORDER_INFO = "/api/app/order/getHomeOrderInfo";

    /**
     * [实时库存]获取商品品类
     */
    public static final String FIND_GOODS_CATEGORY = "/api/app/kc/findGoodsCategory";

    /**
     * 商品列表-数据列表
     */
    public static final String FIND_GOODS_LIST = "/api/pc/jc/goods/findGoodsList";

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
     * [押金]获取开押本列表
     */
    public static final String FIND_DEPOSIT_BOOK_LIST = "/api/app/depositManage/findDepositBookList";


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
    public static final String FIND_AREAS = "/api/app/kh/findAreas";

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

    /**
     * [客户]新增/编辑维护记录
     */
    public static final String SAVE_MAINTAIN_LOG = "/api/app/kh/saveMaintainLog";

    /**
     * /api/app/kh/delMaintainLog
     */
    public static final String DEL_MAINTAIN_LOG = "/api/app/kh/delMaintainLog";

    /**
     * [押金]获取开押列表
     */
    public static final String FIND_DEPOSIT_LIST = "/api/app/depositManage/findDepositYjList";

    /**
     * [押金]提交开押
     */
    public static final String ADD_DEPOSIT_INFO = "/api/app/depositManage/saveDepositYj";

    /**
     * [押金]提交退押
     */
    public static final String BACK_DEPOSIT = "/api/app/depositManage/backDeposit";


    /**
     * [押金]退押
     */
    public static final String RETURN_DEPOSIT = "/api/app/depositManage/returnDeposits";


    /**
     * [押金]新增退押 - 列表数据
     */
    public static final String FIND_BY_RETURN_DEPOSITS = "/api/app/depositManage/findByKhReturnDeposits";

    /**
     * [押金]提交退押（手动录入）
     */
    public static final String BACK_DEPOSIT_FILL = "/api/app/depositManage/backDepositFill";

    /**
     *[押金]存储历史退押
     */
    public static final String SAVE_HISTORY = "/api/app/depositManage/saveHistory";


    /**
     * 客户搜索
     */
    public static final String SEARCH = "/api/app/customer/search";

    /**
     * 客户地址列表
     */
    public static final String FIND_ADDRESS = "/api/app/customer/findAddress";

    /**
     * [直接销售]-录入订单
     */
    public static final String ENTER_ORFER = "/api/app/order/enterOrder";

    /**
     * [2.0]客户商品价格配置-获取指定客户商品单价
     */
    public static final String GET_CUSTOMER_PRICE = "/api/pc/kh/customerGoodsConfig/getCustomerPrice";

    /**
     * [直接销售]-订单详情
     */
    public static final String GET_ORDER_INFO = "/api/app/order/getOrderInfo";

    /**
     * [首页]-作废订单
     */
    public static final String DIRECT_SELLING = "/api/app/order/directSelling";

    /**
     * [直接销售]-选择商品列表
     */
    public static final String FIND_SHOP = "/api/app/order/findGoods";

    /**
     * [直接销售]-根据品类查询商品
     */
    public static final String FIND_WATER_TICKET = "/api/app/order/findGoodsByCategory";

    /**
     * 上传接口
     */
    public static final String UPLOAD_FILE = "/api/pc/upload/uploadFile";

    /**
     * [账务]账务管理统计
     */
    public static final String  MANAGER_COUNT= "/api/app/finance/ssManagerCount";

    /**
     * [账务]账务管理-查询订单列表
     */
    public static final String FIND_SS_ORDERS = "/api/app/finance/findSsOrders";

    /**
     * [账务]历史订单统计
     */
    public static final String COUNT_LS_ORDER = "/api/app/finance/countLsOrder";

    /**
     * [账务]历史订单列表数据
     */
    public static final String FIND_LS_ORDERS = "/api/app/finance/findLsOrders";

    /**
     * [账目]账目列表
     */
    public static final String  FIND_ORDER_ACCOUNT = "/api/app/finance/findOrderAccounts";

    /**
     * [账目]账目详情
     */
    public static final String  GET_ACCOUNT_INFOv = "/api/app/finance/getAccountInfo";

    /**
     * [押金]押金本详情
     */
    public static final String DEPOSIT_BOOK_INFO = "/api/app/depositManage/getDepositBookInfo";

    /**
     * [实时库存]获取所有有效员工
     */
    public static final String FIND_STAFFS = "/api/app/kc/findStaffs";

    /**
     * [首页]-配送
     */
    public static final String DELIVERY_ORDER = "/api/app/order/deliveryOrder";

    /**
     * [直接销售]-加载绑定回收材料
     */
    public static final String FIND_GOODS_MATERIAL = "/api/app/order/findGoodsMaterial";

    /**
     * 转订单
     */
    public static final String TURN_ORDER = "/api/app/order/turnOrder";

    /**
     * [账务]账务管理 - 结账
     */
    public static final String SETTLE = "/api/app/finance/settle";

    /**
     * [账务]账务管理 - 作废
     */
    public static final String ACCOUNT_CANCEL = "/api/app/finance/cancel";

    /**
     * [账目]销账
     */
    public static final String CANCEL_ACCOUNT = "/api/app/finance/cancelAccount";


    /**
     * [押金]退押订单列表
     */
    public static final String  FIND_RETURN_DEPOSITS = "/api/app/depositManage/findReturnDeposits";

    /**
     * [押金]查询开押商品
     */
    public static final String FIND_DEPOSIT_GOODS = "/api/app/depositManage/findDepositGoods";

    /**
     * [押金]获取作废退押单
     */
    public static final String FIND_CANCEL_LIST =  "/api/app/depositManage/findCancelList";

    /**
     *  [实时库存]-调拨主体列表
     */
    public static final String FIND_STOCK_MAIN_LIST = "/api/app/kc/findStockMainList";

    /**
     * [押金]查询押金编号
     */
    public static final String FIND_DEPOSIT_YJMOLIST = "/api/app/depositManage/findDepositYjNoList";

    /**
     * [押金]作废退押单
     */
    public  static final String CANCEL_RETURN_DEPOSIT = "/api/app/depositManage/cancelReturnDeposit";

    /**
     * 库存主体 - 结账汇总 - 合计
     */
    public static final String FIND_SUMMARY_HJ = "/api/pc/xs/summary/findSummaryHj";

    /**
     * [5.0]销售汇总
     */
    public static final String FIND_ORDER_SUM_LIST = "/api/app/order/findOrderSumList";

    /**
     * 库存主体 - 结账汇总 - 收入情况
     */
    public static final String FIND_SUMMARY_SR = "/api/pc/xs/summary/findSummarySr";

    public static final String  FIND_SUMMARY_FSSTHSLB = "/api/pc/xs/summary/findSummaryFSSThsLb";

    /**
     * 库存主体 - 结账汇总 - 开退押情况
     */
    public static final String FIND_SUMMARY_KY = "/api/pc/xs/summary/findSummaryKy";


    /**
     * 库存主体 - 结账汇总 - 销售情况 - 商品列表
     */
    public static final String FIND_SUMMARY_SALE_QS = "/api/pc/xs/summary/findSummarySaleQs";


    /**
     * 库存主体 - 结账汇总 - 销售情况 - 材料列表
     */
    public static final String FIND_SUMMARY_SALE_HT = "/api/pc/xs/summary/findSummarySaleHt";

    /**
     * [新增]拆包
     */
    public static final String UNPACK = "/api/app/unpackLog/unpack";

    /**
     * 获取维护用户信息
     */
    public static final String GET_WHCUSTOMER_INFP = "/api/app/kh/getWhCustomerInfo";

}
