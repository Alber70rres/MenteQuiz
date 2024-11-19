package mente.app.mentequiz;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class homePage extends AppCompatActivity {
    private TextView questionText, questionNumber;
    private RadioButton optionA, optionB, optionC, optionD;
    private Button nextButton;
    private dbConnection dbHelper;
    private Cursor questionCursor;
    private int questionIndex = 0;
    private String correctAnswer;
    private double questionScore = 0.25;
    private double totalResult = 0;
    private double incorrectPenalty = 0.0625;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        questionText = findViewById(R.id.question);
        questionNumber = findViewById(R.id.textoNumero);
        optionA = findViewById(R.id.questionA);
        optionB = findViewById(R.id.questionB);
        optionC = findViewById(R.id.questionC);
        optionD = findViewById(R.id.questionD);
        nextButton = findViewById(R.id.btnQuestion);

        dbHelper = new dbConnection(this);
        questionCursor = dbHelper.getAllQuestions();

        if (questionCursor != null && questionCursor.getCount() > 0) {
            questionCursor.moveToFirst();
            questionIndex = 0;
            showQuestion();
        }
    }

    public void Siguiente(View view) {
        if (nextButton.isEnabled()) {
            optionA.setChecked(false);
            optionB.setChecked(false);
            optionC.setChecked(false);
            optionD.setChecked(false);

            setTextColorBasedOnMode();

            EnableOptions();
            if (questionCursor != null && questionCursor.moveToNext()) {
                questionIndex++;
                showQuestion();
            } else {
                evaluatePerformance();
            }
        }
    }

    private void showQuestion() {
        if (questionCursor != null) {
            try {
                String question = questionCursor.getString(questionCursor.getColumnIndexOrThrow("question_text"));
                String optA = questionCursor.getString(questionCursor.getColumnIndexOrThrow("option_a"));
                String optB = questionCursor.getString(questionCursor.getColumnIndexOrThrow("option_b"));
                String optC = questionCursor.getString(questionCursor.getColumnIndexOrThrow("option_c"));
                String optD = questionCursor.getString(questionCursor.getColumnIndexOrThrow("option_d"));
                correctAnswer = questionCursor.getString(questionCursor.getColumnIndexOrThrow("correct_answer"));

                questionText.setText(question);
                optionA.setText(optA);
                optionB.setText(optB);
                optionC.setText(optC);
                optionD.setText(optD);

                questionNumber.setText("Pregunta " + (questionIndex + 1));
                if(questionIndex == 19){
                    nextButton.setText("Finalizar");
                }
                nextButton.setEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void verificarRespuesta(View view) {
        if (view instanceof RadioButton) {
            RadioButton selectedRadioButton = (RadioButton) view;
            String selectedText = selectedRadioButton.getText().toString();

            if (selectedText.equals(correctAnswer)) {
                selectedRadioButton.setTextColor(Color.GREEN);
                totalResult += questionScore;
                nextButton.setEnabled(true);
                disableOptions();
            } else {
                selectedRadioButton.setTextColor(Color.RED);
                selectedRadioButton.setEnabled(false);
                totalResult -= incorrectPenalty;
            }
        }
    }

    private void evaluatePerformance() {
        String performance;
        double roundedResult = Math.floor(totalResult * 100) / 100;

        if (roundedResult >= 4.0) {
            performance = "Excelente";
        } else if (roundedResult >= 2.9) {
            performance = "Regular";
        } else {
            performance = "Mal";
        }

        Intent intent = new Intent(this, Finalizado.class);
        intent.putExtra("calificacion", roundedResult);
        intent.putExtra("desempeno", performance);
        startActivity(intent);
        finish();
    }



    private void disableOptions() {
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
    }

    private void EnableOptions() {
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
    }
    private void setTextColorBasedOnMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int textColor = (currentNightMode == Configuration.UI_MODE_NIGHT_YES) ? Color.WHITE : Color.BLACK;

        optionA.setTextColor(textColor);
        optionB.setTextColor(textColor);
        optionC.setTextColor(textColor);
        optionD.setTextColor(textColor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (questionCursor != null) {
            questionCursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void CerrarSesion (View view){
        Intent layoutLogin = new Intent(this, StartExam.class);
        startActivity(layoutLogin);
        finish();
    }
}