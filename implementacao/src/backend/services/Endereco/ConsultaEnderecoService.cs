using System.Threading.Tasks;
using core.Interfaces;
using core.Results;
using services.Util;

namespace services.Endereco
{
    public class ConsultaEnderecoService : IConsultaEndereco
    {
        public Task<EnderecoResult> Consultar(string cep)
        {
            return RemoteClient<EnderecoResult>.ExecuteGet($"https://viacep.com.br/ws/{cep}/json/");

        }
    }
}