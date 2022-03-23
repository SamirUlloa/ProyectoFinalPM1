package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityGranosB extends AppCompatActivity {

    int contador;
    int preciou;
    Button btnMenosGR, btnMasGR, btnACarritoGR;
    TextView txvCantidadGR,txvPTotalGR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador = 0;
        preciou = 14;
        setContentView(R.layout.activity_granos_b);

        txvCantidadGR = (TextView) findViewById(R.id.txtvCantidadG);
        txvPTotalGR = (TextView) findViewById(R.id.txvPTotalG);
        btnACarritoGR = (Button) findViewById(R.id.btnAggCarritoG);

        btnMenosGR = (Button) findViewById(R.id.btnmenosG);
        btnMenosGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador =1;
                txvCantidadGR.setText(Integer.toString(contador));
                txvPTotalGR.setText(Integer.toString(preciou));
            }
        });
        btnMasGR = (Button) findViewById(R.id.btnmasG);
        btnMasGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador ++;
                txvCantidadGR.setText(Integer.toString(contador));
                txvPTotalGR.setText(Integer.toString(contador*preciou));
            }
        });


    }

    //metodos
}