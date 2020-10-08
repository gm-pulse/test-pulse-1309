using System.Threading.Tasks;
using core.Entities;
using core.Inputs;
using core.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
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

        public ClienteController(IPasswordVerification passwordVerificationService,ClientService clienteService)
        {
            this.passwordVerificationService = passwordVerificationService;
            this.clienteService = clienteService;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="email"></param>
        /// <returns></returns>
        /// <response code="200">Token com os dados do cliente existente</response>
        /// <response code="204">Não existe um cliente com o email informado</response>  
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpGet("{email}")]
        public async Task<ActionResult> GetClientByEmail([FromRoute] string email){
            var result = await clienteService.ClientExists(email);
            if(!result)
                return NoContent();
            
            return Ok();
        }

        /// <summary>
        /// 
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
        /// <param name="telefoneCliente"></param>
        /// <returns></returns>
        /// <response code="422">Se os parametros para solicitar um código de validação forem inválidos</response>
        /// <response code="400">Se ocorrer alguma falha na solicitação de um código de validação</response>
        /// <response code="200">Código de validação enviado com sucesso</response>  
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPost("SolicitarCodigo/{telefoneCliente}")]
        public async Task<ActionResult> SolicitarCodigo([FromRoute] string telefoneCliente){
            var input = new RequestClientPhoneNumberPasswordInput(telefoneCliente);

            input.Validate();

            if(input.Invalid)
                return UnprocessableEntity(input.Notifications);
            
            var result = await passwordVerificationService.SolicitarCodigoAsync(telefoneCliente, "sms");
            
            if (!result.IsValid)
                return BadRequest(result.Errors);
            
            return Ok();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="telefoneCliente"></param>
        /// <param name="codigo"></param>
        /// <returns></returns>
        /// <response code="422">Se os parametros para validar um código de validação forem inválidos</response>
        /// <response code="400">Se ocorrer alguma falha na validação de um código de validação ou se o código for incorreto</response>
        /// <response code="200">Código validado com sucesso</response>  
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpPost("ValidarCodigo/{telefoneCliente}/{codigo}")]
        public async Task<IActionResult> ValidarCodigo([FromRoute] string telefoneCliente,[FromRoute] string codigo){
            var input = new PasswordConfirmInput(telefoneCliente, codigo);

            input.Validate();

            if(input.Invalid)
                return UnprocessableEntity(input.Notifications);
            
            var result = await passwordVerificationService.ValidarCodigoAsync(telefoneCliente, codigo);
            
            if (!result.IsValid)
                return BadRequest(result.Errors);
            
            return Ok();
        }

        
    }
}