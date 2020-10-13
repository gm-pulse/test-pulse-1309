namespace core.Interfaces
{
    public interface IFreteService
    {
        string ProviderName { get; }
         float Calcular(string cepOrigem, string cepDestino);
    }
}