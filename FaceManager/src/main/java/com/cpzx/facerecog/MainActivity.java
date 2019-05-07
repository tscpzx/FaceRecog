package com.cpzx.facerecog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cjw.library.http.rx.HttpResult;
import com.cjw.library.http.rx.HttpResultSubscriber;
import com.cjw.library.http.rx.RxDoOnSubscribe;
import com.cjw.library.http.rx.RxSchedulers;
import com.cjw.library.http.rx.RxTrHttpMethod;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.tv)
  TextView mTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    doHttp();
  }

  private void doHttp() {
    Map<String, String> map = new HashMap<>();
    map.put("", "");

    RxTrHttpMethod.getInstance()
      .createService(HttpService.class)
      .device_list(map)
      .compose(RxSchedulers.<HttpResult<List<JsonObject>>>defaultSchedulers())
      .doOnSubscribe(new RxDoOnSubscribe(this))
      .subscribe(new HttpResultSubscriber<List<JsonObject>>(this) {
        @Override
        public void _onSuccess(List<JsonObject> result) {
        }
      });
  }
}
