package com.cpzx.facerecog.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.util.NetUtil;
import com.cpzx.facerecog.util.QRCodeUtil;

import java.net.SocketException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 以太网配置
 *
 * @author xwr
 * @date 2019/5/28
 */
public class EthernetActivity extends BaseActivity {
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sp_equipment)
    Spinner spEquipment;
    @BindView(R.id.tv_r)
    TextView tvR;
    private String net;
    @BindView(R.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R.id.et_dns_address)
    EditText etDnsAddress;
    @BindView(R.id.rg_ip_type)
    RadioGroup rgIpType;
    @BindView(R.id.et_subnet_mask)
    EditText etSubnetMask;
    @BindView(R.id.et_gate_address)
    EditText etGateAddress;
    @BindView(R.id.btn_config)
    Button btnConfig;
    @BindView(R.id.code)
    ImageView ivCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethernet);
        init();
    }

    private void init() {
        tvTitle.setText("以太网配置");
        tvR.setText("取消");
        tvR.setVisibility(View.VISIBLE);
        initData();
    }

    @OnClick({R.id.iv_go_back, R.id.btn_config, R.id.tv_r})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.btn_config:
                verify();
                break;
            case R.id.tv_r:
                ivCode.setVisibility(View.GONE);
                initData();
                break;
            default:
                break;
        }
    }

    private void initData() {
        if (NetUtil.getAllNetInterface() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NetUtil.getAllNetInterface());
            spEquipment.setAdapter(adapter);
            spEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    net = (String) spEquipment.getSelectedItem();
                    etDnsAddress.setText(NetUtil.getLocalDNS(net));
                    etSubnetMask.setText(NetUtil.getLocalMask(net));
                    etGateAddress.setText(NetUtil.getLocalGATE(net));
                    try {
                        etIpAddress.setText(NetUtil.getIpAddress(net));
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
            Toast.makeText(EthernetActivity.this, "无可用网卡", Toast.LENGTH_SHORT).show();
        }
    }

    private void verify() {
        String ip = etIpAddress.getText().toString();
        if (NetUtil.isIP(ip) || ip.equals("")) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            Bitmap qr = QRCodeUtil.createQRImage("网卡设备：" + net + ";ip地址：" + ip + ";子网掩码：" + etSubnetMask.getText().toString() + ";DNS地址：" + etDnsAddress.getText().toString() + ";网关地址：" + etGateAddress.getText().toString(), 800, 800, bmp);
            ivCode.setImageBitmap(qr);
            ivCode.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(EthernetActivity.this, "ip格式不正确", Toast.LENGTH_SHORT).show();
        }
    }

}
