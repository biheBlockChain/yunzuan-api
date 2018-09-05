package one.bihe.yunzuan.core;

public class YunZuan {
    private static String sAppkey;
    private static String sSecretKey;

    public static void init(String appkey, String secretKey) {
        sAppkey = appkey;
        sSecretKey = secretKey;
    }

    public static String getAppkey() {
        return sAppkey;
    }

    public static String getSecretKey() {
        return sSecretKey;
    }
}
