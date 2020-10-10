using core.Entities;
using infra.Mappings;
using Microsoft.EntityFrameworkCore;

namespace infra
{
    public class PulseTesteContext: DbContext
    {
        public PulseTesteContext(DbContextOptions<PulseTesteContext> options):base(options){
            this.Database.EnsureDeleted();
            this.Database.EnsureCreated();
        }

        public DbSet<Client> Clients { get; set; }

        public DbSet<Address> Addresses { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<OrderItem> OrderItems { get; set; }
        public DbSet<Payment> Payments { get; set; }
        public DbSet<PaymentType> PaymentTypes { get; set; }
        public DbSet<Voucher> Vouchers { get; set; }
        public DbSet<Carrier> Carriers { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder){
            modelBuilder.OnClientCreating();
            modelBuilder.OnAddressCreating();
            modelBuilder.OnOrderCreating();
            modelBuilder.OnCarrierCreating();
            modelBuilder.OnPaymentTypeCreating();
            modelBuilder.OnVoucherCreating();
            modelBuilder.OnPaymentCreating();
            modelBuilder.OnOrderItemCreating();
            modelBuilder.OnProductCreating();
        }
    }
}