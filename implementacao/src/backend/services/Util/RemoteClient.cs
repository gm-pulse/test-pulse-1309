using System.Collections;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace services.Util
{
    public static class RemoteClient<T>
    {
        public static async Task<T> ExecutePost(string url, object data, Dictionary<string, string> headers){
            using (var client = new HttpClient())
            {
                if(headers != null){
                    foreach (var header in headers)
                    {
                        client.DefaultRequestHeaders.Add(header.Key,header.Value);
                    }
                }
                client.DefaultRequestHeaders.Accept.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                var request = await client.PostAsync(url,new StringContent(JsonConvert.SerializeObject(data), Encoding.UTF8, "application/json")); 
                return JsonConvert.DeserializeObject<T>(await request.Content.ReadAsStringAsync());
            }
        }
        public static async Task<T>  ExecutePost(string url, object data){
            return await ExecutePost(url,data,null);
        }
    }
}