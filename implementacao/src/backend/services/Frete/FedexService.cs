using core.Interfaces;

namespace services.Frete
{
    public class FedexService: IFreteService
    {
        public float calcular(string cepOrigem, string cepDestino)
        {
            return 45f;
        }
    }
}