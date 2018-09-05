package one.bihe.yunzuan.api;

import com.google.gson.reflect.TypeToken;
import okhttp3.Headers;
import okhttp3.Response;
import one.bihe.yunzuan.constant.ApiConstant;
import one.bihe.yunzuan.entry.ApiTransactionVo;
import one.bihe.yunzuan.util.HttpUtil;
import one.bihe.yunzuan.util.JsonHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class YunZuanHttpRequest {

    /**
     * 获取余额
     * @param params
     * @param sign
     * @return
     */
    public static ResultData<String> getBalance(Map<String, String> params, String sign) {
        HttpUtil.Param param = new HttpUtil.Param(params);
        try {
            Response response = HttpUtil.post(ApiConstant.API_URL, "/yz/getBalance", getHeaders(sign), param);
            String result = response.body().string();
            ResultData<String> resultData = JsonHelper.fromJson(result, new TypeToken<ResultData<String>>() {
            }.getType());
            if (resultData != null) {
                return resultData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.createError("");
    }

    /**
     * 获取交易次数
     * @param params
     * @param sign
     * @return
     */
    public static ResultData<String> getTransactionCount(Map<String, String> params, String sign) {
        HttpUtil.Param param = new HttpUtil.Param(params);
        try {
            Response response = HttpUtil.post(ApiConstant.API_URL, "/yz/getTransactionCount", getHeaders(sign), param);
            String result = response.body().string();
            ResultData<String> resultData = JsonHelper.fromJson(result, new TypeToken<ResultData<String>>() {
            }.getType());
            if (resultData != null) {
                return resultData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.createError("");
    }

    /**
     * 获取交易列表
     * @param params
     * @param sign
     * @return
     */
    public static ResultData<ApiTransactionVo> getTransactionList(Map<String, String> params, String sign) {
        HttpUtil.Param param = new HttpUtil.Param(params);
        try {
            Response response = HttpUtil.post(ApiConstant.API_URL, "/yz/getTransactionList", getHeaders(sign), param);
            String result = response.body().string();
            ResultData<ApiTransactionVo> resultData = JsonHelper.fromJson(result, new TypeToken<ResultData<ApiTransactionVo>>() {
            }.getType());
            if (resultData != null) {
                return resultData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.createError("");
    }

    /**
     * 发送交易
     * @param params
     * @param sign
     * @return
     */
    public static ResultData<String> sendRawTransaction(Map<String, String> params, String sign) {
        HttpUtil.Param param = new HttpUtil.Param(params);
        try {
            Response response = HttpUtil.post(ApiConstant.API_URL, "/yz/sendRawTransaction", getHeaders(sign), param);
            String result = response.body().string();
            ResultData<String> resultData = JsonHelper.fromJson(result, new TypeToken<ResultData<String>>() {
            }.getType());
            if (resultData != null) {
                return resultData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultData.createError("");
    }

    private static Headers getHeaders(String sign) {
        Map<String, String> headers = new HashMap<>();
        headers.put("sign", sign);
        return Headers.of(headers);
    }
}
