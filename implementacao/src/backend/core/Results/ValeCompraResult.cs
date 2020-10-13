using System;

namespace core.Results
{
    public class VoucherResult
    {
        public string Code { get; set; }
        public float Value { get; set; }
        public string ErrMessage { get; set; }
        public DateTime ExpireAt { get; set; }
        public int ClienteId { get; set; }
    }
}