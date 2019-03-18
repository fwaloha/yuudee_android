package http;


import com.qlzx.mylibrary.bean.BaseBean;


import bean.AllTrainRecordBean;
import bean.AssementReviewBean;
import bean.ChildMessageBean;
import bean.CommenBean;
import bean.GeneralLoginBean;
import bean.RegisterBean;
import bean.SplashPicBean;
import bean.LocationBean;
import bean.PhoneIsRegisterBean;
import bean.ProductIntroductionBean;
import bean.TranningFileMonthBean;
import bean.TranningRecordBean;
import bean.VersionMessageBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


import java.util.List;

public interface RemoteApi {

    //-------------------------------------------------------------------------------------------

    /**
     * 校验手机合法性
     * @param phone
     * @param qcellcoreId
     * @return
     */
    @GET("app/system/verify/phoneNumber")
    Observable<BaseBean<String>> isNumberLegal(@Query("phone") String phone,@Query("qcellcoreId") String qcellcoreId);

    /**
     * 获取手机号归属地数据列表
     *
     * @return
     */
    @GET("app/system/qcellcoreList")
    Observable<BaseBean<List<LocationBean>>> qcellcoreList();

    /**
     * 发送验证码
     * phone	是	string	手机号
     * qcellcoreId	是	int	手机号归属地id
     * type	是	string	发送类型(1：忘记密码 2：修改密码)
     *
     * @param phone
     * @param qcellcoreId
     * @param type
     * @return
     */
    @POST("app/user/sendCode")
    @FormUrlEncoded
    Observable<BaseBean<String>> sendCode(@Field("phone") String phone, @Field("qcellcoreId") String qcellcoreId
            , @Field("type") String type);


    /**
     * 判断该手机号是否已注册
     * phone	是	string	用户名
     * districeId	是	int	手机号归属地id
     *
     * @param phone
     * @param districeId
     * @return
     */
    @POST("app/user/phoneIsRegister")
    @FormUrlEncoded
    Observable<BaseBean<PhoneIsRegisterBean>> phoneIsRegister(@Field("phone") String phone, @Field("districeId") String districeId);

    /**
     * 快捷登录图片验证码效验
     * imageCode	是	string	图片验证码
     *
     * @param imageCode
     * @return
     */
    @POST("app/user/verify/imageCode")
    @FormUrlEncoded
    Observable<BaseBean<String>> verifyImageCode(@Field("imageCode") String imageCode);

    /**
     * 忘记密码
     * phone	是	string	手机号
     * password	是	string	新密码
     * code	是	string	验证码
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @POST("app/user/resetPassword")
    @FormUrlEncoded
    Observable<BaseBean<String>> resetPassword(@Field("phone") String phone, @Field("password") String password
            , @Field("code") String code);

    /**
     * 修改密码
     * token	是	string	用户标识
     * oldPassword	是	string	旧密码
     * newPassword	是	string	新密码
     *
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @POST("app/user/updatePassword")
    @FormUrlEncoded
    Observable<BaseBean<String>> updatePassword(@Field("token") String token, @Field("oldPassword") String oldPassword
            , @Field("newPassword") String newPassword);


    /**
     * 修改儿童昵称
     * token	是	string	标识
     * name	是	string	昵称
     *
     * @param token
     * @param name
     * @return
     */
    @POST("app/user/updateChildInfo")
    @FormUrlEncoded
    Observable<BaseBean<String>> updateChildInfo(@Field("token") String token, @Field("name") String name
    );

    /**
     * 用户修改手机号
     * token	是	string	标识
     * phone	是	string	手机号
     * districeId	是	string	手机号归属地id
     * code	是	string	验证码
     *
     * @param token
     * @param phone
     * @param districeId
     * @param code
     * @return
     */
    @POST("app/user/updatePhone")
    @FormUrlEncoded
    Observable<BaseBean<String>> updatePhone(@Field("token") String token, @Field("phone") String phone
            , @Field("districeId") String districeId, @Field("code") String code
    );


    /**
     * 完善儿童个人信息
     * <p>
     * token	是	string	用户标识
     * name	是	string	儿童昵称
     * sex	是	string	儿童性别（0：男 1：女）
     * birthdate	是	string	儿童出生日期 (yyyy-MM-dd)
     * medical	是	string	医学诊断类型
     * medicalState	否	string	医学诊断程度
     * firstLanguage	否	string	第一语言
     * firstRests	否	string	其他语言
     * secondLanguage	否	string	第二语言
     * secondRests	否	string	其他语言
     * fatherCulture	是	string	父亲文化程度
     * motherCulture	是	string	母亲文化程度
     * trainingMethod	是	string	目前训练及疗法
     * trainingRests	否	string	其他训练疗法
     * states	是	Sring	是否完善（0 否 1是）
     * <p>
     * <p>
     * medical：0 “自闭症”, 1 “语言发育迟缓（其他正常）”, 2 “单纯性智力低下（无自闭症）”, 3 “其他诊断”, 4 “正常”,
     * medicalState：0：轻 1：中 2：重 3：不清楚 （当medical为0时才传）
     * firstLanguage：0:普通话 1: 方言 10：其他语言
     * secondLanguage：0:普通话 1: 方言 10：其他语言
     * fatherCulture与motherCulture：0：小学-:高中 1：硕士研究生 2：博士或类似
     * trainingMethod：1:ABA 2:其他训练疗法 3：无训练
     */
    @POST("app/user/perfection/addChild")
    @FormUrlEncoded
    Observable<BaseBean<String>> perfectionAddChild(@Field("token") String token, @Field("name") String name
            , @Field("sex") String sex, @Field("birthdate") String birthdate, @Field("medical") String medical,
                                                    @Field("medicalState") String medicalState, @Field("firstLanguage") String firstLanguage
            , @Field("firstRests") String firstRests
            , @Field("secondLanguage") String secondLanguage, @Field("secondRests") String secondRests
            , @Field("fatherCulture") String fatherCulture
            , @Field("motherCulture") String motherCulture
            , @Field("trainingMethod") String trainingMethod
            , @Field("trainingRests") String trainingRests
            , @Field("states") String states);

    /**
     * 家长注册
     * phone	是	string	手机号
     * password	是	string	密码
     * qcellcoreId	是	int	手机号归属地id
     *
     * @param phone
     * @param password
     * @param qcellcoreId
     * @return
     */
    @POST("app/user/register")
    @FormUrlEncoded
    Observable<BaseBean<RegisterBean>> register(@Field("phone") String phone, @Field("password") String password
            , @Field("qcellcoreId") String qcellcoreId);

    /**
     * 快捷登录获取验证码
     * phone	是	string	手机号
     * qcellcoreId	是	int	手机归属地id
     *
     * @param phone
     * @param qcellcoreId
     * @return
     */
    @POST("app/user/shortcutLoginSend")
    @FormUrlEncoded
    Observable<BaseBean<String>> shortcutLoginSend(@Field("phone") String phone, @Field("qcellcoreId") String qcellcoreId
    );

    /**
     * 快捷登录
     * phone	是	string	手机号
     * code	是	string	验证码
     * qcellcoreId	是	int	手机号归属地id
     *
     * @param phone
     * @param code
     * @param qcellcoreId
     * @return
     */
    @POST("app/user/shortcutLogin")
    @FormUrlEncoded
    Observable<BaseBean<String>> shortcutLogin(@Field("phone") String phone, @Field("code") String code
            , @Field("qcellcoreId") String qcellcoreId
    );

    /**
     * 注册获取验证码
     * phone	是	string	手机号
     * districeId	是	int	手机号所属地区id
     *
     * @param phone
     * @param districeId
     * @return
     */
    @POST("app/user/registerSendCode")
    @FormUrlEncoded
    Observable<BaseBean<String>> registerSendCode(@Field("phone") String phone, @Field("districeId") String districeId
    );

    /**
     * 家长注册、手机验证码验证
     * phone	是	string	手机号
     * districeId	是	int	手机号归属地id
     * code	是	string	验证码
     *
     * @param phone
     * @param districeId
     * @param code
     * @return
     */
    @POST("app/user/registerCodeverify")
    @FormUrlEncoded
    Observable<BaseBean<String>> registerCodeverify(@Field("phone") String phone, @Field("districeId") String districeId
            , @Field("code") String code
    );

    /**
     * 普通登录
     * phone	是	string	手机号
     * password	是	string	密码
     * qcellcoreId	是	int	手机号归属地id
     *
     * @param phone
     * @param password
     * @param qcellcoreId
     * @return
     */
    @POST("app/user/generalLogin")
    @FormUrlEncoded
    Observable<BaseBean<GeneralLoginBean>> generalLogin(@Field("phone") String phone, @Field("password") String password
            , @Field("qcellcoreId") String qcellcoreId
    );



    /*训练测试*/


    /**
     * 随机10条 动词短语结构训练
     *
     * @return
     */
    @POST("app/trainTest/getVerbTraining")
    @FormUrlEncoded
    Observable<BaseBean<String>> getVerbTraining();

    /**
     * 随机10条 动词短语测试
     *
     * @return
     */
    @POST("app/trainTest/getVerbTest")
    @FormUrlEncoded
    Observable<BaseBean<String>> generalLogin();


    /**
     * 随机10条 句子分解测试
     *
     * @return
     */
    @POST("app/trainTest/getSentenceResolveTest")
    @FormUrlEncoded
    Observable<BaseBean<String>> getSentenceResolveTest();

    /**
     * 随机10条 句子结构成组训练
     *
     * @return
     */
    @POST("app/trainTest/getSentencegroupTraining")
    @FormUrlEncoded
    Observable<BaseBean<String>> getSentencegroupTraining();


    /*************************************   parents   center    ******************************************/
    /**
     *
     * @param token
     * @return
     */
    @GET("xiaoyudi/pages/abcquestionnaire.html")
    Observable<BaseBean<String>> getAbcMessage(@Query("token") String token);



    /**
     * 评估评测页面
     *
     * @param token
     * @return
     */
    @POST("app/parents/toAssess")
    @FormUrlEncoded
    Observable<BaseBean<AssementReviewBean>> getAssessmentReview(@Field("token") String token);

    /**
     * 训练档案、总测试通关学习情况记录
     *
     * @param token
     * @return
     */
    @POST("app/statistics/noun/totalInfo")
    @FormUrlEncoded
    Observable<BaseBean<AllTrainRecordBean>> getTranningFileMaster(@Field("token") String token);

    /**
     * 训练档案、按月查询统计学习情况记录
     *
     * @param token
     * @param module 模块类型（1 名词 2 动词 3 句子成组 4句子分解）
     * @param scene  场景（1训练 2测试 3意义）
     * @return
     */
    @POST("app/statistics/noun/monthInfo")
    @FormUrlEncoded
    Observable<BaseBean<TranningFileMonthBean>> getTranningFileMothly(@Field("token") String token, @Field("module") String module, @Field("scene") String scene);

    /**
     * 训练档案、按周查询统计学习情况记录
     *
     * @param token
     * @param module 模块类型（1 名词 2 动词 3 句子成组 4句子分解）
     * @param scene  场景（1训练 2测试 3意义）
     * @return
     */
    @POST("app/statistics/noun/weekInfo")
    @FormUrlEncoded
    Observable<BaseBean<CommenBean>> getTranningFileWeekly(@Field("token") String token, @Field("module") String module, @Field("scene") String scene);

    /**
     * 训练档案、按天查询统计学习情况记录
     *
     * @param token
     * @param module 模块类型（1 名词 2 动词 3 句子成组 4句子分解）
     * @param scene  场景（1训练 2测试 3意义）
     * @return
     */
    @POST("app/statistics/noun/dayInfo")
    @FormUrlEncoded
    Observable<BaseBean<CommenBean>> getTranningFileDayly(@Field("token") String token, @Field("module") String module, @Field("scene") String scene);


    /**
     * 训练档案一级页面
     *
     * @param token
     * @return
     */
    @POST("app/parents/training/records")
    @FormUrlEncoded
    Observable<BaseBean<TranningRecordBean>> getRecrdProgressList(@Field("token") String token);

    /*****************************    system api    *****************************************/
    /**
     * 家长中心   产品介绍
     *
     * @param type 	(1:家长中心产品介绍，2：首页产品详情)
     * @return
     */
    @GET("app/system/productInfo")
    Observable<BaseBean<ProductIntroductionBean>> getProductIntroduction(@Query("type") String type);

    /**
     * 更多建议反馈和版本信息
     * @param versionsNumber 当前版本唯一标识ID
     * @param type 客户端类型（1:android 2:ios）
     * @param token
     * @return
     */
    @GET("app/system/aboutUs")
    Observable<BaseBean<VersionMessageBean>> getVersionMessageData(@Query("versionsNumber") String versionsNumber,@Query("type") String type, @Query("token") String token);

    /************************公共接口**************************/

    /**
     * 获取图片信息
     * type 1:app启动页图片
     * @param type
     * @return
     */
    @GET("app/system/appStartImage")
    Observable<BaseBean<SplashPicBean>> getImagefromServer(@Query("type") String type);

    /**
     * 获取儿童信息
     * @param token
     * @return
     */
    @POST("app/user/getChilInfo")
    @FormUrlEncoded
    Observable<BaseBean<ChildMessageBean>> getChildMessage(@Field("token") String token);


}