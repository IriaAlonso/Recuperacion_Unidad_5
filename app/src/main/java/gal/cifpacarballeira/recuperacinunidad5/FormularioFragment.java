package gal.cifpacarballeira.recuperacinunidad5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;

public class FormularioFragment extends Fragment {

    private VideojuegoViewModel videojuegoViewModel;
    private TextInputEditText tituloEditText;
    private RadioGroup puntuacionRadioGroup;
    private Spinner estadoSpinner;
    private Videojuego videojuegoSeleccionado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_formulario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar el ViewModel para compartir datos entre fragments
        videojuegoViewModel = new ViewModelProvider(requireActivity()).get(VideojuegoViewModel.class);

        // Referencias a los elementos de la vista
        tituloEditText = view.findViewById(R.id.tituloEditText);
        puntuacionRadioGroup = view.findViewById(R.id.puntuacionRadioGroup);
        estadoSpinner = view.findViewById(R.id.estadoSpinner);
        Button guardarButton = view.findViewById(R.id.guardarButton);
        Button modificarButton = view.findViewById(R.id.modificarButton);
        Button eliminarButton = view.findViewById(R.id.eliminarButton);

        // Configurar el Spinner con los valores del enum EstadoJuego
        ArrayAdapter<EstadoJuego> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                EstadoJuego.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSpinner.setAdapter(adapter);

        // Configurar el botón para agregar un videojuego a la lista
        guardarButton.setOnClickListener(v -> agregarVideojuego());

        // Configurar el botón para modificar un videojuego
        modificarButton.setOnClickListener(v -> modificarVideojuego());

        // Eliminar objeto
        eliminarButton.setOnClickListener(v -> eliminarVideojuego());

        // Observar el videojuego seleccionado en el ViewModel
        videojuegoViewModel.getVideojuegoSeleccionado().observe(getViewLifecycleOwner(), videojuego -> {
            if (videojuego != null) {
                // Si hay un videojuego seleccionado, cargar sus datos en el formulario
                tituloEditText.setText(videojuego.getTitulo());

                // Establecer la puntuación seleccionada en el RadioGroup
                for (int i = 0; i < puntuacionRadioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) puntuacionRadioGroup.getChildAt(i);
                    if (Integer.parseInt(radioButton.getText().toString()) == videojuego.getPuntuacion()) {
                        radioButton.setChecked(true);
                    }
                }

                // Establecer el estado del videojuego en el Spinner
                ArrayAdapter<EstadoJuego> spinnerAdapter = (ArrayAdapter<EstadoJuego>) estadoSpinner.getAdapter();
                for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                    if (spinnerAdapter.getItem(i) == videojuego.getEstado()) {
                        estadoSpinner.setSelection(i);
                        break;
                    }
                }

                // Asignar el videojuego seleccionado para las modificaciones posteriores
                videojuegoSeleccionado = videojuego;
            }
        });
    }

    private void agregarVideojuego() {
        String titulo = tituloEditText.getText().toString().trim();
        int puntuacion = obtenerPuntuacionSeleccionada();
        EstadoJuego estado = (EstadoJuego) estadoSpinner.getSelectedItem();

        if (!titulo.isEmpty() && puntuacion != -1) {
            Videojuego nuevoJuego = new Videojuego(titulo, puntuacion, estado);
            videojuegoViewModel.agregarVideojuego(nuevoJuego); // Agregar videojuego al ViewModel
            limpiar();
        }
    }

    private void modificarVideojuego() {
        String titulo = tituloEditText.getText().toString().trim();
        int puntuacion = obtenerPuntuacionSeleccionada();
        EstadoJuego estado = (EstadoJuego) estadoSpinner.getSelectedItem();

        if (!titulo.isEmpty() && puntuacion != -1 && videojuegoSeleccionado != null) {
            // Crear un nuevo videojuego con los datos modificados
            Videojuego videojuegoModificado = new Videojuego(titulo, puntuacion, estado);

            // Actualizar el videojuego en el ViewModel
            videojuegoViewModel.actualizarVideojuego(videojuegoSeleccionado, videojuegoModificado);
            limpiar();
        }
    }

    private void eliminarVideojuego() {
        if (videojuegoSeleccionado != null) {
            // Eliminar el videojuego seleccionado de la lista
            videojuegoViewModel.eliminarVideojuego(videojuegoSeleccionado);
            limpiar();
        }
    }

    private int obtenerPuntuacionSeleccionada() {
        int selectedId = puntuacionRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedButton = puntuacionRadioGroup.findViewById(selectedId);
            return Integer.parseInt(selectedButton.getText().toString());
        }
        return -1; // Retorna -1 si no se seleccionó ninguna puntuación
    }

    private void limpiar() {
        // Limpiar el campo de texto
        tituloEditText.setText("");

        // Limpiar el RadioGroup (desmarcar todas las opciones)
        for (int i = 0; i < puntuacionRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) puntuacionRadioGroup.getChildAt(i);
            radioButton.setChecked(false);
        }

        // Limpiar el Spinner (restablecer a la primera opción)
        estadoSpinner.setSelection(0);
    }

}
