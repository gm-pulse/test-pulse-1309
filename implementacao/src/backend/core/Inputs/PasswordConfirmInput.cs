using core.Interfaces;
using core.Validations;
using core.Validations.Contracts;

namespace core.Inputs
{
    public class PasswordConfirmInput : Notifiable, IValidatable
    {
        public string PhoneNumber { get; set; }
        public string PasswordCode { get; set; }

        public PasswordConfirmInput(string phoneNumber, string passwordCode)
        {
            this.PhoneNumber = phoneNumber;
            this.PasswordCode = passwordCode;
        }
        public void Validate()
        {
            AddNotifications(
                new Contract().Requires()
                    .IsNotNullOrEmpty(PhoneNumber,"PhoneNumber","Número do telefone inválido")
                    .IsNotNullOrEmpty(PasswordCode,"PasswordCode","Código de validação inválido")
                );
        }
    }
}