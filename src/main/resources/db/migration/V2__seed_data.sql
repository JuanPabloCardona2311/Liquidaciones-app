-- Sembramos datos iniciales
INSERT INTO usuarios (username, password, email, nombre_completo, rol, activo, created_at, updated_at)
VALUES ('admin', '$2a$10$slYQmyNdGzin7olVG0p.OPST9/PgBkqquzi8Qf8QloolqiMGXWFYi', 'admin@example.com', 'Administrador', 'ADMIN', b'1', NOW(), NOW());
