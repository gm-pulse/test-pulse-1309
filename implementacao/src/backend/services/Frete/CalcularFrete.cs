using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using core.Entities;
using core.Interfaces;
using core.Results;
using infra;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;

namespace services.Frete
{
    public class CalcularFreteService
    {
        private readonly IConfiguration configuration;
        private readonly PulseTesteContext context;
        private readonly IEnumerable<IFreteService> freteProviders;
        private readonly ILogger<CalcularFreteService> log;

        public CalcularFreteService(IConfiguration configuration, PulseTesteContext context,IEnumerable<IFreteService> freteProviders, ILogger<CalcularFreteService> log)
        {
            this.configuration = configuration;   
            this.context = context;
            this.freteProviders = freteProviders;
            this.log = log;
        }
        public async Task<IList<ValorFreteResult>> Calcular(string cepDestino){
            var result = new List<ValorFreteResult>();
            try{
                //O Cep de Origem é sempre o Cep do Centro de Distribuição, obtido do App Settings
                var cepOrigem = configuration.GetValue<string>("CEPCD");

                var provedoresFrete = await context.Carriers.Where(c=>c.Active == true).ToListAsync();

                foreach (Carrier provedor in provedoresFrete)
                {
                    try{
                        var calculadorFrete = freteProviders.FirstOrDefault(fp=>fp.ProviderName.Equals(provedor.ProviderName));
                        result.Add(new ValorFreteResult{
                            Operador = provedor.Name,
                            Valor = calculadorFrete.Calcular(cepOrigem,cepDestino)
                        });
                    }catch(Exception error){
                        log.LogError($"Ocorreu o seguinte erro ao tentar realizar o cálculo de valor de frete para o provedor: {provedor.Name}");
                        log.LogError(error.Message);
                        if(error.InnerException != null)
                            log.LogError(error.InnerException.Message);
                    }
                }
            }catch(Exception error){
                log.LogError("Ocorreu o seguinte erro ao tentar realizar o cálculo de valor de frete");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
            return result;
        }
    }
}