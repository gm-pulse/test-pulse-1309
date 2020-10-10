using System;
using System.Threading.Tasks;
using core.Results;
using infra;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace services
{
    public class DiscountService
    {
        private readonly PulseTesteContext context;
        private readonly ILogger<DiscountService> log;
        public DiscountService(PulseTesteContext context,ILogger<DiscountService> log)
        {
            this.context = context;
            this.log = log;
        }

        public async Task RegisterDiscountUse(string code){
            try{
                var discount = await context.Discounts.FirstOrDefaultAsync(d=>d.Identifier.Equals(code));
                discount.Utilized++;
                await context.SaveChangesAsync();
            }catch(Exception error){
                log.LogError($"Ocorreu um erro ao registrar o uso do desconto de código: {code}");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
        }

        public async Task<DescontoResult> ConsultarDesconto(string code){
            try{
                var discount = await context.Discounts.FirstOrDefaultAsync(d=>d.Identifier.Equals(code));
                if(discount is null){
                    return new DescontoResult{
                        Erro = "Desconto não localizado"
                    };
                }else if(discount.ExpireAt < DateTime.Now.Date){
                    return new DescontoResult{
                        Erro = "Desconto expirado"
                    };
                }else if(discount.Utilized < discount.MaxUse){
                    return new DescontoResult{
                        Erro = "A quantidade máxima de utilização deste desconto foi atingida"
                    };
                }else{
                    return new DescontoResult{
                        Codigo = code,
                        Valor = discount.Value
                    };
                }
            }catch(Exception error){
                log.LogError("Ocorreu um erro ao tentar consultar descontos válidos");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
            
        }
        
    }
}