package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityLacteos extends AppCompatActivity {

    int contador,preciou;
    Button btnMenosLA, btnMasLA, btnACarritoLA;
    TextView txvCantidadLA,txvPTotalLA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador =0;
        preciou = 30;
        setContentView(R.layout.activity_lacteos);

        txvCantidadLA = (TextView) findViewById(R.id.txtvCantidadL);
        txvPTotalLA = (TextView) findViewById(R.id.txvPTotalL);

        btnMenosLA = (Button) findViewById(R.id.btnMenosL);
        btnMenosLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador =1;
                txvCantidadLA.setText(Integer.toString(contador));
                txvPTotalLA.setText(Integer.toString(preciou));
            }
        });
        btnMasLA = (Button) findViewById(R.id.btnMasL);
        btnMasLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador ++;
                txvCantidadLA.setText(Integer.toString(contador));
                txvPTotalLA.setText(Integer.toString(contador*preciou));
            }
        });

        btnACarritoLA = (Button) findViewById(R.id.btnAggCaritoL);



    }
}