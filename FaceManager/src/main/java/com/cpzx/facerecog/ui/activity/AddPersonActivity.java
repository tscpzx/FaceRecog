package com.cpzx.facerecog.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.adapter.DeviceListAdapter;
import com.cpzx.facerecog.model.Device;
import com.cpzx.facerecog.model.Person;
import com.cpzx.facerecog.presenter.AddPersonPresenter;
import com.cpzx.facerecog.presenter.impl.AddPersonPresenterImpl;
import com.cpzx.facerecog.view.AddPersonView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cpzx.facerecog.Constant.CHOOSE_PHOTO;
import static com.cpzx.facerecog.Constant.CROP_PHOTO;
import static com.cpzx.facerecog.Constant.IMAGE_FILE_NAME;
import static com.cpzx.facerecog.Constant.TAKE_PHOTO;

public class AddPersonActivity extends BaseActivity implements AddPersonView {
    private final String TAG = AddPersonActivity.class.getSimpleName();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.ll_choose_pic)
    LinearLayout llChoosePicture;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.tv_device_num)
    TextView tvDeviceNum;
    private AddPersonPresenter mAddPersonPresenter;
    private List<Device> mDeviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        init();
    }

    private void init() {
        initData();
    }

    private void initData() {
        tvTitle.setText("添加用户");
        mAddPersonPresenter = new AddPersonPresenterImpl(this, this);
        mAddPersonPresenter.getDeviceList();
    }


    @OnClick({R.id.ll_choose_pic, R.id.iv_go_back, R.id.btn_add, R.id.iv_header, R.id.tv_device_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_choose_pic:
                showDialog();
                break;
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.iv_header:
                showDialog();
                break;
            case R.id.btn_add:
                addPerson();
                break;
            case R.id.tv_device_num:
                showDeviceDialog();
                break;
            default:
                break;
        }
    }

    private void addPerson() {
        if (etName.getText().toString().isEmpty()) {
            showToast("姓名不能为空");
        } else if (ivHeader.getDrawable() == null) {
            showToast("请上传头像");
        } else {
            Person person = new Person(etName.getText().toString(), etNum.getText().toString(), ((BitmapDrawable) (ivHeader).getDrawable()).getBitmap());
            mAddPersonPresenter.addPerson(person);
        }
    }

    private void showDeviceDialog() {
        AlertDialog.Builder builder;
        // 动态加载一个listview的布局文件进来
        LayoutInflater inflater = LayoutInflater.from(AddPersonActivity.this);
        View view = inflater.inflate(R.layout.dialog_device_lsit, null);

        // 给ListView绑定内容
        ListView listview = (ListView) view.findViewById(R.id.lv_device_list);
        DeviceListAdapter adapter = new DeviceListAdapter(this, mDeviceList);
        // 给listview加入适配器
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        builder = new AlertDialog.Builder(this);
        //设置加载的listview
        builder.setView(view);
        builder.create().show();

    }

    private void showDialog() {
    }


    @Override
    public void takePhoto(Intent intent, int code) {
        startActivityForResult(intent, code);
    }

    @Override
    public void choosePhoto(Intent intent, int code) {
        startActivityForResult(intent, code);
    }

    @Override
    public void onAddSuccess() {
        showToast("添加成功");
        refresh();
    }


    @Override
    public void onAddFail() {
        Log.d(TAG, "添加失败");
    }

    @Override
    public void onGetDeviceListSuccess(List<Device> deviceList) {
        mDeviceList = deviceList;
    }

    @Override
    public void onGetDeviceListFail() {
        Log.d(TAG, "获取失败");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PHOTO) {
            if (data != null) {
                cropPhoto(data.getData());
            }
        } else if (requestCode == TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                File file = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                cropPhoto(Uri.fromFile(file));
            } else {
                showToast("未找到存储卡");
            }
        } else if (requestCode == CROP_PHOTO) {
            if (data != null) {
                setImage(data);
            }
        }
    }

    // 设置图片
    private void setImage(Intent intent) {
        Bundle extra = intent.getExtras();
        if (extra != null) {
            Bitmap bitmap = extra.getParcelable("data");
            ivHeader.setImageBitmap(bitmap);
            ivHeader.setVisibility(View.VISIBLE);
            llChoosePicture.setVisibility(View.GONE);
        }
    }

    // 裁剪
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        // 裁剪框的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪出图片的大小
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);
    }

}
