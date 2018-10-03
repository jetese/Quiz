package com.example.jetes.quiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstQuestion extends AppCompatActivity {
    RadioGroup rg;
    RadioButton rb;
    Spinner sp;
    ArrayAdapter<CharSequence> adap;
    int pregunta;
    int puntuacion;

    ImageButton selectedButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);
        rg = (RadioGroup) findViewById(R.id.rgroup);
        pregunta = 0;
        puntuacion = 0;
        sp = (Spinner)findViewById(R.id.spinner);
        adap = ArrayAdapter.createFromResource(this,R.array.bad_guys,android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adap);
        View d = findViewById(R.id.question1);
        d.setVisibility(View.GONE);
        d = findViewById(R.id.question2);
        d.setVisibility(View.GONE);

        d = findViewById(R.id.question3);
        d.setVisibility(View.GONE);
        System.out.println("Creada esta vaina");
    }


    public void continuar(View v){
        boolean acceptChange = true;
        if (pregunta == 0){
            int radiobuttonid = rg.getCheckedRadioButtonId();
            rb = (RadioButton) findViewById(radiobuttonid);
            String nombre = rb.getText().toString();
            if(nombre.equals("Lanzar Telarañas")  ) {
                increasePunt(3);
            }
            else{
                decreasePunt(2);
            }


        }
        if (pregunta==1){
            String text = sp.getSelectedItem().toString();
            if(text.equals("Electro")  ) {
                increasePunt(3);
                //Toast.makeText(getBaseContext(),"¡Has acertado! Tu puntuación aumenta en 3",Toast.LENGTH_LONG).show();
            }
            else{
                decreasePunt(2);
                //Toast.makeText(getBaseContext(),"Has fallado. Tu puntuación se reduce en 2",Toast.LENGTH_LONG).show();
            }
        }
        if(pregunta==2){
            if(selectedButton == null) {
                acceptChange = false;
            }
            else{
                int id = selectedButton.getId();
                if(id!= -1){
                    increasePunt(3);
                }
                else{
                    decreasePunt(2);
                }
            }
        }
        
        if(pregunta ==3){
            System.out.println("Pregunta 3");
            View b;
            b = findViewById(R.id.question3);
            b.setVisibility(View.GONE);
            Button but = (Button) v;
            if(but.getText() == "Tom Cruise"){
                increasePunt(3);
            }
            else{
                decreasePunt(2);
            }
            acceptChange = false;
            nextQuestion(v);
        }

        else if(pregunta == 4){
            System.out.println("Pregunta 4");
            View b;
            b = findViewById(R.id.question3);
            b.setVisibility(View.GONE);
            Button but = (Button) v;
            if(but.getText() == "May"){
                increasePunt(3);
            }
            else{
                decreasePunt(2);
            }
            acceptChange = false;
            nextQuestion(v);
        }

        if(acceptChange) {
            View b = findViewById(R.id.button);
            b.setVisibility(View.GONE);
            b = findViewById(R.id.button2);
            b.setVisibility(View.VISIBLE);
            b = findViewById(R.id.button3);
            b.setVisibility(View.VISIBLE);
        }
        //Comprobar si acierto
        //Mostrar acierto o fallo
        //Incrementar puntuacion
        //Mostrar botones de continuar o volver en caso de fallo
    }

    public void imageClick(View v){
        ImageButton ib = (ImageButton)v;
        if(selectedButton != null){
            selectedButton.setAlpha(0.5f);
            selectedButton = ib;
            selectedButton.setAlpha(1.0f);
        }
        selectedButton = ib;
        selectedButton.setAlpha(1.0f);
    }

    public void startAgain(View v){
        startActivity(new Intent(FirstQuestion.this, MainActivity.class));
    }

    public void nextQuestion(View v){
        pregunta++;
        setQuestion();
    }

    private void setQuestion(){
        System.out.println("La virgen puta");
        View b = findViewById(R.id.button);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.button3);
        b.setVisibility(View.GONE);
        TextView myTextView = (TextView)findViewById(R.id.textView2);
        switch(pregunta){
            case 1:
                myTextView.setText("¿Cuál es el nombre del siguiente villano?");
                b = findViewById(R.id.question0);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.question1);
                b.setVisibility(View.VISIBLE);
                break;
            case 2:
                myTextView.setText("Seleccione el traje clásico de Spiderman");
                b = findViewById(R.id.question1);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.question2);
                b.setVisibility(View.VISIBLE);
                break;
            case 3:
                myTextView.setText("¿Cuál de estos actores no ha interpretado al trepamuros?");
                b = findViewById(R.id.button);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.question2);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.question3);
                b.setVisibility(View.VISIBLE);
                ArrayList<String> texts = new ArrayList<>();
                ArrayList<Integer> images = new ArrayList<>();
                texts.add("Tobey Maguire");
                texts.add("Andrew Garfield");
                texts.add("Tom Cruise");
                texts.add("Tom Holland");

                images.add(R.drawable.tobey);
                images.add(R.drawable.andrew);
                images.add(R.drawable.tomcruise);
                images.add(R.drawable.tom);
                ListView lv = (ListView) findViewById(R.id.listview_question);
                lv.setAdapter(new CustomListView(this, R.layout.list_view, texts, images));
                break;
            case 4:
                myTextView.setText("¿Cuál de las siguientes es la tía de Peter Parker?");
                b = findViewById(R.id.button);
                b.setVisibility(View.GONE);
                b = findViewById(R.id.question3);
                b.setVisibility(View.VISIBLE);
                texts = new ArrayList<>();
                images = new ArrayList<>();
                texts.add("Tomás");
                texts.add("May");
                texts.add("Antonia");
                texts.add("Josefina");

                images.add(R.drawable.viejo);
                images.add(R.drawable.tia_may);
                images.add(R.drawable.vieja2);
                images.add(R.drawable.vieja3);

                lv = (ListView) findViewById(R.id.listview_question);
                lv.setAdapter(new CustomListView(this, R.layout.list_view, texts, images));
                break;
            case 5:
                Intent puntIntent = new Intent(getBaseContext(), Puntuation.class);
                puntIntent.putExtra("PUNTUATION", puntuacion);
                startActivity(puntIntent);
            default:
                System.out.println("Fallo");
        }
    }

    public void increasePunt(int punt){
        puntuacion += punt;
        Toast toast = Toast.makeText(this, "¡Has acertado! Tu puntuación aumenta en 3", Toast.LENGTH_LONG);
        TextView viewVar = (TextView) toast.getView().findViewById(android.R.id.message);
        viewVar.setTextSize(25);
        viewVar.setTextColor(Color.BLUE);
        toast.show();
    }

    public void decreasePunt(int punt){
        puntuacion -=punt;
        if (puntuacion <0 )     puntuacion = 0;
        Toast toast = Toast.makeText(this, "Has fallado. Tu puntuación se reduce en 2", Toast.LENGTH_LONG);
        TextView viewVar = (TextView) toast.getView().findViewById(android.R.id.message);
        viewVar.setTextSize(25);
        viewVar.setTextColor(Color.RED);
        toast.show();
    }

}
