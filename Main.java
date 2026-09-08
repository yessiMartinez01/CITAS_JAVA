import java.util.List;

public class Main {
    public static void main(String[] args) {
        CitaService servicio = new CitaService();
        try {
            System.out.println("--- Agendando una cita ---");
            Cita nueva = servicio.agendarCita(1, 1, "Control de rutina");
            System.out.println("Cita creada con id: " + nueva.getIdCita());
            System.out.println("\n--- Consultando todas las citas ---");
            List<Cita> citas = servicio.consultarCitas();
            for (Cita c : citas) {
                System.out.println("Cita " + c.getIdCita() + " | usuario: " + c.getIdUsuario() + " | horario: "
                        + c.getIdHorario() + " | motivo: " + c.getMotivo() + " | estado: " + c.getEstado());
            }
            System.out.println("\n--- Actualizando el motivo de la cita " + nueva.getIdCita() + " ---");
            servicio.actualizarCita(nueva.getIdCita(), "Control de rutina - reprogramado");
            System.out.println("\n--- Cancelando la cita " + nueva.getIdCita() + " ---");
            servicio.cancelarCita(nueva.getIdCita());
            System.out.println("\n--- Consultando de nuevo despues de los cambios ---");
            for (Cita c : servicio.consultarCitas()) {
                System.out.println(
                        "Cita " + c.getIdCita() + " | motivo: " + c.getMotivo() + " | estado: " + c.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error: " + e.getMessage());
        }
    }
}