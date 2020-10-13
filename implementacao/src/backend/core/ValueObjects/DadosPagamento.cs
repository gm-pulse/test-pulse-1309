using core.Enumerations;
using core.Extensions;
using core.Validations;

namespace core.ValueObjects
{
    public  class DadosPagamento: Notifiable
    {
        // public DadosPagamento(){}
        // public DadosPagamento(PagamentoProvider provider)
        // {
        //     Tipo = provider.ToDescriptionString();
        // }
        public string Tipo { get; set; }
    }
}