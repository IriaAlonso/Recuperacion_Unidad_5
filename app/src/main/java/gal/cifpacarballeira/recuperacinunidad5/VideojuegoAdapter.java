package gal.cifpacarballeira.recuperacinunidad5;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideojuegoAdapter extends RecyclerView.Adapter<VideojuegoAdapter.VideojuegoViewHolder> {

    private List<Videojuego> videojuegos;
    private OnItemClickListener listener;  // Interface para manejar el clic en los items

    // Interfaz para manejar el clic en los items
    public interface OnItemClickListener {
        void onItemClick(Videojuego videojuego);
    }

    // Constructor con el OnItemClickListener
    public VideojuegoAdapter(List<Videojuego> videojuegos, OnItemClickListener listener) {
        this.videojuegos = videojuegos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideojuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videojuego_item, parent, false);
        return new VideojuegoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideojuegoViewHolder holder, int position) {
        Videojuego videojuego = videojuegos.get(position);
        holder.tituloTextView.setText(videojuego.getTitulo());
        holder.puntuacionTextView.setText("Puntuación: " + videojuego.getPuntuacion());
        holder.estadoTextView.setText("Estado: " + videojuego.getEstado().toString());

        // Setear el clic en cada item
        holder.itemView.setOnClickListener(v -> {
            // Añadir Log para verificar que se ejecuta el clic
            Log.d("VideojuegoAdapter", "Clic en: " + videojuego.getTitulo());
            listener.onItemClick(videojuego);
        });
    }

    @Override
    public int getItemCount() {
        return videojuegos.size();
    }

    public static class VideojuegoViewHolder extends RecyclerView.ViewHolder {
        public TextView tituloTextView;
        public TextView puntuacionTextView;
        public TextView estadoTextView;

        public VideojuegoViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloTextView);
            puntuacionTextView = itemView.findViewById(R.id.puntuacionTextView);
            estadoTextView = itemView.findViewById(R.id.estadoTextView);
        }
    }
}
