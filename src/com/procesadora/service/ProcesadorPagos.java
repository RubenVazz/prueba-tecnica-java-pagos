

import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorPagos {

    public List<Operacion> obtenerPendientes() {
        List<Operacion> lista = new ArrayList<>();
        String sql = "SELECT id, id_cuenta, monto, tipo, estado FROM operacion WHERE estado = 'PENDIENTE'";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Operacion(
                        rs.getLong("id"),
                        rs.getLong("id_cuenta"),
                        rs.getBigDecimal("monto"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error consultando pendientes: " + e.getMessage());
        }
        return lista;
    }

    public void procesarOperacion(Operacion op) {
        String callSP = "{call actualizar_saldo(?, ?, ?)}";
        String updateSql = "UPDATE operacion SET estado = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (CallableStatement cstmt = conn.prepareCall(callSP);
                 PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

                System.out.println("Procesando Operacion ID: " + op.id() + "...");

                cstmt.setLong(1, op.idCuenta());
                cstmt.setBigDecimal(2, op.monto());
                cstmt.setString(3, op.tipo());
                cstmt.execute();

                pstmt.setString(1, "PROCESADO");
                pstmt.setLong(2, op.id());
                pstmt.executeUpdate();

                conn.commit();
                System.out.println("--> Exito.");

            } catch (SQLException e) {
                conn.rollback();
                System.err.println("--> Fallo: " + e.getMessage());
                marcarError(op.id(), conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void marcarError(Long id, Connection conn) {
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE operacion SET estado = 'ERROR' WHERE id = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            conn.commit(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}