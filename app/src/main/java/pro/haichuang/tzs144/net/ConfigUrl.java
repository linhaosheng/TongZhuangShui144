package pro.haichuang.tzs144.net;

import rxhttp.wrapper.annotation.DefaultDomain;

public class ConfigUrl {


    public static final int GET = 0X110;
    public static final int POST = 0X111;


    /**
     * base url
     */
    @DefaultDomain
    public static final String BASE_URL = "https://api-tzs144.haichuang.pro/";

    /**
     * 登录
     */
    public static final String LOGIN  = "/api/login";

    /**
     * 修改密码
     */
    public static final String UPDATE_PASSWORD  = "/api/password";


    /**
     * 帮助中心
     */
    public static final String HELP_CENTER = "/api/helper";

    /**
     * 用户设备列表
     */
    public static final String DEVICES_LIST = "/api/devices";


    /**
     * 设备详情
     */
    public static final String DEVICE_DETAIL = "/api/devices/detail";

    /**
     * 更新头像
     */
    public static final String UPDATE_AVATOR = "/api/avatar";

    /**
     * 用户展项列表
     */
    public static final String EXHIBITION_LIST = "/api/exhibitions";

    /**
     *展项详情
     */
    public static final String EXHIBITION_DETAIL = "/api/exhibitions/detail";


    /**
     * 保修订单状态列表
     */
    public static final String MALFUNCTIOMN_ORDERS_STATES = "/api/malfunction/orders/states";

    /**
     * 订单审核
     */
    public static final String MALFUNCTIOMN_ORDERS_CHECK = "/api/malfunction/orders/check";

    /**
     * 报修订单列表
     */
    public static final String MALFUNCTIOMN_ORDERS = "/api/malfunction/orders";

    /**
     * 报修订单详情
     */
    public static final String MALFUNCTIOMN_ORDERS_DETAIL = "/api/malfunction/orders/detail";


    /**
     * 维修订单状态列表
     */
    public static final String SERVICE_ORDER_STATES = "/api/service/orders/states";

    /**
     * 维修单列表
     */
    public static final String SERVICE_ORDERS = "/api/service/orders";


    /**
     * 获取设备的维修历史记录
     */
    public static final String SERVICE_HISTORY = "/api/services/history";


    /**
     *开始维修
     */
    public static final String SERVICE_ORDER_START = "/api/service/orders/start";


    /**
     * 维修完成
     */
    public static final String SERVICE_ORDER_FINISH = "/api/service/orders/finish";

    /**
     * 测试完成
     */
    public static final String SERVICE_ORDER_TEST = "/api/service/orders/test";

    /**
     * 首页订单统计接口
     */
    public static final String STATISTICS_ORDERS = "/api/statistics/orders";

    /**
     * 订单创建
     */
    public static final String ORDER_CREATE = "/api/order/create";

    /**
     * 上传图片
     */
    public static final String UPLOAD_IMG = "/api/upload/image";

    /**
     * 上传视频
     */
    public static final String UPLOAD_VIDEO = "/api/upload/video";

    /**
     * 获取维修人员列表
     */
    public static final String MANLFUNCTION_MEMBERS  = "/api/malfunction/members";

    /**
     * 维修订单详情
     */
    public static final String SERVER_ORDER_DETAIL  = "/api/service/orders/detail";

    /**
     * 故障等级
     */
    public static final String MANLFUNCTION_LEVEL = "/api/malfunction/level";

    /**
     * 故障类型
     */
    public static final String  MANLFUNCTION_TYPES = "/api/malfunction/types";

    /**
     * 故障状态
     */
    public static final String MANLFUNCTION_STATUS = "/api/device/states";

    /**
     * 报修单完结
     */
    public static final String SERVER_ORDER_SUCCESS = "/api/service/orders/success";



}
