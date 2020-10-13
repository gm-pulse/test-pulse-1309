using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class PaymentTypeMap
    {
        public static ModelBuilder OnPaymentTypeCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<PaymentType>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("TiposPagamento");

                e.Property(p=>p.Id).ValueGeneratedOnAdd();
                e.Property(p=>p.Description).HasColumnName("Descricao").IsRequired().HasMaxLength(100);
                e.Property(p=>p.TypeIdentifier).HasColumnName("Identificador").IsRequired().HasMaxLength(50);
            });

            return modelBuilder;
        }
    }
}