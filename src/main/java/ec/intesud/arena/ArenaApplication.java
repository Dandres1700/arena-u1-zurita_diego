package ec.intesud.arena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada. @SpringBootApplication activa el escaneo de componentes
 * del paquete ec.intesud.arena y de todos sus subpaquetes (controller, service...).
 */
@SpringBootApplication
public class ArenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArenaApplication.class, args);
    }
}
