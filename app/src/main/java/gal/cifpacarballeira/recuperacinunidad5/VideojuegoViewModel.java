package gal.cifpacarballeira.recuperacinunidad5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class VideojuegoViewModel extends ViewModel {

    // Lista de videojuegos
    private final MutableLiveData<List<Videojuego>> listaVideojuegos = new MutableLiveData<>();

    // Videojuego seleccionado
    private final MutableLiveData<Videojuego> videojuegoSeleccionado = new MutableLiveData<>();

    public VideojuegoViewModel() {
        // Cargar los videojuegos predeterminados
        cargarVideojuegosIniciales();
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
        List<Videojuego> juegosActuales = listaVideojuegos.getValue();
        if (juegosActuales != null) {
            juegosActuales.add(videojuego);
            listaVideojuegos.setValue(juegosActuales);
        }
    }

    // Eliminar un videojuego de la lista
    public void eliminarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = listaVideojuegos.getValue();
        if (juegosActuales != null) {
            juegosActuales.remove(videojuego);
            listaVideojuegos.setValue(juegosActuales);
        }
    }

    // Actualizar un videojuego en la lista
    public void actualizarVideojuego(Videojuego videojuegoAntiguo, Videojuego videojuegoNuevo) {
        List<Videojuego> juegosActuales = listaVideojuegos.getValue();
        if (juegosActuales != null) {
            int index = juegosActuales.indexOf(videojuegoAntiguo);
            if (index != -1) {
                // Reemplazamos el videojuego antiguo por el nuevo en la lista
                juegosActuales.set(index, videojuegoNuevo);
                listaVideojuegos.setValue(juegosActuales);
            }
        }
    }

    // Limpiar la lista de videojuegos y restaurar los videojuegos iniciales
    public void limpiar() {
        cargarVideojuegosIniciales(); // Restauramos los videojuegos predeterminados
        videojuegoSeleccionado.setValue(null); // Limpiamos el videojuego seleccionado
    }

    // Método para cargar los videojuegos predeterminados (inicialización de la lista)
    private void cargarVideojuegosIniciales() {
        List<Videojuego> videojuegosGenerados = VideojuegoGenerator.generarVideojuegos();
        listaVideojuegos.setValue(videojuegosGenerados);
    }
}
