package com.example.dota2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BuscarHeroe extends Activity{

	private Activity me;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarheroe);
        me = this;
        
        /*ImageButton imageB = (ImageButton) findViewById(R.id.botonBuscar);
        imageB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String nick = ((EditText) findViewById(R.id.editBuscate)).getText().toString();
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dotabuff.com/search?utf8=%E2%9C%93&q="+nick));
				startActivity(i);
				
			}
		});*/
        
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
