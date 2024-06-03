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


CREATE OR REPLACE FUNCTION remover_quantidade_estoque_pedido_personalizacao() RETURNS TRIGGER AS $$
DECLARE
    qtde_pedido INTEGER;
BEGIN
    SELECT quantidadeProduto INTO qtde_pedido FROM pedidoPersonalizacao WHERE idPedido = NEW.idPedido;

    UPDATE produto SET quantidadeEstoque = quantidadeEstoque - qtde_pedido WHERE idProduto = NEW.idProduto;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION atualizar_estoque_e_notificar() RETURNS void AS $$
DECLARE
    produto_record RECORD;
BEGIN
    FOR produto_record IN SELECT idProduto, quantidadeEstoque, quantidadeMinima FROM produto LOOP
        IF produto_record.quantidadeEstoque < produto_record.quantidadeMinima THEN
            DECLARE
                quantidade_notificacao INT;
            BEGIN
                quantidade_notificacao := produto_record.quantidadeMinima - produto_record.quantidadeEstoque;

                IF NOT EXISTS(SELECT 1 FROM notificacao WHERE idProduto = produto_record.idProduto) THEN
                    INSERT INTO notificacao (idProduto, quantidade, dataLimite)
                    VALUES (produto_record.idProduto, quantidade_notificacao, CURRENT_DATE + INTERVAL '7 days');
                ELSE
                    UPDATE notificacao
                    SET quantidade = quantidade_notificacao,
                        dataLimite = CURRENT_DATE + INTERVAL '7 days'
                    WHERE idProduto = produto_record.idProduto;
                END IF;
            END;
        ELSE
            UPDATE notificacao
            SET dataLimite = CURRENT_DATE + INTERVAL '7 days'
            WHERE idProduto = produto_record.idProduto;
        END IF;
    END LOOP;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION verificar_estoque_suficiente() RETURNS TRIGGER AS $$
DECLARE
    qtde_estoque INTEGER;
BEGIN
    -- Obtém a quantidade em estoque do produto associado
    SELECT quantidadeEstoque INTO qtde_estoque FROM produto WHERE idProduto = NEW.idProduto;
    -- Verifica se a quantidade em estoque é suficiente para atender ao pedido
    IF qtde_estoque < NEW.quantidadeProduto THEN
        RAISE EXCEPTION 'Quantidade em estoque insuficiente para atender ao pedido de personalização';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER verificar_estoque_suficiente_trigger
BEFORE INSERT ON pedidoPersonalizacao
FOR EACH ROW EXECUTE FUNCTION verificar_estoque_suficiente();

INSERT INTO departamento (nome, tipoVeiculo, tipo)
VALUES ('Dono', NULL, 'D');

INSERT INTO departamento (nome, tipoVeiculo, tipo)
VALUES ('Compras', NULL, 'C');



