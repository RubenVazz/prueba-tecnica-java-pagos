

import java.math.BigDecimal;

public record Operacion(
    Long id,
    Long idCuenta,
    BigDecimal monto,
    String tipo,
    String estado
) {}