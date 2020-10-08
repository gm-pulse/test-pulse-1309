using System.Threading.Tasks;
using core.Entidades;
using core.Inputs;
using core.Interfaces;
using core.Results;

namespace services.Pagamento
{
    public class ValeCompraService : IPagamentoService
    {
        public Task<CobrancaResult> Cobrar(PagamentoInput input)
        {
            throw new System.NotImplementedException();
        }
    }
}