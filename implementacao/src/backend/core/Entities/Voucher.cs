using System;

namespace core.Entities
{
    public class Voucher
    {
        public int Id { get; set; }
        public string Identifier { get; set; }
        public int ClientId { get; set; }
        public float Value { get; set; }
        public DateTime ExpireAt { get; set; }
        public bool Utilized { get; set; }
        public virtual Client Client { get; set; }
    }
}