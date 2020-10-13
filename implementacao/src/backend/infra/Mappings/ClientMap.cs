using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class ClientMap
    {
        public static ModelBuilder OnClientCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Client>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("Clientes");

                e.Property(c=>c.Id).ValueGeneratedOnAdd();
                e.Property(c=>c.Name).HasColumnName("Nome").IsRequired();
                e.Property(c=>c.Telephone).HasColumnName("Telefone").IsRequired();

                e.HasMany(c=>c.Addresses).WithOne(a=>a.Client).HasForeignKey(a=>a.ClientId);

            });

            return modelBuilder;
        }
    }
}