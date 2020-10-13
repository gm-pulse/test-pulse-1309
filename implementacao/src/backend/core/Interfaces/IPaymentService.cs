using System.Threading.Tasks;
using core.Entidades;
using core.Inputs;
using core.Results;
using core.ValueObjects;

namespace core.Interfaces
{
    public interface IPaymentService
    {
        string ProviderName { get; }
         Task<CobrancaResult> Process(string input);
    }
}