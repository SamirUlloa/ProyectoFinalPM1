package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aplicacion.proyectofinalpm1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ActivityCarritoCompras extends AppCompatActivity {

    private final int cargaDatos = 400;

    TextView tvSubtotal, tvImpuesto, tvTotal;
    TextView tvNomP1, tvPreP1, tvCantP1, tvSubP1;
    TextView tvNomP2, tvPreP2, tvCantP2, tvSubP2;
    TextView tvNomP3, tvPreP3, tvCantP3, tvSubP3;
    TextView tvNomP4, tvPreP4, tvCantP4, tvSubP4;
    TextView tvNomP5, tvPreP5, tvCantP5, tvSubP5;
    TextView tvNomPdes1, tvNomPdes2, tvNomPdes3, tvNomPdes4, tvNomPdes5;
    TextView tvPrePdes1, tvPrePdes2, tvPrePdes3, tvPrePdes4, tvPrePdes5;
    TextView tvCantPdes1, tvCantPdes2, tvCantPdes3, tvCantPdes4, tvCantPdes5;
    TextView tvSubPdes1, tvSubPdes2, tvSubPdes3, tvSubPdes4, tvSubPdes5;

    ImageView imgP1, imgP2, imgP3, imgP4, imgP5;

    Button btnCerrarP;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String idUsuario = "";
    String idGenerado = "";

    String NomProBebes = "", cantidadPañales = "", precioPañales = "";
    String NomProBebidas = "", cantidadBebidas = "", precioBebidas = "";
    String NomProCarnes = "", cantidadCarnes = "", precioCarnes = "";
    String NomProGranosB = "", cantidadGranosB = "", precioGranosB = "";
    String NomProLacteos = "", cantidadLacteos = "", precioLacteos = "";

    double subtotalBebes = 0, subtotalBebidas = 0, subtotalCarnes = 0, subtotalGranosB = 0, subtotalLacteos = 0, subtotal = 0;
    double impBebes = 0, impBebidas = 0, impCarnes = 0, impGranosB = 0, impLacteos = 0, impuesto = 0;
    double totalBebes = 0, totalBebidas = 0, totalCarnes = 0, totalGranosB = 0, totalLacteos = 0, total = 0;
    double precUBebes = 0, precUBebidas = 0, PrecUCarnes = 0, precGranosB = 0, precULacteos = 0;
    String nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, correoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);

        //Id de usuario
        mAuth = FirebaseAuth.getInstance();
        idUsuario = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Instancia de las cajas de Textos
        instanciarTextos();

        //Limpia las casillas
        limpiar();

        catBebidas();
        catBebes();
        catCarnes();
        catGranosB();
        catLacteos();
        infoUsuario();

        //Carga de Datos
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                impuesto = impBebes + impBebidas + impCarnes + impGranosB + impLacteos;
                subtotal = subtotalBebes + subtotalBebidas + subtotalCarnes + subtotalGranosB + subtotalLacteos;
                total = totalBebes + totalBebidas + totalCarnes + totalGranosB + totalLacteos;

                tvSubtotal.setText(subtotal + " Lps");
                tvImpuesto.setText(impuesto + " Lps");
                tvTotal.setText(total + " Lps");
            }
        },cargaDatos);
        //Termina carga de Datos

        //Boton para realizar la compra
        btnCerrarP = (Button) findViewById(R.id.btnCerrarP);
        btnCerrarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCarritoCompras.this);
                builder.setMessage("¿Desea Realizar el Pedido?")
                        .setTitle("Atención");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realizarPedido();
                        eliminarCarrito();
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void infoUsuario(){
        mDatabase.child("usuarios").child("clientes").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    nombreCliente = dataSnapshot.child("nombre").getValue().toString();
                    apellidoCliente = dataSnapshot.child("apellido").getValue().toString();
                    telefonoCliente = dataSnapshot.child("telefono").getValue().toString();
                    direccionCliente = dataSnapshot.child("direccion").getValue().toString();
                    correoCliente = dataSnapshot.child("correo").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void catBebidas(){
        mDatabase.child("carrito").child(idUsuario).child("catBebidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    cantidadBebidas = dataSnapshot.child("cantidadBebidas").getValue().toString();
                    precioBebidas = dataSnapshot.child("precioBebidas").getValue().toString();
                    NomProBebidas = dataSnapshot.child("NomProBebidas").getValue().toString();
                    String precUBebidas = dataSnapshot.child("precUBebidas").getValue().toString();
                    String imagenURL = dataSnapshot.child("imgUrlBebidas").getValue().toString();

                    String subtota = precioBebidas;
                    subtotalBebidas = Integer.parseInt(subtota);
                    impBebidas = subtotalBebidas * 0.15;
                    totalBebidas = subtotalBebidas + impBebidas;

                    if (tvNomP1.getText().toString().isEmpty()) {
                        tvNomP1.setText(NomProBebidas);
                        tvPreP1.setText(precUBebidas + " Lps");
                        tvCantP1.setText(cantidadBebidas);
                        tvSubP1.setText(precioBebidas + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP1);
                        productoUno();
                    } else if (tvNomP2.getText().toString().isEmpty()) {
                        tvNomP2.setText(NomProBebidas);
                        tvPreP2.setText(precUBebidas + " Lps");
                        tvCantP2.setText(cantidadBebidas);
                        tvSubP2.setText(precioBebidas + ".00 .Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP2);
                        productoDos();
                    } else if (tvNomP3.getText().toString().isEmpty()) {
                        tvNomP3.setText(NomProBebidas);
                        tvPreP3.setText(precUBebidas + " Lps");
                        tvCantP3.setText(cantidadBebidas);
                        tvSubP3.setText(precioBebidas + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP3);
                        productoTres();
                    } else if (tvNomP4.getText().toString().isEmpty()) {
                        tvNomP4.setText(NomProBebidas);
                        tvPreP4.setText(precUBebidas + " Lps");
                        tvCantP4.setText(cantidadBebidas);
                        tvSubP4.setText(precioBebidas + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP4);
                        productoCuatro();
                    } else if (tvNomP5.getText().toString().isEmpty()) {
                        tvNomP5.setText(NomProBebidas);
                        tvPreP5.setText(precUBebidas + " Lps");
                        tvCantP5.setText(cantidadBebidas);
                        tvSubP5.setText(precioBebidas + "00. Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP5);
                        productoCinco();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void catBebes(){
        mDatabase.child("carrito").child(idUsuario).child("catBebes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    cantidadPañales = dataSnapshot.child("cantidadPañales").getValue().toString();
                    precioPañales = dataSnapshot.child("precioPañales").getValue().toString();
                    String precUPañales = dataSnapshot.child("precUPañales").getValue().toString();
                    NomProBebes = dataSnapshot.child("NomProBebes").getValue().toString();
                    String imagenURL = dataSnapshot.child("imgUrlBebes").getValue().toString();

                    String subtota = precioPañales;
                    subtotalBebes = Integer.parseInt(subtota);
                    impBebes = subtotalBebes * 0.15;
                    totalBebes = subtotalBebes + impBebes;

                    if (tvNomP1.getText().toString().isEmpty()) {
                        tvNomP1.setText(NomProBebes);
                        tvPreP1.setText(precUPañales + " Lps");
                        tvCantP1.setText(cantidadPañales);
                        tvSubP1.setText(precioPañales + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP1);
                        productoUno();
                    } else if (tvNomP2.getText().toString().isEmpty()) {
                        tvNomP2.setText(NomProBebes);
                        tvPreP2.setText(precUPañales + " Lps");
                        tvCantP2.setText(cantidadPañales);
                        tvSubP2.setText(precioPañales + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP2);
                        productoDos();
                    } else if (tvNomP3.getText().toString().isEmpty()) {
                        tvNomP3.setText(NomProBebes);
                        tvPreP3.setText(precUPañales + " Lps");
                        tvCantP3.setText(cantidadPañales);
                        tvSubP3.setText(precioPañales + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP3);
                        productoTres();
                    } else if (tvNomP4.getText().toString().isEmpty()) {
                        tvNomP4.setText(NomProBebes);
                        tvPreP4.setText(precUPañales + " Lps");
                        tvCantP4.setText(cantidadPañales);
                        tvSubP4.setText(precioPañales + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP4);
                        productoCuatro();
                    } else if (tvNomP5.getText().toString().isEmpty()) {
                        tvNomP5.setText(NomProBebes);
                        tvPreP5.setText(precUPañales + " Lps");
                        tvCantP5.setText(cantidadPañales);
                        tvSubP5.setText(precioPañales + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP5);
                        productoCinco();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void catCarnes(){
        mDatabase.child("carrito").child(idUsuario).child("catCarnes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    cantidadCarnes = dataSnapshot.child("cantidadCarnes").getValue().toString();
                    precioCarnes = dataSnapshot.child("precioCarnes").getValue().toString();
                    NomProCarnes = dataSnapshot.child("NomProCarnes").getValue().toString();
                    String precUCarnes = dataSnapshot.child("precUCarnes").getValue().toString();
                    String imagenURL = dataSnapshot.child("imgUrlCarnes").getValue().toString();

                    String subtota = precioCarnes;
                    subtotalCarnes = Integer.parseInt(subtota);
                    impCarnes = subtotalCarnes * 0.15;
                    totalCarnes = subtotalCarnes + impCarnes;

                    if (tvNomP1.getText().toString().isEmpty()) {
                        tvNomP1.setText(NomProCarnes);
                        tvPreP1.setText(precUCarnes + " Lps");
                        tvCantP1.setText(cantidadCarnes);
                        tvSubP1.setText(precioCarnes + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP1);
                        productoUno();
                    } else if (tvNomP2.getText().toString().isEmpty()) {
                        tvNomP2.setText(NomProCarnes);
                        tvPreP2.setText(precUCarnes + " Lps");
                        tvCantP2.setText(cantidadCarnes);
                        tvSubP2.setText(precioCarnes + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP2);
                        productoDos();
                    } else if (tvNomP3.getText().toString().isEmpty()) {
                        tvNomP3.setText(NomProCarnes);
                        tvPreP3.setText(precUCarnes + " Lps");
                        tvCantP3.setText(cantidadCarnes);
                        tvSubP3.setText(precioCarnes + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP3);
                        productoTres();
                    } else if (tvNomP4.getText().toString().isEmpty()) {
                        tvNomP4.setText(NomProCarnes);
                        tvPreP4.setText(precUCarnes + " Lps");
                        tvCantP4.setText(cantidadCarnes);
                        tvSubP4.setText(precioCarnes + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP4);
                        productoCuatro();
                    } else if (tvNomP5.getText().toString().isEmpty()) {
                        tvNomP5.setText(NomProCarnes);
                        tvPreP5.setText(precUCarnes + " Lps");
                        tvCantP5.setText(cantidadCarnes);
                        tvSubP5.setText(precioCarnes + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP5);
                        productoCinco();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void catGranosB(){
        mDatabase.child("carrito").child(idUsuario).child("catGranosB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    cantidadGranosB = dataSnapshot.child("cantidadGranosB").getValue().toString();
                    precioGranosB = dataSnapshot.child("precioGranosB").getValue().toString();
                    NomProGranosB = dataSnapshot.child("NomProGranosB").getValue().toString();
                    String precUGranosB = dataSnapshot.child("precUGranosB").getValue().toString();
                    String imagenURL = dataSnapshot.child("imgUrlGranosB").getValue().toString();

                    String subtota = precioGranosB;
                    subtotalGranosB = Integer.parseInt(subtota);
                    impGranosB = subtotalGranosB * 0.15;
                    totalGranosB = subtotalGranosB + impGranosB;

                    if (tvNomP1.getText().toString().isEmpty()) {
                        tvNomP1.setText(NomProGranosB);
                        tvPreP1.setText(precUGranosB + " Lps");
                        tvCantP1.setText(cantidadGranosB);
                        tvSubP1.setText(precioGranosB + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP1);
                        productoUno();
                    } else if (tvNomP2.getText().toString().isEmpty()) {
                        tvNomP2.setText(NomProGranosB);
                        tvPreP2.setText(precUGranosB + " Lps");
                        tvCantP2.setText(cantidadGranosB);
                        tvSubP2.setText(precioGranosB + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP2);
                        productoDos();
                    } else if (tvNomP3.getText().toString().isEmpty()) {
                        tvNomP3.setText(NomProGranosB);
                        tvPreP3.setText(precUGranosB + " Lps");
                        tvCantP3.setText(cantidadGranosB);
                        tvSubP3.setText(precioGranosB + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP3);
                        productoTres();
                    } else if (tvNomP4.getText().toString().isEmpty()) {
                        tvNomP4.setText(NomProGranosB);
                        tvPreP4.setText(precUGranosB + " Lps");
                        tvCantP4.setText(cantidadGranosB);
                        tvSubP4.setText(precioGranosB + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP4);
                        productoCuatro();
                    } else if (tvNomP5.getText().toString().isEmpty()) {
                        tvNomP5.setText(NomProGranosB);
                        tvPreP5.setText(precUGranosB + " Lps");
                        tvCantP5.setText(cantidadGranosB);
                        tvSubP5.setText(precioGranosB + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP5);
                        productoCinco();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void catLacteos(){
        mDatabase.child("carrito").child(idUsuario).child("catLacteos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    cantidadLacteos = dataSnapshot.child("cantidadLacteos").getValue().toString();
                    precioLacteos = dataSnapshot.child("precioLacteos").getValue().toString();
                    NomProLacteos = dataSnapshot.child("NomProLacteos").getValue().toString();
                    String precULacteos = dataSnapshot.child("precULacteos").getValue().toString();
                    String imagenURL = dataSnapshot.child("imgUrlLacteos").getValue().toString();

                    String subtota = precioLacteos;
                    subtotalLacteos = Integer.parseInt(subtota);
                    impLacteos = subtotalLacteos * 0.15;
                    totalLacteos = subtotalLacteos + impLacteos;

                    if (tvNomP1.getText().toString().isEmpty()) {
                        tvNomP1.setText(NomProLacteos);
                        tvPreP1.setText(precULacteos + " Lps");
                        tvCantP1.setText(cantidadLacteos);
                        tvSubP1.setText(precioLacteos + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP1);
                        productoUno();
                    } else if (tvNomP2.getText().toString().isEmpty()) {
                        tvNomP2.setText(NomProLacteos);
                        tvPreP2.setText(precULacteos + " Lps");
                        tvCantP2.setText(cantidadLacteos);
                        tvSubP2.setText(precioLacteos + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP2);
                        productoDos();
                    } else if (tvNomP3.getText().toString().isEmpty()) {
                        tvNomP3.setText(NomProLacteos);
                        tvPreP3.setText(precULacteos + " Lps");
                        tvCantP3.setText(cantidadLacteos);
                        tvSubP3.setText(precioLacteos + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP3);
                        productoTres();
                    } else if (tvNomP4.getText().toString().isEmpty()) {
                        tvNomP4.setText(NomProLacteos);
                        tvPreP4.setText(precULacteos + " Lps");
                        tvCantP4.setText(cantidadLacteos);
                        tvSubP4.setText(precioLacteos + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP4);
                        productoCuatro();
                    } else if (tvNomP5.getText().toString().isEmpty()) {
                        tvNomP5.setText(NomProLacteos);
                        tvPreP5.setText(precULacteos + " Lps");
                        tvCantP5.setText(cantidadLacteos);
                        tvSubP5.setText(precioLacteos + ".00 Lps");
                        Picasso.with(ActivityCarritoCompras.this).load(imagenURL).into(imgP5);
                        productoCinco();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void realizarPedido(){

        idGenerado = mDatabase.push().getKey();

        if (!NomProBebes.isEmpty()) {
            Map<String, Object> pBebes = new HashMap<>();
            pBebes.put("id", idUsuario);
            pBebes.put("NomProBebes", NomProBebes);
            pBebes.put("cantidadPañales", cantidadPañales);
            pBebes.put("precioPañales", precioPañales);
            pBebes.put("precUPañales", "120.00");
            pBebes.put("imgUrlBebes", "https://firebasestorage.googleapis.com/v0/b/appsupermercado-37259.appspot.com/o/img_productos%2Fpaniales.png?alt=media&token=cd9f01a7-1159-49d5-9f2c-5a791793e0c6");
            //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("catBebes").setValue(pBebes);
            mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("catBebes").setValue(pBebes);
        }

        if (!NomProBebidas.isEmpty()) {
            Map<String, Object> pBebidas = new HashMap<>();
            pBebidas.put("NomProBebidas", NomProBebidas);
            pBebidas.put("cantidadBebidas", cantidadBebidas);
            pBebidas.put("precioBebidas", precioBebidas);
            pBebidas.put("precUBebidas", "150.00");
            pBebidas.put("imgUrlBebidas", "https://firebasestorage.googleapis.com/v0/b/appsupermercado-37259.appspot.com/o/img_productos%2FbebidaCoronaC.png?alt=media&token=6805fb9f-3bdd-4541-90c7-95e8a9dcec20");
            mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("catBebidas").setValue(pBebidas);
            //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("catBebidas").setValue(pBebidas);
        }

        if(!NomProCarnes.isEmpty()) {
            Map<String, Object> pCarnes = new HashMap<>();
            pCarnes.put("NomProCarnes", NomProCarnes);
            pCarnes.put("cantidadCarnes", cantidadCarnes);
            pCarnes.put("precioCarnes", precioCarnes);
            pCarnes.put("precUCarnes", "100.00");
            pCarnes.put("imgUrlCarnes", "https://firebasestorage.googleapis.com/v0/b/appsupermercado-37259.appspot.com/o/img_productos%2Fchuleta.jpg?alt=media&token=36715cc1-23ee-43b6-92ab-a6d5a2a258d4");
            mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("catCarnes").setValue(pCarnes);
            //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("catCarnes").setValue(pCarnes);
        }

        if(!NomProGranosB.isEmpty()) {
            Map<String, Object> pGranosB = new HashMap<>();
            pGranosB.put("NomProGranosB", NomProGranosB);
            pGranosB.put("cantidadGranosB", cantidadGranosB);
            pGranosB.put("precioGranosB", precioGranosB);
            pGranosB.put("precUGranosB", "14.00");
            pGranosB.put("imgUrlGranosB", "https://firebasestorage.googleapis.com/v0/b/appsupermercado-37259.appspot.com/o/img_productos%2Farroz.jpg?alt=media&token=8e691cc4-1b32-4cd8-bb6f-7e07e07f6831");
            mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("catGranosB").setValue(pGranosB);
            //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("catGranosB").setValue(pGranosB);
        }

        if (!NomProLacteos.isEmpty()) {
            Map<String, Object> pLacteos = new HashMap<>();
            pLacteos.put("NomProLacteos", NomProLacteos);
            pLacteos.put("cantidadLacteos", cantidadLacteos);
            pLacteos.put("precioLacteos", precioLacteos);
            pLacteos.put("precULacteos", "30.00");
            pLacteos.put("imgUrlLacteos", "https://firebasestorage.googleapis.com/v0/b/appsupermercado-37259.appspot.com/o/img_productos%2FlecheCeteco.png?alt=media&token=7bc5a649-956e-42e7-b00b-adf651115661");
            mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("catLacteos").setValue(pLacteos);
            //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("catLacteos").setValue(pLacteos);
        }

        Map<String, Object> pPedidoInfo = new HashMap<>();
        pPedidoInfo.put("subtotal", subtotal);
        pPedidoInfo.put("impuesto", impuesto);
        pPedidoInfo.put("total", total);
        //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("infoPedido").setValue(pPedidoInfo);
        mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("infoPedido").setValue(pPedidoInfo);

        Map<String, Object> pClienteInfo = new HashMap<>();
        pClienteInfo.put("idCliente", idUsuario);
        pClienteInfo.put("nomCliente", nombreCliente);
        pClienteInfo.put("apeCliente", apellidoCliente);
        pClienteInfo.put("telCliente", telefonoCliente);
        pClienteInfo.put("dirCliente", direccionCliente);
        pClienteInfo.put("corCliente", correoCliente);
        mDatabase.child("pedidos").child("nuevos").child(idGenerado).child("infoCliente").setValue(pClienteInfo);
        //mDatabase.child("pedidos").child("nuevos").child(idUsuario).child("infoCliente").setValue(pClienteInfo);

        registroPedidos();

        Toast.makeText(ActivityCarritoCompras.this, "Pedido Realizado", Toast.LENGTH_LONG).show();
    }

    public void registroPedidos(){
        Map<String, Object> Regpedido = new HashMap<>();
        //Regpedido.put(idUsuario, idUsuario);
        //mDatabase.child("pedidos").child("registroNue").updateChildren(Regpedido);
        Regpedido.put(idGenerado, idGenerado);
        mDatabase.child("pedidos").child("registroNue").updateChildren(Regpedido);

        Map<String, Object> RegpedidoID = new HashMap<>();
        //Regpedido.put(idUsuario, idUsuario);
        //mDatabase.child("pedidos").child("registroNue").updateChildren(Regpedido);
        RegpedidoID.put(idGenerado, idGenerado);
        mDatabase.child("pedidos").child("registroNueID").child(idUsuario).updateChildren(RegpedidoID);
    }

    public void eliminarCarrito(){
        mDatabase.child("carrito").child(idUsuario).removeValue();
    }

    public void instanciarTextos(){
        //Cuadros de Texto
        tvSubtotal = (TextView) findViewById(R.id.tvSubtotal);
        tvImpuesto = (TextView) findViewById(R.id.tvImpuesto);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        tvNomP1 = (TextView) findViewById(R.id.tvNomP1);
        tvNomP2 = (TextView) findViewById(R.id.tvNomP2);
        tvNomP3 = (TextView) findViewById(R.id.tvNomP3);
        tvNomP4 = (TextView) findViewById(R.id.tvNomP4);
        tvNomP5 = (TextView) findViewById(R.id.tvNomP5);

        tvPreP1 = (TextView) findViewById(R.id.tvPreP1);
        tvPreP2 = (TextView) findViewById(R.id.tvPreP2);
        tvPreP3 = (TextView) findViewById(R.id.tvPreP3);
        tvPreP4 = (TextView) findViewById(R.id.tvPreP4);
        tvPreP5 = (TextView) findViewById(R.id.tvPreP5);

        tvCantP1 = (TextView) findViewById(R.id.tvCantP1);
        tvCantP2 = (TextView) findViewById(R.id.tvCantP2);
        tvCantP3 = (TextView) findViewById(R.id.tvCantP3);
        tvCantP4 = (TextView) findViewById(R.id.tvCantP4);
        tvCantP5 = (TextView) findViewById(R.id.tvCantP5);

        tvSubP1 = (TextView) findViewById(R.id.tvSubP1);
        tvSubP2 = (TextView) findViewById(R.id.tvSubP2);
        tvSubP3 = (TextView) findViewById(R.id.tvSubP3);
        tvSubP4 = (TextView) findViewById(R.id.tvSubP4);
        tvSubP5 = (TextView) findViewById(R.id.tvSubP5);

        //Instancia de Imagenes (Productos)
        imgP1 = (ImageView) findViewById(R.id.imgP1);
        imgP2 = (ImageView) findViewById(R.id.imgP2);
        imgP3 = (ImageView) findViewById(R.id.imgP3);
        imgP4 = (ImageView) findViewById(R.id.imgP4);
        imgP5 = (ImageView) findViewById(R.id.imgP5);

        tvNomPdes1 = (TextView) findViewById(R.id.tvNomPdes1);
        tvNomPdes2 = (TextView) findViewById(R.id.tvNomPdes2);
        tvNomPdes3 = (TextView) findViewById(R.id.tvNomPdes3);
        tvNomPdes4 = (TextView) findViewById(R.id.tvNomPdes4);
        tvNomPdes5 = (TextView) findViewById(R.id.tvNomPdes5);

        tvPrePdes1 = (TextView) findViewById(R.id.tvPrePdes1);
        tvPrePdes2 = (TextView) findViewById(R.id.tvPrePdes2);
        tvPrePdes3 = (TextView) findViewById(R.id.tvPrePdes3);
        tvPrePdes4 = (TextView) findViewById(R.id.tvPrePdes4);
        tvPrePdes5 = (TextView) findViewById(R.id.tvPrePdes5);

        tvCantPdes1 = (TextView) findViewById(R.id.tvCantPdes1);
        tvCantPdes2 = (TextView) findViewById(R.id.tvCantPdes2);
        tvCantPdes3 = (TextView) findViewById(R.id.tvCantPdes3);
        tvCantPdes4 = (TextView) findViewById(R.id.tvCantPdes4);
        tvCantPdes5 = (TextView) findViewById(R.id.tvCantPdes5);

        tvSubPdes1 = (TextView) findViewById(R.id.tvSubPdes1);
        tvSubPdes2 = (TextView) findViewById(R.id.tvSubPdes2);
        tvSubPdes3 = (TextView) findViewById(R.id.tvSubPdes3);
        tvSubPdes4 = (TextView) findViewById(R.id.tvSubPdes4);
        tvSubPdes5 = (TextView) findViewById(R.id.tvSubPdes5);
    }

    public void limpiar(){
        instanciarTextos();
        tvNomP1.setVisibility(View.INVISIBLE);
        tvNomP2.setVisibility(View.INVISIBLE);
        tvNomP3.setVisibility(View.INVISIBLE);
        tvNomP4.setVisibility(View.INVISIBLE);
        tvNomP5.setVisibility(View.INVISIBLE);

        tvPreP1.setVisibility(View.INVISIBLE);
        tvPreP2.setVisibility(View.INVISIBLE);
        tvPreP3.setVisibility(View.INVISIBLE);
        tvPreP4.setVisibility(View.INVISIBLE);
        tvPreP5.setVisibility(View.INVISIBLE);

        tvCantP1.setVisibility(View.INVISIBLE);
        tvCantP2.setVisibility(View.INVISIBLE);
        tvCantP3.setVisibility(View.INVISIBLE);
        tvCantP4.setVisibility(View.INVISIBLE);
        tvCantP5.setVisibility(View.INVISIBLE);

        tvSubP1.setVisibility(View.INVISIBLE);
        tvSubP2.setVisibility(View.INVISIBLE);
        tvSubP3.setVisibility(View.INVISIBLE);
        tvSubP4.setVisibility(View.INVISIBLE);
        tvSubP5.setVisibility(View.INVISIBLE);

        tvNomPdes1.setVisibility(View.INVISIBLE);
        tvNomPdes2.setVisibility(View.INVISIBLE);
        tvNomPdes3.setVisibility(View.INVISIBLE);
        tvNomPdes4.setVisibility(View.INVISIBLE);
        tvNomPdes5.setVisibility(View.INVISIBLE);

        tvPrePdes1.setVisibility(View.INVISIBLE);
        tvPrePdes2.setVisibility(View.INVISIBLE);
        tvPrePdes3.setVisibility(View.INVISIBLE);
        tvPrePdes4.setVisibility(View.INVISIBLE);
        tvPrePdes5.setVisibility(View.INVISIBLE);

        tvCantPdes1.setVisibility(View.INVISIBLE);
        tvCantPdes2.setVisibility(View.INVISIBLE);
        tvCantPdes3.setVisibility(View.INVISIBLE);
        tvCantPdes4.setVisibility(View.INVISIBLE);
        tvCantPdes5.setVisibility(View.INVISIBLE);

        tvSubPdes1.setVisibility(View.INVISIBLE);
        tvSubPdes2.setVisibility(View.INVISIBLE);
        tvSubPdes3.setVisibility(View.INVISIBLE);
        tvSubPdes4.setVisibility(View.INVISIBLE);
        tvSubPdes5.setVisibility(View.INVISIBLE);

        imgP1.setVisibility(View.INVISIBLE);
        imgP2.setVisibility(View.INVISIBLE);
        imgP3.setVisibility(View.INVISIBLE);
        imgP4.setVisibility(View.INVISIBLE);
        imgP5.setVisibility(View.INVISIBLE);
    }

    public void productoUno(){
        imgP1.setVisibility(View.VISIBLE);

        tvNomP1.setVisibility(View.VISIBLE);
        tvPreP1.setVisibility(View.VISIBLE);
        tvCantP1.setVisibility(View.VISIBLE);
        tvSubP1.setVisibility(View.VISIBLE);

        tvNomPdes1.setVisibility(View.VISIBLE);
        tvPrePdes1.setVisibility(View.VISIBLE);
        tvCantPdes1.setVisibility(View.VISIBLE);
        tvSubPdes1.setVisibility(View.VISIBLE);
    }

    public void productoDos(){
        imgP2.setVisibility(View.VISIBLE);

        tvNomP2.setVisibility(View.VISIBLE);
        tvPreP2.setVisibility(View.VISIBLE);
        tvCantP2.setVisibility(View.VISIBLE);
        tvSubP2.setVisibility(View.VISIBLE);

        tvNomPdes2.setVisibility(View.VISIBLE);
        tvPrePdes2.setVisibility(View.VISIBLE);
        tvCantPdes2.setVisibility(View.VISIBLE);
        tvSubPdes2.setVisibility(View.VISIBLE);
    }

    public void productoTres(){
        imgP3.setVisibility(View.VISIBLE);

        tvNomP3.setVisibility(View.VISIBLE);
        tvPreP3.setVisibility(View.VISIBLE);
        tvCantP3.setVisibility(View.VISIBLE);
        tvSubP3.setVisibility(View.VISIBLE);

        tvNomPdes3.setVisibility(View.VISIBLE);
        tvPrePdes3.setVisibility(View.VISIBLE);
        tvCantPdes3.setVisibility(View.VISIBLE);
        tvSubPdes3.setVisibility(View.VISIBLE);
    }

    public void productoCuatro(){
        imgP4.setVisibility(View.VISIBLE);

        tvNomP4.setVisibility(View.VISIBLE);
        tvPreP4.setVisibility(View.VISIBLE);
        tvCantP4.setVisibility(View.VISIBLE);
        tvSubP4.setVisibility(View.VISIBLE);

        tvNomPdes4.setVisibility(View.VISIBLE);
        tvPrePdes4.setVisibility(View.VISIBLE);
        tvCantPdes4.setVisibility(View.VISIBLE);
        tvSubPdes4.setVisibility(View.VISIBLE);
    }

    public void productoCinco(){
        imgP5.setVisibility(View.VISIBLE);

        tvNomP5.setVisibility(View.VISIBLE);
        tvPreP5.setVisibility(View.VISIBLE);
        tvCantP5.setVisibility(View.VISIBLE);
        tvSubP5.setVisibility(View.VISIBLE);

        tvNomPdes5.setVisibility(View.VISIBLE);
        tvPrePdes5.setVisibility(View.VISIBLE);
        tvCantPdes5.setVisibility(View.VISIBLE);
        tvSubPdes5.setVisibility(View.VISIBLE);
    }
}