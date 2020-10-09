using System.Threading.Tasks;
using core.Entidades;
using core.Inputs;
using core.Results;
using core.ValueObjects;

namespace core.Interfaces
{
    public interface IPagamentoService
    {
        string ProviderName { get; }
         Task<CobrancaResult> Processar(string input);
    }
}