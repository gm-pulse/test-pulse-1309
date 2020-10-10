using System;
using System.Threading.Tasks;
using core.Results;
using infra;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace services
{
    public class VoucherService
    {
        private readonly PulseTesteContext context;
        private readonly ILogger<DiscountService> log;
        public VoucherService(PulseTesteContext context,ILogger<DiscountService> log)
        {
            this.context = context;
            this.log = log;
        }

        public async Task RegisterVoucherUse(string code){
            try{
                var voucher = await context.Vouchers.FirstOrDefaultAsync(d=>d.Identifier.Equals(code));
                voucher.Utilized = true;
                await context.SaveChangesAsync();
            }catch(Exception error){
                log.LogError($"Ocorreu um erro ao registrar o uso do vale-compra de código: {code}");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
        }

        public async Task<VoucherResult> ConsultarValeCompra(string code){
            try{
                var voucher = await context.Vouchers.FirstOrDefaultAsync(d=>d.Identifier.Equals(code));
                if(voucher is null){
                    return new VoucherResult{
                        ErrMessage = "Vale-Compra não localizado"
                    };
                }else if(voucher.ExpireAt < DateTime.Now.Date){
                    return new VoucherResult{
                        ErrMessage = "Vale-Compra expirado"
                    };
                }else if(voucher.Utilized){
                    return new VoucherResult{
                        ErrMessage = "Vale-Compra já utilizado anteriormente"
                    };
                }else{
                    return new VoucherResult{
                        Code = code,
                        Value = voucher.Value,
                        ExpireAt = voucher.ExpireAt,
                        ClienteId = voucher.ClientId
                    };
                }
            }catch(Exception error){
                log.LogError("Ocorreu um erro ao tentar consultar vales-compra válidos");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
        }
    }
}