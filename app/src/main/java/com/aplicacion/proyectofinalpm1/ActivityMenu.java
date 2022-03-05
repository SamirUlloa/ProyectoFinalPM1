package com.aplicacion.proyectofinalpm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityMenu extends AppCompatActivity {

    Button btnCerrarSesion, btncategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Imprime el nombre del usuario logueado.
        TextView usuarioRecibido = (TextView) findViewById(R.id.tvUser);
        Intent intent = getIntent();
        usuarioRecibido.setText("Usuario: " + intent.getStringExtra("email"));
        //

        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ActivityMenu.this, "Se Cerro la Sesión", Toast.LENGTH_SHORT).show();
                sesionCerrada();    //Retorna al Login
            }
        });


        btncategoria = (Button) findViewById(R.id.btnCategoria);
        btncategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityCategoria.class);
                startActivity(intent);
            }
        });

    }

    private void sesionCerrada() {
        Intent i = new Intent(ActivityMenu.this, ActivityLogin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}