using core.Interfaces;
using core.Validations;

namespace core.Inputs
{
    public class PagamentoInput : Notifiable, IValidatable
    {
        public float Valor { get; set; }
        public int NumeroParcelas { get; set; }

        public long NumeroPedido { get; set; }
        public string NomeCliente { get; set; }

        public IDetalhesPagamento InformacoesAdicionais { get; set; }

        public void Validate()
        {
            throw new System.NotImplementedException();
        }
    }
}