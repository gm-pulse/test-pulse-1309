using core.Interfaces;
using services;
using Xunit;

namespace test
{
    public class PasswordVerificationTest
    {
        [Fact]
        public void PossoSolicitarUmCodigo()
        {
            IPasswordVerification passwordVerificationService = new PasswordVerification();
            var result = passwordVerificationService.SolicitarCodigoAsync("+5585996549759","sms").Result;
            Assert.True(result.IsValid);
        }

        [Fact]
        public void ConsigoValidarUmCodigoRecebido()
        {
            IPasswordVerification passwordVerificationService = new PasswordVerification();
            var result = passwordVerificationService.ValidarCodigoAsync("+5585996549759","429412").Result;
            Assert.True(result.IsValid);
        }

        [Fact]
        public void UmCodigoInvaidoNaoEhValidado()
        {
            IPasswordVerification passwordVerificationService = new PasswordVerification();
            var result = passwordVerificationService.ValidarCodigoAsync("+5585996549759","123546").Result;
            Assert.False(result.IsValid);
        }
    }
}