using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class PedidoController: ControllerBase
    {
        private readonly ILogger<PedidoController> log;

        public PedidoController(ILogger<PedidoController> log)
        {
            this.log = log;
        }

        [HttpGet("listar/{codigoCliente}")]
        public  ActionResult Get([FromRoute] int codigoCliente){
           return Ok();
        }

        [HttpGet("{codigoPedido}")]
        public  ActionResult GetPedido([FromRoute] int codigoPeido){
           return Ok();
        }
        
    }
}