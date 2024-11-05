package Main;

import Emun.Sala;
import Genericos.Trabajador;
import Hilos.ReservaSala;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        Trabajador<String> trabajador1 = new Trabajador<>("Juan", "Gerente");
        Trabajador<Integer> trabajador2 = new Trabajador<>("Maria", 2);
        Thread reservaS=new Thread(new ReservaSala(Sala.SMALL_ROOM,semaphore));
        Thread reservaM=new Thread(new ReservaSala(Sala.MEDIUM_ROOM,semaphore));
        Thread reservaL=new Thread(new ReservaSala(Sala.LARGE_ROOM,semaphore));

        JOptionPane.showMessageDialog(null,"Nombre: " + trabajador1.getNombre() + ", Puesto: " + trabajador1.getPuesto());
        JOptionPane.showMessageDialog(null,"Nombre: " + trabajador2.getNombre() + ", Puesto: " + trabajador2.getPuesto());

        reservaS.start();
        reservaM.start();
        reservaL.start();

        try{
            reservaS.join();
            reservaM.join();
            reservaL.join();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        JOptionPane.showMessageDialog(null,"Todas las Reservas Se Han Realizado Con Exito");
    }
}
