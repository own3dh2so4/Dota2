package com.example.dota2;

import java.util.List;

import android.app.ListActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dota2.bbdd.BBDDHeroe;
import com.example.dota2.modelo.Heroe;

public class MiAdaptador  extends BaseAdapter{

	private final ListActivity actividad;
	private final List<Heroe> heroes;;
	
	public MiAdaptador(ListActivity actividad, List<Heroe> heroes)
	{
		super();
		this.actividad=actividad;
		this.heroes=heroes;
	}
	
	@Override
	public int getCount() {
		return heroes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return heroes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		View view = arg1;
		
		if( view == null )
		
		{
			LayoutInflater inflater = actividad.getLayoutInflater();
			view = inflater.inflate(R.layout.elemento, null, true);
		}
		
		if (heroes.get(arg0) !=null)
		{
			this.inicializaVista(view, heroes.get(arg0));
		}
		
		else
			new MasHeroes(arg0+1,actividad,view).execute();
		
		return view;
	}
	
	public class MasHeroes extends AsyncTask<Void, Void, Heroe>{

		private int idHero;
		private ListActivity activity;
		private BBDDHeroe bdHero;
		private View v;
		
		public MasHeroes (int i, ListActivity activity, View vista)
		{
			idHero=i;
			this.activity=activity;
			bdHero = new BBDDHeroe(this.activity);
			v=vista;
		}
		
		@Override
		protected Heroe doInBackground(Void... params) {
			Heroe newHeroe = bdHero.findById(idHero);
			
			return newHeroe;
		}
		
		protected void onPostExecute(final Heroe success){
			
			inicializaVista(v, success);
		}
		
	}
	
	public void inicializaVista (View v, final Heroe success){
		TextView textView = (TextView)v.findViewById(R.id.nombre);
		textView.setText(success.getName());
		
		TextView subTitulo = (TextView)v.findViewById(R.id.subtitulo);
		subTitulo.setText(success.getRol());
		
		ImageView imagen = (ImageView) v.findViewById(R.id.icono);
		Uri.Builder uriB = new Uri.Builder();
	    uriB.path(Environment.getExternalStorageDirectory().getAbsolutePath()   + "/fotosDota2/" +success.getPhoto()+".png");
        imagen.setImageURI(uriB.build());
		
		final ImageButton imageButton = (ImageButton) v.findViewById(R.id.botonBuscar);
		if (success.getGusta()==1)
			imageButton.setImageResource(android.R.drawable.btn_star_big_on);
		else
			imageButton.setImageResource(android.R.drawable.btn_star_big_off);
		
		imageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				 BBDDHeroe bdHero = new BBDDHeroe(actividad);
				 if (success.getGusta()==1)
					 {
					 	success.setGusta(0);
					 	bdHero.update(success);
					 	imageButton.setImageResource(android.R.drawable.btn_star_big_off);
					 }
				 else
				 {
					 success.setGusta(1);
					 bdHero.update(success);
					 imageButton.setImageResource(android.R.drawable.btn_star_big_on);
				 }
					 
			}
		});
	}
	
	

}
