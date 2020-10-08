using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class OrderMap
    {
         public static ModelBuilder OnOrderCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Order>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("Pedidos");

                e.Property(p=>p.Id).ValueGeneratedOnAdd();
                e.Property(p=>p.DiscountAmount).HasColumnName("ValorDesconto");
                e.Property(p=>p.TotalAmount).HasColumnName("ValorTotal").IsRequired();
                e.Property(p=>p.ClientId).HasColumnName("IdCliente").IsRequired();
                
                e.HasOne(p=>p.Client).WithMany().HasForeignKey(p=>p.ClientId);

            });

            return modelBuilder;
        }
    }
}