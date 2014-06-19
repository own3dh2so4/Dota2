package com.example.dota2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Actividad que permite buscar un heroe de entre todos
 * los que aparecen en la lista principal
 * @author David García
 * @author Daniel Serrano
 */
public class BuscarHeroe extends Activity{

	private Activity me;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarheroe);
        me = this;
        
        Button boton = (Button) findViewById(R.id.button1);
        boton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String nombre = ((EditText) findViewById(R.id.editText1)).getText().toString();
				Intent intent = new Intent(me, HeroesBuscados.class);
		    	intent.putExtra("heroe", nombre);
		    	startActivity(intent);
				
			}
		});
	}
}
