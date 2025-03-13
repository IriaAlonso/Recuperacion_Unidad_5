package gal.cifpacarballeira.recuperacinunidad5;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoViewModel extends AndroidViewModel {

    // Lista de videojuegos (inicialmente vacía)
    private final MutableLiveData<List<Videojuego>> listaVideojuegos = new MutableLiveData<>(new ArrayList<>());

    // Videojuego seleccionado
    private final MutableLiveData<Videojuego> videojuegoSeleccionado = new MutableLiveData<>();

    // Instancia de la base de datos
    private final VideojuegoDB database;

    public VideojuegoViewModel(Application application) {
        super(application);
        database = new VideojuegoDB(application.getApplicationContext()); // Inicializamos la base de datos
        cargarVideojuegosDesdeBD(); // Cargar videojuegos guardados en la BD
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

    // Agregar un videojuego a la lista y guardarlo en la BD
    public void agregarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue()); // Copia defensiva
        juegosActuales.add(videojuego);
        listaVideojuegos.setValue(juegosActuales);

        database.agregarVideojuego(videojuego); // Guardamos en la base de datos
    }

    // Eliminar un videojuego de la lista (falta implementar en la BD)
    public void eliminarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue()); // Copia defensiva
        juegosActuales.remove(videojuego);
        listaVideojuegos.setValue(juegosActuales);

        // Aquí podríamos implementar un método en la BD para eliminarlo
    }

    // Cargar los videojuegos desde la base de datos al iniciar la app
    private void cargarVideojuegosDesdeBD() {
        List<Videojuego> videojuegosGuardados = database.getVideojuegos(); // Obtener datos de la BD
        listaVideojuegos.setValue(videojuegosGuardados); // Actualizar LiveData
    }
}
