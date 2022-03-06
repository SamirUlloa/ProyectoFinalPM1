package com.aplicacion.proyectofinalpm1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMenu extends AppCompatActivity {

    Button btnCerrarSesion;
    TextView tvMenuUsuario;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        tvMenuUsuario = (TextView) findViewById(R.id.tvMenuUser);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(ActivityMenu.this, ActivityLogin.class));
                finish();
            }
        });
        //Extrae de la BD el identificador del usuario logueado
        tipoUsuario();
    }

    private void tipoUsuario(){

        String id = mAuth.getCurrentUser().getUid();

        //Evalua los usuarios clientes en la BD
        mDatabase.child("usuarios").child("clientes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    tvMenuUsuario.setText("Bienvenido: "+ nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}