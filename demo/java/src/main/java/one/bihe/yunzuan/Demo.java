package one.bihe.yunzuan;

import one.bihe.yunzuan.api.ResultData;
import one.bihe.yunzuan.api.YunZuanApi;
import one.bihe.yunzuan.core.YunZuan;
import one.bihe.yunzuan.entry.ApiTransactionVo;
import one.bihe.yunzuan.entry.TxVo;
import one.bihe.yunzuan.util.JsonHelper;
import one.bihe.yunzuan.util.OkhttpUtils;
import org.ethereum.core.Transaction;
import org.ethereum.util.Unit;
import org.ethereum.wallet.CommonWallet;
import org.ethereum.wallet.Wallet;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SignatureException;
import java.util.List;

public class Demo {
    private static String address = "0x0A0d28c5D47E445D11B67aF7c2877e9220aF45cA";
    private static String keystore = "{\"version\":3,\"id\":\"991142e5-3706-4ff5-9837-70d8d8afdb2c\",\"address\":\"bfcb57b8f98cc1c5f93835e1dce8725afd38ad0d\",\"Crypto\":{\"ciphertext\":\"62492605e0ce1ea84960c35b20d8ee9b0e4096bc4f122cfb98555e970ae02a76\",\"cipherparams\":{\"iv\":\"4de2c519ff17f614d12019589fc99530\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"408d97a74040f826725a09f0e76dcc45024eb3cfceecf323f4ab5e88b670ae61\",\"n\":8192,\"r\":8,\"p\":1},\"mac\":\"06d68abc09fe9a07f64e82aa68c0b0eae5fb5a69041ac138eade39ea8ff9ff74\"}}";

    public static void main(String[] args) {

        YunZuan.init("YjkwZDQ1NThm", "TdiYWU0NDFjNmU5ZjU1N2QyYmIxNTMxMDJkO");
        //OkhttpUtils.initProxy("127.0.0.1", 8888);
        //获取余额
        getBalance();
        //获取交易次数
        getTransactionCount();

        //获取交易列表
        getTransactionList();


        //发送交易
        sendTransaction();

    }

    private static void getTransactionList() {
        ResultData<ApiTransactionVo> transactionList = YunZuanApi.getTransactionList(address, 3, "");
        System.out.println(JsonHelper.toJson(transactionList));
        //获取下一页交易列表
        List<TxVo> transactionRecords = transactionList.getData().getTransactionRecords();
        transactionList = YunZuanApi.getTransactionList(address, 3, transactionRecords.get(transactionRecords.size() - 1).getId());
        System.out.println(JsonHelper.toJson(transactionList));
    }

    private static void sendTransaction() {
        ResultData<String> transactionCount = YunZuanApi.getTransactionCount("0xbfcb57b8f98cc1c5f93835e1dce8725afd38ad0d");
        if (!transactionCount.isSuccess()) {
            System.out.println(transactionCount.getMessage());
            return;
        }
        BigInteger nonce = new BigInteger(transactionCount.getData().substring(2), 16);
        Wallet wallet;
        try {
            wallet = CommonWallet.fromV3(keystore, "111111111");
            //使用私钥
            //wallet=CommonWallet.fromPrivateKey();
        } catch (GeneralSecurityException e) {
            System.out.println("钱包密码错误");
            return;
        }
        if (wallet == null) {
            System.out.println("钱包密码错误");
            return;
        }
        BigInteger gasLimit = new BigInteger("5208", 16);
        BigInteger gasPrice = new BigInteger("2e90edd000", 16);

        //交易数量
        BigInteger amount = Unit.valueOf(Unit.ether.toString()).toWei(String.valueOf("1"));
        Transaction transaction = Transaction.create(address.replace("0x", ""), amount, nonce, gasPrice, gasLimit, null);
        try {
            transaction.sign(wallet);
        } catch (SignatureException e) {
            System.out.println("签名失败");
            e.printStackTrace();
            return;
        }
        byte[] encoded = transaction.getEncoded();
        String tx = "0x" + Hex.toHexString(encoded);
        System.out.println(tx);
        ResultData<String> resultData = YunZuanApi.sendRawTransaction(tx);
        System.out.println(JsonHelper.toJson(resultData));
    }

    private static void getTransactionCount() {
        ResultData<String> count = YunZuanApi.getTransactionCount(address);
        System.out.println(JsonHelper.toJson(count));
    }

    private static void getBalance() {
        ResultData<String> balance = YunZuanApi.getBalance(address);
        System.out.println(JsonHelper.toJson(balance));
    }
}
