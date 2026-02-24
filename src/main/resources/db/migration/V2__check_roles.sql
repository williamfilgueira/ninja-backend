-- V3__check_roles.sql
ALTER TABLE usuarios
  ADD CONSTRAINT chk_usuarios_permissao
  CHECK (permissao IN ('ADMIN', 'MODERATOR', 'USER'));