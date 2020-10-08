using System.Collections.Generic;

namespace core.Entities
{
    public class Client
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Telephone { get; set; }
        public string Email { get; set; }
        public virtual IList<Address> Addresses { get; set; }
    }
}