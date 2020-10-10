using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class OrderMap
    {
         public static ModelBuilder OnOrderCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Order>(e=>{
                e.HasKey(o=>o.Id);
                e.ToTable("Pedidos");

                e.Property(o=>o.Id).ValueGeneratedOnAdd();
                e.Property(o=>o.DiscountAmount).HasColumnName("ValorDesconto");
                e.Property(o=>o.TotalAmount).HasColumnName("ValorTotal").IsRequired();
                e.Property(o=>o.ClientId).HasColumnName("IdCliente").IsRequired();
                e.Property(o=>o.Date).HasColumnName("Data").IsRequired();
                e.Property(o=>o.ShippingAmount).HasColumnName("ValorFrete");
                e.Property(o=>o.CarrierId).HasColumnName("IdTransportadora");
                e.HasOne(o=>o.Client).WithMany().HasForeignKey(o=>o.ClientId);
                e.HasOne(o=>o.Carrier).WithMany().HasForeignKey(o=>o.CarrierId);
                e.HasMany(o=>o.Items).WithOne(i=>i.Order).HasForeignKey(i=>i.OrderId);
                e.HasMany(o=>o.Payments).WithOne(p=>p.Order).HasForeignKey(p=>p.OrderId);

            });

            return modelBuilder;
        }
    }
}