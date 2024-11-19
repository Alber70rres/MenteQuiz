package angie.app.datarecolecter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dataRecolecter.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "RegisterUsers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_PASSWORD = "password";

    // Constructor correcto
    public dbConnection(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
                ")";
        BaseDeDatos.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean registerUser(String name, String email, int age, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_PASSWORD, password);

        // Insertar datos y verificar si se inserta correctamente
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});

        // Verificar si hay resultados
        boolean isValid = cursor.getCount() > 0;
        cursor.close(); // Cerrar el cursor
        db.close(); // Cerrar la base de datos
        return isValid; // Retornar si el usuario es válido
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            cursor.close(); // Cerrar el cursor
            db.close(); // Cerrar la base de datos
            return userName;
        } else {
            return null; // Si no se encuentra el usuario
        }
    }
}