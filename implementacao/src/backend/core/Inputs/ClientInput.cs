using core.Interfaces;
using core.Validations;
using core.Validations.Contracts;

namespace core.Inputs
{
    public class ClientInput : Notifiable, IValidatable
    {
        public string Name { get; set; }
        public string Telephone { get; set; }
        public string Email { get; set; }

        public void Validate()
        {
            AddNotifications(
                new Contract().Requires()
                    .IsNotNullOrEmpty(Name,"Name","Nome é de preenchimento obrigatório")
                    .IsNotNullOrEmpty(Email,"Email","Email é de preenchimento obrigatório")
                    .IsNotNullOrEmpty(Telephone,"Telephone","Número do telefone é de preenchimento obrigatório")
                );
        }
    }
}