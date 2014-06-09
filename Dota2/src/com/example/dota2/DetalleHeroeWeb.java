package com.example.dota2;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class DetalleHeroeWeb extends Activity{
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalleheroeweb);
        
        
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("heroe");
        String web = adapterTheName(nombre);
        
        TextView titulo = (TextView) findViewById(R.id.tituloWeb);
        titulo.setText(nombre);
        
        String url = "http://www.dota2.com/hero/"+web+"/";
        WebView myWebView = (WebView) this.findViewById(R.id.webView1);
        myWebView.loadUrl(url);
	}
	
	private String adapterTheName (String web){
		char[] aux = web.toCharArray();
		for (int i=1; i<aux.length; i++)
		{
			if (aux[i]==' ')
			{
				aux[i] = '_';
			}
		}
		String ret = new String(aux);
		return ret;
	}
	

}
