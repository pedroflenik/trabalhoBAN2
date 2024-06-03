CREATE TABLE cliente (
    idCliente SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(11) UNIQUE,
    telefone VARCHAR(20)
);

CREATE TABLE departamento (
    idDep SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    tipoVeiculo VARCHAR(50),
    tipo CHAR(1)
);

CREATE TABLE fornecedor (
    idFornecedor SERIAL PRIMARY KEY,
    endereco VARCHAR(200),
    nome VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE veiculo (
    idVeiculo SERIAL PRIMARY KEY,
    modelo VARCHAR(100),
    ano VARCHAR(4),
    marca VARCHAR(100),
    idDono INT,
    FOREIGN KEY (idDono) REFERENCES Cliente(idCliente)
);

CREATE TABLE funcionario (
    idFuncionario SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    dataContratacao DATE,
    idDepartamento INT REFERENCES Departamento(idDep),
    tipo CHAR(1)
);



CREATE TABLE produto (
    idProduto SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    precoPorUnidade DECIMAL(10, 2),
    quantidadeEstoque INT,
    fornecedorPrincipal INT,
    tipo CHAR(1),
    quantidadeMinima INT,
    descricao TEXT,
    FOREIGN KEY (fornecedorPrincipal) REFERENCES Fornecedor(idFornecedor)
);


CREATE TABLE notificacao (
    idNotificacao SERIAL PRIMARY KEY,
    idProduto INT,
    quantidade INT,
    dataLimite DATE,
    FOREIGN KEY (idProduto) REFERENCES produto(idProduto)
);

CREATE TABLE pedidoPersonalizacao (
    idPedido SERIAL PRIMARY KEY,
    dataEntrega DATE,
    descricao VARCHAR(200),
    valorPersonalizacao DECIMAL(10, 2),
    idVeiculo INT,
    departamentoResponsavel INT,
    quantidadeProduto INT,
    idProduto INT,
    FOREIGN KEY (idProduto) REFERENCES Prodtuo (idProduto),
    FOREIGN KEY (idVeiculo) REFERENCES Veiculo(idVeiculo),
    FOREIGN KEY (departamentoResponsavel) REFERENCES Departamento(idDep)
);

CREATE TABLE pedidoCompra (
    idPedidoCompra SERIAL PRIMARY KEY,
    idFornecedor INT,
    idNotificacao INT,
    quantidade INT,
    totalCompra DECIMAL(10, 2),
    idProduto INT,
    entregue BOOLEAN,
    FOREIGN KEY (idFornecedor) REFERENCES Fornecedor(idFornecedor),
    FOREIGN KEY (idNotificacao) REFERENCES Notificacao(idNotificacao),
    FOREIGN KEY (idProduto) REFERENCES Produto(idProduto)
);


CREATE OR REPLACE FUNCTION atualizar_pedido_e_produto(
    idPedido integer
)
RETURNS void AS
$$
DECLARE
    idProdutoPedido integer;
    quantidadePedido integer;
BEGIN
    UPDATE pedidoCompra
    SET entregue = TRUE
    WHERE idPedidoCompra = idPedido;

    SELECT idProduto, quantidade INTO idProdutoPedido, quantidadePedido
    FROM pedidoCompra
    WHERE idPedidoCompra = idPedido;

    UPDATE produto
    SET quantidadeEstoque = quantidadeEstoque + quantidadePedido
    WHERE idProduto = idProdutoPedido;

    
    RAISE NOTICE 'Pedido e produto atualizados com sucesso';
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION atualizar_pedido_e_produto(
    idPedido integer
)
RETURNS void AS
$$
DECLARE
    idProdutoPedido integer;
    quantidadePedido integer;
BEGIN
    UPDATE pedidoCompra
    SET entregue = TRUE
    WHERE idPedidoCompra = idPedido;

    SELECT idProduto, quantidade INTO idProdutoPedido, quantidadePedido
    FROM pedidoCompra
    WHERE idPedidoCompra = idPedido;

    UPDATE produto
    SET quantidadeEstoque = quantidadeEstoque + quantidadePedido
    WHERE idProduto = idProdutoPedido;

    
    RAISE NOTICE 'Pedido e produto atualizados com sucesso';
END;
$$
LANGUAGE plpgsql;



INSERT INTO departamento (nome, tipoVeiculo, tipo)
VALUES ('Dono', NULL, 'D');

INSERT INTO departamento (nome, tipoVeiculo, tipo)
VALUES ('Compras', NULL, 'C');



