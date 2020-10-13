using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using core.Attributes;
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
            return GetDescriptionString(val);
        }

        private static string GetDescriptionString(object obj){
            DescriptionAttribute[] attributes = (DescriptionAttribute[])obj
                .GetType()
                .GetField(obj.ToString())
                .GetCustomAttributes(typeof(DescriptionAttribute), false);
            return attributes.Length > 0 ? attributes[0].Description : string.Empty;
        }

        public static string ToTitleString(this PagamentoProvider val){
            return GetTitleString(val);
        }

        private static string GetTitleString(object obj){
            TitleAttribute[] attributes = (TitleAttribute[])obj
                .GetType()
                .GetField(obj.ToString())
                .GetCustomAttributes(typeof(TitleAttribute), false);
            return attributes.Length > 0 ? attributes[0].Description : string.Empty;
        }

        public static Dictionary<string,string> ToDictionary<T>(){
            var result = new Dictionary<string,string>();
            Array enumValues = Enum.GetValues(typeof(T));
            foreach (PagamentoProvider item in enumValues){
                result.Add(GetDescriptionString(item),GetTitleString(item));
            }
            return result;
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