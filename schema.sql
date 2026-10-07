-- Sem isso, o cliente mysql que roda este script na inicializacao do
-- container usa latin1 e corrompe qualquer acento (vira "JoÃ£o").
SET NAMES utf8mb4;

CREATE TABLE usuario (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    tipo VARCHAR(20) NOT NULL -- CLIENTE ou ADMINISTRADOR
);

CREATE TABLE categoria (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE produto (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    id_categoria VARCHAR(36) NOT NULL,
    imagem VARCHAR(255),
    CONSTRAINT fk_produto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE
);

CREATE TABLE pedido (
    id VARCHAR(36) PRIMARY KEY,
    id_usuario VARCHAR(36) NOT NULL,
    preco_total DECIMAL(10,2) NOT NULL,
    tipo_entrega VARCHAR(50) NOT NULL, -- RETIRADA / DELIVERY
    endereco VARCHAR(255), -- obrigatorio apenas quando tipo_entrega = DELIVERY
    forma_pagamento VARCHAR(50) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    horario_criacao DATETIME NOT NULL,
    horario_saida DATETIME,
    horario_finalizacao DATETIME,
    status VARCHAR(50) NOT NULL, -- CRIADO / EM_PREPARO / SAIU_PARA_ENTREGA / CONCLUIDO / CANCELADO
    motivo_cancelamento TEXT,
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE produto_pedido (
    id VARCHAR(36) PRIMARY KEY,
    id_pedido VARCHAR(36) NOT NULL,
    id_produto VARCHAR(36) NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL, -- preco do produto no momento do pedido
    CONSTRAINT fk_prod_ped_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_prod_ped_produto FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE
);

CREATE TABLE reserva (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    data_hora DATETIME NOT NULL,
    quantidade_pessoas INT NOT NULL,
    area_preferida VARCHAR(20) NOT NULL, -- INTERNA / EXTERNA / INDIFERENTE
    precisa_cadeirao BOOLEAN NOT NULL DEFAULT FALSE,
    comemoracao_aniversario BOOLEAN NOT NULL DEFAULT FALSE,
    precisa_acessibilidade BOOLEAN NOT NULL DEFAULT FALSE,
    observacoes TEXT,
    status VARCHAR(20) NOT NULL, -- ATIVA / CANCELADA
    motivo_cancelamento TEXT
);

-- Usuarios de exemplo (login ainda nao existe, senha fica em texto puro por enquanto)
INSERT INTO usuario (id, nome, email, senha, telefone, tipo) VALUES
('123e4567-e89b-12d3-a456-426614174000', 'Administrador', 'admin@nonna.com', 'admin123', '48999990000', 'ADMINISTRADOR'),
('223e4567-e89b-12d3-a456-426614174001', 'João Silva', 'joao@example.com', 'senha123', '48988887777', 'CLIENTE'),
('323e4567-e89b-12d3-a456-426614174002', 'Maria Souza', 'maria@example.com', 'senha123', '48977776666', 'CLIENTE');

-- Categoria e produto de exemplo
INSERT INTO categoria (id, nome) VALUES ('423e4567-e89b-12d3-a456-426614174003', 'Massas');

INSERT INTO produto (id, nome, descricao, preco, id_categoria, imagem) VALUES
('523e4567-e89b-12d3-a456-426614174004', 'Fettucine ao molho branco', 'Massa fresca ao molho de creme de leite com parmesão ralado na hora.', 42.00, '423e4567-e89b-12d3-a456-426614174003', 'public/images/massa.jpg');
