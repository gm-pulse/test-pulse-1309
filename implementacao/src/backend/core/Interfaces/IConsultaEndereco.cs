using System.Threading.Tasks;
using core.Results;

namespace core.Interfaces
{
    public interface IConsultaEndereco
    {
         Task<EnderecoResult> Consultar(string cep);
    }
}