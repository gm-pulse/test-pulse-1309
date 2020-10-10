using System.Collections.Generic;
using System.Threading.Tasks;
using core.Interfaces;
using core.Results;
using infra;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using services;
using services.Frete;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class UtilController: ControllerBase
    {
        private readonly IConsultaEndereco consultaEnderecoService;
        private readonly CalcularFreteService calculoFreteService;
        private readonly ParametersService parametrosService;
        

        public UtilController(IConsultaEndereco consultaEnderecoService, CalcularFreteService calculoFreteService, ParametersService parametrosService)
        {
            this.consultaEnderecoService = consultaEnderecoService;
            this.calculoFreteService = calculoFreteService;
            this.parametrosService = parametrosService;
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

        /// <summary>
        /// 
        /// </summary>
        /// <param name="cepDestino"></param>
        /// <returns>Detalhes de um endereço a partir de um CEP</returns>
        /// <response code="422">Se o cep informado for inválido</response>
        /// <response code="400">Se ocorrer um erro na requisição</response>
        /// <response code="200">Retorna os valores de frete de acordo com o cep informado para cada transportador</response>
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [HttpGet("ConsultarValorFrete/{cepDestino}")]
        public  async Task<ActionResult<IList<ValorFreteResult>>> ConsultarValorFrete([FromRoute] string cepDestino){
           try{
                if(string.IsNullOrEmpty(cepDestino))
                    return UnprocessableEntity();
                return Ok(await calculoFreteService.Calcular(cepDestino));
           }catch{
                return BadRequest();
            }
        }

        [HttpGet("TiposPagamento")]
        public  ActionResult<Dictionary<string,string>> ConsultarTiposPagamento(){
            try{
                return Ok(parametrosService.ObterTiposPagamentos());
            }catch{
                return BadRequest();
            }
        }

    }
}