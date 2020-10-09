using core.Enumerations;
using core.Extensions;
using core.Inputs;
using core.Interfaces;
using Newtonsoft.Json;
using services.Pagamento;
using Xunit;

namespace test
{
    public class CieloTest
    {
        [Fact]
        public void ConsigoPagarComCartaoDeCredito()
        {
            IPagamentoService cieloService = new CieloService();
            var input = new PagamentoCieloInput{
                Tipo = PagamentoProvider.CARTAO_CREDITO.ToDescriptionString(),
                NomeCliente = "Rafael V Araujo",
                NumeroPedido = 20201007001,
                ValorCompra = 100.4f,
                NumeroParcelas = 1,
                NumeroCartao = "4024007153763191",
                ValidadeCartao = "12/2021",
                CodigoSeguranca ="087"
            };
            var result = cieloService.Processar(JsonConvert.SerializeObject(input)).Result;

            
            Assert.True(result.Aprovado);
        }

        [Fact]
        public void ConsigoRetornarUmPagmentoNaoAutorizao()
        {
            IPagamentoService cieloService = new CieloService();
            var input = new PagamentoCieloInput{
                Tipo = PagamentoProvider.CARTAO_CREDITO.ToDescriptionString(),
                NomeCliente = "Rafael V Araujo",
                NumeroPedido = 20201007001,
                ValorCompra = 100f,
                NumeroParcelas = 1,
                NumeroCartao = "4024007153763192",
                ValidadeCartao = "12/2021",
                CodigoSeguranca ="087"
            };
            var result = cieloService.Processar(JsonConvert.SerializeObject(input)).Result;

            
            Assert.False(result.Aprovado);
        }
    }
}