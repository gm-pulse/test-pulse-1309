using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class DiscountMap
    {
         public static ModelBuilder OnDiscountCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Discount>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("Descontos");
                e.Property(v=>v.Id).ValueGeneratedOnAdd();
                e.Property(v=>v.Value).HasColumnName("Valor").IsRequired();
                e.Property(v=>v.ExpireAt).HasColumnName("DataValidade").IsRequired();
                e.Property(v=>v.Utilized).HasColumnName("Utilizados").IsRequired();
                e.Property(v=>v.Identifier).HasColumnName("Codigo").IsRequired().HasMaxLength(10);
                e.Property(v=>v.MaxUse).HasColumnName("Quantidade").IsRequired().HasDefaultValue(0);
            });

            return modelBuilder;
        }
    }
}