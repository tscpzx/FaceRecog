package com.cpzx.facerecog;


import com.cjw.library.http.rx.HttpResult;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpService {
  @FormUrlEncoded
  @POST("/cpfr/app/device_list")
  Observable<HttpResult<List<JsonObject>>> device_list(@FieldMap Map<String, String> map);
}
