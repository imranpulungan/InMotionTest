package com.teknologi.labakaya.inmotiontest.presenter;

import android.content.Context;

import com.teknologi.labakaya.inmotiontest.R;
import com.teknologi.labakaya.inmotiontest.activity.MainActivity;
import com.teknologi.labakaya.inmotiontest.api.ApiClient;
import com.teknologi.labakaya.inmotiontest.api.response.ApiResponse;
import com.teknologi.labakaya.inmotiontest.api.response.UserResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class Presenter extends MainActivity {
    public static final String RES_CHECK_DATA_USER = "resp_check_user_is_exist";
    public static final String RES_GET_DATA_CATEGORY = "resp_get_data_category";
    public static final String RES_GET_DATA_PRODUCT = "resp_get_data_product";
    public static final String RES_GET_DATA_CART = "resp_get_data_cart";
    public static final String RES_POST_ADD_CARTLIST = "resp_post_data_wishlist";
    public static final String RES_GET_DATA_PROPERTY = "resp_get_data_property";
    public static final String RES_POST_ADD_WISHLIST = "resp_post_add_wishlist";
    public static final String RES_GET_DATA_TRANSACTION = "resp_get_data_transaction";
    public static final String RES_TRANSACTION = "resp_post_transaction";
    public static final int LIST_ACTION_ADD = 1;
    public static final int LIST_ACTION_REMOVE = 0;

    protected static Context context;
    protected iPresenterResponse presenterresponse;

    public Presenter(Context context, iPresenterResponse iPresenterResponse){
        this.context = context;
        this.presenterresponse = iPresenterResponse;
    }

    public ApiResponse handleErrorBody(ResponseBody errorBody) throws IOException {
        Converter<ResponseBody, ApiResponse> converter = ApiClient.getInstance(context).getClient().responseBodyConverter(ApiResponse.class, new Annotation[0]);
        ApiResponse responBody = converter.convert(errorBody);
        return responBody;
    }

    public void onFailure(Throwable t){
        if (t instanceof ConnectException)
            presenterresponse.doConnectionError();
        else
            presenterresponse.doFailClient(R.string.failed_connection);
    }

    protected void handleError(ResponseBody errorBody, int code) throws IOException{
        ApiResponse responBody = handleErrorBody(errorBody);
//        if (code == 422)
//            presenterresponse.doValidationError(responBody.error);
//        if (code >= 400 || code < 500)
//            presenterresponse.doFail(responBody.message);
        if (code >= 500)
            presenterresponse.doFailClient(R.string.server_error);
        else
            presenterresponse.doConnectionError();
    }

    public void getDataUser(int page){
        ApiClient.getInstance(context).getApi().getDataUser(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                    if (response.isSuccessful())
                        presenterresponse.doSuccess(response.body(), RES_CHECK_DATA_USER);
                    else handleError(response.errorBody(), response.code());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Presenter.this.onFailure(t);
            }
        });
    }
}
