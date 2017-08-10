package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Resultados extends Activity implements View.OnClickListener{

    Map<String, String> mapaResultados = new TreeMap<String, String>();
    String valores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        Intent myIntent = getIntent(); // gets the previously created intent
        valores = myIntent.getStringExtra("valores");
        System.out.println("Resultados: "+valores);

        parserResultados(valores);


        EditText editText = (EditText)findViewById(R.id.var_dni);
        editText.setText(mapaResultados.get("dni"));

        EditText editText1 = (EditText)findViewById(R.id.var_ape);
        editText1.setText(mapaResultados.get("apellido"));

        EditText editText2 = (EditText)findViewById(R.id.var_nombre);
        editText2.setText(mapaResultados.get("nombre"));

        EditText editText3 = (EditText)findViewById(R.id.var_nacimiento);
        editText3.setText(mapaResultados.get("f_nacimiento"));

        EditText editText4 = (EditText)findViewById(R.id.var_sexo);
        editText4.setText(mapaResultados.get("sexo"));
    }


 //00196390135@THEILER@MARTIN@M@27898586@A@25/03/1980@05/06/2013

    public void parserResultados(String valores){

        String[] val = valores.split("@");
        mapaResultados.put("apellido",val[1]);
        mapaResultados.put("nombre",val[2]);
        mapaResultados.put("sexo",val[3]);
        mapaResultados.put("dni",val[4]);
        mapaResultados.put("f_nacimiento",val[6]);

    }


    public void leerContenido(View v){

        System.out.println("leerContenido ");
        Intent leerCont = new Intent(Resultados.this, LeerContenidoActivity.class);

        System.out.println("leerContenido 1");
        leerCont.putExtra("mapaValores",valores );
        System.out.println("leerContenido 3");
        startActivity(leerCont);
    }


    @Override
    public void onClick(View view) {

    }
}
