using System;

namespace core.Results
{
    public class CobrancaResult
    {
        public bool Aprovado { get; set; }
        public DateTime Data { get; set; }
        public float Valor { get; set; }
        public string DetalhesPagamento { get; set; }
    }
}