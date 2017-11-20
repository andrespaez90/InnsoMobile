package com.innso.mobile.utils;


import com.google.gson.Gson;
import com.innso.mobile.utils.models.DefaultError;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorUtils {

    public static final int ORDER_UNAVAILABLE_CODE = 422;

    private static final String CONNECTION_ERROR = "Revisa tu conexión a internet.";

    public static String getMessageError(Throwable e) {
        e.printStackTrace();
        String error = "Server Error";
        try {
            error = e.getMessage();
            if (e instanceof HttpException) {

                ResponseBody responseBody = ((HttpException) e).response().errorBody();

                DefaultError defaultError;

                defaultError = new Gson().fromJson(responseBody.string(), DefaultError.class);

                error = defaultError.getError();
            } else if (e instanceof IOException) {
                error = CONNECTION_ERROR;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return error;
    }

    public static DefaultError getError(Throwable e) {
        DefaultError error = null;
        try {
            if (e instanceof HttpException) {
                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                DefaultError defaultError;
                defaultError = new Gson().fromJson(responseBody.string(), DefaultError.class);
                error = defaultError;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return error;
    }

}
