using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using core.Entities;
using core.Inputs;
using infra;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using Serilog;

namespace services
{
    public class ClientService
    {
        private readonly PulseTesteContext context;
        
        private readonly ILogger<ClientService> log;
        public ClientService(PulseTesteContext context, ILogger<ClientService> log)
        {
            this.context = context;
            this.log =log;
        }

        public async Task<Client> GetClientByEmail(string email){
            return await context.Clients.FirstOrDefaultAsync(c=>c.Email.Equals(email));
        }

        public async Task<Client> CreateClient(ClientInput model){
            try{
                var newClient = new Client{
                    Name = model.Name,
                    Email = model.Email,
                    Telephone = model.Telephone
                };
                await context.Clients.AddAsync(newClient);
                await context.SaveChangesAsync();
                return newClient;
            }catch(Exception error){
                log.LogError("Ocorreu o seguinte erro ao cadastrar um novo cliente na base de dados:",new object[]{});
                log.LogError(error.Message,new object[]{});
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message,new object[]{});
                log.LogError(JsonConvert.SerializeObject(model),new object[]{});
                throw error; 
            }
        }

        public async Task<IList<Address>> GetAddresses(int clientId){
            try{
                var clientddresses = await context.Addresses.Where(a=>a.ClientId == clientId).ToListAsync();
                return clientddresses;
            }catch(Exception error){
                log.LogError($"Ocorreu o seguinte erro ao buscar os endereços do cliente de código: {clientId}");
                log.LogError(error.Message,new object[]{});
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message,new object[]{});
                throw error; 
            }
        }

        public async Task<IList<Address>> AddAddress(AddressInput model, int clientId){
            try{
                var newAddress = new Address{
                    PostalCode = model.PostalCode,
                    Street = model.Street,
                    Number = model.Number,
                    City = model.City,
                    State = model.State,
                    District = model.District,
                    Complement = model.Complement,
                    ClientId = clientId
                };

                await context.Addresses.AddAsync(newAddress);
                await context.SaveChangesAsync();
                return await context.Addresses.Where(a=>a.ClientId == clientId).ToListAsync();

            }catch(Exception error){
                log.LogError($"Ocorreu o seguinte erro ao cadastrar um novo endereço para o cliente de código: {clientId}");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                log.LogError(JsonConvert.SerializeObject(model));
                throw error; 
            }
        }

        public async Task<IList<Address>> ChangeAddress(AddressInput model, int addressId,int clientId){
            try{
                var currentAddress = await context.Addresses.FirstOrDefaultAsync(a=>a.Id == addressId);

                if(currentAddress != null){
                    currentAddress.PostalCode = model.PostalCode;
                    currentAddress.Street = model.Street;
                    currentAddress.Number = model.Number;
                    currentAddress.City = model.City;
                    currentAddress.State = model.State;
                    currentAddress.District = model.District;
                    currentAddress.Complement = model.Complement;

                    await context.SaveChangesAsync();
                }
                return await context.Addresses.Where(a=>a.ClientId == clientId).ToListAsync();
                
            }catch(Exception error){
                log.LogError($"Ocorreu o seguinte erro ao alterar um endereço para o cliente de código: {clientId}");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                log.LogError(JsonConvert.SerializeObject(model));
                throw error; 
            }
        }

        public async Task<IList<Address>> RemoveAddress(int addressId,int clientId){
            try{
                var currentAddress = await context.Addresses.FirstOrDefaultAsync(a=>a.Id == addressId);

                if(currentAddress != null){
                    context.Addresses.Remove(currentAddress);
                    await context.SaveChangesAsync();
                }
                return await context.Addresses.Where(a=>a.ClientId == clientId).ToListAsync();
                
            }catch(Exception error){
                log.LogError($"Ocorreu o seguinte erro ao remover o endereço com Id: {addressId} para o cliente de código: {clientId}");
                log.LogError(error.Message);
                if(error.InnerException != null)
                    log.LogError(error.InnerException.Message);
                throw error; 
            }
        }
    }
}