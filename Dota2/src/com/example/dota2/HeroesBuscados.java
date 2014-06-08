package com.example.dota2;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.dota2.bbdd.BBDDHeroe;
import com.example.dota2.modelo.Heroe;

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
        
        
       
        
        //file_download("http://cdn.dota2.com/apps/dota2/images/heroes/tiny_sb.png");
        
        
       
       }

}
