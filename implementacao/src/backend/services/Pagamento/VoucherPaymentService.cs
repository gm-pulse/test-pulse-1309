using System;
using System.Threading.Tasks;
using core.Entities;
using core.Enumerations;
using core.Extensions;
using core.Inputs;
using core.Interfaces;
using core.Results;
using infra;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;

namespace services.Pagamento
{
    public class VoucherPaymentService : IPaymentService
    {
        private readonly PulseTesteContext context;
        private readonly ILogger<PaymentService> log;
        private readonly VoucherService voucherService;

        public VoucherPaymentService(PulseTesteContext context,ILogger<PaymentService> log,VoucherService voucherService)
        {
            this.context = context;
            this.log = log;
            this.voucherService = voucherService;
        }

        
        public string ProviderName => PagamentoProvider.VALE_COMPRA.ToDescriptionString();

        public async Task<CobrancaResult> Process(string input)
        {
            try{
                var infoPagamento = JsonConvert.DeserializeObject<PagamentoValeCompraInput>(input);

                var valeCompra = await voucherService.ConsultarValeCompra(infoPagamento.CodigoValeCompras); //await context.Vouchers.FirstOrDefaultAsync(v=>v.Identifier == infoPagamento.CodigoValeCompras && v.ClientId == infoPagamento.CodigoCliente);
                if(string.IsNullOrEmpty(valeCompra.ErrMessage)){
                    return new CobrancaResult{
                        Aprovado =false,
                        Erro = valeCompra.ErrMessage
                    };
                }else if(valeCompra.ClienteId != infoPagamento.CodigoCliente){
                    return new CobrancaResult{
                        Aprovado =false,
                        Erro = "Vale-compra não localizado"
                    };
                }else{
                    await voucherService.RegisterVoucherUse(infoPagamento.CodigoValeCompras);
                    var paymentType = await context.PaymentTypes.FirstOrDefaultAsync(pt=>pt.TypeIdentifier.Equals(ProviderName));
                    var newPayment = new Payment{
                        OrderId = infoPagamento.CodigoPedido,
                        Date = DateTime.Now,
                        TypeId = paymentType.Id,
                        Amount = infoPagamento.ValorCompra,
                        ExtraInfo = infoPagamento.CodigoValeCompras
                    };
                    await context.AddAsync(newPayment);
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