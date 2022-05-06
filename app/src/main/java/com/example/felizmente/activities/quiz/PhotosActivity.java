package com.example.felizmente.activities.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Random;

public class PhotosActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private ImageView imageQuestion;
    private Button option1Btn, option2Btn, option3Btn;
    private ArrayList<com.example.felizmente.activities.quiz.QuizModal> quizModalArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 1, currentPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        getSupportActionBar().hide();

        question = findViewById(R.id.idQuestion);
        questionNumber = findViewById(R.id.idQuestionAttempted);
        imageQuestion = findViewById(R.id.imageQuestion);
        option1Btn = findViewById(R.id.option1);
        option2Btn = findViewById(R.id.option2);
        option3Btn = findViewById(R.id.option3);
        quizModalArrayList = new ArrayList<>();
        random = new Random();
        getQuizQuestions(quizModalArrayList);
        currentPos = random.nextInt(quizModalArrayList.size());
        setDataToViews(currentPos);

        option1Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase().equalsIgnoreCase(option1Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos = random.nextInt(quizModalArrayList.size());
            setDataToViews(currentPos);
        });

        option2Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase().equalsIgnoreCase(option2Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos = random.nextInt(quizModalArrayList.size());
            setDataToViews(currentPos);
        });

        option3Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase().equalsIgnoreCase(option3Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos = random.nextInt(quizModalArrayList.size());
            setDataToViews(currentPos);
        });

    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout)findViewById(R.id.layoutScore));
        TextView score = bottomSheetView.findViewById(R.id.idScore);
        Button restartQuizBtn = bottomSheetView.findViewById(R.id.restartButton);
        score.setText("Tu puntuación es: \n" + currentScore + "/10");
        restartQuizBtn.setOnClickListener(view -> {
            currentPos = random.nextInt(quizModalArrayList.size());
            setDataToViews(currentPos);
            questionAttempted = 1;
            currentScore = 0;
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void setDataToViews(int pos){
        questionNumber.setText("Preguntas contestadas: " + questionAttempted + "/10");
        if(questionAttempted == 10){
            showBottomSheet();
        } else {
            question.setText(quizModalArrayList.get(currentPos).getQuestion());
            option1Btn.setText(quizModalArrayList.get(currentPos).getOption1());
            option2Btn.setText(quizModalArrayList.get(currentPos).getOption2());
            option3Btn.setText(quizModalArrayList.get(currentPos).getOption3());
        }

    }

//    private void getQuizQuestions(ArrayList<QuizModal> quizModalArrayList) {
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "@drawable/conchavelasco", "Carmen Sevilla", "Sara Montiel", "Concha Velasco", "Carmen Sevilla"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img2", "Lola Flores", "Gracita Morales", "Florida Chico", "Lola Flores"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img3", "Manolo Escobar", "Paco Martinez Soria", "Nino Bravo", "Manolo Escobar"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img4", "El Dúo Dinámico", "Los Beatles", "Elvis Presley", "El Dúo Dinámico"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img5", "Marisol", "Rocío Durcal", "Concha Velasco", "Marisol"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img6", "Joan Manuel Serrat", "Nino Bravo", "Raphael", "Raphael"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img7", "Camilo Sesto", "Julio Iglesias", "Peret", "Julio Iglesias"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img8", "Tony LeBlanc", "Alfredo Landa", "Antonio Ozores", "Alfredo Landa"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img9", "Pelé", "Alfredo DiStefano", "Maradona", "Pelé"));
//        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "img10", "Felipe González", "Jose María Aznar", "Adolfo Suárez", "Felipe González"));
//
//    }

    private void getQuizQuestions(ArrayList<com.example.felizmente.activities.quiz.QuizModal> quizModalArrayList) {
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?", "Carmen Sevilla", "Sara Montiel", "Concha Velasco", "Carmen Sevilla"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?", "Lola Flores", "Gracita Morales", "Florida Chico", "Lola Flores"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?", "Manolo Escobar", "Paco Martinez Soria", "Nino Bravo", "Manolo Escobar"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?", "El Dúo Dinámico", "Los Beatles", "Elvis Presley", "El Dúo Dinámico"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?",  "Marisol", "Rocío Durcal", "Concha Velasco", "Marisol"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?",  "Joan Manuel Serrat", "Nino Bravo", "Raphael", "Raphael"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?",  "Camilo Sesto", "Julio Iglesias", "Peret", "Julio Iglesias"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?",  "Tony LeBlanc", "Alfredo Landa", "Antonio Ozores", "Alfredo Landa"));
        quizModalArrayList.add(new com.example.felizmente.activities.quiz.QuizModal("¿Reconoces al personaje de la foto?",  "Pelé", "Alfredo DiStefano", "Maradona", "Pelé"));
        quizModalArrayList.add(new QuizModal("¿Reconoces al personaje de la foto?", "Felipe González", "Jose María Aznar", "Adolfo Suárez", "Felipe González"));

    }
}