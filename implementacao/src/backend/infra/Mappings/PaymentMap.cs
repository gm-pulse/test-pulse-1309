using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class PaymentMap
    {
         public static ModelBuilder OnPaymentCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Payment>(e=>{
                e.HasKey(p=>p.Id);
                e.ToTable("Pagamentos");
                e.Property(o=>o.Id).ValueGeneratedOnAdd();
                e.Property(o=>o.OrderId).HasColumnName("IdPedido").IsRequired();
                e.Property(o=>o.Amount).HasColumnName("Valor").IsRequired();
                e.Property(o=>o.Date).HasColumnName("Data").IsRequired();
                e.Property(o=>o.ExtraInfo).HasColumnName("Detahes");
                e.Property(o=>o.TypeId).HasColumnName("Tipo");
                e.HasOne(o=>o.Type).WithMany().HasForeignKey(o=>o.TypeId);
            });

            return modelBuilder;
        }
    }
}