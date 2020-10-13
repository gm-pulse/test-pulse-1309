using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class OrdemItemMap
    {
        public static ModelBuilder OnOrderItemCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<OrderItem>(e=>{
                e.HasKey(o=>new {o.OrderId, o.ProductId});
                e.ToTable("ItensPedido");

                e.Property(o=>o.OrderId).HasColumnName("IdPedido").IsRequired();
                e.Property(o=>o.ProductId).HasColumnName("IdProduto").IsRequired();
                e.Property(o=>o.Quantity).HasColumnName("Quantidade").IsRequired();
            });

            return modelBuilder;
        }
    }
}