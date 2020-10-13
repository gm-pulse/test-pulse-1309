using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using core.Entities;
using core.Inputs;
using core.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using services;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class ClienteController: ControllerBase
    {
        private readonly IPasswordVerification passwordVerificationService;
        private readonly ClientService clienteService;
        private readonly ILogger<ClienteController> log;

        public ClienteController(IPasswordVerification passwordVerificationService,ClientService clienteService, ILogger<ClienteController> log)
        {
            this.passwordVerificationService = passwordVerificationService;
            this.clienteService = clienteService;
            this.log = log;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="email"></param>
        /// <returns></returns>
        /// <response code="200">Será enviado um código de validação para o telefone do cliente para que ele valide seu acesso</response>
        /// <response code="204">Não existe um cliente com o email informado</response>
        /// <response code="400">Ocorreu um erro ao enviar um código de validação parao telefone do cliente</response>  
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet("{email}")]
        public async Task<ActionResult> GetClientByEmail([FromRoute] string email){
            var client = await clienteService.GetClientByEmail(email);

            if(client is null)
                return NoContent();
            
            var result = await passwordVerificationService.SolicitarCodigoAsync(client.Telephone, "sms");

            if (!result.IsValid){
                log.LogTrace(string.Join("\n",result.Errors));
                return BadRequest(result.Errors);
                
            }
                
            
            return Ok();
        }

        /// <summary>
        /// Método para realizar o cadastro de um cliente na plataforma
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        /// <response code="200">Novo cliente cadastrado</response>
        /// <response code="422">Parametros inválidos</response>
        /// <response code="400">Ocorreu um erro na requisição</response>  
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpPost()]
        public async Task<ActionResult<Client>> CreateClient([FromBody] ClientInput input){
            try{
                input.Validate();
                if(input.Invalid)
                    return UnprocessableEntity(input.Notifications);

                var result = await clienteService.CreateClient(input);
                return Ok(result);
            }catch{
                return BadRequest();
            }
        }

        

        /// <summary>
        /// 
        /// </summary>
        /// <param name="email"></param>
        /// <param name="codigo"></param>
        /// <returns></returns>
        /// <response code="422">Se os parametros para validar um código de validação forem inválidos</response>
        /// <response code="400">Se ocorrer alguma falha na validação de um código de validação ou se o código for incorreto</response>
        /// <response code="200">Código validado com sucesso</response>  
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPost("ValidarCodigo/{email}/{codigo}")]
        public async Task<ActionResult<Client>> ValidarCodigo([FromRoute] string email,[FromRoute] string codigo){
            var client = await clienteService.GetClientByEmail(email);
            var input = new PasswordConfirmInput(client.Telephone, codigo);

            input.Validate();

            if(input.Invalid){
                log.LogCritical("Chamada com parametros inválidos");
                log.LogCritical(string.Join("\n",input.Notifications));
                return UnprocessableEntity(input.Notifications);
            }
                
            var result = await passwordVerificationService.ValidarCodigoAsync(client.Telephone, codigo);
            
            if (!result.IsValid){
                log.LogCritical(string.Join("\n",result.Errors));
                return BadRequest(result.Errors);
            }
                
            return Ok(client);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="codigoCliente"></param>
        /// <returns></returns>
        /// <response code="204">Cliente sem endereço cadastrado</response>
        /// <response code="400">Se ocorrer alguma falha na buscar de endereços de um cliente</response>
        /// <response code="200">Lista de Endereços de um cliente</response>  
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpGet("{codigoCliente}/ListarEnderecos")]
        public async Task<ActionResult<IList<Address>>> ListarEnderecos([FromRoute] int codigoCliente){
            try{
                var clientAddress = await clienteService.GetAddresses(codigoCliente);

                if(!clientAddress.Any())
                    return NoContent();

                return Ok(clientAddress);

            }catch{
                return BadRequest();
            }
            
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="codigoCliente"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        /// <response code="422">Se os parametros para cadastrar um endereço forem inválidos</response>
        /// <response code="400">Se ocorrer alguma falha na criação de um endereço</response>
        /// <response code="200">Endereço cadastrado com sucesso</response>  
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPost("{codigoCliente}/AdicionarEndereco")]
        public async Task<ActionResult<IList<Address>>> AdicionarEndereco([FromRoute] int codigoCliente, [FromBody] AddressInput input){
            
            try{
                input.Validate();
                if(input.Invalid)
                    return UnprocessableEntity();
                
                var result = await clienteService.AddAddress(input,codigoCliente);
                return Ok(result);
            }catch{
                return BadRequest();
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="codigoCliente"></param>
        /// <param name="codigoEndereco"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        /// <response code="422">Se os parametros para cadastrar um endereço forem inválidos</response>
        /// <response code="400">Se ocorrer alguma falha na criação de um endereço</response>
        /// <response code="200">Endereço cadastrado com sucesso</response>  
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPut("{codigoCliente}/AlterarEndereco/{codigoEndereco}")]
        public async Task<ActionResult<IList<Address>>> AlterarEndereco([FromRoute] int codigoCliente, [FromRoute] int codigoEndereco,[FromBody] AddressInput input){
            
            try{
                input.Validate();
                if(input.Invalid)
                    return UnprocessableEntity();
                
                var result = await clienteService.ChangeAddress(input,codigoEndereco,codigoCliente);
                return Ok(result);
            }catch{
                return BadRequest();
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="codigoCliente"></param>
        /// <param name="codigoEndereco"></param>
        /// <returns></returns>
        /// <response code="400">Se ocorrer alguma falha na criação de um endereço</response>
        /// <response code="200">Endereço cadastrado com sucesso</response>  
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpDelete("{codigoCliente}/AlterarEndereco/{codigoEndereco}")]
        public async Task<ActionResult<IList<Address>>> ExcluirEndereco([FromRoute] int codigoCliente, [FromRoute] int codigoEndereco){
            
            try{
               var result = await clienteService.RemoveAddress(codigoEndereco,codigoCliente);
                return Ok(result);
            }catch{
                return BadRequest();
            }
        }


        
    }
}