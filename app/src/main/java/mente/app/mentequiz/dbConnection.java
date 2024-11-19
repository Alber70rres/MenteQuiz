package mente.app.mentequiz;
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

    private static final String TABLE_QUESTIONS = "QuizQuestions";
    private static final String COLUMN_QUESTION_ID = "question_id";
    private static final String COLUMN_QUESTION_TEXT = "question_text";
    private static final String COLUMN_OPTION_A = "option_a";
    private static final String COLUMN_OPTION_B = "option_b";
    private static final String COLUMN_OPTION_C = "option_c";
    private static final String COLUMN_OPTION_D = "option_d";
    private static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    private static final String COLUMN_SCORE = "score";

    public dbConnection(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_TEXT + " TEXT NOT NULL, " +
                COLUMN_OPTION_A + " TEXT NOT NULL, " +
                COLUMN_OPTION_B + " TEXT NOT NULL, " +
                COLUMN_OPTION_C + " TEXT NOT NULL, " +
                COLUMN_OPTION_D + " TEXT NOT NULL, " +
                COLUMN_CORRECT_ANSWER + " TEXT NOT NULL, " +
                COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0" +
                ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        insertInitialQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public boolean registerUser(String name, String email, int age, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    private void insertInitialQuestions(SQLiteDatabase db) {
        insertQuestion(db, "Sistema operativo de código abierto", "Linux", "iOS", "Windows", "macOS", "Linux");
        insertQuestion(db, "Componente del sistema operativo que gestiona la memoria", "Controlador", "Interfaz", "Kernel", "Shell", "Kernel");
        insertQuestion(db, "¿Qué tipo de sistema operativo permite múltiples tareas?", "Embebido", "Tiempo real", "Monolítico", "Multitarea", "Multitarea");
        insertQuestion(db, "Función del administrador de tareas en Windows", "Configurar red", "Gestionar procesos", "Actualizar SO", "Instalar", "Gestionar procesos");
        insertQuestion(db, "¿Qué es la memoria virtual?", "Memoria en servidores", "Memoria externa", "Disco como RAM", "Memoria física", "Disco como RAM");
        insertQuestion(db, "En SO, ¿qué es un proceso?", "Instrucciones en ejecución", "Archivo en disco", "Programa inactivo", "Tarea programada", "Instrucciones en ejecución");
        insertQuestion(db, "¿Cuál es una afirmación verdadera sobre el kernel?", "Intermediario hardware-software", "Programa grande", "Solo interfaz gráfica", "No accede a recursos", "Intermediario hardware-software");
        insertQuestion(db, "SO diseñado para tareas específicas y recursos limitados", "Multitarea", "Distribuido", "General", "Embebido", "Embebido");
        insertQuestion(db, "Propósito del sistema de archivos en SO", "Conectar redes", "Organizar datos", "Ejecutar aplicaciones", "Proteger hardware", "Organizar datos");
        insertQuestion(db, "Comando para listar archivos en Windows", "show", "list", "dir", "ls", "dir");
        insertQuestion(db, "Característica de SO en tiempo real", "Interfaz avanzada", "Respuestas predecibles", "Procesos sin límites", "Para uso personal", "Respuestas predecibles");
        insertQuestion(db, "¿Qué significa 'multitasking'?", "Múltiples procesos", "Alternar aplicaciones", "Un proceso a la vez", "Procesos en secuencia", "Múltiples procesos");
        insertQuestion(db, "¿Qué es un controlador?", "Permite comunicación hardware-SO", "Protocolo de red", "Parte del hardware", "Gestor de archivos", "Permite comunicación hardware-SO");
        insertQuestion(db, "Función del 'bootloader'", "Instalar apps", "Protección antivirus", "Cargar núcleo", "Configurar red", "Cargar núcleo");
        insertQuestion(db, "¿Qué representa el 'escritorio' en un entorno gráfico?", "Archivos temporales", "Interfaz principal", "Gestor de redes", "Instalar controladores", "Interfaz principal");
        insertQuestion(db, "¿Qué es un 'sistema distribuido'?", "Interfaz gráfica", "Redes locales", "Computadoras distribuidas", "Opera en una sola PC", "Computadoras distribuidas");
        insertQuestion(db, "Ventaja de las máquinas virtuales", "Sin configuración extra", "Ejecutan varios SO", "Reducen capacidades", "Ejecutan un tipo de software", "Ejecutan varios SO");
        insertQuestion(db, "Comando en Linux para cambiar permisos", "mv", "chmod", "ls", "chown", "chmod");
        insertQuestion(db, "¿Qué hace la GUI?", "Acceso remoto", "Gestionar hardware", "Interacción visual", "Interacción solo texto", "Interacción visual");
        insertQuestion(db, "¿Qué es un virus informático?", "Mejora rendimiento", "Código malicioso", "Archivo temporal", "Optimiza redes", "Código malicioso");
    }

    private void insertQuestion(SQLiteDatabase db, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_TEXT, questionText);
        values.put(COLUMN_OPTION_A, optionA);
        values.put(COLUMN_OPTION_B, optionB);
        values.put(COLUMN_OPTION_C, optionC);
        values.put(COLUMN_OPTION_D, optionD);
        values.put(COLUMN_CORRECT_ANSWER, correctAnswer);
        db.insert(TABLE_QUESTIONS, null, values);
    }

    public Cursor getAllQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS, null);
    }

}