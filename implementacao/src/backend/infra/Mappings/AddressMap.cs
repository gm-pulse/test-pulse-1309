using core.Entities;
using Microsoft.EntityFrameworkCore;

namespace infra.Mappings
{
    public static class AddressMap
    {
        public static ModelBuilder OnAddressCreating(this ModelBuilder modelBuilder){
            modelBuilder.Entity<Address>(e=>{
                e.HasKey(c=>c.Id);
                e.ToTable("Enderecos");

                e.Property(a=>a.Id).ValueGeneratedOnAdd();
                e.Property(a=>a.PostalCode).HasColumnName("Cep").IsRequired();
                e.Property(a=>a.Street).HasColumnName("Logradouro").IsRequired();
                e.Property(a=>a.Complement).HasColumnName("Complemento");
                e.Property(a=>a.District).HasColumnName("Bairro").IsRequired();
                e.Property(a=>a.City).HasColumnName("Cidade").IsRequired();
                e.Property(a=>a.State).HasColumnName("Uf").IsRequired();
                e.Property(a=>a.ClientId).HasColumnName("IdCliente").IsRequired();

                e.HasOne(a=>a.Client).WithMany(c=>c.Addresses).HasForeignKey(a=>a.ClientId);

            });

            return modelBuilder;
        }
    }
}