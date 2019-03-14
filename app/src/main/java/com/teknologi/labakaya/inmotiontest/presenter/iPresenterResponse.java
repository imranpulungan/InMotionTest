package com.teknologi.labakaya.inmotiontest.presenter;


import com.teknologi.labakaya.inmotiontest.api.response.ApiResponse;

public interface iPresenterResponse {
    void doSuccess(ApiResponse response, String tag);
    void doFail(String message);
    void doFailClient(int message);
    //    void doValidationError(List<ApiResponse.Error> errors);
    void doConnectionError();
}
