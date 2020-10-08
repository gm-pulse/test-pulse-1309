using System.Threading.Tasks;
using core.Inputs;
using core.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ClienteController: ControllerBase
    {
        private readonly IPasswordVerification passwordVerificationService;

        public ClienteController(IPasswordVerification passwordVerificationService)
        {
            this.passwordVerificationService = passwordVerificationService;
        }

        [HttpPost("SolicitarCodigo/{telefoneCliente}")]
        public async Task<IActionResult> SolicitarCodigo([FromRoute] string telefoneCliente){
            var input = new RequestClientPhoneNumberPasswordInput(telefoneCliente);

            input.Validate();

            if(input.Invalid)
                return UnprocessableEntity(input.Notifications);
            
            var result = await passwordVerificationService.SolicitarCodigoAsync(telefoneCliente, "sms");
            
            if (!result.IsValid)
                return BadRequest(result.Errors);
            
            return Ok();
        }

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