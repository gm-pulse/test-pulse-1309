using System;
using System.Collections.Generic;
using core.Enumerations;
using core.Extensions;

namespace services
{
    public class ParametrosService
    {
        public Dictionary<string,string> ObterTiposPagamentos(){
            return ObjectExtensions.ToDictionary<PagamentoProvider>();
        }
    }
}