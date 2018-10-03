package com.example.jetes.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Puntuation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuation);
        int punt = getIntent().getIntExtra("PUNTUATION", 0);
        View v = findViewById(R.id.puntText);
        TextView t= (TextView) v;
        t.setText("Puntuacion: " + punt);
    }

    public void goBack(View v){
        startActivity(new Intent(Puntuation.this, MainActivity.class));
    }
}
