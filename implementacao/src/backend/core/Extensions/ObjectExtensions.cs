using System;
using System.ComponentModel;
using System.Linq;
using core.Entidades;
using core.Entidades.Cielo;
using core.Enumerations;
using core.Inputs;
using core.Results;
using Newtonsoft.Json;

namespace core.Extensions
{
    public static class ObjectExtensions
    {
        public static int FormatCieloValue(this float valor){
            return Convert.ToInt32(valor.ToString("N2").Replace(".","").Replace(",",""));
        }
        public static PaymentRequest ToPaymentRequest(this PagamentoCieloInput infoPagamento){
            return new PaymentRequest{
                MerchantOrderId = infoPagamento.NumeroPedido.ToString(),
                Customer = new CustomerPayment{
                    Name = infoPagamento.NomeCliente
                },
                Payment = new Payment{
                    Type= "CreditCard",
                    Amount = infoPagamento.ValorCompra.FormatCieloValue(),
                    Installments =infoPagamento.NumeroParcelas,
                    CreditCard = new CreditCardInfo{
                        CardNumber = infoPagamento.NumeroCartao,
                        Holder = infoPagamento.NomeCliente,
                        ExpirationDate = infoPagamento.ValidadeCartao,
                        SecurityCode = infoPagamento.CodigoSeguranca,
                        Brand = "Visa"//dadosCartao.Bandeira
                    }
                }
            };        
        }

        public static CobrancaResult ToCobrancaResult(this CieloResult result){
            var valorTransacao = result.Payment.Amount.ToString();
            return new CobrancaResult{
                Aprovado = result.Payment.Status == 1,
                Data = DateTime.Now,
                Valor = float.Parse(valorTransacao.Insert(valorTransacao.Length -2,".")),
                DetalhesPagamento = JsonConvert.SerializeObject(result)
            };
        }

        public static string ToDescriptionString(this PagamentoProvider val){
        DescriptionAttribute[] attributes = (DescriptionAttribute[])val
           .GetType()
           .GetField(val.ToString())
           .GetCustomAttributes(typeof(DescriptionAttribute), false);
        return attributes.Length > 0 ? attributes[0].Description : string.Empty;
        }

        private static Random random = new Random();
        public static string RandomString(int length)
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, length)
            .Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }
}