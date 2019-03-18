package http;

import android.text.TextUtils;


import com.qlzx.mylibrary.bean.BaseBean;

import java.util.HashMap;

import bean.PhoneIsRegisterBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/1.
 */
/*
 * 放网络请求的url地址，每个接口需要加上注释
 */
public class Setting {
//{"msg":"登录成功！","code":200,"data":{"IsRemind":"2","parents":{"id":16,"nickname":null,"age":null,"sex":null,"qcellcoreId":1,
// "phoneNumber":"18210182462","password":null,"createTime":1541039924000,"childId":5,"updateTime":1541384303000,"status":"1",
// "token":"/LBFWE/X7wbgP1eMSoQ9dQ==","feel":null,"city":null,"phonePrefix":null}}}

    /**
     * IConstants 这个类也有需要配置的
     */

    // public static final boolean DEBUG = BuildConfig.DEBUG;//
    // BuildConfig.DEBUG
    public static final boolean DEBUG = false;// BuildConfig.DEBUG 预发布环境为fasle,测试环境为true

    // ===================================================================================
    private final static String HTTP = "http://";
    private final static String HTTPS = "https://";
    private final static String SPLIT = "/";

    // ==========================================================================================================
    /*
     * app接口环境
	 */
    //  app.zzbhqdd.com/child/  正式环境  180.76.163.33:9090
    private static final String HOST_TEST = "api.t.drking.club";// 测试环境 180.76.163.33:9090
    //    private static final String HOST_ONLINE_R = "192.168.144.246:9090/admin"; // 预发布环境 114.115.213.85/   api.r.drking.club ， 114.115.213.85/child/app ，192.168.1.166:8080   app.zzbhqdd.com/child/app
    private static final String HOST_ONLINE_R = "47.95.244.242/XiaoyudiApplication"; // 预发布环境 114.115.213.85/   api.r.drking.club ， 114.115.213.85/child/app ，192.168.1.166:8080   app.zzbhqdd.com/child/app

    private static final String HOST_ONLINE = "dkapi.ixinzang.com";// 发布环境

    // image
    private static final String HOST_TEST_IMG = "img.t.drking.club";
    private static final String HOST_ONLINE_IMG_R = "img.r.drking.club";
    private static final String HOST_ONLINE_IMG = "dkimg.ixinzang.com";

    //===================================================================
    // detail
    private static final String HOST_DETAIL_TEST = "http://api.t.drking.club";
    private static final String HOST_DETAIL_ONLINE_R = "http://api.r.drking.club";
    private static final String HOST_DETAIL_ONLINE = "http://dkapi.ixinzang.com";

    // 微信历史病例
    private static final String HOST_WX_BL_TEST = "http://api.t.drking.club";
    private static final String HOST_WX_BL_ONLINE_R = "http://img.r.drking.club";
    private static final String HOST_WX_BL_ONLINE = "http://dkimg.ixinzang.com";

    //========================================================================
    // can modify
    private final static String HOST = DEBUG ? HOST_TEST
            : HOST_ONLINE_R;// 配置上线是什么环境！！！！！！！！！！！！！！！！！！！！！！！！
    public final static String HOST_DETAIL = DEBUG ? HOST_DETAIL_TEST
            : HOST_DETAIL_ONLINE_R;// 配置上线是什么环境
    private final static String HOST_IMG = DEBUG ? HOST_TEST_IMG
            : HOST_ONLINE_IMG_R;
    public final static String HOST_WX_BL = DEBUG ? HOST_WX_BL_ONLINE
            : HOST_WX_BL_ONLINE;

    // ==============================================================
    //测试地址
   // public final static String URL_API_HOST_HTTP = "http://47.95.244.242/XiaoyudiApplication/";// 基地址

    //正式地址
    public final static String URL_API_HOST_HTTP = "https://api.xiaoyudi.org/";// 基地址


    //    public final static String URL_API_HOST_HTTP = HTTP + HOST + SPLIT;// 基地址
    public final static String URL_API_HOST_HTTP_IMG = HTTP + HOST_IMG + SPLIT;// image
    public final static String URL_API_HOST_DETAIL_HTTP = HOST_DETAIL + SPLIT;// detail
    public final static String URL_WX_BL_HTTP = HOST_WX_BL + SPLIT;// detail

    /*
     * 获取图片接口
     */
    public static String getImageUrl(String imageUrl) {
        String url = "";
        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl.startsWith("http://")) {
                url = imageUrl;
            } else {
                url = URL_API_HOST_HTTP_IMG + imageUrl;
            }
        }
        return url;
    }

    /**
     * 普通登录
     * phone	是	string	手机号
     * password	是	string	密码
     * qcellcoreId	是	int	手机号归属地id
     *
     * @return
     */
    public static String generalLogin() {
        return URL_API_HOST_HTTP + "app/user/generalLogin";  //
    }

    /**
     * 快捷登录图片验证码效验
     *
     * @return
     */
    public static String verifyimageCode() {
        return URL_API_HOST_HTTP + "app/user/verify/imageCode";  //
    }

    /**
     * 快捷登录
     *
     * @return
     */
    public static String shortcutLogin() {
        return URL_API_HOST_HTTP + "app/user/shortcutLogin";  //
    }

    /**
     * 快捷登录获取验证码
     *
     * @return
     */
    public static String shortcutLoginSend() {
        return URL_API_HOST_HTTP + "app/user/shortcutLoginSend";  //
    }

    /**
     * 修改密码
     *
     * @return
     */
    public static String updatePassword() {
        return URL_API_HOST_HTTP + "app/user/updatePassword";  //
    }

    /**
     * 修改儿童昵称
     *
     * @return
     */
    public static String updateChildInfo() {
        return URL_API_HOST_HTTP + "app/user/updateChildInfo";  //
    }

    /**
     * 名词
     *
     * @return
     */
    public static String getNoun() {
        return URL_API_HOST_HTTP + "app/trainTest/getNoun";  //
    }

    /**
     * 记录体验接口
     * token	是	string	登录标识
     * type	是	string	模块：1训练 2测试
     *
     * @return
     */
    public static String parentsAddRecord() {
        return URL_API_HOST_HTTP + "app/parents/addRecord";  //
    }

    /**
     * 动词
     *
     * @return
     */
    public static String getVerb() {
        return URL_API_HOST_HTTP + "app/trainTest/getVerb";  //
    }

    /**
     * 句子成组
     *
     * @return
     */
    public static String getSentencegroup() {
        return URL_API_HOST_HTTP + "app/trainTest/getSentencegroup";  //
    }

    /**
     * 句子分解
     *
     * @return
     */
    public static String getSentenceResolve() {
        return URL_API_HOST_HTTP + "app/trainTest/getSentenceResolve";  //
    }


    /**
     * 发送验证码
     * phone	是	string	手机号
     * qcellcoreId	是	int	手机号归属地id
     * type	是	string	发送类型(1：忘记密码 2：修改密码 3：更换手机号旧发送验证码 4:更换手机号新手机发送验证码)
     *
     * @return
     */
    public static String sendCode() {
        return URL_API_HOST_HTTP + "app/user/sendCode";  //
    }

    /**
     * 用户修改手机号
     * token	是	string	标识
     * phone	是	string	手机号
     * districeId	是	string	手机号归属地id
     * code	是	string	验证码
     *
     * @return
     */
    public static String updatePhone() {
        return URL_API_HOST_HTTP + "app/user/updatePhone";  //
    }

    /**
     * 忘记密码
     * phone	是	string	手机号
     * password	是	string	新密码
     * code	是	string	验证码
     *
     * @return
     */
    public static String resetPassword() {
        return URL_API_HOST_HTTP + "app/user/resetPassword";  //
    }

    /**
     * 手机号code验证
     * phone	是	string	手机号
     * code	是	string	验证码
     * districeId	是	int	手机号归属地id
     *
     * @return
     */
    public static String efficacyCode() {
        return URL_API_HOST_HTTP + "app/user/efficacyCode";  //
    }

    /**
     * 完善儿童个人信息
     *
     * @return
     */
    public static String perfectionAddChild() {
        return URL_API_HOST_HTTP + "app/user/perfection/addChild";  //
    }

    /**获取儿童信息
     * @return
     */
    public static String getChilInfo() {
        return URL_API_HOST_HTTP + "app/user/getChilInfo";  //
    }
    public static String toAssess() {
        return URL_API_HOST_HTTP + "app/parents/toAssess";  //
    }


    /**
     * 添加儿童训练测试课件结果
     *
     * @return token    是	string	用户标识
     * coursewareId	是	int	课件id
     * scene	是	string	学习场景 1训练 2测试 3意义
     * module	是	string	训练测试模块 1名词 2动词 3句子组成 4句子分解
     * startTime	是	data	开始时间
     * name	是	string	课件名字 例：男孩吃苹果
     * pass	是	string	是否通过 1 是 0 否
     * stayTimeList	否	string	停留时间 逗号分隔 例”5,5,6,7”
     * disTurbName	否	string	干扰课件名称 只有名词意义才统计
     * errorType	否	string	错误类型 1干扰形容词 2干扰名词 3预选形容词 4预选名词 只有名词意义级别才统计错误类型
     * stayTime	是	int	总停留时间
     * groupId	是	int	当前组课件记录表id
     * <p>
     * *
     */
    public static String addTrainingResult() {
        return URL_API_HOST_HTTP + "app/trainingResult/addTrainingResult";  //
    }

    /**
     * 获取做题进度
     *
     * @return token
     * <p>
     * {
     * "msg": "查询成功！",
     * "code": 200,
     * "list": {
     * "sentence": "1",
     * "verb": "1",
     * "noun": "2",
     * "group": "1"
     * }
     * "data": [
     * {
     * "id": 5,
     * "userId": 5,
     * "learningTime": 58,
     * "module": "1",
     * "childModule": "3",
     * "passNumber": 1,
     * "states": "1",
     * "createTime": 1541470561000,
     * "updateTime": null,
     * "rate": 1
     * }
     * ]
     * }
     * 返回参数说明
     * <p>
     * 参数名	类型	说明
     * data	Object	通关进度
     * childModule	string	学习子模块 1训练 2测试 3意义
     * module	string	学习模块 1 名词 2 动词 3 句子成组 4句子分解
     * userId	int	儿童id
     * learningTime	int	当前模块总学习时长
     * passNumber	int	连续通过的次数累计
     * rate	doubloe	模块通关进度
     * ———	——-	——-
     * groupTraining	Object	某个模块小进度
     * scene	String	学习场景 1训练 2测试 3意义
     * module	String	训练测试模块： 1名词 2动词 3句子组成 4句子分解 5名词意义级别
     * length	int	做到第几（length）题
     * gold	int	金币
     * groupId	int	做题的时候传过去这个id
     * ———	——-	——-
     * list	Object	应该做某道子模块进度
     * noun	string	名词 学习子模块 1训练 2测试 3意义
     * verb	string	动词 学习子模块 1训练 2测试 3意义
     * group	string	句子成组 学习子模块 1训练 2测试 3意义
     * sentence	string	句子分解 学习子模块 1训练 2测试 3意义
     * <p>
     * 应该是做题的时候，中途退出，20分钟内，进来的时候，显示的刚才的做题位置，如果超过20分钟就从第一个课件开始做
     */
    public static String getSystemStatistics() {
        return URL_API_HOST_HTTP + "app/trainTest/getSystemStatistics";  //
    }

    /**
     * 获取强化物金币
     *
     * @return token    是	string	用户信息
     * module	string	1名词 2动词 3句子组成 4句子分解
     * gold	int	金币总量
     */
    public static String getFortifier() {
        return URL_API_HOST_HTTP + "app/trainTest/getFortifier";  //
    }

    /**
     * 强化物添加金币
     * <p>
     * token	是	string	用户信息
     * module	是	string	模块
     * state	是	string	1减少 0添加
     *
     * @return
     */
    public static String addFortifier() {
        return URL_API_HOST_HTTP + "app/trainTest/addFortifier";  //
    }


    /**
     * 查询儿童剩余积木数
     *
     * @return token    是	string	用户x信息
     * module	是	string	1名词 2动词 3句子组成 4句子分解
     * number	int	第n块积木
     */
    public static String toyList() {
        return URL_API_HOST_HTTP + "app/toy/toyList";  //
    }


    /**
     * 修改儿童积木状态
     *
     * @return
     */
    public static String toyUseToy() {
        return URL_API_HOST_HTTP + "app/toy/useToy";  //
    }


    /**
     * 获取三级城市列表
     *
     * @return
     */
    public static String systemGetThisCityList() {
        return URL_API_HOST_HTTP + "app/system/getThisCityList";  //
    }


    /**
     * 上传图片
     * image	是	string	上传图片的name
     *
     * @return
     */
    public static String ossFileUpload() {
        return URL_API_HOST_HTTP + "app/system/oss/fileUpload";  //
    }

    /**
     * 修改儿童昵称或头像
     *
     * @return token    是	string	标识
     * name	否	string	昵称
     * photo	否	string	头像地址
     */
    public static String userUpdateChildInfo() {
        return URL_API_HOST_HTTP + "/app/user/updateChildInfo";  //
    }

    public static String weekInfo() {
        return URL_API_HOST_HTTP + "app/statistics/noun/weekInfo";  //
    }

    public static String monthInfo() {
        return URL_API_HOST_HTTP + "app/statistics/noun/monthInfo";  //
    }

    public static String dayInfo() {
        return URL_API_HOST_HTTP + "app/statistics/noun/dayInfo";  //
    }

    /**
     * 自定义消息推送
     * type	是	string	推送方式：1、“tag”标签推送，2、“alias”别名推送
     * value	是	string	推送的标签或别名值
     * alert	是	string	推送的内容
     * 备注：推送方式（type）不为空时，推送的值（alert）也不能为空；推送方式为空时，推送值不做要求
     *
     * @return
     */
    public static String pushMsg() {
        return URL_API_HOST_HTTP + "app//jpush/pushMsg";  //
    }


//    @POST("app/user/phoneIsRegister")
//    @FormUrlEncoded
//    Observable<BaseBean<PhoneIsRegisterBean>> phoneIsRegister(@Field("phone") String phone, @Field("districeId") String districeId);

    public static String phoneIsRegister() {
        return URL_API_HOST_HTTP + "app/user/phoneIsRegister";  //
    }

    /**
     * @return
     */
    public static String appStartImage() {
        return URL_API_HOST_HTTP + "app/system/appStartImage";  //
    }
//    通关重置强化物积木
    public static String clearanceReset() {
        return URL_API_HOST_HTTP + "/app/toy//empty/useToy";  //
    }



//    @POST("app/user/sendCode")
//    @FormUrlEncoded
//    Observable<BaseBean<String>> sendCode(@Field("phone") String phone, @Field("qcellcoreId") String qcellcoreId
//            , @Field("type") String type);

// {"msg":"登录成功！","code":200,"data":{"IsRemind":"2","parents":{"id":16,"nickname":null,"age":null,
// "sex":null,"qcellcoreId":1,"phoneNumber":"18210182462","password":null,"createTime":1541039924000,"childId":5,
// "updateTime":1541384303000,"status":"1","token":"LPJBUOKlmkrDmB/7l/5Xww==","city":null,"phonePrefix":null}}}

}
