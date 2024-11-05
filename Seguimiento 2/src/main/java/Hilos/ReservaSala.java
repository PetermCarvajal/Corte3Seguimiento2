package Hilos;

import Emun.Sala; // Assuming this import is correct

import jakarta.persistence.*;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.time.*;

@Entity
@Table(name = "reserva")
public class ReservaSala implements Runnable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Sala sala;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", updatable = false, insertable = false,nullable = true)
    private Date fecha;

    @Transient
    private Semaphore semaphore;

    public ReservaSala() {}
    public ReservaSala(Sala sala, Semaphore semaphore) {
        this.sala = sala;
        this.semaphore = semaphore;
        this.fecha = new Date();
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            guardarReserva(sala.name(),fecha);
            System.out.println("Reserva realizada para: " + sala);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    private void guardarReserva(String nombreSala,Date fecha) {
        String url = "jdbc:mysql://localhost:3306/reservas_salas"; // Replace with your actual database name
        String usuario = "root";
        String contraseña = "";

        String sql = "INSERT INTO reservas (sala,fecha) VALUES (?,?)";


        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreSala);
            stmt.setTimestamp(2, new Timestamp(new Date().getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar la reserva en la base de datos: " + e.getMessage());
        }
    }
}