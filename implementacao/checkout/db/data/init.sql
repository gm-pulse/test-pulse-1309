
GRANT ALL PRIVILEGES ON DATABASE checkout TO postgres;

create table public.ck01_produto
(
	ck01_cod_produto bigint not null
		constraint ck01_produto_pk
			primary key,
	ck01_descricao varchar(255) not null,
	ck01_valor_unitario numeric not null
);

alter table public.ck01_produto owner to postgres;

create unique index ck01_produto_ck01_cod_produto_uindex
	on public.ck01_produto (ck01_cod_produto);

create sequence IF NOT EXISTS public.ck01_produto_ck01_cod_produto_seq;
    alter sequence public.ck01_produto_ck01_cod_produto_seq owner to postgres;


create table public.ck03_endereco
(
	ck03_cod_endereco bigserial not null
		constraint ck03_endereco_pk
			primary key,
	ck03_rua varchar(255) not null,
	ck03_numero integer not null,
	ck03_complemento varchar(50),
	ck03_bairro varchar(255) not null,
	ck03_cep varchar(9) not null

);

alter table public.ck03_endereco owner to postgres;

create unique index ck03_endereco_ck03_cod_endereco_uindex
	on public.ck03_endereco (ck03_cod_endereco);

create sequence IF NOT EXISTS public.ck03_endereco_ck03_cod_endereco_seq;
    alter sequence public.ck03_endereco_ck03_cod_endereco_seq owner to postgres;


create table public.ck02_cliente
(
	ck02_cod_cliente bigserial not null
		constraint ck02_cliente_pk
			primary key,
	ck02_nome_cliente varchar(255) not null,
	ck02_cpf_cliente varchar(14) not null,
	ck02ck03_cod_endereco bigint not null
		constraint fkck02_cliente_endereco
			references public.ck03_endereco
);

alter table public.ck02_cliente owner to postgres;

create unique index ck02_cliente_ck02_cod_cliente_uindex
	on public.ck02_cliente (ck02_cod_cliente);

create sequence IF NOT EXISTS public.ck02_cliente_ck02_cod_cliente_seq;
    alter sequence public.ck02_cliente_ck02_cod_cliente_seq owner to postgres;



create table public.ck04_transportadora
(
	ck04_cod_transportadora bigserial not null
		constraint ck04_transportadora_pk
			primary key,
	ck04_nome varchar(255) not null,
	ck04_cnpj varchar(14) not null,
	ck04_valor_frete numeric not null
);

alter table public.ck04_transportadora owner to postgres;

create unique index ck04_transportadora_ck04_cod_transportadora_uindex
	on public.ck04_transportadora (ck04_cod_transportadora);

create sequence IF NOT EXISTS public.ck04_transportadora_ck04_cod_transportadora_seq;
    alter sequence public.ck04_transportadora_ck04_cod_transportadora_seq owner to postgres;


create table public.ck06_tipo_pagamento
(
	ck06_cod_tipo_pag bigserial not null
		constraint ck06_tipo_pagamento_pk
			primary key,
	ck06_descricao varchar(100) not null
);

alter table public.ck06_tipo_pagamento owner to postgres;

create unique index ck06_tipo_pagamento_ck06_cod_tipo_pag_uindex
	on public.ck06_tipo_pagamento (ck06_cod_tipo_pag);

create sequence IF NOT EXISTS public.ck06_tipo_pagamento_ck06_cod_tipo_pag_seq;
    alter sequence public.ck06_tipo_pagamento_ck06_cod_tipo_pag_seq owner to postgres;

create table public.ck07_carrinho_compras
(
	ck07_cod_carrinho_compras bigserial not null
		constraint ck07_carrinho_compras_pk
			primary key,
	ck07_total_itens integer not null,
	ck07_valor_total numeric not null,
	ck07ck02_cod_cliente bigint not null
		constraint fkck07ck02_carrinho_cliente
			references public.ck02_cliente
);

alter table public.ck07_carrinho_compras owner to postgres;

create unique index ck07_carrinho_compras_ck07_cod_carrinho_compras_uindex
	on public.ck07_carrinho_compras (ck07_cod_carrinho_compras);

create sequence IF NOT EXISTS public.ck07_carrinho_compras_ck07_cod_carrinho_compras_seq;
    alter sequence public.ck07_carrinho_compras_ck07_cod_carrinho_compras_seq owner to postgres;



create table public.ck10_status_compra
(
	ck10_cod_status_compra bigserial not null
		constraint ck10_status_compra_pk
			primary key,
	ck10_descricao varchar(25) not null
);

alter table public.ck10_status_compra owner to postgres;

create table public.ck09_compra
(
	ck09_cod_compra bigserial not null
		constraint ck09_compra_pk
			primary key,
	ck09_cod_rastreio varchar(13) not null,
	ck09_numero_pedido varchar(8) not null,
	ck09ck10_cod_status bigint not null
	    constraint fkck09ck10_cod_status_compra
			references public.ck10_status_compra
);

alter table public.ck09_compra owner to postgres;

create unique index ck09_compra_ck09_cod_compra_uindex
	on public.ck09_compra (ck09_cod_compra);


create table public.ck05_pagamento
(
	ck05_cod_pagamento bigserial not null
		constraint ck05_pagamento_pk
			primary key,
	ck05ck06_cod_tipo_pag bigint not null
		constraint ck05ck06_pagamento_tipo
			references public.ck06_tipo_pagamento,
	ck05ck07_cod_carrinho bigint not null
		constraint ck05ck07_pagamento_carrinho
			references public.ck07_carrinho_compras,
	ck05ck03_cod_endereco bigint not null
	    constraint ck05ck03_cod_endereco_entrega
	        references public.ck03_endereco,
	ck05ck04_cod_transportadora bigint not null
		constraint ck05ck04_pagamento_transportadora
			references public.ck04_transportadora,
	ck05ck09_cod_compra bigint not null
		constraint ck05ck09_pagamento_compra
			references public.ck09_compra,
	ck05_valor_total numeric not null
);

alter table public.ck05_pagamento owner to postgres;

create unique index ck05_pagamento_ck05_cod_pagamento_uindex
	on public.ck05_pagamento (ck05_cod_pagamento);

create sequence IF NOT EXISTS public.ck05_pagamento_ck05_cod_pagamento_seq;
    alter sequence public.ck05_pagamento_ck05_cod_pagamento_seq owner to postgres;


create table public.ck08_produto_pedido
(
	ck08_cod_produto_pedido bigserial not null
		constraint ck08_produtos_pedidos_pk
			primary key,
	ck08ck01_cod_produto bigint not null
		constraint fkck08ck01_produto_pedidos
			references public.ck01_produto,
	ck08_qtd_itens integer not null,
	ck08_valor_total numeric not null,
	ck08ck07_cod_carrinho_compras bigint not null
		constraint fkck08ck07_pedido_carrinho
			references public.ck07_carrinho_compras
			on delete cascade
);

alter table public.ck08_produto_pedido owner to postgres;

create unique index ck08_produtos_pedidos_ck08_cod_produtos_pedidos_uindex
	on public.ck08_produto_pedido (ck08_cod_produto_pedido);




-- Inserindo os dados:

--Produtos(CK01_PRODUTO):

INSERT INTO
    public.ck01_produto (ck01_cod_produto, ck01_descricao, ck01_valor_unitario) VALUES
        (1, 'Sabonete Granado', 2.55),
        (2, 'Shampoo Seda', 15.39),
        (3, 'Arroz Tio Jorge', 5.1),
        (4, 'Nescau em pó', 7.99),
        (5, 'Leite Piracanjuba Integral', 5.12),
        (6, 'Papel Toalha Scala 2x1', 2.99),
        (7, 'Refrigerante Guaraná Antartica 2L ', 4.99);

        SELECT pg_catalog.setval('public.ck01_produto_ck01_cod_produto_seq', 8, true);


--Endereco (CK03_ENDERECO):
INSERT INTO
    public.ck03_endereco (ck03_cod_endereco, ck03_rua, ck03_numero, ck03_complemento, ck03_bairro, ck03_cep) VALUES
        (1,'Rua Júpiter', 704, null, 'Raiar do Sol', '69316-042'),
        (2,'Rua São Miguel', 13, 'Quadra 20', 'Pavão Filho', '65081-072'),
        (3,'Rua 12', 28, null, 'Cidade Operária', '65051-652');

    SELECT pg_catalog.setval('public.ck03_endereco_ck03_cod_endereco_seq', 4, true);


--Cliente (CK02_CLIENTE):
INSERT INTO
    public.ck02_cliente (ck02_cod_cliente, ck02_nome_cliente, ck02_cpf_cliente, ck02ck03_cod_endereco) VALUES
        (1, 'Luiza Isabelly Pinto', '39336033824', 1),
        (2, 'Lúcia Lavínia Vanessa da Mota', '86458325770', 2),
        (3, 'Martin Felipe Lorenzo Sales', '84119346404', 3);

        SELECT pg_catalog.setval('public.ck02_cliente_ck02_cod_cliente_seq', 4, true);

-- Transportadora (CK04_TRANSPORTADORA)
INSERT INTO
    public.ck04_transportadora (ck04_cod_transportadora, ck04_nome, ck04_cnpj, ck04_valor_frete) VALUES
        (1, 'JDLOG', '52035712000140', 15.55),
        (2, 'DIRECT', '39251402000102', 18.65);

        SELECT pg_catalog.setval('public.ck04_transportadora_ck04_cod_transportadora_seq', 3, true);

-- TipoPagamento (CK06_TIPO_PAGAMENTO)
INSERT INTO
    public.ck06_tipo_pagamento (ck06_cod_tipo_pag, ck06_descricao) VALUES
        (1, 'À Vista'),
        (2, 'Cartão');

    SELECT pg_catalog.setval('public.ck06_tipo_pagamento_ck06_cod_tipo_pag_seq', 3, true);

-- CarrinhoCompras (CK07_CARRINHO_COMPRAS)
INSERT INTO public.ck07_carrinho_compras
    (ck07_cod_carrinho_compras, ck07_total_itens, ck07_valor_total, ck07ck02_cod_cliente) VALUES
        (1, 7, 31.57, 1),
        (2, 8, 20.40, 2),
        (3, 1, 11.96, 3);

    SELECT pg_catalog.setval('public.ck07_carrinho_compras_ck07_cod_carrinho_compras_seq', 4, true);

INSERT INTO public.ck10_status_compra
    (ck10_cod_status_compra, ck10_descricao) VALUES
        (1, 'ABERTO'),
        (2, 'EM PROCESSAMENTO'),
        (3, 'CONCLUÍDO');

    SELECT pg_catalog.setval('public.ck10_status_compra_ck10_cod_status_compra_seq', 4, true);


INSERT INTO  public.ck09_compra
    (ck09_cod_compra, ck09_cod_rastreio, ck09_numero_pedido, ck09ck10_cod_status) VALUES
        (1, 'AA123456789BR', '11111-21', 1),
        (2, 'AA987654321BR', '21453-01', 2),
        (3, 'AA100833276BR', '14788-55', 3);

    SELECT pg_catalog.setval('public.ck09_compra_ck09_cod_compra_seq', 4, true);

-- Pagamento (CK05_PAGAMENTO)
INSERT INTO public.ck05_pagamento
    (ck05_cod_pagamento, ck05ck06_cod_tipo_pag, ck05ck07_cod_carrinho, ck05ck03_cod_endereco, ck05ck04_cod_transportadora, ck05_valor_total, ck05ck09_cod_compra) VALUES
        (1, 1, 1, 1, 2, 50.22, 1),
        (2, 2, 2, 2, 2, 39.05, 2),
        (3, 2, 3, 3, 1, 27.51, 3);

    SELECT pg_catalog.setval('public.ck05_pagamento_ck05_cod_pagamento_seq', 4, true);

INSERT INTO public.ck08_produto_pedido
    (ck08_cod_produto_pedido, ck08ck01_cod_produto, ck08_qtd_itens, ck08_valor_total, ck08ck07_cod_carrinho_compras) VALUES
        (1, 1, 4, 10.2, 1),
        (2, 6, 2, 5.98, 1),
        (3, 2, 1, 15.39, 1),
        (4, 2, 8, 20.4, 2),
        (5, 6, 4, 11.96, 3);

    SELECT pg_catalog.setval('public.ck08_produto_pedido_ck08_cod_produto_pedido_seq', 6, true);




