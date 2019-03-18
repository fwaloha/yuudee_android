package permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2016/11/1.
 */
public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void handleIntent(Intent intent) {
        String[] permissions = intent.getStringArrayExtra("permissions");
        int requestCode = intent.getIntExtra("requestCode", PermissionUtil.PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionUtil.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
        finish();
    }

    /**
     *  mMultiBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    PermissionUtil.getInstance().request(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS},
    new PermissionResultCallBack() {
    @Override
    public void onPermissionGranted() {
    Toast.makeText(MainActivity.this, "all granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionGranted(String... permissions) {
    StringBuilder builder = new StringBuilder();
    for (String permission : permissions) {
    builder.append(permission.substring(permission.lastIndexOf(".") + 1) + " ");
    }
    Toast.makeText(MainActivity.this, builder.toString() + " is granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDenied(String... permissions) {
    StringBuilder builder = new StringBuilder();
    for (String permission : permissions) {
    builder.append(permission.substring(permission.lastIndexOf(".") + 1) + " ");
    }
    Toast.makeText(MainActivity.this, builder.toString() + " is denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRationalShow(String... permissions) {
    StringBuilder builder = new StringBuilder();
    for (String permission : permissions) {
    builder.append(permission.substring(permission.lastIndexOf(".") + 1) + " ");
    }
    Toast.makeText(MainActivity.this, builder.toString() + " show Rational", Toast.LENGTH_SHORT).show();
    }
    });
    }
    });
     mSingleBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    PermissionUtil.getInstance().request(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR},
    new PermissionOriginResultCallBack() {

    @Override
    public void onResult(List<PermissionInfo> acceptList, List<PermissionInfo> rationalList, List<PermissionInfo> deniedList) {
    if (!acceptList.isEmpty()) {
    Toast.makeText(MainActivity.this, acceptList.get(0).getName() + " is accepted", Toast.LENGTH_SHORT).show();
    }
    if (!rationalList.isEmpty()) {
    Toast.makeText(MainActivity.this, rationalList.get(0).getName() + " is rational", Toast.LENGTH_SHORT).show();
    }
    if (!deniedList.isEmpty()) {
    Toast.makeText(MainActivity.this, deniedList.get(0).getName() + " is denied", Toast.LENGTH_SHORT).show();
    }
    }
    });
    }
    });

     mSecondBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }
    });

     mCheckSingleBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    int result = PermissionUtil.getInstance().checkSinglePermission(Manifest.permission.CAMERA);
    Toast.makeText(MainActivity.this, "result:" + result, Toast.LENGTH_SHORT).show();
    }
    });

     mCheckMultiBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    Map<String, List<PermissionInfo>> map = PermissionUtil.getInstance().checkMultiPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS});
    Toast.makeText(MainActivity.this, map.toString(), Toast.LENGTH_SHORT).show();
    }
    });
     */
}