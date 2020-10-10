\connect "checkoutdb"

-- Tabela Clientes

CREATE TABLE public."Clientes" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Nome" text NOT NULL,
	"Telefone" text NOT NULL,
	"Email" text NULL,
	CONSTRAINT "PK_Clientes" PRIMARY KEY ("Id")
);

-- Tabela Endereços

CREATE TABLE public."Enderecos" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Cep" text NOT NULL,
	"Logradouro" text NOT NULL,
	"Numero" text NULL,
	"Complemento" text NULL,
	"Cidade" text NOT NULL,
	"Uf" text NOT NULL,
	"Bairro" text NOT NULL,
	"IdCliente" int4 NOT NULL,
	CONSTRAINT "PK_Enderecos" PRIMARY KEY ("Id")
);
CREATE INDEX "IX_Enderecos_IdCliente" ON public."Enderecos" USING btree ("IdCliente");


-- public."Enderecos" foreign keys

ALTER TABLE public."Enderecos" ADD CONSTRAINT "FK_Enderecos_Clientes_IdCliente" FOREIGN KEY ("IdCliente") REFERENCES "Clientes"("Id") ON DELETE CASCADE;

-- Tabela Transportadoras

CREATE TABLE public."Transportadoras" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Nome" varchar(100) NOT NULL,
	"NomeServico" varchar(20) NOT NULL,
	"Ativo" bool NOT NULL,
	CONSTRAINT "PK_Transportadoras" PRIMARY KEY ("Id")
);

-- Tabela Tipos Pagamento

CREATE TABLE public."TiposPagamento" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Descricao" varchar(100) NOT NULL,
	"Identificador" varchar(50) NOT NULL,
	CONSTRAINT "PK_TiposPagamento" PRIMARY KEY ("Id")
);

-- Tabela Produtos

CREATE TABLE public."Produtos" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Descricao" varchar(200) NOT NULL,
	"Valor" float4 NOT NULL,
	"QuantidadeEstoque" int4 NOT NULL,
	CONSTRAINT "PK_Produtos" PRIMARY KEY ("Id")
);

-- Tabela Pedidos

CREATE TABLE public."Pedidos" (
	"Id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Data" timestamp NOT NULL,
	"ValorTotal" float4 NOT NULL,
	"ValorDesconto" float4 NULL,
	"IdCliente" int4 NOT NULL,
	"ValorFrete" float4 NOT NULL,
	"IdTransportadora" int4 NOT NULL,
	CONSTRAINT "PK_Pedidos" PRIMARY KEY ("Id")
);
CREATE INDEX "IX_Pedidos_IdCliente" ON public."Pedidos" USING btree ("IdCliente");
CREATE INDEX "IX_Pedidos_IdTransportadora" ON public."Pedidos" USING btree ("IdTransportadora");


-- public."Pedidos" foreign keys

ALTER TABLE public."Pedidos" ADD CONSTRAINT "FK_Pedidos_Clientes_IdCliente" FOREIGN KEY ("IdCliente") REFERENCES "Clientes"("Id") ON DELETE CASCADE;
ALTER TABLE public."Pedidos" ADD CONSTRAINT "FK_Pedidos_Transportadoras_IdTransportadora" FOREIGN KEY ("IdTransportadora") REFERENCES "Transportadoras"("Id") ON DELETE CASCADE;

-- Tabela Itens Pedido

CREATE TABLE public."ItensPedido" (
	"IdPedido" int8 NOT NULL,
	"IdProduto" int4 NOT NULL,
	"Quantidade" int4 NOT NULL,
	CONSTRAINT "PK_ItensPedido" PRIMARY KEY ("IdPedido", "IdProduto")
);
CREATE INDEX "IX_ItensPedido_IdProduto" ON public."ItensPedido" USING btree ("IdProduto");


-- public."ItensPedido" foreign keys

ALTER TABLE public."ItensPedido" ADD CONSTRAINT "FK_ItensPedido_Pedidos_IdPedido" FOREIGN KEY ("IdPedido") REFERENCES "Pedidos"("Id") ON DELETE CASCADE;
ALTER TABLE public."ItensPedido" ADD CONSTRAINT "FK_ItensPedido_Produtos_IdProduto" FOREIGN KEY ("IdProduto") REFERENCES "Produtos"("Id") ON DELETE CASCADE;

-- Tabela Pagamentos

CREATE TABLE public."Pagamentos" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Valor" float4 NOT NULL,
	"Data" timestamp NOT NULL,
	"Detahes" text NULL,
	"IdPedido" int8 NOT NULL,
	"Tipo" int4 NOT NULL,
	CONSTRAINT "PK_Pagamentos" PRIMARY KEY ("Id")
);
CREATE INDEX "IX_Pagamentos_IdPedido" ON public."Pagamentos" USING btree ("IdPedido");
CREATE INDEX "IX_Pagamentos_Tipo" ON public."Pagamentos" USING btree ("Tipo");


-- public."Pagamentos" foreign keys

ALTER TABLE public."Pagamentos" ADD CONSTRAINT "FK_Pagamentos_Pedidos_IdPedido" FOREIGN KEY ("IdPedido") REFERENCES "Pedidos"("Id") ON DELETE CASCADE;
ALTER TABLE public."Pagamentos" ADD CONSTRAINT "FK_Pagamentos_TiposPagamento_Tipo" FOREIGN KEY ("Tipo") REFERENCES "TiposPagamento"("Id") ON DELETE CASCADE;

-- Tabela Vales Compra

CREATE TABLE public."ValesCompra" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Codigo" varchar(10) NOT NULL,
	"IdCliente" int4 NOT NULL,
	"Valor" float4 NOT NULL,
	"DataValidade" timestamp NOT NULL,
	"Utilizado" bool NOT NULL,
	CONSTRAINT "PK_ValesCompra" PRIMARY KEY ("Id")
);
CREATE INDEX "IX_ValesCompra_IdCliente" ON public."ValesCompra" USING btree ("IdCliente");


-- public."ValesCompra" foreign keys

ALTER TABLE public."ValesCompra" ADD CONSTRAINT "FK_ValesCompra_Clientes_IdCliente" FOREIGN KEY ("IdCliente") REFERENCES "Clientes"("Id") ON DELETE CASCADE;

-- Tabela Descontos

CREATE TABLE public."Descontos" (
	"Id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"Codigo" varchar(10) NOT NULL,
	"Valor" float4 NOT NULL,
	"DataValidade" timestamp NOT NULL,
	"Quantidade" int4 NOT NULL DEFAULT 0,
	"Utilizados" int4 NOT NULL,
	CONSTRAINT "PK_Descontos" PRIMARY KEY ("Id")
);



--- Insert's 

insert into public."Transportadoras" ("Nome", "NomeServico", "Ativo")
values 
('Correios Brasil','CORREIOS','1'),
('Fedex','FEDEX','1'),
('Jadlog','JADLOG','1'),
('Mercado Envios','MERCADOENVIOS','1');

insert into public."TiposPagamento" ("Descricao", "Identificador")
values
('Cartão de Crédito','CARTAOCREDITO'),
('Vale-Compra','VALECOMPRA');


insert into public."Descontos" ("Codigo","Valor", "DataValidade","Quantidade")
values
('RBWGJL5Y',5,'2020-10-31',10),
('7USOPBGE',5,'2020-09-30',20),
('1MRGUIZF',10.5,'2020-11-15',30);

insert into public."Descontos" ("Codigo","Valor", "DataValidade","Quantidade","Utilizados")
values
('MJ889P1I',5,'2020-12-31',10,10),
('68VXEPBI',7,'2020-12-31',10,9);