using System.Threading.Tasks;
using core.Results;
using core.ValueObjects;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using services;
using services.Pagamento;

namespace api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Produces("application/json")]
    public class CheckoutController: ControllerBase
    {
        private readonly ILogger<CheckoutController> log;
        private readonly PaymentService pagamentoService;
        private readonly DiscountService discountService;
        private readonly VoucherService voucherService;

        public CheckoutController(ILogger<CheckoutController> log,PaymentService pagamentoService, DiscountService discountService, VoucherService voucherService)
        {
            this.log = log;
            this.pagamentoService = pagamentoService;
            this.discountService = discountService;
            this.voucherService = voucherService;
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

        /// <summary>
        /// 
        /// </summary>
        /// <param name="codigo"></param>
        /// <returns></returns>
        /// <response code="200">Detalhes do desconto que será aplicado ao pedido</response>
        /// <response code="400">Ocorreu um erro ao consultar os dados do desconto</response>  
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet("ValidarValeCompra/{codigo}")]
        public  async Task<ActionResult<VoucherResult>> ValidarValeCompra([FromRoute] string codigo){
           try{
               return Ok(await voucherService.ConsultarValeCompra(codigo));
           }catch{
                return BadRequest();
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="codigo"></param>
        /// <returns></returns>
        /// <response code="200">Detalhes do desconto que será aplicado ao pedido</response>
        /// <response code="400">Ocorreu um erro ao consultar os dados do desconto</response>  
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [HttpGet("ValidarDesconto/{codigo}")]
        public  async Task<ActionResult<DescontoResult>> ValidarDesconto([FromRoute] string codigo){
           try{
               return Ok(await discountService.ConsultarDesconto(codigo));
           }catch{
                return BadRequest();
            }
        }
    }
}