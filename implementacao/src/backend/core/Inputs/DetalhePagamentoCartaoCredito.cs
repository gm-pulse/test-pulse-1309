using core.Interfaces;

namespace core.Inputs
{
    public class DetalhePagamentoCartaoCredito : IDetalhesPagamento
    {
        public string Numero { get; set; }
        public string Portador { get; set; }
        public string DataExpiracao { get; set; }
        public string CodigoSeguranca { get; set; }
        public string Bandeira { get; set; }
    }
}