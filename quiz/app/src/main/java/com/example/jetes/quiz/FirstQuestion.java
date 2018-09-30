package com.example.jetes.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FirstQuestion extends AppCompatActivity {
    RadioGroup rg;
    RadioButton rb;
    Spinner sp;
    ArrayAdapter<CharSequence> adap;
    int pregunta;
    int puntuacion;
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
    }


    public void continuar(View v){
        if (pregunta == 0){
            int radiobuttonid = rg.getCheckedRadioButtonId();
            rb = (RadioButton) findViewById(radiobuttonid);
            String nombre = rb.getText().toString();//getResources().getResourceEntryName(radiobuttonid);
            if(nombre.equals("Lanrzar Telarañas")  ) {
                puntuacion += 3;
                Toast toast = Toast.makeText(this, "¡Has acertado! Tu puntuación aumenta en 3", Toast.LENGTH_LONG);
                TextView viewVar = (TextView) toast.getView().findViewById(android.R.id.message);
                viewVar.setTextSize(25);
                viewVar.setTextColor(Color.BLUE);
                toast.show();
                //Toast.makeText(getBaseContext(),"¡Has acertado! Tu puntuación aumenta en 3",Toast.LENGTH_LONG).show();
            }
            else{
                puntuacion -=2;
                if (puntuacion <0 )     puntuacion = 0;
                Toast toast = Toast.makeText(this, "Has fallado. Tu puntuación se reduce en 2", Toast.LENGTH_LONG);
                TextView viewVar = (TextView) toast.getView().findViewById(android.R.id.message);
                viewVar.setTextSize(25);
                viewVar.setTextColor(Color.RED);
                toast.show();
                //Toast.makeText(getBaseContext(),"Has fallado. Tu puntuación se reduce en 2",Toast.LENGTH_LONG).show();
            }


        }
        if (pregunta==1){
            String text = sp.getSelectedItem().toString();
            if(text.equals("Electro")  ) {
                puntuacion += 3;
                Toast toast = Toast.makeText(this, "¡Has acertado! Tu puntuación aumenta en 3", Toast.LENGTH_LONG);
                TextView viewVar = (TextView) toast.getView().findViewById(android.R.id.message);
                viewVar.setTextSize(25);
                viewVar.setTextColor(Color.BLUE);
                toast.show();
                //Toast.makeText(getBaseContext(),"¡Has acertado! Tu puntuación aumenta en 3",Toast.LENGTH_LONG).show();
            }
            else{
                puntuacion -=2;
                if (puntuacion <0 )     puntuacion = 0;
                Toast toast = Toast.makeText(this, "Has fallado. Tu puntuación se reduce en 2", Toast.LENGTH_LONG);
                TextView viewVar = (TextView) toast.getView().findViewById(android.R.id.message);
                viewVar.setTextSize(25);
                viewVar.setTextColor(Color.RED);
                toast.show();
                //Toast.makeText(getBaseContext(),"Has fallado. Tu puntuación se reduce en 2",Toast.LENGTH_LONG).show();
            }
        }
        View b = findViewById(R.id.button);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.button2);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.button3);
        b.setVisibility(View.VISIBLE);
        //Comprobar si acierto
        //Mostrar acierto o fallo
        //Incrementar puntuacion
        //Mostrar botones de continuar o volver en caso de fallo
    }
    public void startAgain(View v){
        startActivity(new Intent(FirstQuestion.this, MainActivity.class));
    }

    public void nextQuestion(View v){
        pregunta++;
        setQuestion();
    }

    private void setQuestion(){
        View b = findViewById(R.id.button);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.button3);
        b.setVisibility(View.GONE);
        if (pregunta == 1){
            TextView myTextView = (TextView)findViewById(R.id.textView2);
            myTextView.setText("¿Cuál es el nombre del siguiente villano?");
            b = findViewById(R.id.question0);
            b.setVisibility(View.GONE);
            b = findViewById(R.id.question1);
            b.setVisibility(View.VISIBLE);

        }
    }

}
