-- V4__usuarios_bloqueio_expiracao_moderator.sql
SET NAMES utf8mb4;

ALTER TABLE usuarios
  ADD COLUMN bloqueado TINYINT(1) NOT NULL DEFAULT 0 AFTER permissao,
  ADD COLUMN expira_em DATETIME NULL AFTER bloqueado;

CREATE INDEX idx_usuarios_bloqueado ON usuarios (bloqueado);
CREATE INDEX idx_usuarios_expira_em ON usuarios (expira_em);