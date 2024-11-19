package mente.app.mentequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Finalizado extends AppCompatActivity {
    private double calificacion;
    private String desempeno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finalizado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        calificacion = getIntent().getDoubleExtra("calificacion", 0.0);
        desempeno = getIntent().getStringExtra("desempeno");

        TextView calificacionTextView = findViewById(R.id.numberCalification);
        TextView desempenoTextView = findViewById(R.id.desempeño);

        calificacionTextView.setText(String.valueOf(calificacion));
        desempenoTextView.setText(desempeno);
    }
    public void CerrarSesion (View view){
        Intent layoutLogin = new Intent(this, MainActivity.class);
        startActivity(layoutLogin);
        finish();
    }
    public void IntentarOtravez (View view){
        Intent layoutHome = new Intent(this, StartExam.class);
        startActivity(layoutHome);
        finish();
    }
}