package gal.cifpacarballeira.recuperacinunidad5;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class ListaFragment extends Fragment {

    private RecyclerView videojuegosRecyclerView;
    private VideojuegoAdapter videojuegoAdapter;
    private List<Videojuego> videojuegos;
    private VideojuegoViewModel videojuegoViewModel; // ViewModel para gestionar la lista de videojuegos

    public ListaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        // Inicialización del RecyclerView
        videojuegosRecyclerView = view.findViewById(R.id.videojuegosRecyclerView);
        videojuegosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar el ViewModel
        videojuegoViewModel = new ViewModelProvider(requireActivity()).get(VideojuegoViewModel.class);

        // Generar la lista de videojuegos inicial
        videojuegos = VideojuegoGenerator.generarVideojuegos();

        // Crear el adaptador con la lista de videojuegos
        videojuegoAdapter = new VideojuegoAdapter(videojuegos, new VideojuegoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Videojuego videojuego) {
                // Al hacer clic en un item, actualizar el videojuego seleccionado en el ViewModel
                videojuegoViewModel.setVideojuegoSeleccionado(videojuego);
            }
        });
        videojuegosRecyclerView.setAdapter(videojuegoAdapter);

        // Observar los cambios en la lista de videojuegos del ViewModel
        videojuegoViewModel.getListaVideojuegos().observe(getViewLifecycleOwner(), new Observer<List<Videojuego>>() {
            @Override
            public void onChanged(List<Videojuego> nuevosVideojuegos) {
                videojuegos.clear(); // Limpiar la lista actual
                videojuegos.addAll(nuevosVideojuegos); // Agregar los nuevos videojuegos
                videojuegoAdapter.notifyDataSetChanged(); // Notificar cambios al adaptador
            }
        });

        return view;
    }
}
