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
    public static final String BACK_SERVER = "http://gn.51118518.com";

    /**
     * @Fields GQC_SERVER : 正式环境
     */
    public static final String GQC_SERVER = "http://192.168.1.78:10013";
    /**
     * @Field testMode : 运行模式 测试与正式区分
     */
    public static boolean TEST_MODE = false;

    /**
     * @Fields PERMISSION_REQUEST_CODE:请求权限的返回CODE
     */
    public static final int PERMISSION_REQUEST_CODE = 10001;


    public static final String USER_LINGDAO = "0";
    public static final String USER_YEWUYUAN = "4";
    public static final String USER_MENWEI = "5";
//    public static final String USER_MENWEI = "0";
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


    public static final String RESPONSE_FAILURE = "failure";
    public static final String RESPONSE_SUCCESS = "success";
    /**
     * @Fields SERVER_ROOT_PATH : 服务端接口上下文
     */
    public static final String CONTEXT_PATH = "/sdp";//"/sdp-ydjw2";
    /**
     * @Field PROTOCAL_HEADER_DWDM :应用单位编码
     */
    //public static final String PROTOCAL_HEADER_DWDM = "cmxj";
    public static final String PROTOCAL_HEADER_DWDM = "hkfj";

    public static final String ROOT_PATH = Environment
            .getExternalStorageDirectory().getPath();

    /**
     * @Field PROTOCAL_HEADER_YYDM :应用编码
     */
    //public static final String PROTOCAL_HEADER_YYDM = "cmjw";
    public static final String PROTOCAL_HEADER_YYDM = "zcgl";
    /**
     * @Fields DOWNLOAD_PATH : 文件下载路径
     */
    public static final String DOWNLOAD_PATH = "/.sh.gaj/" + "sh.gaj." + PROTOCAL_HEADER_DWDM + "." +  PROTOCAL_HEADER_YYDM ;

    public static final String getLocalDefaultPath() {
        return ROOT_PATH.concat(DOWNLOAD_PATH);
    }

    /**
     * createUrl:创建接口地址
     *
     * @param path
     * @return String
     * @Title: createUrl
     */
    public static final String createUrl(String path) {
        if (TEST_MODE) {
            String preHostUrl = BACK_SERVER.concat(CONTEXT_PATH);
            return path.startsWith("/") ? preHostUrl.concat(path) : preHostUrl
                    .concat("/").concat(path);
        } else {
            String preHostUrl = GQC_SERVER.concat("/")
                    .concat(PROTOCAL_HEADER_DWDM).concat("/")
                    .concat(PROTOCAL_HEADER_YYDM);
            return path.startsWith("/") ? preHostUrl.concat(path) : preHostUrl
                    .concat("/").concat(path);
        }
    }

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
