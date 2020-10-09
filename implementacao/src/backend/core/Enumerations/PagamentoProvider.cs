using System.ComponentModel;
using core.Attributes;

namespace core.Enumerations
{
    public enum PagamentoProvider
    {
        [Description("VALECOMPRA")]
        [Title("Vale-Compra")]
        VALE_COMPRA,
        [Description("CARTAOCREDITO")]
        [Title("Cartão de Crédito")]
        CARTAO_CREDITO
    }
}