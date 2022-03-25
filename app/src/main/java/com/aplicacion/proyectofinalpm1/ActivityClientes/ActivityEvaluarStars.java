package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityEvaluarStars extends AppCompatActivity {

    private RatingBar ratingBar;
    Button btnEvaluar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar_stars);


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnEvaluar = (Button) findViewById(R.id.btnEvaluarS);

       /* ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                Toast.makeText( ActivityEvaluarStars.this,"Calificacion "+rating,Toast.LENGTH_LONG).show();
            }
        });*/
        btnEvaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estrella = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext() ,estrella + " Gracias por Evaluarnos!!"
                        ,Toast.LENGTH_SHORT).show();
            }
        });


    }
}