package com.example.felizmente.activities.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
    int currentScore = 1, questionsOutOfTen = 1, currentPos;
    private final String QUESTION = "¿Reconoces al personaje de la foto?";
    private ArrayList<Integer> randomNumbers = new ArrayList<>();
    private AlertDialog.Builder builder;

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
        random = new Random();
        quizModalArrayList = new ArrayList<>();
        getQuizQuestions(quizModalArrayList);
        currentPos = checkingNoRepeatsInRandom(randomNumbers);

        setDataToViews(currentPos);

        option1Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase().equalsIgnoreCase(option1Btn.getText().toString().trim())){
                ++currentScore;
            }
            questionsOutOfTen++;
            currentPos = checkingNoRepeatsInRandom(randomNumbers);
            setDataToViews(currentPos);
        });

        option2Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase().equalsIgnoreCase(option2Btn.getText().toString().trim())){
                ++currentScore;
            }
            questionsOutOfTen++;
            currentPos = checkingNoRepeatsInRandom(randomNumbers);
            setDataToViews(currentPos);
        });

        option3Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase().equalsIgnoreCase(option3Btn.getText().toString().trim())){
                ++currentScore;
            }
            questionsOutOfTen++;
            currentPos = checkingNoRepeatsInRandom(randomNumbers);
            setDataToViews(currentPos);
        });

    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, findViewById(R.id.layoutScore));
        TextView score = bottomSheetView.findViewById(R.id.idScore);
        Button restartQuizBtn = bottomSheetView.findViewById(R.id.restartButton);
        score.setText("Tu puntuación es: \n" + currentScore + "/10");

        restartQuizBtn.setOnClickListener(view -> {
            randomNumbers = new ArrayList<>();
            currentPos = checkingNoRepeatsInRandom(randomNumbers);
            setDataToViews(currentPos);
            questionsOutOfTen = 1;
            currentScore = 1;
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void setDataToViews(int pos){
        questionNumber.setText(questionsOutOfTen + "/10");
        if(questionsOutOfTen == 10){
            showBottomSheet();
        } else {
            question.setText(quizModalArrayList.get(pos).getQuestion());

            switch(quizModalArrayList.get(pos).getId()){
                case 1:
                    imageQuestion.setImageResource(R.drawable.concha_velasco);
                    break;
                case 2:
                    imageQuestion.setImageResource(R.drawable.lola_flores);
                    break;
                case 3:
                    imageQuestion.setImageResource(R.drawable.manolo_escobar);
                    break;
                case 4:
                    imageQuestion.setImageResource(R.drawable.duo_dinamico);
                    break;
                case 5:
                    imageQuestion.setImageResource(R.drawable.marisol);
                    break;
                case 6:
                    imageQuestion.setImageResource(R.drawable.raphael);
                    break;
                case 7:
                    imageQuestion.setImageResource(R.drawable.julio_iglesias);
                    break;
                case 8:
                    imageQuestion.setImageResource(R.drawable.alfredo_landa);
                    break;
                case 9:
                    imageQuestion.setImageResource(R.drawable.pele);
                    break;
                case 10:
                    imageQuestion.setImageResource(R.drawable.felipe_gonzalez);
                    break;
            }

            option1Btn.setText(quizModalArrayList.get(pos).getOption1());
            option2Btn.setText(quizModalArrayList.get(pos).getOption2());
            option3Btn.setText(quizModalArrayList.get(pos).getOption3());
        }

    }

    private void getQuizQuestions(ArrayList<QuizModal> quizModalArrayList) {
        quizModalArrayList.add(new QuizModal(1,QUESTION,
                "Carmen Sevilla", "Sara Montiel", "Concha Velasco",
                "Concha Velasco"));
        quizModalArrayList.add(new QuizModal(2,QUESTION,
                "Lola Flores", "Gracita Morales", "Florinda Chico",
                "Lola Flores"));
        quizModalArrayList.add(new QuizModal(3,QUESTION,
                "Manolo Escobar", "José Orjas", "Nino Bravo",
                "Manolo Escobar"));
        quizModalArrayList.add(new QuizModal(4,QUESTION,
                "Dúo Dinámico", "Los Beatles", "Elvis Presley",
                "Dúo Dinámico"));
        quizModalArrayList.add(new QuizModal(5,QUESTION,
                "Marisol", "Rocío Durcal", "Concha Velasco",
                "Marisol"));
        quizModalArrayList.add(new QuizModal(6,QUESTION,
                "Paco Rabal", "Nino Bravo", "Raphael",
                "Raphael"));
        quizModalArrayList.add(new QuizModal(7,QUESTION,
                "Camilo Sesto", "Julio Iglesias", "Peret",
                "Julio Iglesias"));
        quizModalArrayList.add(new QuizModal(8,QUESTION,
                "Tony LeBlanc", "Alfredo Landa", "Antonio Ozores",
                "Alfredo Landa"));
        quizModalArrayList.add(new QuizModal(9,QUESTION,
                "Pelé", "DiStefano", "Maradona",
                "Pelé"));
        quizModalArrayList.add(new QuizModal(10,QUESTION,
                "Felipe González", "Pedro Sánchez", "Adolfo Suárez",
                "Felipe González"));

    }

    private int checkingNoRepeatsInRandom(ArrayList<Integer> number){

        int otherNumber = random.nextInt(quizModalArrayList.size());
        if(!number.contains(otherNumber)){
            number.add(otherNumber);
            return otherNumber;
        } else {
            return checkingNoRepeatsInRandom(number);
        }
    }

}