using System.Threading.Tasks;
using core.Entidades;
using core.Inputs;
using core.Results;

namespace core.Interfaces
{
    public interface IPagamentoService
    {
         Task<CobrancaResult> Cobrar(PagamentoInput input);
    }
}