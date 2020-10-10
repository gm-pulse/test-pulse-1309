using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class ProductMap
    {
         public static ModelBuilder OnProductCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Product>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("Produtos");

                e.Property(p=>p.Id).ValueGeneratedOnAdd();
                e.Property(p=>p.Description).HasColumnName("Descricao").IsRequired().HasMaxLength(200);
                e.Property(p=>p.Value).HasColumnName("Valor").IsRequired();
                e.Property(p=>p.UnitsInStock).HasColumnName("QuantidadeEstoque").IsRequired();
            });

            return modelBuilder;
        }
    }
}