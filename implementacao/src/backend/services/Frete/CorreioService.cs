using core.Interfaces;

namespace services.Frete
{
    public class CorreioService : IFreteService
    {
        public string ProviderName => "CORREIOS";
        public float Calcular(string cepOrigem, string cepDestino)
        {
            return 34.5f;
        }
    }
}