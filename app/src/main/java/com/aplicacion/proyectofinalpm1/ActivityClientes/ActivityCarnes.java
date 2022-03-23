package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityCarnes extends AppCompatActivity {

    int contador;
    int preciou;
    Button btnMenosCA, btnMasCA, btnACarritoCA;
    TextView txvCantidadCA,txvPTotalCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador =0;
        preciou = 100;
        setContentView(R.layout.activity_carnes);

        txvCantidadCA = (TextView) findViewById(R.id.txtvCantidadC);
        txvPTotalCA = (TextView) findViewById(R.id.txvPTotalC);


        btnMenosCA = (Button) findViewById(R.id.btnmenosC);
        btnMenosCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador =1;
                txvCantidadCA.setText(Integer.toString(contador));
                txvPTotalCA.setText(Integer.toString(preciou));

            }
        });
        btnMasCA = (Button) findViewById(R.id.btnmasC);
        btnMasCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador ++;
                txvCantidadCA.setText(Integer.toString(contador));
                txvPTotalCA.setText(Integer.toString(contador*preciou));
            }
        });
        btnACarritoCA = (Button) findViewById(R.id.btnAggCarritoC);



    }
}