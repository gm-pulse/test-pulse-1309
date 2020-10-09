using System.Threading.Tasks;
using core.Interfaces;
using core.Results;
using infra;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class UtilController: ControllerBase
    {
        private readonly IConsultaEndereco consultaEnderecoService;
        private readonly PulseTesteContext context;

        public UtilController(IConsultaEndereco consultaEnderecoService, PulseTesteContext context)
        {
            this.consultaEnderecoService = consultaEnderecoService;
            this.context = context;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="cep"></param>
        /// <returns>Detalhes de um endereço a partir de um CEP</returns>
        /// <response code="204">Se o cep estiver incorreto ou não existir</response>
        /// <response code="200">Retorna os detalhes do endereço de acordo com o cep consultado</response>  
        [HttpGet("ConsultarEndereco/{cep}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public async Task<ActionResult<EnderecoResult>> ConsultarEndereco([FromRoute] string cep){
            var result = await consultaEnderecoService.Consultar(cep);
            if(result.Erro)
                return NoContent();
            
            return Ok(result);
        }

        [HttpGet("ConsultarValorFrete/{cepDestino}")]
        public  ActionResult ConsultarValorFrete([FromRoute] string cepDestino){
           return Ok();
        }

    }
}