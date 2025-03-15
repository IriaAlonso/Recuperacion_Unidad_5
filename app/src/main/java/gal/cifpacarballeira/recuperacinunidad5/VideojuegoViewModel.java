package gal.cifpacarballeira.recuperacinunidad5;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Videojuego>> listaVideojuegos = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Videojuego> videojuegoSeleccionado = new MutableLiveData<>();
    private final VideojuegoDB database;

    public VideojuegoViewModel(Application application) {
        super(application);
        database = new VideojuegoDB(application.getApplicationContext());
        cargarVideojuegosDesdeBD();
    }

    public LiveData<List<Videojuego>> getListaVideojuegos() {
        return listaVideojuegos;
    }

    public LiveData<Videojuego> getVideojuegoSeleccionado() {
        return videojuegoSeleccionado;
    }

    public void setVideojuegoSeleccionado(Videojuego videojuego) {
        videojuegoSeleccionado.setValue(videojuego);
    }

    public void agregarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue());
        long id = database.agregarVideojuego(videojuego);
        if (id != -1) {
            juegosActuales.add(videojuego);
            listaVideojuegos.setValue(juegosActuales);
        }
    }

    public void actualizarVideojuego(Videojuego videojuegoAntiguo, Videojuego videojuegoNuevo) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue());
        int index = juegosActuales.indexOf(videojuegoAntiguo);
        if (index != -1) {
            int rowsUpdated = database.actualizarVideojuego(videojuegoAntiguo.getTitulo(), videojuegoNuevo);
            if (rowsUpdated > 0) {
                juegosActuales.set(index, videojuegoNuevo);
                listaVideojuegos.setValue(juegosActuales);
            }
        }
    }

    public void eliminarVideojuego(Videojuego videojuego) {
        List<Videojuego> juegosActuales = new ArrayList<>(listaVideojuegos.getValue());
        int rowsDeleted = database.eliminarVideojuego(videojuego.getTitulo());
        if (rowsDeleted > 0) {
            juegosActuales.remove(videojuego);
            listaVideojuegos.setValue(juegosActuales);
        }
    }

    private void cargarVideojuegosDesdeBD() {
        List<Videojuego> videojuegosGuardados = database.getVideojuegos();
        listaVideojuegos.setValue(videojuegosGuardados);
    }
}
