using System;
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
        //private readonly ILogger log;
        private readonly ILogger<ClientService> log;
        public ClientService(PulseTesteContext context, ILogger<ClientService> log)
        {
            this.context = context;
            this.log =log;
        }

        public async Task<bool> ClientExists(string email){
            return await context.Clients.AnyAsync(c=>c.Email.Equals(email));
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
    }
}