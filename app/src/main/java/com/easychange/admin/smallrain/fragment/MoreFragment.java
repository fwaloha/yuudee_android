package com.easychange.admin.smallrain.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.BuildConfig;
import com.easychange.admin.smallrain.MainActivity;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.activity.ChangePasswordActivity;
import com.easychange.admin.smallrain.activity.EditDataActivity;
import com.easychange.admin.smallrain.activity.HomeActivity;
import com.easychange.admin.smallrain.activity.ModifyingChildNicknamesActivity;
import com.easychange.admin.smallrain.activity.PerfectionChildrenInfoActivity;
import com.easychange.admin.smallrain.base.BaseFragment;
import com.easychange.admin.smallrain.dialog.OrCodeDialog;
import com.easychange.admin.smallrain.event.OnOrCodeClickListener;
import com.easychange.admin.smallrain.utils.GlideUtil;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.views.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.EventBusUtil;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.TakePhotoUtils;
import com.qlzx.mylibrary.util.ToastUtil;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.wildma.pictureselector.PictureSelector;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import bean.AssementReviewBean;
import bean.ExitLoginBean;
import bean.FileUploadBean;
import bean.HeadChangeBean;
import bean.UserBean;
import bean.VersionMessageBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import event.UpdatePhoneEvent;
import http.AsyncRequest;
import http.BaseStringCallback_Host;
import http.RemoteApi;
import http.Setting;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * admin  2018/8/24 wan
 */
public class MoreFragment extends BaseFragment implements AsyncRequest {
    @BindView(R.id.ll_showweixin)
    LinearLayout llShowWinxin;
    @BindView(R.id.tv_versionid)
    TextView tvVersionid;
    @BindView(R.id.tv_updatehead)
    TextView tvUpdateHead;
    @BindView(R.id.ll_edit_message)
    LinearLayout llEditMessage;
    @BindView(R.id.img_user_data)
    CircleImageView imgUserData;
    @BindView(R.id.iv_holdwhite)
    CircleImageView ivHoldWhite ;
    @BindView(R.id.img_home_right)
    ImageView imgHomeRight;
    @BindView(R.id.layout_write_data)
    LinearLayout layoutWriteData;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.layout_change_user_name)
    LinearLayout layoutChangeUserName;
    @BindView(R.id.tv_user_pass)
    TextView tvUserPass;
    @BindView(R.id.layout_change_user_pass)
    LinearLayout layoutChangeUserPass;
    @BindView(R.id.tv_buttom_user_phone)
    TextView tvButtomUserPhone;
    @BindView(R.id.layout_change_user_phone)
    LinearLayout layoutChangeUserPhone;
    @BindView(R.id.tv_wangzhan)
    TextView tvWangzhan;
    @BindView(R.id.tv_youxiang)
    TextView tvYouxiang;
    @BindView(R.id.layout_see_weichart)
    LinearLayout layoutSeeWeichart;
    @BindView(R.id.tv_qq_qun)
    TextView tvQqQun;
    @BindView(R.id.layout_pingjia)
    LinearLayout layoutPingjia;
    @BindView(R.id.layout_out_login)
    LinearLayout layoutOutLogin;
    Unbinder unbinder;
    Unbinder unbinder1;
    //private ArrayList<String> headPhoto = new ArrayList<>();    //

    private OrCodeDialog orCodeDialog;
    private Intent intent;
    private Dialog mDialog;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 200;


    private final int REQUEST_CODE_TAKE_PHOTO = 100;
    private final int REQUEST_CODE_CHOICE_PHOTO = 200;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //调用照相机返回图片临时文件
    private File tempFile;
    private String weixinUrl = "";
    private File takePhotoFile;
    private String picturePath;
    private File file;
    private Bitmap bitmapWeixinUrl;
    private int currentType;

    //tvName.setText(mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length()));

//1.个人信息：
//            1.1如果家长没有完成个人信息完善，那么相应的“完善个人信息”文案按钮变红。点击变红文案按钮，进入相应的完善训练儿童个人信息页面。
//            1.2个人信息完成后，“完善个人信息”文案按钮消失，替换为下方的儿童个人信息。
//            1.2.1儿童昵称字段：“小糊涂”显示的是家长填写的儿童昵称字段。点击下方修改按钮进入修改儿童昵称页面。
//            1.2.2账号密码字段：显示的是设置的登录密码，默认为隐藏状态。点击进下方修改进入修改密码页面。
//            1.2.3手机号字段;显示家长填写的注册手机号，隐藏中间4位。点击下方的修改按钮，进入修改手机号页面。
//            2.当前版本里面的评价打分按钮，点击进入应用市场评价评分页面。
//            3.退出登录按钮：点击退出登录，进入到注册页。
//            4.上方的儿童头像右侧按钮字段为“更改儿童头像”，点击儿童头像或者按钮，调用系统相册，页面下方弹出拍照，或从相册中选择选项，选择成功，图片自动更换；

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.frag_more, null);
        unbinder = ButterKnife.bind(this, view);
        //创建拍照存储的临时文件
        createCameraTempFile(savedInstanceState);
        UserBean userBean = new Gson().fromJson(new PreferencesHelper(getActivity()).getUserInfo(), UserBean.class);

        if (userBean != null) {
            if ("2".equals(userBean.getIsRemind())) {
                layoutWriteData.setVisibility(View.GONE);
                llEditMessage.setVisibility(View.VISIBLE);
            } else if ("1".equals(userBean.getIsRemind())) {
                layoutWriteData.setVisibility(View.VISIBLE);
                llEditMessage.setVisibility(View.GONE);
            }
        }

        getIsRemindDialog(new PreferencesHelper(getActivity()).getToken());
        //TODO  暂时写成“1”     MyUtils.getAppVersion(getActivity())
        getVersionMessage(BuildConfig.VERSION_NAME, "1", new PreferencesHelper(getActivity()).getToken());

        EventBusUtil.register(this);

        return view;
    }


    public void getIsRemindDialog(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getAssessmentReview(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            assementReviewBean = assementReviewBeanBaseBean.data;
                            if (assementReviewBean != null) {

                                if ("2".equals(assementReviewBean.getIsRemind())) {
                                    layoutWriteData.setVisibility(View.GONE);
                                    llEditMessage.setVisibility(View.VISIBLE);
                                } else if ("1".equals(assementReviewBean.getIsRemind())) {
                                    layoutWriteData.setVisibility(View.VISIBLE);
                                    llEditMessage.setVisibility(View.GONE);
                                }

                            }
                        } else if (assementReviewBeanBaseBean.code == 205 || assementReviewBeanBaseBean.code == 209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(getActivity());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePhone(UpdatePhoneEvent event) {
        getVersionMessage(BuildConfig.VERSION_NAME, "1", new PreferencesHelper(getActivity()).getToken());
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void NickNameChangeBean(NickNameChangeBean event) {
//        tvUserName.setText(event.getNickName());
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }


    @Override
    protected void initLazyData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getVersionMessage(String versionName, String type, String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getVersionMessageData(versionName, type, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<VersionMessageBean>>(getActivity(), null) {
                    @Override
                    public void onNext(BaseBean<VersionMessageBean> baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.code == 200) {
                            VersionMessageBean versionMessageBean = baseBean.data;
                            if (versionMessageBean != null) {
                                if (versionMessageBean.getImg()==null||TextUtils.isEmpty(versionMessageBean.getImg())){
                                    if (TextUtils.isEmpty(new PreferencesHelper(getActivity()).getPhoto())||"null".equals(new PreferencesHelper(getActivity()).getPhoto())) {
                                        String chilSex = new PreferencesHelper(getActivity()).getSaveSex();
                                        if (chilSex.equals("0")) {//儿童性别（0：男 1：女）
                                            ivHoldWhite.setVisibility(View.VISIBLE);
                                            GlideUtil.display(getActivity(), R.drawable.nan111, imgUserData);
                                        } else {
                                            ivHoldWhite.setVisibility(View.VISIBLE);
                                            GlideUtil.display(getActivity(), R.drawable.nv111, imgUserData);
                                        }
                                    } else {
                                        String photo = new PreferencesHelper(getActivity()).getPhoto();//这是最新的数据。登录的时候，存储。修改头像的时候，存储。

                                        GlideUtil.display(getActivity(), photo, imgUserData);
                                    }
                                }else {
                                    Picasso.with(getActivity())
                                            .load(versionMessageBean.getImg())
                                            .placeholder(R.drawable.iv_placehold)
                                            .error(R.drawable.iv_placehold)
                                            .into(imgUserData);
                                }
                                if (versionMessageBean.getPhone() != null) {
                                    String mobile = versionMessageBean.getPhone();
                                    String phone = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
                                    tvButtomUserPhone.setText(phone);
                                    tvUpdateHead.setText(phone);
                                }
                                if (versionMessageBean.getName() != null) {
                                    tvUserName.setText(versionMessageBean.getName());
                                }
                                if (versionMessageBean.getSuggest().getNetwork() != null) {
                                    tvWangzhan.setText(versionMessageBean.getSuggest().getNetwork());
                                }
                                if (versionMessageBean.getSuggest().getMail() != null) {
                                    tvYouxiang.setText(versionMessageBean.getSuggest().getMail());
                                }
                                if (versionMessageBean.getSuggest().getQqun() != null) {
                                    tvQqQun.setText(versionMessageBean.getSuggest().getQqun());
                                }

                                weixinUrl = versionMessageBean.getSuggest().getWeixin();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (!TextUtils.isEmpty(weixinUrl)) {
                                                bitmapWeixinUrl = Picasso.with(getActivity()).load(weixinUrl).get();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                                Log.e("weixin", weixinUrl + "___________");
                                if (weixinUrl == null || TextUtils.isEmpty(weixinUrl)) {
                                    llShowWinxin.setVisibility(View.GONE);
                                } else {
                                    llShowWinxin.setVisibility(View.VISIBLE);
                                }
                                tvVersionid.setText("V"+versionMessageBean.getVersions().getNumber());
                            }
                        } else if (baseBean.code == 205 || baseBean.code == 209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(getActivity());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });
    }

    @OnClick({R.id.tv_updatehead, R.id.img_home_right, R.id.layout_write_data, R.id.layout_change_user_name, R.id.layout_change_user_pass
            , R.id.layout_change_user_phone, R.id.layout_see_weichart, R.id.layout_pingjia, R.id.layout_out_login,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_updatehead:
//                uploadHeadImage();
//                DialogPic();
                /** * create方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断 * selectPicture方法参数分别为图片的裁剪宽、裁剪高、宽比例、高比例。默认不传则为宽200，高200，宽高比例为1：1。 */
                PictureSelector.create(MoreFragment.this, PictureSelector.SELECT_REQUEST_CODE).selectPicture(200, 200, 1, 1);

                break;

            case R.id.img_home_right:
                if (!"2".equals(((MainActivity)getActivity()).getIsRemind())){
                    return;
                }
                mActivity.finish();
                break;
            case R.id.layout_write_data:
                intent = new Intent(mContext, PerfectionChildrenInfoActivity.class);
                intent.putExtra("registertype", "back");
                startActivity(intent);
                break;
            case R.id.layout_change_user_name:
                intent = new Intent(mContext, ModifyingChildNicknamesActivity.class);
                intent.putExtra("TYPE", 0);
                startActivityForResult(intent, 1000);
                break;
            case R.id.layout_change_user_pass://修改密码
                intent = new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_change_user_phone:
                intent = new Intent(mContext, EditDataActivity.class);
                intent.putExtra("TYPE", 2);
                startActivity(intent);
                break;
            case R.id.layout_see_weichart:

                if (weixinUrl == null || TextUtils.isEmpty(weixinUrl)) {
                    return;
                } else {
                    if (orCodeDialog == null) {
                        orCodeDialog = new OrCodeDialog(mContext);
                        orCodeDialog.setQrUrl(weixinUrl);
                        orCodeDialog.setOnOrCodeClickListener(new OnOrCodeClickListener() {
                            @Override
                            public void onShared(String imgUrl) {
                                //init code
                                orCodeDialog.dismiss();

                                showDialog("https://www.baidu.com/", "标题", "描述", weixinUrl);
                            }

                            /**
                             * @param imgUrl
                             */
                            @Override
                            public void onSave(String imgUrl) {
                                //init code

                                orCodeDialog.dismiss();

//                                ImageView ivQrcode = orCodeDialog.ivQrcode;
                                boolean b = saveImageToGallery(getActivity(), bitmapWeixinUrl);
                                if (b) {
                                    ToastUtil.showToast(mContext, "保存图片成功");
                                } else {
                                    ToastUtil.showToast(mContext, "保存图片失败");

                                }
                            }
                        });
                    }
                    orCodeDialog.show();

                }
                break;
            case R.id.layout_pingjia:
                break;
            case R.id.layout_out_login:
                PreferencesHelper preferencesHelper = new PreferencesHelper(getActivity());
                preferencesHelper.saveToken("");
                preferencesHelper.saveSmsCode("86");

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);

                EventBusUtil.post(new ExitLoginBean());

                getActivity().finish();
                break;
        }
    }

    /**
     * 分享页面
     */
    private void showDialog(String url, String title, String content, String imageUrl) {

        UMImage thumb = new UMImage(getActivity(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543762113&di=c180e611c852d481f899b4a676d4ad1d&imgtype=jpg&er=1&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmw690%2F005AEZhdty6Kw48IBlw6a%26amp%3B690");
//        UMWeb web = new UMWeb(url);
//        web.setTitle(title);//标题
//        web.setThumb(thumb);  //缩略图
//        web.setDescription(content);//描述

        UMImage image = new UMImage(getActivity(), imageUrl);//网络图片
        image.setTitle(title);//标题
        image.setThumb(thumb);  //缩略图
        image.setDescription(content);//描述

        new ShareAction(getActivity())
//                .withMedia(web)
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
//                        ToastUtil.showToast(MyShareActivity.this, share_media.name() + " 分享");
                        currentType = -1;
                        if ("QQ".equals(share_media.name()) || "QZONE".equals(share_media.name())) {
                            currentType = 1;
                        } else if ("WEIXIN_CIRCLE".equals(share_media.name()) || "WEIXIN".equals(share_media.name())) {
                            currentType = 2;
                        }
//                        else if ("ALIPAY".equals(share_media.name())) {
//                            currentType = 4;
//                        }
//                        if (type != -1) {
//
//                        }
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {


                        if (share_media.name().equals("WEIXIN_FAVORITE")) {
//                            ToastUtil.showToast(MyShareActivity.this, share_media + " 收藏成功啦");
                        } else {
                            if (share_media != SHARE_MEDIA.MORE && share_media != SHARE_MEDIA.SMS
                                    && share_media != SHARE_MEDIA.EMAIL
                                    && share_media != SHARE_MEDIA.FLICKR
                                    && share_media != SHARE_MEDIA.FOURSQUARE
                                    && share_media != SHARE_MEDIA.TUMBLR
                                    && share_media != SHARE_MEDIA.POCKET
                                    && share_media != SHARE_MEDIA.PINTEREST
                                    && share_media != SHARE_MEDIA.INSTAGRAM
                                    && share_media != SHARE_MEDIA.GOOGLEPLUS
                                    && share_media != SHARE_MEDIA.YNOTE
                                    && share_media != SHARE_MEDIA.EVERNOTE) {
//                                ToastUtil.showToast(MyShareActivity.this, share_media + " 分享成功啦");
                            }

                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        if (share_media != SHARE_MEDIA.MORE && share_media != SHARE_MEDIA.SMS
                                && share_media != SHARE_MEDIA.EMAIL
                                && share_media != SHARE_MEDIA.FLICKR
                                && share_media != SHARE_MEDIA.FOURSQUARE
                                && share_media != SHARE_MEDIA.TUMBLR
                                && share_media != SHARE_MEDIA.POCKET
                                && share_media != SHARE_MEDIA.PINTEREST
                                && share_media != SHARE_MEDIA.INSTAGRAM
                                && share_media != SHARE_MEDIA.GOOGLEPLUS
                                && share_media != SHARE_MEDIA.YNOTE
                                && share_media != SHARE_MEDIA.EVERNOTE) {
//                            ToastUtil.showToast(MyShareActivity.this, share_media + " 分享失败啦");
                            if (throwable != null) {
                                Log.d("throw", "throw:" + throwable.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
//                        ToastUtil.showToast(MyShareActivity.this, share_media + " 分享取消了");

                    }
                })
                .open();
    }


    //保存文件到指定路径
    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


//    public static void saveFile(Bitmap bm, String fileName, String path) throws IOException {
//        String subForder = SAVE_REAL_PATH + path;
//        File foder = new File(subForder);
//        if (!foder.exists()) {
//            foder.mkdirs();
//        }
//        File myCaptureFile = new File(subForder, fileName);
//        if (!myCaptureFile.exists()) {
//            myCaptureFile.createNewFile();
//        }
//
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//        bos.flush();
//        bos.close();
//
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        context.sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
//    }

    /**
     */

    public void ossFileUpload() {
        PreferencesHelper helper = new PreferencesHelper(getActivity());
        String token = helper.getToken();

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());

        String url = Setting.ossFileUpload();
        OkHttpUtils
                .post().params(stringStringHashMap)
                .addFile("image", file.getName(), file)
                .url(url)//接口地址
                .id(2)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(getActivity(), this));

    }

    /**
     * @param s
     */
    public void updateChildInfo(String s) {
        String url = Setting.updateChildInfo();

        PreferencesHelper helper = new PreferencesHelper(getActivity());
        String token = helper.getToken();

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", token);
        stringStringHashMap.put("photo", s);


        Log.e("数据", "stringStringHashMap:" + stringStringHashMap.toString());
        OkHttpUtils
                .post().params(stringStringHashMap)
//                .addFile("photo", file.getName(), file)
                .url(url)//接口地址
                .id(3)//XX接口的标识
                .build()
                .execute(new BaseStringCallback_Host(getActivity(), this));

    }

    /**
     * 成功回调
     *
     * @param object XX接口
     * @param data   字符串数据。用  new JSONObject(result);
     */
    @Override
    public void RequestComplete(Object object, Object data) {
        if (object.equals(2)) {//标记那个接口

            String result = (String) data;
            getActivity().runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
//                      {"msg":"上传成功！","code":200,"data":{"images":["http://yuudee.oss-cn-beijing.aliyuncs.com/7b66636113f64796bb6e666c3e0809bd.temp"]}}
                            Gson gson = new Gson();
                            FileUploadBean model = gson.fromJson(result,
                                    new TypeToken<FileUploadBean>() {
                                    }.getType());

                            String s = model.getData().getImages().get(0);
                            Log.e("数据", "images" + s.toString());
                            updateChildInfo(s);

                            PreferencesHelper helper = new PreferencesHelper(getActivity());
                            helper.savePhto(s);

                            GlideUtil.display(getActivity(), s, imgUserData);

                            EventBusUtil.post(new HeadChangeBean());
                        }
                        ToastUtil.showToast(getActivity(), msg1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if (object.equals(3)) {//标记那个接口

            String result = (String) data;
            getActivity().runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String msg1 = (String) jsonObject.getString("msg");

                        if (code.equals("200")) {
//
                        }
                        ToastUtil.showToast(getActivity(), msg1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void RequestError(Object var1, int var2, String var3) {

        getActivity().runOnUiThread(new Runnable() {//在UI线程处理逻辑，当操作控件的时候
            @Override
            public void run() {

                ToastUtil.showToast(mContext, var3 + "");
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (intent != null) {
                picturePath = intent.getStringExtra(PictureSelector.PICTURE_PATH);
                file = new File(picturePath);
                ossFileUpload();
//                Log.e("picturePath", "picturePath" + picturePath);
            }
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String newNickName = intent.getStringExtra("name");
                tvUserName.setText(newNickName);
            }
        }

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            String string = new PreferencesHelper(getActivity()).getString("sp", "nickname",
                    "");
            tvUserName.setText(string);
        }

    }

/*
    public void updateHeadPic() {
        File fileIDFront = new File(headPhoto.get(headPhoto.size() - 1));
        RequestBody IDFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileIDFront);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", new PreferencesHelper(PersionMessageActivity.this).getToken())
                .addFormDataPart("pic[]", fileIDFront.getName(), IDFrontBody);

        BaseSubscriber<BaseBean<Object>> updateHeadPicSubscriber = new BaseSubscriber<BaseBean<Object>>(this, null) {
            @Override
            public void onNext(BaseBean<Object> objectBaseBean) {
                super.onNext(objectBaseBean);
                Log.i("XXX", objectBaseBean.code + "  " + objectBaseBean.message);
                if (objectBaseBean.code == 0) {
                    EventBusUtil.post(new UpdateInformationEvent());
                }
                ToastUtil.showToast(PersionMessageActivity.this, objectBaseBean.message);
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
            }
        };
        HttpHelp.getInstance().create(RemoteApi.class).updateHeadPic(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateHeadPicSubscriber);

    }
*/


    /**
     * 外部存储权限申请返回
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCarema();
            } else {
                // Permission Denied
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            } else {
                // Permission Denied
            }
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        startActivityForResult(cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity.newIntent(getActivity(), null, 1, null, false), REQUEST_PICK);
    }

    /**
     * 跳转到照相机
     */
    private void gotoCarema() {
        try {
            tempFile = TakePhotoUtils.takePhoto(getActivity(), "tempFile", REQUEST_CAPTURE);
        } catch (IOException e) {
            ToastUtil.showToast(getActivity(), "拍照失败");
            e.printStackTrace();
        }
    }

    /**
     * 打开截图界面
     *
     * @param uri
     */
//    public void gotoClipActivity(Uri uri) {
//        if (uri == null) {
//            return;
//        }
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), ClipImageActivity.class);
//        intent.setData(uri);
//        startActivityForResult(intent, REQUEST_CROP_PHOTO);
//    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
//    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
//        if (null == uri) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if (scheme == null)
//            data = uri.getPath();
//        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
//            data = uri.getPath();
//        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
//            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
//            if (null != cursor) {
//                if (cursor.moveToFirst()) {
//                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                    if (index > -1) {
//                        data = cursor.getString(index);
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
//    }

    /**
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }


}
