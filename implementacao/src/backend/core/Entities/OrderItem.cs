namespace core.Entities
{
    public class OrderItem
    {
        public long OrderId { get; set; }
        public int ProductId { get; set; }
        public int Quantity { get; set; }
        public float Value { 
            get{
                return this.Quantity * this.Product.Value;
            }
         }

        public virtual Product Product { get; set; }
        public virtual Order Order { get; set; }
    }
}