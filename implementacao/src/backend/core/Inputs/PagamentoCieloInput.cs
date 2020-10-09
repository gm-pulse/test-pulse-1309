using core.Enumerations;
using core.Interfaces;
using core.Validations;
using core.ValueObjects;

namespace core.Inputs
{
    public class PagamentoCieloInput : DadosPagamento, IValidatable
    {
        //public PagamentoCieloInput(PagamentoProvider provider) : base(provider){}

        public float ValorCompra { get; set; }
        public int NumeroParcelas { get; set; }
        public long NumeroPedido { get; set; }
        public string NomeCliente { get; set; }
        public string NumeroCartao { get; set; }
        public string ValidadeCartao { get; set; }
        public string CodigoSeguranca { get; set; }
        public void Validate()
        {
            throw new System.NotImplementedException();
        }
    }
}