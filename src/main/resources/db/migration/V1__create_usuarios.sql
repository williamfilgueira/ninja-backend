-- V1__create_usuarios.sql
SET NAMES utf8mb4;
SET time_zone = '+00:00';

CREATE TABLE IF NOT EXISTS usuarios (
  id CHAR(36) NOT NULL,
  nome VARCHAR(150) NOT NULL,
  email VARCHAR(120) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  permissao VARCHAR(20) NOT NULL,
  dt_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dt_atz TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_usuarios_email (email),
  KEY idx_usuarios_permissao (permissao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;