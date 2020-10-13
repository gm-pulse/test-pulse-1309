using System;

namespace core.Entities
{
    public class Discount
    {
         public int Id { get; set; }
         public string Identifier { get; set; }
         public float Value { get; set; }
         public DateTime ExpireAt { get; set; }
         public int MaxUse { get; set; }
         public int Utilized { get; set; }
    }
}