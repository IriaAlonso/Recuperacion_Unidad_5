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
import android.widget.Toast;
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

        videojuegoViewModel = new ViewModelProvider(requireActivity()).get(VideojuegoViewModel.class);

        tituloEditText = view.findViewById(R.id.tituloEditText);
        puntuacionRadioGroup = view.findViewById(R.id.puntuacionRadioGroup);
        estadoSpinner = view.findViewById(R.id.estadoSpinner);
        Button guardarButton = view.findViewById(R.id.guardarButton);
        Button modificarButton = view.findViewById(R.id.modificarButton);
        Button eliminarButton = view.findViewById(R.id.eliminarButton);

        ArrayAdapter<EstadoJuego> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                EstadoJuego.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSpinner.setAdapter(adapter);

        guardarButton.setOnClickListener(v -> agregarVideojuego());
        modificarButton.setOnClickListener(v -> modificarVideojuego());
        eliminarButton.setOnClickListener(v -> eliminarVideojuego());

        videojuegoViewModel.getVideojuegoSeleccionado().observe(getViewLifecycleOwner(), videojuego -> {
            if (videojuego != null) {
                tituloEditText.setText(videojuego.getTitulo());

                for (int i = 0; i < puntuacionRadioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) puntuacionRadioGroup.getChildAt(i);
                    if (Integer.parseInt(radioButton.getText().toString()) == videojuego.getPuntuacion()) {
                        radioButton.setChecked(true);
                    }
                }

                ArrayAdapter<EstadoJuego> spinnerAdapter = (ArrayAdapter<EstadoJuego>) estadoSpinner.getAdapter();
                for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                    if (spinnerAdapter.getItem(i) == videojuego.getEstado()) {
                        estadoSpinner.setSelection(i);
                        break;
                    }
                }

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
            videojuegoViewModel.agregarVideojuego(nuevoJuego);
            Toast.makeText(requireContext(), "Videojuego agregado con éxito", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    private void modificarVideojuego() {
        String titulo = tituloEditText.getText().toString().trim();
        int puntuacion = obtenerPuntuacionSeleccionada();
        EstadoJuego estado = (EstadoJuego) estadoSpinner.getSelectedItem();

        if (!titulo.isEmpty() && puntuacion != -1 && videojuegoSeleccionado != null) {
            Videojuego videojuegoModificado = new Videojuego(titulo, puntuacion, estado);
            videojuegoViewModel.actualizarVideojuego(videojuegoSeleccionado, videojuegoModificado);
            Toast.makeText(requireContext(), "Videojuego actualizado con éxito", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    private void eliminarVideojuego() {
        if (videojuegoSeleccionado != null) {
            videojuegoViewModel.eliminarVideojuego(videojuegoSeleccionado);
            Toast.makeText(requireContext(), "Videojuego eliminado con éxito", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    private int obtenerPuntuacionSeleccionada() {
        int selectedId = puntuacionRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedButton = puntuacionRadioGroup.findViewById(selectedId);
            return Integer.parseInt(selectedButton.getText().toString());
        }
        return -1;
    }

    private void limpiar() {
        tituloEditText.setText("");
        for (int i = 0; i < puntuacionRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) puntuacionRadioGroup.getChildAt(i);
            radioButton.setChecked(false);
        }
        estadoSpinner.setSelection(0);
    }
}