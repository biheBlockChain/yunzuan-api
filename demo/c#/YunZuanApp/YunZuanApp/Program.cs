using RestSharp;
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace YunZuanApp
{
    class Program
    {
        static string appKey = "appKey";
        static string secretKey = "secretKey";
        static string apiUrl = "https://yunzuan-api.bihe.one/";
        static void Main(string[] args)
        {
            Console.WriteLine(GetBalance("0x0A0d28c5D47E445D11B67aF7c2877e9220aF45cA"));
            Console.WriteLine(GetTransactionCount("0x0A0d28c5D47E445D11B67aF7c2877e9220aF45cA"));
            Console.WriteLine(GetTransactionList("0x0A0d28c5D47E445D11B67aF7c2877e9220aF45cA","",20));

            Console.ReadKey();
        }

        static RestClient client = new RestClient()
        {
            BaseUrl = new Uri(apiUrl)
        };
        
        /// <summary>
        /// 余额
        /// </summary>
        /// <param name="address"></param>
        /// <returns></returns>
        public static string GetBalance(string address)
        {
            var request = new RestRequest("yz/getBalance", Method.POST);
            request.AddHeader("Cache-Control", "no-cache");
            request.AddHeader("Host", "yunzuan-api.bihe.one");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            Dictionary<string, string> param = new Dictionary<string, string>();
            param.Add("address", address);
            var ts = Utils.GetTimeStamp();
            param.Add("timestamp", ts);
            var sign = GenSign(param);
            Debug.WriteLine(sign);
            request.AddHeader("sign", sign);
            request.AddParameter("application/x-www-form-urlencoded", $"appkey={appKey}&address={address}&timestamp={ts}", ParameterType.RequestBody);
            return client.Execute(request).Content;
        }

        /// <summary>
        /// 交易次数
        /// </summary>
        /// <param name="address"></param>
        /// <returns></returns>
        public static string GetTransactionCount(string address)
        {
            var request = new RestRequest("yz/getTransactionCount",Method.POST);
            request.AddHeader("Cache-Control", "no-cache");
            request.AddHeader("Host", "yunzuan-api.bihe.one");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            Dictionary<string, string> param = new Dictionary<string, string>();
            param.Add("address", address);
            var ts = Utils.GetTimeStamp();
            param.Add("timestamp", ts);
            var sign = GenSign(param);
            request.AddHeader("sign", sign);
            request.AddParameter("application/x-www-form-urlencoded", $"appkey={appKey}&address={address}&timestamp={ts}", ParameterType.RequestBody);
            return client.Execute(request).Content;
        }

        /// <summary>
        /// 交易记录
        /// </summary>
        /// <param name="address"></param>
        /// <param name="id"></param>
        /// <param name="size"></param>
        /// <returns></returns>
        public static string GetTransactionList(string address,string id,int size)
        {
            var request = new RestRequest("yz/getTransactionList",Method.POST);
            request.AddHeader("Cache-Control", "no-cache");
            request.AddHeader("Host", "yunzuan-api.bihe.one");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            Dictionary<string, string> param = new Dictionary<string, string>();
            param.Add("address", address);
            param.Add("size", size.ToString());
            param.Add("id", id);
            var ts = Utils.GetTimeStamp();
            param.Add("timestamp", ts);
            var sign = GenSign(param);
            request.AddHeader("sign", sign);
            request.AddParameter("application/x-www-form-urlencoded", $"appkey={appKey}&address={address}&id={id}&size={size.ToString()}&timestamp={ts}", ParameterType.RequestBody);
            return client.Execute(request).Content;
        }

        /// <summary>
        /// 发送交易
        /// </summary>
        /// <param name="from"></param>
        /// <param name="to"></param>
        /// <param name="amount"></param>
        /// <returns></returns>
        public static string SendRawTransaction(string from,string to ,decimal amount)
        {
            var request = new RestRequest("yz/sendRawTransaction",Method.POST);
            request.AddHeader("Cache-Control", "no-cache");
            request.AddHeader("Host", "yunzuan-api.bihe.one");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            var signTransation = Utils.SignTransation(from, to, amount);
            Dictionary<string, string> param = new Dictionary<string, string>();
            param.Add("tx", signTransation);
            var ts = Utils.GetTimeStamp();
            param.Add("timestamp", ts);
            var sign = GenSign(param);
            request.AddHeader("sign", sign);
            request.AddParameter("application/x-www-form-urlencoded", $"appkey={appKey}&tx={signTransation}&timestamp={ts}", ParameterType.RequestBody);
            return client.Execute(request).Content;
        }

        private static string GenSign(Dictionary<string, string> param)
        {
            param.Add("appkey", appKey);
            param.Add("secretkey", secretKey);
            SortedDictionary<string, string> sortedParam = new SortedDictionary<string, string>(param);
            string str = "";
            foreach (var kv in sortedParam)
            {
                str += kv.Key;
                str += kv.Value;
            }
            Debug.WriteLine(str);
            return Utils.GetSHA256(str);
        }
    }
}
