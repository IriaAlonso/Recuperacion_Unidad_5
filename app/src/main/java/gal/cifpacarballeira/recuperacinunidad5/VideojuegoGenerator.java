package gal.cifpacarballeira.recuperacinunidad5;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase estática para generar una lista de videojuegos.
 */
public class VideojuegoGenerator {

    /**
     * Genera una lista de 10 videojuegos.
     *
     * @return Una lista de 10 objetos Videojuego.
     */
    public static List<Videojuego> generarVideojuegos() {
        List<Videojuego> videojuegos = new ArrayList<>();
        videojuegos.add(new Videojuego("The Legend of Zelda: Breath of the Wild", 5, EstadoJuego.COMPLETADO));
        videojuegos.add(new Videojuego("Red Dead Redemption 2", 4, EstadoJuego.JUGANDO));
        videojuegos.add(new Videojuego("Cyberpunk 2077", 2, EstadoJuego.ABANDONADO));
        videojuegos.add(new Videojuego("The Witcher 3: Wild Hunt", 5, EstadoJuego.COMPLETADO));
        videojuegos.add(new Videojuego("Elden Ring", 4, EstadoJuego.JUGANDO));
        videojuegos.add(new Videojuego("Grand Theft Auto V", 3, EstadoJuego.ABANDONADO));
        videojuegos.add(new Videojuego("Super Mario Odyssey", 5, EstadoJuego.COMPLETADO));
        videojuegos.add(new Videojuego("God of War", 4, EstadoJuego.JUGANDO));
        videojuegos.add(new Videojuego("Minecraft", 3, EstadoJuego.JUGANDO));
        videojuegos.add(new Videojuego("The Last of Us Part II", 4, EstadoJuego.COMPLETADO));
        return videojuegos;
    }

    public static void main(String[] args) {
        // Generar la lista de videojuegos
        List<Videojuego> listaVideojuegos = VideojuegoGenerator.generarVideojuegos();

        // Imprimir la lista de videojuegos
        for (Videojuego videojuego : listaVideojuegos) {
            System.out.println(videojuego);
        }
    }
}