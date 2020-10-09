using core.Enumerations;
using core.Interfaces;
using core.ValueObjects;

namespace core.Inputs
{
    public class PagamentoValeCompraInput: DadosPagamento, IValidatable
    {
        //public PagamentoValeCompraInput(PagamentoProvider provider) : base(provider){}

        public string CodigoValeCompras { get; set; }
        public int CodigoCliente { get; set; }
        public float ValorCompra { get; set; }

        public void Validate()
        {
            throw new System.NotImplementedException();
        }
    }
}