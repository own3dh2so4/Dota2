package com.example.dota2;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dota2.bbdd.BBDDHeroe;
import com.example.dota2.bbdd.BBDDHeroeDetail;
import com.example.dota2.modelo.Heroe;
import com.example.dota2.modelo.HeroeDetail;

public class HeroActivity extends Activity{

	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroactivity);
        
        
        Bundle extras = getIntent().getExtras();
        int idHero = extras.getInt("heroe");
        BBDDHeroe bdHero = new BBDDHeroe(this);
        Heroe heroeAMostrar = bdHero.findById(idHero);
        BBDDHeroeDetail bdHeroDetail = new BBDDHeroeDetail(this);
        HeroeDetail heroDetail = bdHeroDetail.findById(idHero);
        
        if (heroDetail != null && (heroDetail.getPhoto() != null || ! "".equals(heroDetail.getPhoto()))) {
		    Uri.Builder uriB = new Uri.Builder();
		    uriB.path(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fotosDota2/" + heroDetail.getPhoto());

	        ImageView imagenHeroe = (ImageView) findViewById(R.id.imagen);
	        imagenHeroe.setImageURI(uriB.build());
        }
        
        TextView nombreHeroe = (TextView) findViewById(R.id.nombreVistaHeroe);
        nombreHeroe.setText(heroeAMostrar.getName());
        
        TextView rolHeroe = (TextView) findViewById(R.id.rol);
        rolHeroe.setText(heroeAMostrar.getRol());
        
        TextView descripcionHeroe = (TextView) findViewById(R.id.descripcion);
        descripcionHeroe.setText(heroDetail.getDescripcion());
        
	}
	
}
