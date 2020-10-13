using System.Threading.Tasks;
using core.Results;

namespace core.Interfaces
{
    public interface IPasswordVerification
    {
         Task<PasswordVerificationResult> SolicitarCodigoAsync(string phoneNumber, string channel);
         Task<PasswordVerificationResult> ValidarCodigoAsync(string phoneNumber, string code);
    }
}