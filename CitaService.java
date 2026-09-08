import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaService {
    public Cita agendarCita(int idUsuario, int idHorario, String motivo) throws SQLException {
        String sql = "INSERT INTO citas (idusuario, idhorario, motivo, estado) VALUES (?, ?, ?, 'confirmada')";
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idHorario);
            stmt.setString(3, motivo);
            stmt.executeUpdate();
            Cita cita = new Cita(idUsuario, idHorario, motivo, "confirmada");
            ResultSet generadas = stmt.getGeneratedKeys();
            if (generadas.next()) {
                cita.setIdCita(generadas.getInt(1));
            }
            return cita;
        }
    }

    public List<Cita> consultarCitas() throws SQLException {
        String sql = "SELECT idcita, idusuario, idhorario, motivo, estado FROM citas";
        List<Cita> citas = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("idcita"));
                cita.setIdUsuario(rs.getInt("idusuario"));
                cita.setIdHorario(rs.getInt("idhorario"));
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
    }

    public void actualizarCita(int idCita, String nuevoMotivo) throws SQLException {
        String sql = "UPDATE citas SET motivo = ? WHERE idcita = ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nuevoMotivo);
            stmt.setInt(2, idCita);
            stmt.executeUpdate();
        }
    }

    public void cancelarCita(int idCita) throws SQLException {
        String sql = "UPDATE citas SET estado = 'cancelada' WHERE idcita = ?";
        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idCita);
            stmt.executeUpdate();
        }
    }
}