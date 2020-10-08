using core.Interfaces;
using services.Endereco;
using Xunit;

namespace test
{
    public class ConsultaEnderecoTest
    {
        [Fact]
        public void ConsigoConsultarUmEnderecoValido()
        {
            IConsultaEndereco consultaEnderecoService = new ConsultaEnderecoService();
            var endereco = consultaEnderecoService.Consultar("60749020").Result;
            Assert.False(endereco.Erro);
        }

        [Fact]
        public void ConsigoConsultarUmEnderecoInvalido()
        {
            IConsultaEndereco consultaEnderecoService = new ConsultaEnderecoService();
            var endereco = consultaEnderecoService.Consultar("60749028").Result;
            Assert.True(endereco.Erro);
        }
    }
}