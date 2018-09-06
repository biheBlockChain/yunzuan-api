using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Numerics;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace YunZuanApp
{
    class Utils
    {
        public static string GetSHA256(string str)
        {
            byte[] bytValue = Encoding.UTF8.GetBytes(str);
            try
            {
                SHA256 sha256 = new SHA256CryptoServiceProvider();
                byte[] retVal = sha256.ComputeHash(bytValue);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < retVal.Length; i++)
                {
                    sb.Append(retVal[i].ToString("x2"));
                }
                Debug.WriteLine("gen:"+sb.ToString());
                return sb.ToString();
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        /// <summary>
        /// 签名交易
        /// </summary>
        /// <param name="from"></param>
        /// <param name="to"></param>
        /// <param name="amount"></param>
        /// <returns></returns>
        public static string SignTransation(string from, string to, decimal amount)
        {
            var privateKey = "privateKey";//转出钱包的私钥
            var sign = new Nethereum.Signer.TransactionSigner();
            var weiAmount = Nethereum.Util.UnitConversion.Convert.ToWei(amount);
            BigInteger nonce_bigint = Convert.ToInt32(Program.GetTransactionCount(from), 16);
            var gasLimit = new BigInteger(21000);
            var gasPrice = new BigInteger(200000000000);
            var txSign = sign.SignTransaction(privateKey, to, weiAmount, nonce_bigint, gasPrice, gasLimit);
            return "0x" + txSign;
        }

        /// <summary>  
        /// 获取当前时间戳  
        /// </summary>  
        /// <param name="bflag">为真时获取10位时间戳,为假时获取13位时间戳,默认bool bflag = false</param>  
        /// <returns></returns>  
        public static string GetTimeStamp(bool bflag = false)
        {
            TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
            string ret = string.Empty;
            if (bflag)
                ret = Convert.ToInt64(ts.TotalSeconds).ToString();
            else
                ret = Convert.ToInt64(ts.TotalMilliseconds).ToString();

            return ret;
        }
    }
}
