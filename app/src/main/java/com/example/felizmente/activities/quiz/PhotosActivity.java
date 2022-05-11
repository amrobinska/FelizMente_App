package com.example.felizmente.activities.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;
import com.example.felizmente.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

        activateButtonListeners();

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
            questionsOutOfTen = 1;
            currentScore = 1;
            bottomSheetDialog.dismiss();
            setDataToViews(currentPos);
        });

        Button newGame = bottomSheetView.findViewById(R.id.newGame);
        newGame.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            randomNumbers = new ArrayList<>();
            currentPos = checkingNoRepeatsInRandom(randomNumbers);
            questionsOutOfTen = 1;
            currentScore = 1;

        });

        Button exitQuiz = bottomSheetView.findViewById(R.id.exitQuizButton);
        exitQuiz.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        FrameLayout bottomSheet = (FrameLayout)bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behaviour = BottomSheetBehavior.from(bottomSheet);
        behaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        behaviour.setPeekHeight(0);

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
                case 11:
                    imageQuestion.setImageResource(R.drawable.rocio_jurado);
                case 12:
                    imageQuestion.setImageResource(R.drawable.carmen_sevilla);
                    break;
                case 13:
                    imageQuestion.setImageResource(R.drawable.camilo_sesto);
                    break;
                case 14:
                    imageQuestion.setImageResource(R.drawable.gracita_morales);
                    break;
                case 15:
                    imageQuestion.setImageResource(R.drawable.isabel_pantoja);
                    break;
                case 16:
                    imageQuestion.setImageResource(R.drawable.jose_orjas);
                    break;
                case 17:
                    imageQuestion.setImageResource(R.drawable.nino_bravo);
                    break;
                case 18:
                    imageQuestion.setImageResource(R.drawable.paco_rabal);
                    break;
                case 19:
                    imageQuestion.setImageResource(R.drawable.peret);
                    break;
                case 20:
                    imageQuestion.setImageResource(R.drawable.rocio_durcal);
                    break;
                case 21:
                    imageQuestion.setImageResource(R.drawable.sara_montiel);
                    break;
                case 22:
                    imageQuestion.setImageResource(R.drawable.tony_leblanc);


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
        quizModalArrayList.add(new QuizModal(11,QUESTION,
                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
                "Rocío Jurado"));

        quizModalArrayList.add(new QuizModal(12,QUESTION,
                "Isabel Pantoja", "Carmen Sevilla", "Rocío Durcal",
                "Carmen Sevilla"));
        quizModalArrayList.add(new QuizModal(13,QUESTION,
                "Antonio Ozores", "Camilo Sesto", "Peret",
                "Camilo Sesto"));
        quizModalArrayList.add(new QuizModal(14,QUESTION,
                "Carmen Sevilla", "Florinda Chico", "Gracita Morales",
                "Gracita Morales"));
        quizModalArrayList.add(new QuizModal(15,QUESTION,
                "Marisol", "Lola Flores", "Isabel Pantoja",
                "Isabel Pantoja"));
        quizModalArrayList.add(new QuizModal(16,QUESTION,
                "José Orjas", "Paco Rabal", "Antonio Ozores",
                "José Orjas"));
        quizModalArrayList.add(new QuizModal(17,QUESTION,
                "Julio Iglesias", "Nino Bravo", "Felipe González",
                "Nino Bravo"));
        quizModalArrayList.add(new QuizModal(18,QUESTION,
                "Pelé", "Paco Rabal", "Manolo Escobar",
                "Paco Rabal"));
        quizModalArrayList.add(new QuizModal(19,QUESTION,
                "Peret", "El Fary", "Tony Leblanc",
                "Peret"));
        quizModalArrayList.add(new QuizModal(20,QUESTION,
                "Rocío Durcal", "Concha Velasco", "Gracita Morales",
                "Rocío Durcal"));
        quizModalArrayList.add(new QuizModal(21,QUESTION,
                "Marujita Díaz", "Sara Montiel", "Carmen Sevilla",
                "Sara Montiel"));
        quizModalArrayList.add(new QuizModal(22,QUESTION,
                "Tony Leblanc", "Manolo Escobar", "DiStefano",
                "Tony Leblanc"));
//        quizModalArrayList.add(new QuizModal(23,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(24,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(25,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(26,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(27,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(28,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(29,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));
//        quizModalArrayList.add(new QuizModal(30,QUESTION,
//                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
//                "Rocío Jurado"));


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

    public void exitQuiz(View v){
        Intent intent = new Intent(this, MainActivity.class);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres dejar de jugar??")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .create();
        dialog.show();
    }

    private void activateButtonListeners(){
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

}