package gal.cifpacarballeira.recuperacinunidad5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "videojuegosdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_VIDEOJUEGOS = "videojuegos";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_VIDEOJUEGOS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "titulo TEXT UNIQUE, " +  // Se asume que el título es único
            "puntuacion INTEGER, " +
            "estado TEXT" +
            ")";

    public VideojuegoDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOJUEGOS);
        onCreate(db);
    }

    public long agregarVideojuego(Videojuego videojuego) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", videojuego.getTitulo());
        values.put("puntuacion", videojuego.getPuntuacion());
        values.put("estado", videojuego.getEstado().name());

        long id = db.insert(TABLE_VIDEOJUEGOS, null, values);

        return id;
    }

    public List<Videojuego> getVideojuegos() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Videojuego> videojuegoList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_VIDEOJUEGOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                videojuegoList.add(Videojuego.fromCursor(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return videojuegoList;
    }

    public int actualizarVideojuego(String tituloAntiguo, Videojuego videojuegoNuevo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", videojuegoNuevo.getTitulo());
        values.put("puntuacion", videojuegoNuevo.getPuntuacion());
        values.put("estado", videojuegoNuevo.getEstado().name());

        int rowsUpdated = db.update(TABLE_VIDEOJUEGOS, values, "titulo = ?", new String[]{tituloAntiguo});

        return rowsUpdated;
    }

    public int eliminarVideojuego(String titulo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_VIDEOJUEGOS, "titulo = ?", new String[]{titulo});

        return rowsDeleted;
    }
}
