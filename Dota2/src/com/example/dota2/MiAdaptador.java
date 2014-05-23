package com.example.dota2;

import java.util.Vector;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private final Vector<Heroe> heroes;
	private int posicion;
	
	public MiAdaptador(ListActivity actividad, Vector<Heroe> heroes)
	{
		super();
		this.actividad=actividad;
		this.heroes=heroes;
		posicion=-1;
	}
	
	@Override
	public int getCount() {
		return heroes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return heroes.elementAt(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		View view = arg1;
		posicion=arg0;
		
		if( view == null )
		
		{
			LayoutInflater inflater = actividad.getLayoutInflater();
			view = inflater.inflate(R.layout.elemento, null, true);
		}
		
		
		/*TextView textView = (TextView)view.findViewById(R.id.nombre);
		textView.setText(heroes.elementAt(arg0).getName());
		
		TextView subTitulo = (TextView)view.findViewById(R.id.subtitulo);
		subTitulo.setText(heroes.elementAt(arg0).getRol());
		
		ImageView imagen = (ImageView) view.findViewById(R.id.icono);
		Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()   + "/fotosDota2/" +heroes.elementAt(arg0).getName()+".png");
		imagen.setImageBitmap(bmp);
		//imagen.setImageResource(heroes.elementAt(arg0).getPhoto());*/
		
		
		
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
			
			
			String caca;
			if(idHero==24)
				caca="Hola";
			
			return newHeroe;
		}
		
		protected void onPostExecute(final  Heroe success){
			String caca;
			if(idHero==24)
				caca="Hola";
			
			TextView textView = (TextView)v.findViewById(R.id.nombre);
			textView.setText(success.getName());
			
			TextView subTitulo = (TextView)v.findViewById(R.id.subtitulo);
			subTitulo.setText(success.getRol());
			
			ImageView imagen = (ImageView) v.findViewById(R.id.icono);
			Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()   + "/fotosDota2/" +success.getName()+".png");
			imagen.setImageBitmap(bmp);
			
			final ImageButton imageButton = (ImageButton) v.findViewById(R.id.botonBuscar);
			if (success.getGusta()==1)
				imageButton.setImageResource(android.R.drawable.btn_star_big_on);
			else
				imageButton.setImageResource(android.R.drawable.btn_star_big_off);
			
			imageButton.setOnClickListener(new View.OnClickListener() {
				
				private int p = posicion;
				public void onClick(View v) {
					
					 BBDDHeroe bdHero = new BBDDHeroe(actividad);
					 if (heroes.get(p).getGusta()==1)
						 {
						 	heroes.get(p).setGusta(0);
						 	bdHero.update(heroes.get(p));
						 	imageButton.setImageResource(android.R.drawable.btn_star_big_off);
						 }
					 else
					 {
						 heroes.get(p).setGusta(1);
						 bdHero.update(heroes.get(p));
						 imageButton.setImageResource(android.R.drawable.btn_star_big_on);
					 }
						 
				}
			});
		}
		
	}
	

}
