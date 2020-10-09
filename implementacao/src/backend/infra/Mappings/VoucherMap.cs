using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class VoucherMap
    {
        public static ModelBuilder OnVoucherCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Voucher>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("ValesCompra");

                e.Property(v=>v.Id).ValueGeneratedOnAdd();
                e.Property(v=>v.ClientId).HasColumnName("IdCliente").IsRequired();
                e.Property(v=>v.Value).HasColumnName("Valor").IsRequired();
                e.Property(v=>v.ExpireAt).HasColumnName("DataValidade").IsRequired();
                e.Property(v=>v.Utilized).HasColumnName("Utilizado").IsRequired();
                e.Property(v=>v.Identifier).HasColumnName("Codigo").IsRequired().HasMaxLength(10);

                e.HasOne(v=>v.Client).WithMany().HasForeignKey(v=>v.ClientId);
            });

            return modelBuilder;
        }
    }
}