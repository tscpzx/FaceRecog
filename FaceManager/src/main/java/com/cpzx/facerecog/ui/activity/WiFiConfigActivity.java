package com.cpzx.facerecog.ui.activity;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.adapter.WifiListAdapter;
import com.cpzx.facerecog.model.WifiBean;
import com.cpzx.facerecog.util.CollectionUtils;
import com.cpzx.facerecog.util.QRCodeUtil;
import com.cpzx.facerecog.util.WifiUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WiFiConfigActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "chenda";
    private final static int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;
    private EditText wifiName_edit, wifiType_edit;
    private EditText password_edit;
    private Button search;
    private Button connect;
    private ListView listView;
    private ImageView iv_qrcode;
    private List<WifiBean> wifiBeanList = new ArrayList<>();
    private WifiListAdapter adapter;
    private WifiBean mWifiBean = new WifiBean();
    private WifiBroadcastReceiver wifiReceiver;//广播
    private boolean isConnection = false;
    private TextView title;
    private ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wificonfig);

        initView();
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
        wifiName_edit = findViewById(R.id.et_wifiname);
        wifiType_edit = findViewById(R.id.et_wifistyle);
        password_edit = findViewById(R.id.et_wifipassword);
        search = findViewById(R.id.search);
        iv_qrcode = findViewById(R.id.txt);
        listView = findViewById(R.id.listView);
        connect = findViewById(R.id.connect);
        title = findViewById(R.id.tv_title);
        goback = findViewById(R.id.iv_go_back);
        title.setText("网络配置");
        adapter = new WifiListAdapter(this, wifiBeanList);
        listView.setAdapter(adapter);
        goback.setOnClickListener(this);
        search.setOnClickListener(this);
        connect.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mWifiBean = wifiBeanList.get(position);
                wifiName_edit.setText(wifiBeanList.get(position).getWifiName());
                wifiType_edit.setText(wifiBeanList.get(position).getCapabilities());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                listView.setVisibility(View.VISIBLE);
                iv_qrcode.setVisibility(View.GONE);
                sortScaResult();
                break;
            case R.id.connect:
                connectWifi();
                break;
            case R.id.iv_go_back:
                finish();
                break;
            default:
                break;
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
        if (wifiName_edit.getText().toString().equals("")) {
            Toast.makeText(WiFiConfigActivity.this, "请输入WiFi名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (wifiType_edit.getText().toString().equals("")) {
            Toast.makeText(WiFiConfigActivity.this, "请输入WiFi类型", Toast.LENGTH_SHORT).show();
            return;
        }
        mWifiBean.setWifiName(wifiName_edit.getText().toString());
        mWifiBean.setPassword(wifiType_edit.getText().toString());
        mWifiBean.setPassword(password_edit.getText().toString());
        String info = "WIFI:T:" + mWifiBean.getCapabilities() + ";P:\"" + mWifiBean.getPassword() + "\";S:" + mWifiBean.getWifiName() + ";";
        listView.setVisibility(View.GONE);
        //更新UI
        iv_qrcode.setVisibility(View.VISIBLE);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap qr = QRCodeUtil.createQRImage(info, 1080, 1080, bmp);
        iv_qrcode.setImageBitmap(qr);

//        //连接方式
//        WifiConfiguration wifiConfiguration = WifiUtil.createWifiConfig(wifiName_edit.getText().toString(), password_edit.getText().toString(), WifiUtil.getWifiCipher(mWifiBean.getCapabilities()));
//        //连接网络
//        WifiUtil.connectNet(wifiConfiguration, this);
    }
}
