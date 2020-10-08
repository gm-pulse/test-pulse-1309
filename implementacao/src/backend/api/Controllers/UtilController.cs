using System.Threading.Tasks;
using core.Interfaces;
using core.Results;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class UtilController: ControllerBase
    {
        private readonly IConsultaEndereco consultaEnderecoService;

        public UtilController(IConsultaEndereco consultaEnderecoService)
        {
            this.consultaEnderecoService = consultaEnderecoService;
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

    }
}