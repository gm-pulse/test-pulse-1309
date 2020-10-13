using System.Collections.Generic;

namespace core.Results
{
    public class PasswordVerificationResult
    {
        public PasswordVerificationResult(string sid)
        {
            Sid = sid;
            IsValid = true;
        }

        public PasswordVerificationResult(List<string> errors)
        {
            Errors = errors;
            IsValid = false;
        }

        public bool IsValid { get; set; }

        public string Sid { get; set; }
        
        public List<string> Errors { get; set; }
    }
}