namespace core.Entities
{
    public class Order
    {
        public int Id { get; set; }
        public float TotalAmount { get; set; }
        public float DiscountAmount{ get; set; }
        public int ClientId { get; set; }
        public virtual Client Client{ get; set; }
    }
}