using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using SendGrid;
using SendGrid.Helpers.Mail;

namespace services
{
    public class EmailService
    {
        private readonly IConfiguration configuration;

        public EmailService(IConfiguration configuration)
        {
            this.configuration = configuration;
        }
        public async Task SendMessage(EmailAddress to, string subject, string message){
            var apiKey= configuration.GetValue<string>("SendGridApiKey");
            var sendGridClient = new SendGridClient(apiKey);
            var from = new EmailAddress("sac@pulsecommerce.com", "Atendimento ao Cliente");
            var msg = MailHelper.CreateSingleEmail(from, to, subject, string.Empty, message);
            var response = await sendGridClient.SendEmailAsync(msg);
        }
    }
}