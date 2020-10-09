using System.Threading.Tasks;
using core.Results;
using core.ValueObjects;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using services.Pagamento;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class CheckoutController: ControllerBase
    {
         private readonly ILogger<CheckoutController> log;
         private readonly PagamentoService pagamentoService;

        public CheckoutController(ILogger<CheckoutController> log,PagamentoService pagamentoService)
        {
            this.log = log;
            this.pagamentoService = pagamentoService;
        }

        [HttpPost("Pagar")]
        public  async Task<ActionResult<CobrancaResult>> Pagar([FromBody] object input){
           try{
               //var obj = JsonConvert.DeserializeObject(input.ToString());
               return Ok(await pagamentoService.Efetivar(input.ToString()));
               //return Ok();

           }catch{
                return BadRequest();
            }
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