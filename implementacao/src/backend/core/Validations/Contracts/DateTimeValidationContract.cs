using System;

namespace core.Validations.Contracts
{
    public partial class Contract
    {
        public Contract IsGreaterThan(DateTime val, DateTime comparer, string property, string message)
        {
            if (val <= comparer)
                AddNotification(property, message);

            return this;
        }

        public Contract IsGreater(int val, int comparer, string property, string message)
        {
            if (val < comparer)
                AddNotification(property, message);

            return this;
        }

        public Contract IsGreaterOrEqualsThan(DateTime val, DateTime comparer, string property, string message)
        {
            if (val < comparer)
                AddNotification(property, message);

            return this;
        }

        public Contract IsLowerThan(DateTime val, DateTime comparer, string property, string message)
        {
            if (val >= comparer)
                AddNotification(property, message);

            return this;
        }

        public Contract IsLowerOrEqualsThan(DateTime val, DateTime comparer, string property, string message)
        {
            if (val > comparer)
                AddNotification(property, message);

            return this;
        }

        public Contract IsBetween(DateTime val, DateTime from, DateTime to, string property, string message)
        {
            if (!(val >= from && val <= to))
                AddNotification(property, message);

            return this;
        }

        public Contract IsNullOrNullable(DateTime? val, string property, string message)
        {
            if (val == null)
            {
                AddNotification(property, message);
            }

            return this;
        }
    }
}