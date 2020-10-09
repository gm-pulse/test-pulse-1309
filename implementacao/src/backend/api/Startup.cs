using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using core.Interfaces;
using infra;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Models;
using services;
using services.Endereco;
using services.Frete;
using services.Pagamento;

namespace api
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddScoped<IPasswordVerification,PasswordVerification>();
            services.AddScoped<IConsultaEndereco,ConsultaEnderecoService>();
            services.AddScoped<ClientService>();
            services.AddScoped<EmailService>();
            services.AddScoped<ParametrosService>();

            //Injeção de dependência dos serviços de frete
            services.AddScoped<IFreteService,CorreioService>();
            services.AddScoped<IFreteService,FedexService>();
            services.AddScoped<IFreteService,JadLogService>();
            services.AddScoped<IFreteService,MercadoEnvioService>();
            services.AddScoped<CalcularFreteService>();

            //Injeção de dependência dos serviços de pagamento
            services.AddScoped<IPagamentoService,CieloService>();
            services.AddScoped<IPagamentoService,ValeCompraService>();
            services.AddScoped<PagamentoService>();

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1",
                    new OpenApiInfo
                    {
                        Title = "Pulse Ecommerce API",
                        Version = "v1",
                        Description = "Backend REST-API Pulse Ecommerce"
                    });
                var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
                var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
                c.IncludeXmlComments(xmlPath);
            });

            var connectionString = Environment.GetEnvironmentVariable("DB_CONNECTION_STRING");
            #if DEBUG
                connectionString = "host=localhost;port=5432;database=checkoutdb;username=pulseusr;password=_62wjr?b7t6?86fdn65mg";
            #endif
            services.AddDbContext<PulseTesteContext>(options =>
                options.UseNpgsql(
                    connectionString
                )
            );
            
            services.AddControllers()
            .ConfigureApiBehaviorOptions(options =>{
                 options.SuppressMapClientErrors = true;
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseHttpsRedirection();

            app.UseSwagger();

            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("./swagger/v1/swagger.json", "Pulse Ecommerce API V1");
                c.RoutePrefix = string.Empty;
            });

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
