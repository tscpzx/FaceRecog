package com.cpzx.facerecog;


import com.cjw.library.http.rx.HttpResult;
import com.cpzx.facerecog.model.Device;
import com.cpzx.facerecog.model.PageList;
import com.cpzx.facerecog.model.User;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpService {
    @FormUrlEncoded
    @POST("/cpfr/app/device_list")
    Observable<HttpResult<List<JsonObject>>> device_list(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/cpfr/user/login")
    Observable<HttpResult<User>> login(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/cpfr/manager/add_person")
    Observable<HttpResult<User>> addPerson(@Header("access_cpfr_token") String token, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/cpfr/device/list")
    Observable<HttpResult<PageList<Device>>> deviceList(@Header("access_cpfr_token") String token,@FieldMap Map<String, String> map);
}
