using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using core.Interfaces;
using core.Results;
using core.ValueObjects;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;

namespace services.Pagamento
{
    public class PaymentService
    {
        private readonly IEnumerable<IPaymentService> pagamentoProvider;
        private readonly ILogger<PaymentService> log;

        public PaymentService(IEnumerable<IPaymentService> pagamentoProvider, ILogger<PaymentService> log)
        {
            this.pagamentoProvider = pagamentoProvider;
            this.log = log;
        }

        public async Task<CobrancaResult> Efetivar(string input){
            try{
                var infoPagamento = JsonConvert.DeserializeObject<DadosPagamento>(input);
                var provedorPagamento = pagamentoProvider.FirstOrDefault(pp=>pp.ProviderName.Equals(infoPagamento.Tipo));
                return await provedorPagamento.Process(input);
            }catch(Exception error){
                log.LogError("Ocorreu um erro ao tentar efetuar o pagamento de um pedido.");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
            
        }
    }
}