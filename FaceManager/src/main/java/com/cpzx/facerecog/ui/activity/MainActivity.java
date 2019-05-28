package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.adapter.FunctionGridAdapter;
import com.cpzx.facerecog.util.SharedPreferenceUtil;

import butterknife.BindView;

/**
 * @author xwr
 * @date 2019/5/28
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.grid_view)
    GridView mGridView;
    private FunctionGridAdapter mFunctionGridAdapter;
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init() {
        initData();
    }

    private void initData() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        mFunctionGridAdapter = new FunctionGridAdapter(this);
        mGridView.setAdapter(mFunctionGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    goActivity(ConfigActivity.class);
                } else {
                    if (null == sharedPreferenceUtil.getString("token") || sharedPreferenceUtil.getString("token").equals("")) {
                        showToast("请先登录~");
                        goActivity(LoginActivity.class);
                    } else {
                        switch (position) {
                            case 0:
                                goActivity(DeviceManagerActivity.class);
                                break;
                            case 1:
                                goActivity(PersonManagerActivity.class);
                                break;
                            case 2:
                                goActivity(RecordManagerActivity.class);
                                break;
                            case 3:
                                goActivity(AttendManagerActivity.class);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
