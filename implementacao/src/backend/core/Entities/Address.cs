namespace core.Entities
{
    public class Address
    {
        public int Id { get; set; }
        public string PostalCode { get; set; }
        public string Street { get; set; }
        public string Complement { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string District { get; set; }
        public int ClientId { get; set; }
        public virtual Client Client{ get; set; }
        
    }
}