using core.Interfaces;
using core.Validations;
using core.Validations.Contracts;

namespace core.Inputs
{
    public class AddressInput: Notifiable, IValidatable
    {
        public string PostalCode { get; set; }
        public string Street { get; set; }
        public string Number { get; set; }
        public string Complement { get; set; }
        public string District { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public void Validate()
        {
            AddNotifications(
                new Contract().Requires()
                    .IsNotNullOrEmpty(PostalCode,"PostalCode","Cep é de preenchimento obrigatório")
                    .IsNotNullOrEmpty(Street,"Street","Logradouro é de preenchimento obrigatório")
                    .IsNotNullOrEmpty(District,"District","Bairro é de preenchimento obrigatório")
                    .IsNotNullOrEmpty(City,"City","Cidade é de preenchimento obrigatório")
                    .IsNotNullOrEmpty(State,"State","Estado é de preenchimento obrigatório")
                );
        }
    }
}