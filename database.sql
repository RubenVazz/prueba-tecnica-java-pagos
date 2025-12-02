-- 1. Crear tabla CUENTA (Billeteras)
CREATE TABLE cuenta (
    id NUMBER PRIMARY KEY,
    nombre VARCHAR2(100),
    saldo NUMBER(15,2) DEFAULT 0
);

-- 2. Crear tabla OPERACION (Pagos)
CREATE TABLE operacion (
    id NUMBER PRIMARY KEY,
    id_cuenta NUMBER,
    monto NUMBER(15,2),
    tipo VARCHAR2(10), -- Valores: 'ABONO' o 'CARGO'
    estado VARCHAR2(20) DEFAULT 'PENDIENTE', -- Valores: 'PENDIENTE', 'PROCESADO', 'ERROR'
    CONSTRAINT fk_cuenta FOREIGN KEY (id_cuenta) REFERENCES cuenta(id)
);

-- 3. Crear el Procedimiento Almacenado
CREATE OR REPLACE PROCEDURE actualizar_saldo (
    p_id_cuenta IN NUMBER,
    p_monto     IN NUMBER,
    p_tipo      IN VARCHAR2
) AS
BEGIN
    -- Validamos si es suma o resta
    IF p_tipo = 'ABONO' THEN
        UPDATE cuenta SET saldo = saldo + p_monto WHERE id = p_id_cuenta;
    ELSIF p_tipo = 'CARGO' THEN
        UPDATE cuenta SET saldo = saldo - p_monto WHERE id = p_id_cuenta;
    ELSE
        RAISE_APPLICATION_ERROR(-20001, 'Tipo de operacion invalido');
    END IF;
    
    -- Si no encontró la cuenta, lanzamos error
    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Cuenta no encontrada');
    END IF;
END;
/

-- 4. Insertar Datos de Prueba
-- Cuentas
INSERT INTO cuenta (id, nombre, saldo) VALUES (1, 'Juan Perez', 1000.00);
INSERT INTO cuenta (id, nombre, saldo) VALUES (2, 'Maria Gomez', 500.00);

-- Operaciones (Una de abono, una de cargo, una con error a propósito)
INSERT INTO operacion (id, id_cuenta, monto, tipo, estado) VALUES (101, 1, 200.00, 'ABONO', 'PENDIENTE');
INSERT INTO operacion (id, id_cuenta, monto, tipo, estado) VALUES (102, 2, 100.00, 'CARGO', 'PENDIENTE');
INSERT INTO operacion (id, id_cuenta, monto, tipo, estado) VALUES (103, 99, 500.00, 'ABONO', 'PENDIENTE'); -- Cuenta 99 no existe

COMMIT;