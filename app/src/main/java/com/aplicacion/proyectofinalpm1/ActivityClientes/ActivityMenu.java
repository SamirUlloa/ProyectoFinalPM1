package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aplicacion.proyectofinalpm1.ActivityControl.ActivityLogin;
import com.aplicacion.proyectofinalpm1.ActivityRepartidor.MapsActivity;
import com.aplicacion.proyectofinalpm1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMenu extends AppCompatActivity {

    Button btnCerrarSesion;
    LinearLayout btnPerfilClientes,btnCategorias,btnAcercaDe,btnCarrito, btndash, btnubica;
    TextView tvMenuUsuario;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnPerfilClientes = (LinearLayout) findViewById(R.id.btnPerfilCliente);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        tvMenuUsuario = (TextView) findViewById(R.id.tvMenuUser);

        //Extrae de la BD el identificador del usuario logueado
        tipoUsuario();

        //Permisos para obtener ubicación

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION));

            else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }


        //

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(ActivityMenu.this, ActivityLogin.class));
                finish();
            }
        });

        //Boton que lleva al perfil del cliente
        btnPerfilClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, ActivityPerfilCliente.class));
                //finish();
            }
        });

        btnCategorias = (LinearLayout) findViewById(R.id.btnCategoria);
        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, ActivityCategoria.class));
                //finish();
            }
        });

        btnubica = (LinearLayout) findViewById(R.id.btnUbicacion);
        btnubica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, MapsssActivity.class));
                //finish();
            }
        });

        btndash = (LinearLayout) findViewById(R.id.btnDashb);
        btndash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, ActivityDashboard.class));
                //finish();
            }
        });


        btnAcercaDe = (LinearLayout) findViewById(R.id.btnAcercaD);
        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, ActivityAcercaD.class));
                //finish();
            }
        });

        btnCarrito = (LinearLayout) findViewById(R.id.btnVerCarrito);
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, ActivityCarritoCompras.class));
                //finish();
            }
        });


    }

    private void tipoUsuario(){

        String id = mAuth.getCurrentUser().getUid();

        //Evalua los usuarios clientes en la BD
        mDatabase.child("usuarios").child("clientes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String apellido = dataSnapshot.child("apellido").getValue().toString();
                    tvMenuUsuario.setText("Bienvenido: "+ nombre+" "+apellido);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}