package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import vistas.VistaServidor;

public class Servidor extends Observable implements Runnable {

    private ServerSocket server;
    private Socket sc;
    private int puerto;
    private VistaServidor vistaServidor;

    public Servidor(int puerto) {

        this.puerto = puerto;
        
    }
   
    public void setVistaServidor(VistaServidor vistaServidor){
        this.vistaServidor = vistaServidor;
    }

    @Override
    public void run() {
        System.out.println("se inicio el servidor");
        try {
            server = new ServerSocket(puerto);

            while (true) {

                sc = server.accept();
                vistaServidor.notificarConexion("NUEVO CLIENTE CONECTADO");
                Asistente asistente = new Asistente(sc, this);
                Thread t = new Thread(asistente);
                t.start();
                

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo con el cual notificamos a los observadores
     * @param mensaje 
     */
    public void notificacion(String mensaje) {

        //notifico los cambios a los observadores
        this.setChanged();
        this.notifyObservers(mensaje + "\n");
        this.clearChanged();
    }

    /**
     * metodo para informar que un cliente se desconecto
     * @param mensaje 
     */
    public void desconectarCliente(String mensaje) {
        vistaServidor.notificarConexion(mensaje);

    }

}
