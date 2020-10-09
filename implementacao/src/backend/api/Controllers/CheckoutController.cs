using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class CheckoutController: ControllerBase
    {
         private readonly ILogger<CheckoutController> log;

        public CheckoutController(ILogger<CheckoutController> log)
        {
            this.log = log;
        }

        [HttpPost("Pagar")]
        public  ActionResult Get([FromBody] object input){
           return Ok();
        }

        [HttpGet("ValidarValeCompra/{codigo}")]
        public  ActionResult ValidarValeCompra([FromRoute] string codigo){
           return Ok();
        }

         [HttpGet("ValidarDesconto/{codigo}")]
        public  ActionResult ValidarDesconto([FromRoute] string codigo){
           return Ok();
        }
    }
}