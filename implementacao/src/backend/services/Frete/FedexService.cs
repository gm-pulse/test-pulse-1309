using core.Interfaces;

namespace services.Frete
{
    public class FedexService: IFreteService
    {
        public string ProviderName => "FEDEX";
        public float Calcular(string cepOrigem, string cepDestino)
        {
            return 45f;
        }
    }
}