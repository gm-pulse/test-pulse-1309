using System.Collections.Generic;
using System.Threading.Tasks;
using core.Entidades;
using core.Enumerations;
using core.Extensions;
using core.Inputs;
using core.Interfaces;
using core.Results;
using core.ValueObjects;
using Newtonsoft.Json;
using services.Util;

namespace services.Pagamento
{
    public class CieloService : IPagamentoService
    {
        public string ProviderName => PagamentoProvider.CARTAO_CREDITO.ToDescriptionString();

        public async Task<CobrancaResult> Processar(string input)
        {
            var infoPagamento = JsonConvert.DeserializeObject<PagamentoCieloInput>(input);
            var requestData = infoPagamento.ToPaymentRequest();
            var requestHeader = new Dictionary<string,string>();
            requestHeader.Add("MerchantId","e4b52cd8-ecec-4f12-82d6-b6bf2df930af");
            requestHeader.Add("MerchantKey","NUZJGVRBPBIFJRNVDAMWDPRWGSWSJUUGTWVKYDDA");
            var result = await RemoteClient<CieloResult>.ExecutePost("https://apisandbox.cieloecommerce.cielo.com.br/1/sales",requestData,requestHeader);
            return result.ToCobrancaResult();
        }
    }
}