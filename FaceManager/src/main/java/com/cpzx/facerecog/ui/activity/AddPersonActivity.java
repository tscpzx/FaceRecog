package com.cpzx.facerecog.ui.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.model.Device;
import com.cpzx.facerecog.model.Person;
import com.cpzx.facerecog.presenter.AddPersonPresenter;
import com.cpzx.facerecog.presenter.impl.AddPersonPresenterImpl;
import com.cpzx.facerecog.util.ImageUtil;
import com.cpzx.facerecog.util.StringUtil;
import com.cpzx.facerecog.view.AddPersonView;
import com.cpzx.facerecog.widget.dialog.GetPictureDialog;
import com.cpzx.facerecog.widget.dialog.ListDialog;

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
    private String chooseDeviceIds = "";
    private byte[] headBytes;
    private Uri uritempFile;

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
                request();
                break;
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.iv_header:
                showGetPhotoDialog();
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
            Person person = new Person(etName.getText().toString(), etNum.getText().toString(), headBytes, chooseDeviceIds);
            mAddPersonPresenter.addPerson(person);
        }
    }

    private void showDeviceDialog() {
        if (mDeviceList.size() == 0) {
            showToast("暂无数据");
        } else {
            ListDialog listDialog = new ListDialog(this, mDeviceList, this, new ListDialog.GetClickedDevices() {
                @Override
                public void getDevices(List<Integer> devicesIds) {
                    List<Integer> list = devicesIds;
                    tvDeviceNum.setText("已选择：" + list.size() + "台设备");
                    String ids = StringUtil.listToString(list, ',');
                    chooseDeviceIds = ids;
                    Log.d("test", chooseDeviceIds);
                }
            });
            listDialog.show();
        }

    }

    protected void request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            } else {
                showGetPhotoDialog();
            }
        } else {
            showGetPhotoDialog();
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
    }


    private void showGetPhotoDialog() {
        new GetPictureDialog(this) {
            @Override
            public void takePhoto() {
                mAddPersonPresenter.getPhoto(TAKE_PHOTO);
            }

            @Override
            public void choosePic() {
                mAddPersonPresenter.getPhoto(CHOOSE_PHOTO);
            }
        }.show();
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
        finish();
        goActivity(AddPersonActivity.class);
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
            }
        } else if (requestCode == CROP_PHOTO) {
            if (data != null) {
                setImage(data);
            }
        }
    }

    private void setImage(Intent intent) {
        Bundle extra = intent.getExtras();
        if (extra != null) {
            Bitmap bitmap = extra.getParcelable("data");
            byte[] bytes = ImageUtil.BitmapToBytes(bitmap, 100);
            ivHeader.setImageBitmap(bitmap);
            ivHeader.setVisibility(View.VISIBLE);
            llChoosePicture.setVisibility(View.GONE);
            headBytes = ImageUtil.compressImageByBytes(bytes);
        } else {
            showToast("照片获取失败,请稍后再试");
        }
    }


    // 裁剪
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //开启临时权限
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //重点:针对7.0以上的操作
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
            uritempFile = uri;
        } else {
            uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        startActivityForResult(intent, CROP_PHOTO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddPersonPresenter.getDeviceList();
    }
}
