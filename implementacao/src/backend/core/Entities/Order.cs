using System;
using System.Collections.Generic;

namespace core.Entities
{
    public class Order
    {
        public long Id { get; set; }
        public DateTime Date { get; set; }
        public float TotalAmount { get; set; }
        public float? DiscountAmount{ get; set; }
        public int ClientId { get; set; }
        public float ShippingAmount { get; set; }
        public int CarrierId { get; set; }
        public virtual Client Client{ get; set; }
        public virtual Carrier Carrier{ get; set; }
        public virtual IList<OrderItem> Items{ get; set; }
        public virtual IList<Payment> Payments{ get; set; }

    }
}