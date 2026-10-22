-- Create table for business requests
CREATE TABLE solicitacoes_empresariais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    empresa VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) NOT NULL,
    responsavel VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    quantidade_media VARCHAR(100),
    mensagem TEXT NOT NULL,
    arquivo_nome VARCHAR(255),
    arquivo_nome_original VARCHAR(255),
    arquivo_tipo VARCHAR(100),
    arquivo_tamanho BIGINT,
    ip_origem VARCHAR(45),
    user_agent VARCHAR(500),
    status VARCHAR(50) DEFAULT 'PENDENTE',
    status_email VARCHAR(50) DEFAULT 'NAO_ENVIADO',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_cnpj (cnpj),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
