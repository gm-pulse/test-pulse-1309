namespace core.Entidades.Cielo
{
    public class Payment
    {
        public string Type { get; set; }
        public int Amount { get; set; }
        public int Installments { get; set; }
        public CreditCardInfo CreditCard { get; set; }
    }
}