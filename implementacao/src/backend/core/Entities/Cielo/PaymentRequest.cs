namespace core.Entidades.Cielo
{
    public class PaymentRequest
    {
        public string MerchantOrderId { get; set; }
        public CustomerPayment Customer { get; set; }
        public Payment Payment { get; set; }
    }
}