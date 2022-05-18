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

        String gameChosen = this.getIntent().getExtras().getString("Tipo de juego");

        question = findViewById(R.id.idQuestion);
        questionNumber = findViewById(R.id.idQuestionAttempted);
        imageQuestion = findViewById(R.id.imageQuestion);
        option1Btn = findViewById(R.id.option1);
        option2Btn = findViewById(R.id.option2);
        option3Btn = findViewById(R.id.option3);

        random = new Random();
        quizModalArrayList = new ArrayList<>();

        if (gameChosen.equals("pesonajes conocidos")){
            getFamousPeopleQuizQuestions(quizModalArrayList);
        }

        if (gameChosen.equals("cultura española")){
            getSpanishCultureQuestions(quizModalArrayList);
        }

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

        Button goToChooseGame = bottomSheetView.findViewById(R.id.changeGames);
        goToChooseGame.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(this, QuizzTypesActivity.class);
            startActivity(intent);

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
                    .into(imageQuestion);

            option1Btn.setText(quizModalArrayList.get(pos).getOption1());
            option2Btn.setText(quizModalArrayList.get(pos).getOption2());
            option3Btn.setText(quizModalArrayList.get(pos).getOption3());
        }
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


    private void getFamousPeopleQuizQuestions(ArrayList<QuizModal> quizModalArrayList) {

        String questionString = "¿Reconoces al personaje de la foto?";

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

    private void getSpanishCultureQuestions(ArrayList<QuizModal> quizModalArrayList) {

        quizModalArrayList.add(new QuizModal(1, "¿Cómo se llama esta escultura?",
                "https://s1.eestatic.com/2021/02/26/actualidad/561956096_217960834_1706x960.jpg",
                "Dama de Elche", "Princesa Leia", "Reina Sofía",
                "Dama de Elche"));
        quizModalArrayList.add(new QuizModal(2, "¿En dónde está este acueducto?",
                "https://upload.wikimedia.org/wikipedia/commons/1/10/Aqueduct_of_Segovia_02.jpg",
                "Málaga", "Barcelona", "Segovia",
                "Segovia"));
        quizModalArrayList.add(new QuizModal(3, "Se encuentra en Córdoba, ¿pero sabes qué es?",
                "https://guiandalucia.es/wp-content/uploads/2017/08/mezquita-de-cordoba-compress.jpg",
                "La mezquita", "La catedral", "El Retiro",
                "La mezquita"));
        quizModalArrayList.add(new QuizModal(4, "¿Sabes como se llama esta obra de Gaudí?",
                "https://media.viajando.travel/p/1c721736b71f2851975bda82c7466530/adjuntos/236/imagenes/000/295/0000295167/1200x1200/smart/barcelona-sagrada-familiajpg.jpg",
                "Parque Güell", "Sagrada Familia", "Casa Milá",
                "Sagrada Familia"));
        quizModalArrayList.add(new QuizModal(5, "Es el plato valenciano por excelencia...",
                "https://imagenes.elpais.com/resizer/xcLqbhSkGSuSZi4k4cCNGwRERfA=/1960x1470/cloudfront-eu-central-1.images.arcpublishing.com/prisa/XD23OW56EVCGPKA3KDAYFZ6IOU.jpg",
                "Pasta", "Paella", "Fabada",
                "Paella"));
        quizModalArrayList.add(new QuizModal(6, "¿De qué ciudad es emblema el oso y el madroño?",
                "https://www.que.madrid/wp-content/uploads/2021/09/Madrid_-_El_Oso_y_el_Madrono.jpg",
                "Sevilla", "Badajoz", "Madrid",
                "Madrid"));
        quizModalArrayList.add(new QuizModal(7, "¿Cómo se llama este cuadro?",
                "https://i0.wp.com/www.archivoshistoria.com/wp-content/uploads/2016/10/personajes-en-las-meninas-de-velc3a1zquez.jpg?fit=4724%2C2675&ssl=1",
                "Las meninas", "Las chiquitinas", "Las pequeñajas",
                "Las meninas"));
        quizModalArrayList.add(new QuizModal(8, "¿Qué nombre recibe este cuadro?",
                "https://static5.museoreinasofia.es/sites/default/files/obras/DE00050.jpg",
                "Londres", "Guernica", "La batalla",
                "Guernica"));
        quizModalArrayList.add(new QuizModal(9, "¿Cuántas comunidades autónomas tiene España?",
                "https://www.miteco.gob.es/es/biodiversidad/servicios/banco-datos-naturaleza/mapaespana_tcm30-208520.jpg",
                "3", "15", "17",
                "17"));
        quizModalArrayList.add(new QuizModal(10, "¿Dónde se celebra la feria de abril?",
                "https://www.visitasevilla.es/sites/default/files/styles/card_extended_page/public/extended_page/img_card_right/feria-de-abril-bloque-1.jpg?itok=83C_2NQm",
                "Sevilla", "Valencia", "Bilbao",
                "Sevilla"));
        quizModalArrayList.add(new QuizModal(11, "¿Dónde se celebran las Fallas?",
                "https://cloudfront-eu-central-1.images.arcpublishing.com/prisaradio/ZMGNJNALLFIKDBXP5Q6T4DJ73A.jpg",
                "Bilbao", "Santander", "Valencia",
                "Valencia"));
        quizModalArrayList.add(new QuizModal(12, "¿Qué animal es?",
                "https://estaticos-cdn.prensaiberica.es/clip/0500009b-f29c-4216-b84e-4b52d835a00c_16-9-aspect-ratio_default_0.jpg",
                "Lince ibérico", "Gato pardo", "Gato español",
                "Lince ibérico"));
        quizModalArrayList.add(new QuizModal(13, "¿Cómo se llama esta famosa plaza de Sevilla?",
                "https://elviajerofeliz.com/wp-content/uploads/2016/02/La-Plaza-de-España-de-Sevilla-historia-belleza-e-inspiración.jpg",
                "Plaza Grande", "Plaza de España", "La Española",
                "Plaza de España"));
        quizModalArrayList.add(new QuizModal(14, "¿Cómo se llama la región de España que es más famosa por sus vinos?",
                "https://www.opinion.com.bo/asset/thumbnail,992,558,center,center//media/opinion/images/2020/12/13/2020121305164493141.jpg",
                "Rioja", "Albariño", "Tinto",
                "Rioja"));
        quizModalArrayList.add(new QuizModal(15, "¿Dönde es típico comer un bocata de calamares?",
                "https://i.blogs.es/6ac7ba/bocadillo-de-calamares-dap/840_560.jpg",
                "Oviedo", "Mérida", "Madrid",
                "Madrid"));
        quizModalArrayList.add(new QuizModal(16, "Para visitar este peñón te hará falta el pasaporte",
                "https://media.traveler.es/photos/613771e286b46eac7cf5a78f/16:9/w_2000,h_1125,c_limit/124789.jpg",
                "Perejil", "Gibraltar", "Peñón Grande",
                "Gibraltar"));
        quizModalArrayList.add(new QuizModal(17, "Estas islas son volcánicas",
                "https://medioambienteynatural.files.wordpress.com/2015/05/islascanarias.gif",
                "Baleares", "Canarias", "Perejiles",
                "Canarias"));
        quizModalArrayList.add(new QuizModal(18, "Es el volcán más famoso del país",
                "https://www.barcelo.com/guia-turismo/wp-content/uploads/2019/08/parque-nacional-teide.jpg",
                "Teide", "Teneguía", "Croscat",
                "Teide"));
        quizModalArrayList.add(new QuizModal(19, "Si ves este símbolo por todos lados, estás haciendo el camino de...",
                "https://cdn.businessinsider.es/sites/navi.axelspringer.es/public/styles/bi_570/public/media/image/2021/07/camino-santiago-2403907.jpg?itok=7QIFDUf9",
                "Jerez", "Almería", "Santiago",
                "Santiago"));
        quizModalArrayList.add(new QuizModal(20, "¿Cómo se llama esta fiesta?",
               "https://static.dw.com/image/49520473_303.jpg",
                "San Isidro", "La pilarica", "San Fermines",
                "San Fermines"));
        quizModalArrayList.add(new QuizModal(21, "¿Cómo se les llama a las bailarinas en el flamenco?",
                "https://turismo.chiclana.es/fileadmin/_processed_/e/d/csm_flamenco_a941f07216.jpg",
                "Bailarinas", "Bailaoras", "Bailongas",
                "Bailaoras"));
        quizModalArrayList.add(new QuizModal(22, "Nombre de este instrumento que tiene por apellido 'española'",
                "https://www.musisol.com/blog/wp-content/uploads/2021/09/screen-0.jpg",
                "Ukelele", "Castañuelas", "Guitarra",
                "Guitarra"));
        quizModalArrayList.add(new QuizModal(23, "Su nombre completo es mantón de... ",
                "https://zapatosdebaileflamenco.com/blog/wp-content/uploads/2016/07/mantonnegro5.jpg",
                "Manila", "Zaragoza", "Cádiz",
                "Manila"));
        quizModalArrayList.add(new QuizModal(24, "El monumento más famoso de Granada",
                "https://www.granadahoy.com/2021/07/13/sociedad/Alhambra-Granada_1591951619_141281322_1200x675.jpg",
                "La Alhambra", "El alcazar", "La Almudena",
                "La Alhambra"));
        quizModalArrayList.add(new QuizModal(25, "¿Dónde están estas casas colgadas?",
                "https://images.squarespace-cdn.com/content/v1/5a86b05bcf81e0af04936cc7/1629980631052-IPATG53ZYZ0NKKAT3HQX/monumentos-mas-importantes-espana-cuenca.jpg?format=1000w",
                "Aranjuez", "Huesca", "Cuenca",
                "Cuenca"));
        quizModalArrayList.add(new QuizModal(26, "¿Dónde está la famosa Plaza de Toros de Las Ventas?",
                "https://www.las-ventas.com/images/info-1.jpg",
                "Madrid", "Alicante", "Las Palmas",
                "Madrid"));
        quizModalArrayList.add(new QuizModal(27, "Si vamos a la playa de la Concha, ¿en qué ciudad estamos?",
                "https://images.squarespace-cdn.com/content/v1/5a86b05bcf81e0af04936cc7/1614809951543-AM1B23Z6998ZNT974NZY/que-ver-en-espana-concha.jpg?format=1000w",
                "Bilbao", "San Sebastián", "Huelva",
                "San Sebastián"));
        quizModalArrayList.add(new QuizModal(28, "Estas cuevas son famosas por sus pinturas rupestres",
                "https://images.squarespace-cdn.com/content/v1/5a86b05bcf81e0af04936cc7/1614811105442-IPQX3M3Y7CHGG30KEI8S/que-ver-en-espana-altamira.jpg?format=1000w",
                "Altamira", "Águila", "Oscuras",
                "Altamira"));
        quizModalArrayList.add(new QuizModal(29, "¿Qué famoso hidalgo lucha contra molinos de viento?",
                "https://www.65ymas.com/uploads/s1/41/29/66/bigstock-windmills-in-consuegra-toledo-371865850_5_1242x621.jpeg",
                "Don Rodrigo", "El Cid", "Don Quijote",
                "Don Quijote"));
        quizModalArrayList.add(new QuizModal(30, "Caminito cerca de Málaga con nombre en honor a Alfonso XIII",
                "https://elcorreoweb.es/binrepository/675x400/0c0/0d0/none/10703/HCIS/caminito-del-rey_20751873_20211216212840.jpg",
                "Del Rey", "Del Príncipe", "De Alfonso",
                "Del Rey"));
        quizModalArrayList.add(new QuizModal(31, "Es un marisco típico de Galicia",
                "https://static.elnortedecastilla.es/www/multimedia/202005/28/media/cortadas/percebe-RAz2oGksHUHGUXCORvvWUMP-624x385@El%20Norte.jpg",
                "Gambones", "Almejas", "Percebes",
                "Percebes"));
        quizModalArrayList.add(new QuizModal(32, "En San Juan se celebra el inicio de una estación del año. ¿Cuál?",
                "https://viajes.nationalgeographic.com.es/medio/2013/06/17/fuegos-artificiales_1024x683.jpg",
                "Invierno", "Primavera", "Verano",
                "Verano"));
        quizModalArrayList.add(new QuizModal(33, "Fiesta típica catalana en que se regala una rosa y un libro",
                "https://auctemcol.org/wp-content/uploads/2020/04/sant-jordi-1020x638.jpg",
                "San Rafael", "San Jordi", "San Isidro",
                "San Jordi"));
        quizModalArrayList.add(new QuizModal(34, "¿Como se llama a los que cargan los pasos de Semana Santa?",
                "https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2022/04/05/16491504989085.jpg",
                "Jornaleros", "Costaleros", "Santeros",
                "Costaleros"));
        quizModalArrayList.add(new QuizModal(35, "¿Cuándo se celebra el día de la Constitución?",
                "https://www.lavanguardia.com/files/og_thumbnail/uploads/2019/12/05/5fa52d3dcc549.jpeg",
                "6 de diciembre", "7 de julio", "30 de noviembre",
                "6 de diciembre"));
        quizModalArrayList.add(new QuizModal(36, "Si dices que alguien 'está como una cabra', ¿qué quieres decir?",
                "https://www.lavanguardia.com/files/og_thumbnail/uploads/2019/07/10/5fa53b26e9f2d.jpeg",
                "Es inteligente", "Es elegante", "Está loco",
                "Está loco"));
        quizModalArrayList.add(new QuizModal(37, "¿De qué famosas bodegas son estos toros?",
                "https://www.losreplicantes.com/images/articulos/12000/12617/s5.jpg",
                "Osborne", "Tío Pepe", "Ballantines",
                "Osborne"));
        quizModalArrayList.add(new QuizModal(38, "En sus desiertos se han grabado muchas películas del Oeste",
                "https://media.clubrural.com/Catalogador/imgblog/uploads/EScenarios-Tabernas.jpg",
                "Logroño", "León", "Almería",
                "Almería"));
        quizModalArrayList.add(new QuizModal(39, "¿Qué medio de transporte NO cogerías para ir de Valencia a Mallorca?",
                "https://images.ecestaticos.com/dDcM4JTgh2_fCAC1_TYM8XdAXUI=/0x0:2120x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2F932%2F065%2Fec4%2F932065ec404dd7acc44ec6cd04ed1170.jpg",
                "Coche", "Barco", "Avión",
                "Coche"));
        quizModalArrayList.add(new QuizModal(40, "¿Qué tipo de oso es?",
                "https://www.elagoradiario.com/wp-content/uploads/2019/08/Oso-Pardo-Ursus-arctos.jpg",
                "Oso pardo", "Oso panda", "Oso amoroso",
                "Oso pardo"));
    }

}