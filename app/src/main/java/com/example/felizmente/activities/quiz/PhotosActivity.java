package com.example.felizmente.activities.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
    int currentScore = 0, questionsOutOfTen = 1, currentPos;
    private ArrayList<Integer> randomNumbers = new ArrayList<>();
    private String scoreText;

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

        setScoreLine(currentScore);

        if (currentScore > 5) {
            score.setText(currentScore == 10 ? scoreText + "¡Perfecto!" : scoreText + "¡Muy bien!");
        } else {
            score.setText(currentScore == 5 ? scoreText + "¡Regulín!" : scoreText + "\n¡Sigue practicando!");
        }

        restartQuizBtn.setOnClickListener(view -> {
            randomNumbers = new ArrayList<>();
            currentPos = checkingNoRepeatsInRandom(randomNumbers);
            questionsOutOfTen = 1;
            currentScore = 0;
            bottomSheetDialog.dismiss();
            setDataToViews(currentPos);
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

        FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behaviour = BottomSheetBehavior.from(bottomSheet);
        behaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        behaviour.setPeekHeight(0);
    }

    private void setScoreLine(int score){

        if (score == 1){
            scoreText = "Has acertado " + score + " pregunta de 10. ";
        } else {
            scoreText = "Has acertado " + score + " preguntas de 10. ";
        }
    }

    private void setDataToViews(int pos) {

        String questionsDone = questionsOutOfTen + "/ 10";

        if (questionsOutOfTen > 10 ) {
            showBottomSheet();
        } else {
            questionNumber.setText(questionsDone);
            question.setText(quizModalArrayList.get(pos).getQuestion());

            Glide.with(this)
                    .load(quizModalArrayList.get(pos).getUrl())
//                    .placeholder(R.drawable.felizmenteimagen)
                    .into(imageQuestion);

            // estaria guay si podemos poner una foto comodin por si no se carga esta

            option1Btn.setText(quizModalArrayList.get(pos).getOption1());
            option2Btn.setText(quizModalArrayList.get(pos).getOption2());
            option3Btn.setText(quizModalArrayList.get(pos).getOption3());
        }
    }

    private void getQuizQuestions(ArrayList<QuizModal> quizModalArrayList) {
        String questionString = "¿Reconoces al personaje de la foto?\n";
        quizModalArrayList.add(new QuizModal(1, questionString,
                "https://img2.rtve.es/v/6228293/?imgProgApi=imgBackground&w=400",
                "Carmen Sevilla", "Sara Montiel", "Concha Velasco",
                "Concha Velasco"));
        quizModalArrayList.add(new QuizModal(2, questionString,
                "https://okdiario.com/look/img/2018/07/13/lola-flores.jpg",
                "Lola Flores", "Gracita Morales", "Florinda Chico",
                "Lola Flores"));
        quizModalArrayList.add(new QuizModal(3, questionString,
                "https://ep00.epimg.net/cultura/imagenes/2013/09/18/album/1379539495_593870_1379541905_album_normal.jpg",
                "Manolo Escobar", "José Orjas", "Nino Bravo",
                "Manolo Escobar"));
        quizModalArrayList.add(new QuizModal(4, questionString,
                "https://i.ytimg.com/vi/ywRwQf6-9Z8/maxresdefault.jpg",
                "Dúo Dinámico", "Los Beatles", "Elvis Presley",
                "Dúo Dinámico"));
        quizModalArrayList.add(new QuizModal(5, questionString,
                "https://www.larazon.es/resizer/WMtV8lOuVOyqpTiwgK8CIctgcNI=/600x400/smart/filters:format(jpg)/cloudfront-eu-central-1.images.arcpublishing.com/larazon/PYI4JEXQC5CWTOHTLW2YZVMORE.jpg",
                "Marisol", "Rocío Durcal", "Concha Velasco",
                "Marisol"));
        quizModalArrayList.add(new QuizModal(6, questionString,
                "https://cope-cdnmed.agilecontent.com/resources/jpg/8/5/1580390341658.jpg",
                "Paco Rabal", "Nino Bravo", "Raphael",
                "Raphael"));
        quizModalArrayList.add(new QuizModal(7, questionString,
                "https://www.todomusica.org/imagenes/julio_iglesias/biografia-julio-iglesias-1.jpg",
                "Camilo Sesto", "Julio Iglesias", "Peret",
                "Julio Iglesias"));
        quizModalArrayList.add(new QuizModal(8, questionString,
                "https://sabemos.es/wp-content/uploads/2016/05/alfredo-landa.jpg",
                "Tony LeBlanc", "Alfredo Landa", "Antonio Ozores",
                "Alfredo Landa"));
        quizModalArrayList.add(new QuizModal(9, questionString,
                "https://static.dw.com/image/17428830_303.jpg",
                "Pelé", "DiStefano", "Maradona",
                "Pelé"));
        quizModalArrayList.add(new QuizModal(10, questionString,
                "https://www.biografiasyvidas.com/biografia/g/fotos/gonzalez_felipe.jpg",
                "Felipe González", "Pedro Sánchez", "Adolfo Suárez",
                "Felipe González"));
        quizModalArrayList.add(new QuizModal(11, questionString,
                "https://static1.abc.es/media/estilo/2018/06/01/rocio-jurado-kZcB--620x349@abc.jpg",
                "Rocío Jurado", "Isabel Pantoja", "Rocío Durcal",
                "Rocío Jurado"));
        quizModalArrayList.add(new QuizModal(12, questionString,
                "https://cope-cdnmed.agilecontent.com/resources/jpg/7/3/1634118966237.jpg",
                "Isabel Pantoja", "Carmen Sevilla", "Rocío Durcal",
                "Carmen Sevilla"));
        quizModalArrayList.add(new QuizModal(13, questionString,
                "https://ep00.epimg.net/elpais/imagenes/2016/09/16/album/1474019283_425552_1474021177_album_normal.jpg",
                "Antonio Ozores", "Camilo Sesto", "Peret",
                "Camilo Sesto"));
        quizModalArrayList.add(new QuizModal(14, questionString,
                "https://i.ytimg.com/vi/gcW8YeANiLc/mqdefault.jpg",
                "Carmen Sevilla", "Florinda Chico", "Gracita Morales",
                "Gracita Morales"));
        quizModalArrayList.add(new QuizModal(15, questionString,
                "https://www.lecturas.com/medio/2016/07/27/primeros-anos_41ec122f.jpg",
                "Marisol", "Lola Flores", "Isabel Pantoja",
                "Isabel Pantoja"));
        quizModalArrayList.add(new QuizModal(16, questionString,
                "https://i.pinimg.com/736x/03/c2/fb/03c2fbb48091d247aa6e0dc20f1afda7.jpg",
                "José Orjas", "Paco Rabal", "Antonio Ozores",
                "José Orjas"));
        quizModalArrayList.add(new QuizModal(17, questionString,
                "https://s.libertaddigital.com/fotos/noticias/1920/1080/fit/nino-bravo-la-casa-azul.jpg",
                "Julio Iglesias", "Nino Bravo", "Felipe González",
                "Nino Bravo"));
        quizModalArrayList.add(new QuizModal(18, questionString,
                "https://www.canalsur.es/resources/archivos_offline/2016/11/24/1479982089469legadopacorabal.jpg",
                "Pelé", "Paco Rabal", "Manolo Escobar",
                "Paco Rabal"));
        quizModalArrayList.add(new QuizModal(19, questionString,
                "https://media.revistavanityfair.es/photos/60e8432a4f9acb6a2f6dd84c/master/w_1600%2Cc_limit/158089.jpg",
                "Peret", "El Fary", "Tony Leblanc",
                "Peret"));
        quizModalArrayList.add(new QuizModal(20, questionString,
                "https://www.semana.es/wp-content/uploads/2020/10/destacada-20-1068x712.jpg",
                "Rocío Durcal", "Concha Velasco", "Gracita Morales",
                "Rocío Durcal"));
        quizModalArrayList.add(new QuizModal(21, questionString,
                "https://www.experienciademarcagt.com/michelleoquendo/wp-content/uploads/2016/01/sara_montiel.jpg",
                "Marujita Díaz", "Sara Montiel", "Carmen Sevilla",
                "Sara Montiel"));
        quizModalArrayList.add(new QuizModal(22, questionString,
                "https://www.biografiasyvidas.com/biografia/l/fotos/leblanc.jpg",
                "Tony Leblanc", "Manolo Escobar", "DiStefano",
                "Tony Leblanc"));
        quizModalArrayList.add(new QuizModal(23, questionString,
                "https://www.biografiasyvidas.com/biografia/p/fotos/presley_elvis_1.jpg",
                "Matias Prats", "Elvis Presley", "Manolo Escobar",
                "Elvis Presley"));
        quizModalArrayList.add(new QuizModal(24, questionString,
                "https://images11.eitb.eus/multimedia/images/2014/03/21/1339442/Suarez_hauteskundeak_efe_foto610x342.jpg",
                "Felipe González", "Adolfo Suárez", "Pablo Picasso",
                "Adolfo Suárez"));
        quizModalArrayList.add(new QuizModal(25, questionString,
                "https://elcorreoweb.es/binrepository/676x420/0c20/675d400/none/10703/YWNM/quien-fue-audrey-hepburn-foto_20561244_20210111171223.jpg",
                "Sophia Loren", "Audrey Hepburn", "Isabel Pantoja",
                "Audrey Hepburn"));
        quizModalArrayList.add(new QuizModal(26, questionString,
                "https://www.larazon.es/resizer/LdnKWbEUDyowBnUJurYhwsm1Ups=/600x400/filters:focal(830x460:840x450):format(jpg)/cloudfront-eu-central-1.images.arcpublishing.com/larazon/5FSH4EDD35F3DJCX6U3BCWLRWI.jpg",
                "Carmen Ordoñez", "Marilyn Monroe", "Marujita Díaz",
                "Carmen Ordoñez"));
        quizModalArrayList.add(new QuizModal(27, questionString,
                "https://tuespacioujmd.com/wp-content/uploads/2021/04/charles-Chaplin.jpg",
                "Charles Chaplin", "Robert Redford", "Antonio Ozores",
                "Charles Chaplin"));
        quizModalArrayList.add(new QuizModal(28, questionString,
                "https://static.kino.de/wp-content/uploads/2015/05/charlton-heston-bild-rct480x400u.jpg",
                "Matias Prats", "Adolfo Suárez", "Charlton Heston",
                "Charlton Heston"));
        quizModalArrayList.add(new QuizModal(29, questionString,
                "https://cdn.businessinsider.es/sites/navi.axelspringer.es/public/styles/950/public/media/image/2021/12/retrato-salvador-dali-2556951.jpg?itok=6RGltgQN",
                "Maradona", "Dalí", "Martes y Trece",
                "Dalí"));
        quizModalArrayList.add(new QuizModal(30, questionString,
                "https://2015.800noticias.com/cms/wp-content/uploads/2015/04/joaquinsabina_87.jpg",
                "Joaquín Sabina", "Maradona", "Manolo Santana",
                "Joaquín Sabina"));
        quizModalArrayList.add(new QuizModal(31, questionString,
                "https://www.radiopanamericana.com/j/images/galerias/2014-01-27_-1390839166.jpg",
                "Juan Pablo II", "Joaquin Sabina", "Tony Leblanc",
                "Juan Pablo II"));
        quizModalArrayList.add(new QuizModal(32, questionString,
                "https://haztedelalatina.com/wp-content/uploads/2019/04/55d5a97629c3e.jpg",
                "Lola Flores", "Sara Montiel", "Lina Morgan",
                "Lina Morgan"));
        quizModalArrayList.add(new QuizModal(33, questionString,
                "https://www.iberoshow.com.es/u/fotografias/m/2021/11/10/f608x342-32248_61971_0.jpeg",
                "Rocío Jurado", "Lola Herrera", "Carmen Sevilla",
                "Lola Herrera"));
        quizModalArrayList.add(new QuizModal(34, questionString,
                "https://imagenes.20minutos.es/files/image_656_370/files/fp/uploads/imagenes/2021/12/11/manolo-santana.r_d.2504-3414.jpeg",
                "José Orjas", "Charlton Heston", "Manolo Santana",
                "Manolo Santana"));
        quizModalArrayList.add(new QuizModal(35, questionString,
                "https://www.radiolavoz.com.ar/u/fotografias/m/2020/12/31/f608x342-15735_45458_0.jpg",
                "Maradona", "Juan Pablo II", "Pelé",
                "Maradona"));
        quizModalArrayList.add(new QuizModal(36, questionString,
                "https://noticiasargentinas.com/images/NA/Contenidos/eca09cebaaa9-ec9786ec9d8c-134.jpg",
                "Marilyn Monroe", "Isabel Pantoja", "Madonna",
                "Marilyn Monroe"));
        quizModalArrayList.add(new QuizModal(37, questionString,
                "https://www.inmsol.com/wp-content/uploads/2019/08/Martes-13.jpg",
                "The Beatles", "Martes y 13", "Duo Dinámico",
                "Martes y 13"));
        quizModalArrayList.add(new QuizModal(38, questionString,
                "https://upload.wikimedia.org/wikipedia/commons/3/3c/Nieves_Herrero.jpg",
                "Nieves Herrero", "Carmen Ordoñez", "Lina Morgan",
                "Nieves Herrero"));
        quizModalArrayList.add(new QuizModal(39, questionString,
                "https://www.lookoutpro.com/wp-content/uploads/2018/05/picasso.jpg",
                "Elvis Presley", "Dalí", "Pablo Picasso",
                "Pablo Picasso"));
        quizModalArrayList.add(new QuizModal(40, questionString,
                "https://www.xlsemanal.com/wp-content/uploads/sites/3/2018/09/Sophia-Loren-con-fondo-degradado-1024x640.jpg",
                "Sophia Loren", "Sara Montiel", "Audrey Hepburn",
                "Sophia Loren"));
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
                .setMessage(Html.fromHtml("<big>¿Seguro que quieres dejar de jugar?</big>",
                        Html.FROM_HTML_MODE_LEGACY))
                .setPositiveButton(Html.fromHtml("<big><font color='#99173C'>Salir</font></big>",
                        Html.FROM_HTML_MODE_LEGACY) , (dialogInterface, i) -> startActivity(intent))
                .setNegativeButton(Html.fromHtml("<big><font color='#EA596E'> Jugar </font></big>",
                        Html.FROM_HTML_MODE_LEGACY), null)
                .create();
        dialog.show();
    }

    private void activateButtonListeners(){
        option1Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase()
                    .equalsIgnoreCase(option1Btn.getText().toString().trim())){
                currentScore++;
            }
            setButtonFunction();
        });

        option2Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase()
                    .equalsIgnoreCase(option2Btn.getText().toString().trim())){
                currentScore++;
            }
            setButtonFunction();
        });

        option3Btn.setOnClickListener(view -> {
            if(quizModalArrayList.get(currentPos).getAnswer().trim().toLowerCase()
                    .equalsIgnoreCase(option3Btn.getText().toString().trim())){
                currentScore++;
            }
            setButtonFunction();

        });
    }

    private void setButtonFunction(){
        questionsOutOfTen++;
        currentPos = checkingNoRepeatsInRandom(randomNumbers);
        setDataToViews(currentPos);
    }

}