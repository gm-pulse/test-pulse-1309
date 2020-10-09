using core.Interfaces;

namespace services.Frete
{
    public class MercadoEnvioService : IFreteService
    {
        public string ProviderName => "MERCADOENVIOS";
        public float Calcular(string cepOrigem, string cepDestino)
        {
            return 33.4f;
        }
    }
}