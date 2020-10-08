using core.Interfaces;
using core.Validations;
using core.Validations.Contracts;

namespace core.Inputs
{
    public class RequestClientPhoneNumberPasswordInput: Notifiable, IValidatable
    {
        public string PhoneNumber { get; set; }

        public RequestClientPhoneNumberPasswordInput(string phoneNumber)
        {
            this.PhoneNumber = phoneNumber;
        }

        public void Validate()
        {
             AddNotifications(
                new Contract().Requires()
                    .IsNotNullOrEmpty(PhoneNumber,"PhoneNumber","Número do telefone inválido")
                );
        }
    }
}