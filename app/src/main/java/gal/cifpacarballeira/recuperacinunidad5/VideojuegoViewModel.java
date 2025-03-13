package gal.cifpacarballeira.recuperacinunidad5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoViewModel extends ViewModel {

    // Lista de videojuegos (inicialmente vacía)
    private final MutableLiveData<List<Videojuego>> listaVideojuegos = new MutableLiveData<>(new ArrayList<>());

    // Videojuego seleccionado
    private final MutableLiveData<Videojuego> videojuegoSeleccionado = new MutableLiveData<>();

    public VideojuegoViewModel() {
        // No llamamos a cargarVideojuegosIniciales(), así que la lista permanece vacía
    }

    // Obtener la lista de videojuegos
    public LiveData<List<Videojuego>> getListaVideojuegos() {
        return listaVideojuegos;
    }

    // Obtener el videojuego seleccionado
    public LiveData<Videojuego> getVideojuegoSeleccionado() {
        return videojuegoSeleccionado;
    }

    // Establecer el videojuego seleccionado
    public void setVideojuegoSeleccionado(Videojuego videojuego) {
        videojuegoSeleccionado.setValue(videojuego);
    }

    // Agregar un videojuego a la lista
    public void agregarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue()); // Copia defensiva
        juegosActuales.add(videojuego);
        listaVideojuegos.setValue(juegosActuales);
    }

    // Eliminar un videojuego de la lista
    public void eliminarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue()); // Copia defensiva
        juegosActuales.remove(videojuego);
        listaVideojuegos.setValue(juegosActuales);
    }

    // Actualizar un videojuego en la lista
    public void actualizarVideojuego(Videojuego videojuegoAntiguo, Videojuego videojuegoNuevo) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue()); // Copia defensiva
        int index = juegosActuales.indexOf(videojuegoAntiguo);
        if (index != -1) {
            juegosActuales.set(index, videojuegoNuevo);
            listaVideojuegos.setValue(juegosActuales);
        }
    }

    // Limpiar la lista de videojuegos sin inicializarla de nuevo
    public void limpiar() {
        listaVideojuegos.setValue(new ArrayList<>()); // Se deja la lista vacía
        videojuegoSeleccionado.setValue(null); // Limpiamos el videojuego seleccionado
    }
}
