package mente.app.mentequiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText userLogin, passwordLogin;
    private dbConnection databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userLogin = findViewById(R.id.userLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        databaseHelper = new dbConnection(this);
    }

    public void passToRegister(View view) {
        Intent layoutRegister = new Intent(this, Register.class);
        startActivity(layoutRegister);
    }

    public void validateLogin(View view) {
        String email = userLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            userLogin.setError("El correo es obligatorio");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordLogin.setError("La contraseña es obligatoria");
            return;
        }

        boolean isValidUser = databaseHelper.validateUser(email, password);

        if (isValidUser) {
            Intent homeIntent = new Intent(this, StartExam.class);
            startActivity(homeIntent);
            finish();
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}