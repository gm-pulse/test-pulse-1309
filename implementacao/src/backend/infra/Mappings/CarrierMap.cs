using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class CarrierMap
    {
        public static ModelBuilder OnCarrierCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Carrier>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("Transportadoras");
                e.Property(c=>c.Id).ValueGeneratedOnAdd();
                e.Property(c=>c.Name).HasColumnName("Nome").IsRequired().HasMaxLength(100);
                e.Property(c=>c.ProviderName).HasColumnName("NomeServico").IsRequired().HasMaxLength(20);
                e.Property(c=>c.Active).HasColumnName("Ativo").IsRequired();
            });

            return modelBuilder;
        }
    }
}