package mente.app.mentequiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class Register extends AppCompatActivity {

    private EditText registerName, registerEmail, registerAge, registerPassword;
    private dbConnection databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.principal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerAge = findViewById(R.id.registerAge);
        registerPassword = findViewById(R.id.registerPassword);

        databaseHelper = new dbConnection(this);
    }

    public void passToLogin(View view) {
        Intent layoutLogin = new Intent(this, MainActivity.class);
        startActivity(layoutLogin);
        finish();
    }

    public void passToHome(){
        Intent homeIntent = new Intent(this, StartExam.class);
        startActivity(homeIntent);
        finish();
    }
    public void RegisterUser(View view){
        if (validateInputs()) {

            String name = registerName.getText().toString().trim();
            String email = registerEmail.getText().toString().trim();
            int age = Integer.parseInt(registerAge.getText().toString().trim());
            String password = registerPassword.getText().toString().trim();

            boolean isInserted = databaseHelper.registerUser(name, email, age, password);

            if (isInserted) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                registerName.setText("");
                registerEmail.setText("");
                registerAge.setText("");
                registerPassword.setText("");
                passToHome();
            } else {
                Toast.makeText(this, "Error al registrar. Intente de nuevo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs() {
        // Validación del campo Nombre
        if (TextUtils.isEmpty(registerName.getText().toString().trim())) {
            registerName.setError("El nombre es obligatorio");
            return false;
        }

        String email = registerEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            registerEmail.setError("El correo es obligatorio");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError("Ingrese un correo válido");
            return false;
        }

        String ageText = registerAge.getText().toString().trim();
        if (TextUtils.isEmpty(ageText)) {
            registerAge.setError("La edad es obligatoria");
            return false;
        } else {
            try {
                int age = Integer.parseInt(ageText);
                if (age <= 0) {
                    registerAge.setError("Ingrese una edad válida");
                    return false;
                }
            } catch (NumberFormatException e) {
                registerAge.setError("Ingrese un número válido para la edad");
                return false;
            }
        }

        if (TextUtils.isEmpty(registerPassword.getText().toString().trim())) {
            registerPassword.setError("La contraseña es obligatoria");
            return false;
        } else if (registerPassword.getText().toString().length() < 6) {
            registerPassword.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        return true;
    }

}