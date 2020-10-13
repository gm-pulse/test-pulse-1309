using core.Interfaces;

namespace services.Frete
{
    public class JadLogService: IFreteService
    {
        public string ProviderName => "JADLOG";
        public float Calcular(string cepOrigem, string cepDestino)
        {
            return 24.7f;
        }
    }
}