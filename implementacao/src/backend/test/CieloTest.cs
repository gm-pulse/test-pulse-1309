using core.Inputs;
using core.Interfaces;
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
            var input = new PagamentoInput{
                NomeCliente = "Rafael Vieira de Araujo",
                NumeroPedido = 20201007001,
                Valor = 100f,
                NumeroParcelas = 1,
                InformacoesAdicionais = new DetalhePagamentoCartaoCredito{
                    Numero = "0000000000000001",
                    Portador= "Rafael V Araujo",
                    DataExpiracao ="12/2021",
                    CodigoSeguranca = "087",
                    Bandeira = "Visa"
                }
            };
            var result = cieloService.Cobrar(input).Result;

            
            Assert.True(result == null);
        }
    }
}