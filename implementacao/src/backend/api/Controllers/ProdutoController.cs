using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace api.Controllers
{
    
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class ProdutoController: ControllerBase
    {
        private readonly ILogger<ProdutoController> log;

        public ProdutoController(ILogger<ProdutoController> log)
        {
            this.log = log;
        }

        [HttpGet]
        public  ActionResult Get(){
           return Ok();
        }

        [HttpGet("{id}")]
        public  ActionResult Get([FromRoute] int id){
           return Ok();
        }

        
    }
}