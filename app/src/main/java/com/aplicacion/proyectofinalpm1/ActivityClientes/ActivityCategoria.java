package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityCategoria extends AppCompatActivity {

    Button btnvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);


        btnvolver = (Button) findViewById(R.id.btnVolverM);
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCategoria.this, ActivityMenu.class);
                startActivity(intent);
            }
        });
    }
}