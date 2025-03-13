package gal.cifpacarballeira.recuperacinunidad5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar la base de datos de videojuegos.
 */
public class VideojuegoDB extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DB_NAME = "videojuegosdb";
    private static final int DB_VERSION = 1;

    // Nombre de la tabla
    private static final String TABLE_VIDEOJUEGOS = "videojuegos";

    // Definición de la tabla
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_VIDEOJUEGOS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "titulo TEXT, " +
            "puntuacion INTEGER, " +
            "estado TEXT" +
            ")";

    public VideojuegoDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si la base de datos existe y se necesita una actualización, eliminar la tabla existente
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOJUEGOS);
        // Vuelve a crear la tabla
        onCreate(db);
    }

    /**
     * Método para agregar un videojuego a la base de datos.
     *
     * @param videojuego El videojuego a agregar.
     * @return El ID del videojuego insertado.
     */
    public long agregarVideojuego(Videojuego videojuego) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Crear un ContentValues con los datos del videojuego
        ContentValues values = new ContentValues();
        values.put("titulo", videojuego.getTitulo());
        values.put("puntuacion", videojuego.getPuntuacion());
        values.put("estado", videojuego.getEstado().name());

        // Insertar el videojuego en la base de datos y obtener el ID
        long id = db.insert(TABLE_VIDEOJUEGOS, null, values);

        // Cerrar la base de datos

        return id;
    }

    public List<Videojuego> getVideojuegos() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Videojuego> videojuegoList = new ArrayList<>();
        Cursor cursor = db.query("videojuegos", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                videojuegoList.add(Videojuego.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return videojuegoList;
    }

}
