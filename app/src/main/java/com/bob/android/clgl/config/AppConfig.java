package com.bob.android.clgl.config;

import android.os.Environment;

/**
 * @package com.bob.android.clgl.config
 * @fileName AppConfig
 * @Author Bob on 2018/5/15 10:30.
 * @Describe TODO
 */

public class AppConfig {


    /**
     * @Fields DEV_SERVER : 开发测试环境
     */
//    private static final String DEV_SERVER = "http://sqlserver.51118518.com";
    public static final String BACK_SERVER ="http://cartest.51118518.com";//"http://cartest2.51118518.com";//"http://common8088.51118518.com";//// "http://gn.51118518.com";

    /**
     * @Fields GQC_SERVER : 正式环境
     */
    public static final String GQC_SERVER = "http://192.168.1.78:10013";
    /**
     * @Field testMode : 运行模式 测试与正式区分
     */
    public static boolean TEST_MODE = false;


    public static final String USER_LINGDAO = "0";
    public static final String USER_YEWUYUAN = "4";
    public static final String USER_MENWEI = "5";
    /**
     * @Fields PERMISSION_REQUEST_CODE:请求权限的返回CODE
     */
    public static final int PERMISSION_REQUEST_CODE = 10001;
    /***
     * 登录
     */
    public static final String LOGIN_REQUEST = "/login";

    /**
     * 根据条码获取相关信息
     */
    public static final String GET_VEHICLE_INFO = "/app/findByNew";

    /**
     * 提交信息
     */
    public static final String UPLOAD_VEHICLE_INFO = "/app/saveConsumeInfo";
    /**
     * 获取密码
     */
    public static final String CREATE_PASSWORD = "/pwd/generatePwd";

    /**
     * 获取接收人信息
     */
    public static final String GET_RECEIVER = "/receive/findReceive";

    /**
     * 保存接收人信息
     */
    public static final String SAVE_RECEIVER = "/receive/saveReceive";

    /**
     * 剩余票数
     */
    public static final String REMAIND_TICKET = "/ticket/findResidueTicketNum";

    /**
     * 区域进车量查询
     */
    public static final String CHECK_BY_AREA = "/dataAnalyze/findByArea";

    /**
     * 获取区域字典
     */
    public static final String GET_AREA_DICTIONARY = "/app/findAreaList";

    /**
     * 区域票数查询
     */
    public static final String CHECK_TICKET = "/finance/findByMap";

    /**
     * 查询所有单位
     * */
    public static final String GET_UNIT = "/app/findUnitList";

    /**
     * 根据区域ID查询车牌
     */
    public static final String GET_CAR_NUM_BY_AREA_ID = "/app/findCarArea";

    /**
     * 根据公司查询车牌
     */
    public static final String GET_CAR_NUM_BY_UNIT = "/app/findCarCompany";


    public static final String RESPONSE_FAILURE = "failure";
    public static final String RESPONSE_SUCCESS = "success";

    public static final String ROOT_PATH = Environment
            .getExternalStorageDirectory().getPath();



    public static final String requestUrl(String requestUrl){
        if(TEST_MODE){
            String hostUrl = GQC_SERVER.concat(requestUrl);
            return hostUrl;
        }else{
            String hostUrl = BACK_SERVER.concat(requestUrl);
            return hostUrl;
        }
    }
}
