# 云钻api

#### api地址

```
https://yunzuan-api.bihe.one

http://yunzuan-api.bihe.one
```

#### 获取appkey 和secretKey
联系我们购买获取

### 签名方式(示例)

**请求参数**

```
appkey : value
bbb : value1
ccc : value2
zzz : value3
```

拼接请求参数(secretKey需要参与拼接)
假定secretKey=SECRETKEY
将key按照ascall 排序 ，key1+value1+key2+value2+... 形式拼接

```
//拼接好的字符串
str="appkeyvaluebbbvalue1cccvalue2secretKeySECRETKEYzzzvalue3"

//获取sign,使用sha256加密
sign=sha256(str)
//计算sign="9877707bb92f8a7945fb6ea91ea6485850a45d3cdd189741980e4495bee9973b"

```

将sign加入到请求头header中
```
sign : 9877707bb92f8a7945fb6ea91ea6485850a45d3cdd189741980e4495bee9973b
```


### api列表

#### 获取余额

##### path:

`/yz/getBalance`

##### Method:

POST

##### 参数:

```
address  钱包地址
appkey appkey
timestamp 当前时间戳(毫秒，下同)
```

##### 返回值

```json
//code=1 为成功，下同
{
    "code":1,
    "errorCode":0,
    "message":"",
    "data":"0x7933faed6a5a0000"
}

```


#### 获取交易次数

##### path:

`/yz/getTransactionCount`

##### Method:

POST

##### 参数:

```
address  钱包地址
appkey appkey
timestamp 当前时间戳(毫秒，下同)
```

##### 返回值

```json
{
    "code":1,
    "errorCode":0,
    "message":"",
    "data":"0xa"
}

```


#### 获取交易列表

##### path:

`/yz/getTransactionList`

##### Method:

POST

##### 参数:

```
address  钱包地址
size    返回条数(最高100)
id  最后一组数据的id值，为空表示第一页
appkey appkey
timestamp 当前时间戳(毫秒，下同)
```

##### 返回值

```json
{
    "code": 1,
    "timestamp": 1536080182039,
    "data": {
        "hasNext": false,
        "transactionRecords": [
            {
                "id": "56013",
                "blockNumber": "0x31308a",
                "fromAddress": "0x0a0d28c5d47e445d11b67af7c2877e9220af45ca",
                "gasLimit": "0x5208",
                "gasPrice": "0x2e90edd000",
                "hash": "0x93fefacd3c65a339498a84485a17578191ac07219a4b1d6421890697ad5e790b",
                "input": "0x",
                "nonce": "0x4",
                "toAddress": "0x7d6c23b07e931ba39bed2dbd885ca9c280946bd5",
                "transactionValue": "0x1b667a56d488000",
                "transactionTime": 1536047523000,
                "status": 1
            }
        ]
    }
}

```


#### 发送交易

##### path:

`/yz/sendRawTransaction`

##### Method:

POST

##### 参数:

```
tx  签名值（0x...）
appkey appkey
timestamp 当前时间戳(毫秒，下同)
```

##### 返回值

```json
{
    "code":1,
    "errorCode":0,
    "message":"",
    "data":"交易hash"
}

```


