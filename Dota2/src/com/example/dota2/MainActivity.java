package com.example.dota2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.dota2.bbdd.BBDDHeroe;
import com.example.dota2.modelo.Heroe;

public class MainActivity extends ListActivity{
	private List<Heroe> heroes;
	private  BBDDHeroe bdHero;
	private static String URL = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=45B405CD403024157E3FD11668D33BD7&language=es";
	private int auto=1;
	
	@SuppressLint("UseSparseArrays")
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        
        //Lista de los heroes
        heroes = new ArrayList<Heroe>();
        // Base de datos de los heroes
        bdHero = new BBDDHeroe(this);
        
        // Si no existe el directorio con las fotos se crea y se descargan
        if(!exitsDirectory()) {
        	new CargaHeroesTask(this).execute();
        }
        // Si existe, solo se crea la lista a mostrar desde la base de datos
        else
        {
        	 for(int i=1; i<=bdHero.buscarTodos().size();i++)
             {
        		Heroe heroe = bdHero.findById(i);
             	heroes.add(heroe);
             }
             setListAdapter( new MiAdaptador(this, heroes));
        }
       }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
//    	Intent intent = new Intent(this, HeroActivity.class);
//    	intent.putExtra("heroe", heroes.get(position).getId());
		Intent intent = new Intent(this, DetalleHeroeWeb.class);
    	intent.putExtra("heroe", heroes.get(position).getName());
    	startActivity(intent);
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater inflate = getMenuInflater();
		inflate.inflate(R.menu.mi_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		switch(item.getItemId()){
		case R.id.todos:
				heroes = bdHero.buscarTodos();
				 setListAdapter( new MiAdaptador(this, heroes));
			break;
		
		case R.id.fav:
			heroes = bdHero.buscarFavoritos();
			 setListAdapter( new MiAdaptador(this, heroes));
			break;
			
		case R.id.search:
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
			
		case R.id.buscarHeroe:
			Intent inten = new Intent(this, BuscarHeroe.class);
			startActivity(inten);
			break;
		}
		return true;
	}
	
	
	
	
	
	private class CargaHeroesTask extends AsyncTask<String, Void, Boolean>{

		private ProgressDialog dialog;

		private ListActivity activity;
		
		public CargaHeroesTask(ListActivity activity) { 
			this.activity = activity; 
			 
			dialog = new ProgressDialog(activity); }

		protected void onPreExecute() { 
			this.dialog.setMessage("Progress start"); 
			this.dialog.show(); 
			}
		
		protected void onPostExecute(final Boolean success) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			 setListAdapter( new MiAdaptador(activity, heroes));
		}
		
			
		protected Boolean doInBackground(final String... args) { 
			JSONParser jParser = new JSONParser(); 
			// get JSON data from URL 
			JSONObject json = jParser.getJSONObjectFromUrl(URL);
			JSONObject result=null;
			JSONArray structure=null;
			try {
				result = json.getJSONObject("result");
				if (result!=null)
					structure = result.getJSONArray("heroes");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(structure!=null)
			for (int i = 0; i < structure.length(); i++) { 
				try { 
					JSONObject c = structure.getJSONObject(i); 
					String name = c.getString("localized_name"); 
					int id = getAuto();
					String nameDota = c.getString("name");
					String namePhoto = getName(nameDota);
					
					//TODO 
					if (id==52)
						name="Natures Prophet";
					
					// Creamos en heroe y lo insertamos en la BBDD
					Heroe toSave = new Heroe(id, name, "Troll", namePhoto, 0);
					heroes.add(toSave);
					bdHero.insert(toSave);
					
					// Descarga las fotos del heroe, para la lista y la vista de detalle
					file_download("http://cdn.dota2.com/apps/dota2/images/heroes/"+namePhoto+"_hphover.png", namePhoto+".png");
					
					} catch (JSONException e) { 
						e.printStackTrace(); } 
				} 
			return null; 
			}
		
		
		private void file_download(String uRl,String nameFile) {
	        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
	                + "/fotosDota2");

	        if (!direct.exists()) {
	            direct.mkdirs();
	        }

	        DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

	        Uri downloadUri = Uri.parse(uRl);
	        DownloadManager.Request request = new DownloadManager.Request(
	                downloadUri);

	        request.setAllowedNetworkTypes(
	                DownloadManager.Request.NETWORK_WIFI
	                        | DownloadManager.Request.NETWORK_MOBILE)
	                .setAllowedOverRoaming(false)
	                .setDestinationInExternalPublicDir("/fotosDota2", nameFile);

	        mgr.enqueue(request);

	    }
		
		private String getName(String nameDota)
		{
			String ret=null;
			String[] helper = nameDota.split("_");
			if (helper.length==4)
				ret=helper[3];
			else if (helper.length==5)
				ret=helper[3]+"_"+helper[4];
			else if (helper.length==6)
				ret=helper[3]+"_"+helper[4]+"_"+helper[5];
			else if (helper.length==7)
				ret=helper[3]+"_"+helper[4]+"_"+helper[5]+"_"+helper[6];
				
			
			return ret;
		}
		
		
		}
			
		

	private boolean exitsDirectory()
	{
		 File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()   + "/fotosDota2");
		 return direct.exists();
	}
	
	private int getAuto() {
		return auto++;
	}
}
	



