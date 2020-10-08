using core.Entities;
using infra.Mappings;
using Microsoft.EntityFrameworkCore;

namespace infra
{
    public class PulseTesteContext: DbContext
    {
        public PulseTesteContext(DbContextOptions<PulseTesteContext> options):base(options){
            this.Database.EnsureCreated();
        }

        public DbSet<Client> Clients { get; set; }
        public DbSet<Order> Orders { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder){
            modelBuilder.OnClientCreating();
            modelBuilder.OnAddressCreating();
            modelBuilder.OnOrderCreating();
        }
    }
}