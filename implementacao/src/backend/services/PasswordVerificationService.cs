using System.Collections.Generic;
using System.Threading.Tasks;
using core.Interfaces;
using core.Results;
using Twilio;
using Twilio.Exceptions;
using Twilio.Rest.Verify.V2.Service;

namespace services
{
    public class PasswordVerification : IPasswordVerification
    {
        const string AccountSid = "ACf90e3c01ba8c3f78f3d1aedf32b47e75";
        const string AuthToken = "0da07733a36130cef2664c49dc1345c6";
        const string VerificationSid  = "VAd5dd7ee8a481a2ef93577ca4016f3690";

        public PasswordVerification()
        {
            TwilioClient.Init(AccountSid,AuthToken);
        }
        public async Task<PasswordVerificationResult> SolicitarCodigoAsync(string phoneNumber, string channel)
        {
            try
            {
                var verificationResource = await VerificationResource.CreateAsync(
                    to: phoneNumber,
                    channel: channel,
                    pathServiceSid: VerificationSid
                );
                return new PasswordVerificationResult(verificationResource.Sid);
            }
            catch (TwilioException e)
            {
                return new PasswordVerificationResult(new List<string>{e.Message});
            }
        }

        public async Task<PasswordVerificationResult> ValidarCodigoAsync(string phoneNumber, string code)
        {
            try
            {
                var verificationCheckResource = await VerificationCheckResource.CreateAsync(
                    to: phoneNumber,
                    code: code,
                    pathServiceSid: VerificationSid
                );
                return verificationCheckResource.Status.Equals("approved") ?
                    new PasswordVerificationResult(verificationCheckResource.Sid) :
                    new PasswordVerificationResult(new List<string>{"Código incorreto. Tente novamente."});
            }
            catch (TwilioException e)
            {
                return new PasswordVerificationResult(new List<string>{e.Message});
            }
        }
    }
}