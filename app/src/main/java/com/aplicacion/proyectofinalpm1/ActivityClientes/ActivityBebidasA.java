package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityBebidasA extends AppCompatActivity {
    int contador,preciou;
    Button btnMenosBC, btnMasBC, btnACarritoBC;
    TextView txvCantidadBC,txvPTotalBAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador =0;
        preciou = 150;
        setContentView(R.layout.activity_bebidas);

        txvCantidadBC = (TextView) findViewById(R.id.txtvCantidadBC);
        txvPTotalBAC = (TextView) findViewById(R.id.txvPTotalBC);

       //disminuir cantidad
        btnMenosBC = (Button) findViewById(R.id.btnMenosBC);
        btnMenosBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador =1;
                txvCantidadBC.setText(Integer.toString(contador));
                txvPTotalBAC.setText(Integer.toString(preciou));
            }
        });

        //aumentar cantidad
        btnMasBC = (Button) findViewById(R.id.btnMasBC);
        btnMasBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador ++;
                txvCantidadBC.setText(Integer.toString(contador));
                txvPTotalBAC.setText(Integer.toString(contador*preciou));
            }
        });
        btnACarritoBC = (Button) findViewById(R.id.btnAggCaritoBC);
    }
}