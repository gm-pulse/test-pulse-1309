using core.Entidades.Cielo;

namespace core.Results
{
    public class CieloResult
    {
        public string MerchantOrderId { get; set; }
        public CustomerPayment Customer { get; set; }

        public CieloPaymentResult Payment { get; set; }
        

    }

    public class CieloPaymentResult{
        public string ProofOfSale { get; set; }
        public string Tid { get; set; }
        public string AuthorizationCode { get; set; }
        public string PaymentId { get; set; }
        public string Type { get; set; }
        public int Amount { get; set; }
        public int Installments { get; set; }
        public string ReturnMessage { get; set; }
        public string ReturnCode { get; set; }
        public int Status { get; set; }
    }
}