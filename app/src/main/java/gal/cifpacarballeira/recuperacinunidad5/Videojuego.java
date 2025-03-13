package gal.cifpacarballeira.recuperacinunidad5;

/**
 * Clase para representar un videojuego.
 */
public class Videojuego {

    private final String titulo;
    private final int puntuacion;
    private final EstadoJuego estado;

    /**
     * Constructor de la clase Videojuego.
     *
     * @param titulo     El título del videojuego.
     * @param puntuacion La puntuación del videojuego (de 0 a 5).
     * @param estado     El estado actual del videojuego.
     * @throws IllegalArgumentException Si la puntuación está fuera del rango válido (0-5).
     */
    public Videojuego(String titulo, int puntuacion, EstadoJuego estado) {
        if (puntuacion < 0 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 0 y 5");
        }
        this.titulo = titulo;
        this.puntuacion = puntuacion;
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "titulo='" + titulo + '\'' +
                ", puntuacion=" + puntuacion +
                ", estado=" + estado +
                '}';
    }
}

/**
 * Enumeración que representa los posibles estados de un videojuego.
 */
enum EstadoJuego {
    JUGANDO,
    COMPLETADO,
    ABANDONADO
}