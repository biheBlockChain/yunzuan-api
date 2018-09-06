<?php
/**
 * Created by PhpStorm.
 * User: wrbug
 * Date: 2018/9/5
 * Time: 20:39
 */


$curl = curl_init();

//请求参数
$params = array(
    'appkey' => 'YjkwZDQ1NThm',
    'address' => '0x0A0d28c5D47E445D11B67aF7c2877e9220aF45cA',
    'id' => '',
    'size' => 3,
    'timestamp' => time() * 1000,
);

//深拷贝
$p1 = unserialize(serialize($params));
//添加secretKey
$p1['secretkey'] = 'TdiYWU0NDFjNmU5ZjU1N2QyYmIxNTMxMDJkO';
//排序
ksort($p1);

//获取sign
$str = '';
foreach ($p1 as $key => $value) {
    $str = $str . $key . $value;
}
$re = hash('sha256', $str, true);
$sign = bin2hex($re);

//拼接请求参数
$paramStr = '';
foreach ($params as $key => $value) {
    $paramStr = $paramStr . $key . '=' . $value . '&';
}


curl_setopt_array($curl, array(
    CURLOPT_URL => "https://yunzuan-api.bihe.one/yz/getTransactionList",
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_ENCODING => "",
    CURLOPT_MAXREDIRS => 10,
    CURLOPT_TIMEOUT => 30,
    CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    CURLOPT_CUSTOMREQUEST => "POST",
    CURLOPT_POSTFIELDS => $paramStr,
    CURLOPT_HTTPHEADER => array(
        "cache-control: no-cache",
        "content-type: application/x-www-form-urlencoded",
        "host: yunzuan-api.bihe.one",
        "sign: " . $sign
    ),
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);

if ($err) {
    echo "cURL Error #:" . $err;
} else {
    echo $response;
}