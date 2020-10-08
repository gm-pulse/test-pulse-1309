using System.Collections.Generic;
using System.Threading.Tasks;
using core.Entidades;
using core.Extensions;
using core.Inputs;
using core.Interfaces;
using core.Results;
using services.Util;

namespace services.Pagamento
{
    public class CieloService : IPagamentoService
    {
        public async Task<CobrancaResult> Cobrar(PagamentoInput input)
        {
            var requestData = input.ToPaymentRequest();
            var requestHeader = new Dictionary<string,string>();
            requestHeader.Add("MerchantId","e4b52cd8-ecec-4f12-82d6-b6bf2df930af");
            requestHeader.Add("MerchantKey","NUZJGVRBPBIFJRNVDAMWDPRWGSWSJUUGTWVKYDDA");
            var result = await RemoteClient<CieloResult>.ExecutePost("https://apisandbox.cieloecommerce.cielo.com.br/1/sales",requestData,requestHeader);
            return result.ToCobrancaResult();
        }
    }
}