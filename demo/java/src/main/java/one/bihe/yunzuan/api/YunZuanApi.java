package one.bihe.yunzuan.api;

import one.bihe.yunzuan.core.YunZuan;
import one.bihe.yunzuan.entry.ApiTransactionVo;
import one.bihe.yunzuan.util.ShaUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class YunZuanApi {

    /**
     * 获取余额
     *
     * @param address 钱包地址
     * @return
     */
    public static ResultData<String> getBalance(String address) {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        String sign = getSign(map);
        return YunZuanHttpRequest.getBalance(map, sign);
    }

    /**
     * 获取交易次数
     *
     * @param address 钱包地址
     * @return
     */
    public static ResultData<String> getTransactionCount(String address) {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        String sign = getSign(map);
        return YunZuanHttpRequest.getTransactionCount(map, sign);
    }

    /**
     * 获取交易列表
     *
     * @param address 钱包地址
     * @param size    条数
     * @param id      最后一条id，第一页留空
     * @return
     */
    public static ResultData<ApiTransactionVo> getTransactionList(String address, int size, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("size", String.valueOf(size));
        map.put("id", id);
        String sign = getSign(map);
        return YunZuanHttpRequest.getTransactionList(map, sign);
    }

    /**
     * 发送交易
     *
     * @param tx
     * @return
     */
    public static ResultData<String> sendRawTransaction(String tx) {
        Map<String, String> map = new HashMap<>();
        map.put("tx", tx);
        String sign = getSign(map);
        return YunZuanHttpRequest.sendRawTransaction(map, sign);
    }


    private static String getSign(Map<String, String> map) {
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("appkey", YunZuan.getAppkey());
        Map<String, String> signMap = new TreeMap<>();
        signMap.putAll(map);
        signMap.put("secretKey", YunZuan.getSecretKey());
        StringBuilder builder = new StringBuilder();
        signMap.forEach((key, value) -> {
            builder.append(key).append(value);
        });
        String sign = ShaUtils.sha256(builder.toString());
        return sign;
    }
}
