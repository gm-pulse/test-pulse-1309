using System;

namespace core.Entities
{
    public class Payment
    {
        public int Id { get; set; }
        public float Amount { get; set; }
        public DateTime Date { get; set; }
        public string ExtraInfo { get; set; }
        public long OrderId { get; set; }
        public int TypeId { get; set; }
        public virtual Order Order { get; set; }
        public virtual PaymentType Type { get; set; }
    }
}