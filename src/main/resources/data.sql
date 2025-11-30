INSERT INTO roles (nombre, descripcion) VALUES ('ADMINISTRADOR', 'Admin') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO roles (nombre, descripcion) VALUES ('PROPIETARIO', 'Propietario de un establecimiento') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO roles (nombre, descripcion) VALUES ('EMPLEADO', 'Empleado de un establecimiento') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO roles (nombre, descripcion) VALUES ('CLIENTE', 'Cliente de la plazoleta') ON CONFLICT (nombre) DO NOTHING;

INSERT INTO usuarios (nombre, apellido, numero_documento, celular, fecha_nacimiento, correo, clave, id_rol) VALUES ('Cliente Test', 'Test', '123456789', '+57123456789', '2000-5-19', 'test@gmail.com', '$2a$10$IsNGpLkS0LnFaP.TMMBMuu/ssdfnBGA8UcXmQa4CA1BNNyRDyjNbe', 4) ON CONFLICT (correo) DO NOTHING;