package com.cpzx.facerecog.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.adapter.WifiListAdapter;
import com.cpzx.facerecog.model.WifiBean;
import com.cpzx.facerecog.util.CollectionUtils;
import com.cpzx.facerecog.util.QRCodeUtil;
import com.cpzx.facerecog.util.ScreenSettingUtil;
import com.cpzx.facerecog.util.WifiUtil;
import com.cpzx.facerecog.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * wifi配置
 *
 * @author xwr
 * @date 2019/5/28
 */
public class WiFiConfigActivity extends BaseActivity {
    private static final String TAG = WiFiConfigActivity.class.getSimpleName();
    private final static int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_r)
    TextView tvR;
    @BindView(R.id.et_wifiname)
    EditText etWifiname;
    @BindView(R.id.et_wifistyle)
    EditText etWifistyle;
    @BindView(R.id.et_wifipassword)
    EditText etWifipassword;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.lv_wifi_list)
    NoScrollListView lvWifiList;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.ll_wifi_tip)
    LinearLayout llWifiTip;

    private List<WifiBean> wifiBeanList = new ArrayList<>();
    private WifiListAdapter adapter;
    private WifiBean mWifiBean = new WifiBean();
    private WifiBroadcastReceiver wifiReceiver;//广播
    private boolean isConnection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wificonfig);
        init();
    }

    private void init() {
        initView();
        setWifiManager();
    }

    private void setWifiManager() {
        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifimanager.isWifiEnabled()) {
            wifiReceiver = new WifiBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);// 监听wifi开关变化的状态
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);// 监听wifi连接状态广播,是否连接了一个有效路由
            filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);//额外信息（wifi密码错误)
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);//扫描
            this.registerReceiver(wifiReceiver, filter);
            wifimanager.setWifiEnabled(true);
        } else {
            registerPermission();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wifiReceiver != null) {
            unregisterReceiver(wifiReceiver);
            wifiReceiver = null;
        }
    }

    @OnClick({R.id.iv_go_back, R.id.tv_r, R.id.btn_connect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.tv_r:
                etWifiname.setText(null);
                etWifipassword.setText(null);
                etWifistyle.setText(null);
                ivCode.setVisibility(View.GONE);
                llWifiTip.setVisibility(View.VISIBLE);
                lvWifiList.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_connect:
                connectWifi();
                break;
        }
    }

    /**
     * 监听wifi状态
     */
    public class WifiBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (state) {
                    case WifiManager.WIFI_STATE_DISABLED: {
                        Log.d(TAG, "WIFI已经关闭");
                        break;
                    }
                    case WifiManager.WIFI_STATE_DISABLING: {
                        Log.d(TAG, "WIFI正在关闭");
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLED: {
                        Log.d(TAG, "WIFI已经打开");
                        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifimanager.startScan();
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLING: {
                        Log.d(TAG, "WIFI正在打开");
                        break;
                    }
                    case WifiManager.WIFI_STATE_UNKNOWN: {
                        Log.d(TAG, "WIFI未知状态");
                        break;
                    }
                }
            } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (NetworkInfo.State.DISCONNECTED == info.getState()) {
                    // wifi没连接上
                    Log.d(TAG, "wifi没连接上");
                } else if (NetworkInfo.State.CONNECTED == info.getState()) {
                    // wifi连接上了
                    Log.d(TAG, "wifi连接上了");
                } else if (NetworkInfo.State.CONNECTING == info.getState()) {
                    // 正在连接
                    Log.d(TAG, "wifi正在连接");
                }
            } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int linkWifiResult = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 123);
                if (linkWifiResult == WifiManager.ERROR_AUTHENTICATING) {
                    Log.e(TAG, "onReceive:密码错误");
                }
            } else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
                Log.d(TAG, "wifi扫描完毕");
                registerPermission();
                if (wifiReceiver == null)
                    return;
                unregisterReceiver(wifiReceiver);
                wifiReceiver = null;
            }

        }
    }

    private void initView() {
        tvTitle.setText("wifi配置");
        tvR.setText("取消");
        tvR.setVisibility(View.VISIBLE);
        adapter = new WifiListAdapter(this, wifiBeanList);
        lvWifiList.setAdapter(adapter);
        lvWifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mWifiBean = wifiBeanList.get(position);
                etWifiname.setText(wifiBeanList.get(position).getWifiName());
                etWifistyle.setText(wifiBeanList.get(position).getCapabilities());
            }
        });
    }

    private void registerPermission() {
        List<String> permissionsList = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionsList.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
                return;
            }
        }
        sortScaResult();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            sortScaResult();
        }
    }


    /**
     * 获取wifi列表
     */
    private void sortScaResult() {
        List<ScanResult> scanResults = WifiUtil.noSameName(WifiUtil.getWifiScanResult(this));
        Log.d(TAG, "scanResults length = " + scanResults.size());
        if (scanResults.size() == 0) {
            WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifimanager.startScan();
            return;
        }
        wifiBeanList.clear();
        if (!CollectionUtils.isNullOrEmpty(scanResults)) {
            for (int i = 0; i < scanResults.size(); i++) {
                WifiBean wifiBean = new WifiBean();
                wifiBean.setWifiName(scanResults.get(i).SSID);
                wifiBean.setCapabilities(WifiUtil.getCapabilitiesString(scanResults.get(i).capabilities));
                wifiBean.setLevel(WifiUtil.getLevel(scanResults.get(i).level) + "");
                wifiBeanList.add(wifiBean);
                //排序
                Collections.sort(wifiBeanList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void connectWifi() {
        if (etWifiname.getText().toString().equals("")) {
            showToast("请输入WiFi名称");
            return;
        }
        if (etWifistyle.getText().toString().equals("")) {
            showToast("请输入wifi类型");
            return;
        }
        if (etWifipassword.getText().toString().equals("")) {
            showToast("请输入密码");
            return;
        }
        mWifiBean.setWifiName(etWifiname.getText().toString());
        mWifiBean.setPassword(etWifipassword.getText().toString());
        mWifiBean.setCapabilities(etWifistyle.getText().toString());
        String info = "wifi:T:" + mWifiBean.getCapabilities() + ";P:\"" + mWifiBean.getPassword() + "\";S:" + mWifiBean.getWifiName() + ";";
        llWifiTip.setVisibility(View.GONE);
        lvWifiList.setVisibility(View.GONE);
        ivCode.setVisibility(View.VISIBLE);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap qr = QRCodeUtil.createQRImage(info, 800, 800, bmp);
        ivCode.setImageBitmap(qr);
        if (ScreenSettingUtil.getScreenBrightnes(this) <= 200) {
            ScreenSettingUtil.setScreenBrightness(this, 200);
        }
    }
}
