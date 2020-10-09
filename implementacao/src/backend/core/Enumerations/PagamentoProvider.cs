using System.ComponentModel;

namespace core.Enumerations
{
    public enum PagamentoProvider
    {
        [Description("VALECOMPRA")]
        VALE_COMPRA,
        [Description("CARTAOCREDITO")]
        CARTAO_CREDITO
    }
}