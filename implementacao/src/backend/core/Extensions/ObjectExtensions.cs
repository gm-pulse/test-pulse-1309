using System;
using core.Entidades;
using core.Entidades.Cielo;
using core.Inputs;

namespace core.Extensions
{
    public static class ObjectExtensions
    {
        public static int FormatCieloValue(this float valor){
            return Convert.ToInt32(valor.ToString("N2").Replace(".","").Replace(",",""));
        }
        public static PaymentRequest ToPaymentRequest(this PagamentoInput infoPagamento){
            var dadosCartao = (DetalhePagamentoCartaoCredito)infoPagamento.InformacoesAdicionais;
            return new PaymentRequest{
                MerchantOrderId = infoPagamento.NumeroPedido.ToString(),
                Customer = new CustomerPayment{
                    Name = infoPagamento.NomeCliente
                },
                Payment = new Payment{
                    Type= "CreditCard",
                    Amount = infoPagamento.Valor.FormatCieloValue(),
                    Installments =infoPagamento.NumeroParcelas,
                    CreditCard = new CreditCardInfo{
                        CardNumber = dadosCartao.Numero,
                        Holder = dadosCartao.Portador,
                        ExpirationDate = dadosCartao.DataExpiracao,
                        SecurityCode = dadosCartao.CodigoSeguranca,
                        Brand = dadosCartao.Bandeira
                    }
                }
            };        
        }
    }
}