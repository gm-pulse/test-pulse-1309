## Documentação da sua Solução

### Parametros para teste
**Teste de Pagamento com Cartão de Crédito**
```
{
  "Tipo":"CARTAOCREDITO",
  "ValorCompra":150,
  "NumeroParcelas":1,
  "NumeroPedido":2020100801,
  "NomeCliente":"Arthur André G Castro",
  "NumeroCartao":"4024007153763191",
  "ValidadeCartao":"12/2021",
  "CodigoSeguranca":"087"
}
```



A solução pode ser executada através do docker, utilizando 03 contêineres definidos conforme abaixo: <br/>
* **API** (*http://localhost:8080*) - Contêiner utilizado para subir a api rest que funciona como backend da solução.
* **Banco de Dados** - Contêiner utilizado para subir a o banco de dados Postgres na porta *5432*.
* **Monitoramento de Log** (*http://localhost:5431*) - Contêiner utilizado para subir uma aplicação (Seq - Datalust) para monitoramento dos logs da API.

Para rodar a solução utilizando o docker pode ser utilizado o arquivo [Docker Compose](/implementacao/src/docker-compose.yml)

#### Visualização de logs da API
Através da ferramenta Seq da DataLust (https://datalust.co) é possível monitorar o log da api desenvolvida.<br/>
A ferramenta está disponível através da URL http://localhost:5341 <br/>
![seq print](/documentacao/img/seq.png)

#### Rest API
O backend da solução proposta está disponível através da seguinte URL: http://localhost:8080 <br/>

![swagger print](/documentacao/img/api.png)
