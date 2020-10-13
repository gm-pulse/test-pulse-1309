## Documentação da sua Solução

### Modelo Banco de Dados
O modelo do banco de dados pensado para a solucão pode ser encontrado [aqui](/documentacao/diagrama.pdf)

### Integração Cielo 
Fpoi implementada uma integração com a plataforma da Cielo para realização da implementação do checkout transparente.

### Observações
Ao cadastrar um novo cliente, o sistema irá gerar de forma automática 03 vales-compra para serem utilizados na validação da rotina de pagamento utilizando vale-compra. Sendo 01 vale-compra já expirado, 01 já utilizado e 01 válido, para possibilitar o teste de alguns cenários.

Para testar o pagamento através de cartão de crédito podem ser utilizados os cartões abaixo, no caso da data de validade do cartão basta ser informado um período superior à data atual e para o código de segurança, um número qualquer de 03 posições, como  087 por exemplo.
* **4024007153763191** *Cartão válido para o ambiente de teste*
* **4024007153763192** *Cartão que irá retornar uma compra não autorizada no ambiente de teste*

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
