package com.example.dota2;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.dota2.bbdd.BBDDHeroe;
import com.example.dota2.modelo.Heroe;

/**
 * Actividad que permite buscar un heroe por el nombre.
 * @author David García
 * @author Daniel Serrano
 */
public class HeroesBuscados extends ListActivity{
	private List<Heroe> heroes;
	private  BBDDHeroe bdHero;
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        Intent intent = getIntent();
        String name = intent.getStringExtra("heroe");
        bdHero = new BBDDHeroe(this);
        heroes = bdHero.buscarPorNombre(name);
        
        
        setListAdapter( new MiAdaptador(this, heroes));      
       }
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    	Intent intent = new Intent(this, DetalleHeroeWeb.class);
	    	intent.putExtra("heroe", heroes.get(position).getName());
	    	startActivity(intent);
	  }

}
