using System;
using System.Threading.Tasks;
using core.Entidades;
using core.Enumerations;
using core.Extensions;
using core.Inputs;
using core.Interfaces;
using core.Results;
using core.ValueObjects;
using infra;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;

namespace services.Pagamento
{
    public class ValeCompraService : IPagamentoService
    {
        private readonly PulseTesteContext context;
        private readonly ILogger<PagamentoService> log;

        public ValeCompraService(PulseTesteContext context,ILogger<PagamentoService> log)
        {
            this.context = context;
            this.log = log;
        }

        
        public string ProviderName => PagamentoProvider.VALE_COMPRA.ToDescriptionString();
        public async Task<CobrancaResult> Processar(string input)
        {
            try{
                var infoPagamento = JsonConvert.DeserializeObject<PagamentoValeCompraInput>(input);
                var valeCompra = await context.Vouchers.FirstOrDefaultAsync(v=>v.Identifier == infoPagamento.CodigoValeCompras && v.ClientId == infoPagamento.CodigoCliente);
                if(valeCompra is null){
                    return new CobrancaResult{
                        Aprovado =false,
                        Erro = "Vale-Compra não localizado"
                    };
                }else if(valeCompra.ExpireAt < DateTime.Now.Date){
                    return new CobrancaResult{
                        Aprovado =false,
                        Erro = "O vale-compra expirou"
                    };
                }else if(valeCompra.Utilized){
                    return new CobrancaResult{
                        Aprovado =false,
                        Erro = "O vale-compra informado já foi utilizado anteriormente"
                    };
                }else if(valeCompra.Value > infoPagamento.ValorCompra){
                    return new CobrancaResult{
                        Aprovado =false,
                        Erro = "O valor do vale-compra deve ser menor ou igual ao valor da compra"
                    };
                }else{
                    valeCompra.Utilized = true;
                    await context.SaveChangesAsync();
                    return new CobrancaResult{
                        Aprovado = true,
                        Data = DateTime.Now,
                        Valor = infoPagamento.ValorCompra
                    };
                }

            }catch(Exception error){
                log.LogError("Ocorreu um erro ao tentar efetuar o pagamento de um pedido com um vale-compra");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                log.LogError(input);
                throw error; 
            }
            
        }
    }
}