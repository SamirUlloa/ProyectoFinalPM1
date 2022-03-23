package com.aplicacion.proyectofinalpm1.ActivityClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aplicacion.proyectofinalpm1.R;

public class ActivityABebes extends AppCompatActivity {
    int contador,preciou;
    Button btnMenosAB, btnMasAB, btnACarritoAB;
    TextView txvCantidadAB,txvPTotalABBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador =0;
        preciou = 120;
        setContentView(R.layout.activity_abebes);

        txvCantidadAB = (TextView) findViewById(R.id.txtvCantidadABB);
        txvPTotalABBE = (TextView) findViewById(R.id.txvPTotalABB);

        btnMenosAB = (Button) findViewById(R.id.btnMenosABB);
        btnMenosAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador =1;
                txvCantidadAB.setText(Integer.toString(contador));
                txvPTotalABBE.setText(Integer.toString(preciou));
            }
        });
        btnMasAB = (Button) findViewById(R.id.btnMasABB);
        btnMasAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador ++;
                txvCantidadAB.setText(Integer.toString(contador));
                txvPTotalABBE.setText(Integer.toString(contador*preciou));
            }
        });
        btnACarritoAB = (Button) findViewById(R.id.btnAggCaritoABB);

    }
}