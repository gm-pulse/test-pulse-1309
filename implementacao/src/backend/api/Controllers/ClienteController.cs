using System.Threading.Tasks;
using core.Inputs;
using core.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class ClienteController: ControllerBase
    {
        private readonly IPasswordVerification passwordVerificationService;

        public ClienteController(IPasswordVerification passwordVerificationService)
        {
            this.passwordVerificationService = passwordVerificationService;
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