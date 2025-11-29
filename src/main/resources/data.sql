INSERT INTO roles (nombre, descripcion) VALUES ('ADMINISTRADOR', 'Admin') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO roles (nombre, descripcion) VALUES ('PROPIETARIO', 'Propietario de un establecimiento') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO roles (nombre, descripcion) VALUES ('EMPLEADO', 'Empleado de un establecimiento') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO roles (nombre, descripcion) VALUES ('CLIENTE', 'Cliente de la plazoleta') ON CONFLICT (nombre) DO NOTHING;
