package com.example.dota2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.example.dota2.bbdd.BBDDHeroeDetail;
import com.example.dota2.modelo.Heroe;
import com.example.dota2.modelo.HeroeDetail;

public class MainActivity extends ListActivity{
	private List<Heroe> heroes;
	private  BBDDHeroe bdHero;
	private BBDDHeroeDetail bdHeroDetail;
	private static String URL = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=45B405CD403024157E3FD11668D33BD7&language=es";
	private Map<Integer, String[]> fotosHeroes;
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
        // Base de datos de los dealles de los heroes
        bdHeroDetail = new BBDDHeroeDetail(this);
        
        // Nombre de las fotos de lista y detalle de los heroes
        fotosHeroes = new HashMap<Integer, String[]>();
        
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
        //file_download("http://cdn.dota2.com/apps/dota2/images/heroes/tiny_sb.png");
       }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent = new Intent(this, HeroActivity.class);
    	intent.putExtra("heroe", heroes.get(position).getId());
    	startActivity(intent);
	}
	
	private void generarDetalleHeroe(Heroe heroe) {
		
		String desc = "Descripci�n por defecto";
		int id = heroe.getId();
		
		switch (id) {
		case 1:
			desc = "Como un g�lem o una g�rgola, Earthshaker fue uno con la tierra, pero ahora camina libremente sobre ella. A diferencia de las otras entidades, se cre� a s� mismo a trav�s de un acto de voluntad, y no sirve a ning�n otro maestro. En su sue�o inquieto, encerrado en una profunda fisura en la piedra, se dio cuenta de la vida que transcurr�a libremente por encima de �l y se volvi� curioso. Durante una �poca de temblores, los picos de Nishai fueron sacudidos, provocando derrumbamientos que cambiaron el curso de los r�os y convirtieron los valles poco profundos en abismos sin fondo. Cuando la tierra finalmente dej� de temblar, Earthshaker sali� del polvo que se asentaba, apartando enormes rocas como si se tratasen de una ligera manta. Se convirti� en una bestia mortal, y se llam� a s� mismo Raigor Pezu�ap�trea. Ahora sangra, respira y por lo tanto puede morir, pero su esp�ritu sigue siendo ese de la tierra. Su poder reside en el t�tem m�gico que nunca deja de lado y, el d�a que regrese al polvo, la tierra lo tratar� como a un hijo pr�digo.";
			break;
		}
		
		HeroeDetail heroeDetail = new HeroeDetail(id, desc, getFotoDetail(id));
		
		bdHeroDetail.insert(heroeDetail);
	}
	
	private String getFotoDetail(int id) {
		return (fotosHeroes != null && fotosHeroes.get(id) != null ) ? fotosHeroes.get(id)[0] : "";
	}
	
	/*public void minarDataBase(){
		if (bdHero.findById(1)==null)
		{
			Toast toast = Toast.makeText(this, "Cargando Informaci�n", Toast.LENGTH_LONG);
	        toast.show();
			Heroe a = new Heroe(1, "Eartseaker", "Support", R.drawable.earthshaker_hphover,0);		
	        bdHero.insert(a);
	        a= new Heroe(2, "Sven", "SemiCarry", R.drawable.sven_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(3, "Tiny", "Caster", R.drawable.tiny_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(4, "Kunkka", "CarryTank", R.drawable.kunkka_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(5, "BeastMaster", "Support", R.drawable.beastmaster_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(6, "Dragon Knight", "CarryTank", R.drawable.dragon_knight_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(7, "Clockwerk", "Tank", R.drawable.rattletrap_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(8, "Omniknight", "Support", R.drawable.omniknight_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(9, "Huskar", "CarryTank", R.drawable.huskar_hphover,0);
	        bdHero.insert(a);
	        a = new Heroe(10, "Alchemist", "CarryTank", R.drawable.alchemist_hphover,0);
	        bdHero.insert(a);
	        
	        HeroeDetail b = new HeroeDetail(1, "Como un g�lem o una g�rgola, Earthshaker fue uno con la tierra, pero ahora camina libremente sobre ella. A diferencia de las otras entidades, se cre� a s� mismo a trav�s de un acto de voluntad, y no sirve a ning�n otro maestro. En su sue�o inquieto, encerrado en una profunda fisura en la piedra, se dio cuenta de la vida que transcurr�a libremente por encima de �l y se volvi� curioso. Durante una �poca de temblores, los picos de Nishai fueron sacudidos, provocando derrumbamientos que cambiaron el curso de los r�os y convirtieron los valles poco profundos en abismos sin fondo. Cuando la tierra finalmente dej� de temblar, Earthshaker sali� del polvo que se asentaba, apartando enormes rocas como si se tratasen de una ligera manta. Se convirti� en una bestia mortal, y se llam� a s� mismo Raigor Pezu�ap�trea. Ahora sangra, respira y por lo tanto puede morir, pero su esp�ritu sigue siendo ese de la tierra. Su poder reside en el t�tem m�gico que nunca deja de lado y, el d�a que regrese al polvo, la tierra lo tratar� como a un hijo pr�digo.	", R.drawable.earthshaker_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(2, "Sven es el hijo bastardo de un Caballero de la Vigilia, nacido de un Merante P�lido y criado en las Ruinas de la Costa Sombr�a. Con su padre ejecutado por violar el C�dice de la Vigilia y su madre rechazada por su raza salvaje, Sven cree que el honor no se encuentra en ning�n orden social, sino en uno mismo. Tras cuidar a su madre durante una prolongada agon�a, se ofreci� como aprendiz de los Caballeros de la Vigilia, sin revelar nunca su identidad. Durante trece a�os estudi� en la escuela de su padre, dominando el r�gido c�digo que declar� su existencia una abominaci�n. Luego, el d�a en que deber�a haber prestado juramento, tom� la Hoja del Exiliado, destruy� el Yelmo Sagrado y quem� el C�dice en la Divina Llama de la Vigilia. Se alej� de la Fortaleza de la Vigilia, siempre en solitario, siguiendo hasta la �ltima estricta runa de su c�digo privado. A�n sigue siendo un caballero, s�... pero un Caballero Renegado. No responde m�s que ante s� mismo.", R.drawable.sven_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(3, "Tras cobrar vida como un trozo de roca, los or�genes de Tiny son un misterio sobre el que especula continuamente. Ahora es un Gigante de Piedra, �pero qu� era antes? �un fragmento del tal�n de un G�lem? �un pedazo sobrante del taller de un escultor de g�rgolas? �una parte del Rostro Oracular de Garthos? Se mueve por una gran curiosidad, y viaja por todo el mundo sin descanso en busca de sus or�genes, su ascendencia y su gente. A medida que camina, aumenta de peso y de tama�o, y las fuerzas que el tiempo ejerce sobre las rocas haci�ndolas m�s y m�s peque�as causan en Tiny el efecto contrario, provocando que crezca sin cesar.", R.drawable.tiny_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(4, "Como almirante de la poderosa Armada Claddiense, Kunkka estaba a cargo de proteger las islas de su tierra natal cuando los demonios de la Catarata emprendieron una incursi�n en las tierras de los hombres. Tras a�os de peque�as incursiones y atrevidos ataques cada vez m�s devastadores, la flota demon�aca llev� a todos sus buques carn�voros a la Isla Tr�mula. Desesperados, los Magos Suicidas de Cladd llevaron a cabo su rito definitivo, invocando a un ej�rcito de esp�ritus ancestrales para proteger a su armada. Esto fue apenas suficiente para cambiar el rumbo de los acontecimientos en contra de los demonios. Mientras Kunkka los ve�a tomar sus embarcaciones una a una, �l tuvo la satisfacci�n de deshacer la flota enemiga al completo con su magia ancestral. Pero en el apogeo del combate, algo en el choque entre demonios, hombres y esp�ritus at�vicos debi� despertar a una cuarta fuerza que estaba dormitando en las profundidades. Las olas se alzaron en chorros elevados en torno a los pocos nav�os restantes y Maelrawn el Tentacular apareci� en medio de la batalla. Sus tent�culos bambolearon a las naves, juntando a los barcos humanos y demon�acos y batiendo el agua y el viento en un furioso caos. Lo que ocurri� en el crisol de la tormenta, nadie lo puede aseverar realmente. La Catarata desapareci� en el vac�o, despojada de sus antiguos moradores. Kunkka es ahora el almirante de un solo barco, un fantasmag�rico bote que incesantemente repite los �ltimos segundos de su destrucci�n. Si �l muri� en aquel choque no es posible saberlo. Ni siquiera Tidehunter, el invocador de Maelrawn, lo sabe con seguridad.	", R.drawable.kunkka_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(5, "Karroch era un chico normal y corriente. Su madre muri� al dar a luz; su padre, un herrero que trabajaba para el �ltimo Rey de Slom, muri� aplastado cuando Karroch ten�a cinco a�os. Tras eso, se vio obligado a trabajar en la casa de fieras del rey, donde creci� rodeado de las bestias de la corte real: leones, monos, ciervos y cosas menos conocidas, cosas en las que apenas cre�a. Cuando el muchacho ten�a siete a�os, un explorador trajo a un animal nunca antes visto. Arrastrada ante el rey entre cadenas, la bestia habl�, pero su boca no se movi�. Sus palabras: una s�plica por la libertad. El rey se limit� a re�r y orden� a la bestia que hablase para su divertimiento. Cuando �sta se neg�, la golpe� con el Cetro de la Locura y orden� que fuese llevada de nuevo con las dem�s fieras. Durante los meses siguientes, Karroch trajo comida y medicinas a escondidas a la criatura herida, pero s�lo logr� ralentizar su empeoramiento. Sorprendentemente, la bestia habl� al muchacho, y con el tiempo su v�nculo se fortaleci� hasta que el ni�o descubri� que pod�a mantener con ella una conversaci�n. De hecho, ahora pod�a hablar con todas las criaturas de la casa de fieras del rey. La noche en que la bestia muri�, la rabia se apoder� del chico. Incit� a los dem�s animales de la corte a rebelarse y abri� sus jaulas para que causasen estragos en los terrenos del palacio. El Rey Loco fue atacado en medio de la confusi�n. Durante el caos, un ciervo real se inclin� ante el muchacho que lo hab�a liberado, y, con el Se�or de las Bestias (Beastmaster) montado sobre �l, saltaron los altos muros de la fortaleza y lograron escapar. Ahora como hombre, Karroch, el Se�or de las Bestias, no ha perdido su habilidad de conversar con las criaturas salvajes. Se ha convertido en un guerrero en armon�a con la naturaleza salvaje.", R.drawable.beastmaster_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(6, "Despu�s de a�os tras la pista de un legendario Eldwurm, el Caballero Davion se encontr� frente a un enemigo decepcionante: el temible Slyrak se hab�a vuelto viejo y fr�gil, sus alas estaban hechas jirones, sus pocas escamas restantes afectadas por la podredumbre, sus colmillos desgastados hasta las enc�as y su aliento de fuego no era m�s amenazador que una caja de cerillas mojada. Al ver que no obtendr�a honor por matar al drag�n, el Caballero Davion se prepar� para el viaje de regreso y dejar a su viejo enemigo morir en paz. Pero una voz penetr� en sus pensamientos, y Slyrak le susurr� a Davion que ser�a un honor para �l morir en combate. Davion acept�, y se vio recompensado m�s all� de las expectativas por su acto de misericordia: mientras hund�a su hoja en el pecho de Slyrak, el drag�n atraves� la garganta de Davion con una garra. A medida que su sangre se mezclaba, Slyrak transfiri� su poder a trav�s del flujo sangu�neo, ofreci�ndole al caballero toda su fuerza y siglos de sabidur�a. La muerte del drag�n sell� su v�nculo, y el Caballero Drag�n (Dragon Knight) hab�a nacido. El ancestral poder permanece dormido dentro del Caballero Drag�n Davion, despert�ndose cuando �l lo llama. O quiz�s es el Drag�n el que llama al Caballero...", R.drawable.dragon_knight_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(7, "Perteneciente a la misma raza que Sniper y Tinker y al igual que muchos otros de la Gente Ingeniosa, Rattletrap ha sabido compensar su corta estatura con una combinaci�n de artefactos e ingenio. Hijo del hijo de un relojero, Rattletrap fue aprendiz de ese oficio durante a�os antes de que la guerra descendiera de las monta�as y despojase a las sencillas aldeas de unas vocaciones tan inocentes. �Tu nuevo negocio es la guerra�, dijo su moribundo padre mientras la aldea de sus ancestros yac�a entre humeantes escombros. Un artesano pobre es aquel que culpa a sus herramientas, y Rattletrap nunca fue de los que buscan excusas. Tras enterrar a su padre entre las ruinas de su pueblo, emprendi� su cruzada particular para transformarse en la herramienta de combate m�s poderosa que el mundo hubiese visto. Se prometi� a s� mismo que nunca volver�an a cogerle desprevenido, as� que, vali�ndose de su talento, construy� una armadura ensamblando piezas de relojer�a que, en comparaci�n, har�a que las de los caballeros de otras tierras pareciesen de hojalata. Ahora Rattletrap vive gracias a un sinf�n de dispositivos; un guerrero peque�o pero mort�fero, cuyas habilidades para emboscar y destruir han alcanzado niveles de eficacia casi propios de una m�quina. Es un artesano de la muerte, sus mecanizaciones (letales para los desprevenidos) anuncian un nuevo amanecer en esta �poca de guerra. �Qu� hora es? �Es la hora de Clockwerk!	", R.drawable.rattletrap_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(8, "Purist Furiatrueno era un duro luchador con mucho camino recorrido y un caballero profundamente comprometido, habiendo jurado obediencia a la orden en la que hab�a crecido como escudero de caballeros veteranos de gran reputaci�n. Hab�a pasado la vida entera al servicio de la Omnisciencia, La Que Todo lo Ve. La de ellos era una sagrada lucha y tan arraigada estaba en �l su labor, que nunca la cuestion� mientras tuvo fuerza para combatir y el impetuoso valor de la juventud. Pero los largos a�os de la cruzada iban pasando, cuando sus mayores pasaban a mejor vida y eran enterrados en penosas tumbas a los lados de caminos embarrados, cuando sus hermanos ca�an en combate contra ignorantes criaturas que se negaban a someterse a la Omnisciencia, cuando sus propios escuderos eran aniquilados por emboscadas, plagas y agua no potable, empez� a cuestionarse el significado de sus votos y el de toda la cruzada. Tras una profunda meditaci�n, parti� con su ej�rcito y comenz� un largo camino de vuelta hacia los acantilados llenos de cuevas de Emauracus, y all� desafi� a los monjes de la Omnisciencia. Ning�n caballero los hab�a cuestionado antes y ellos intentaron arrojarlo al foso de los sacrificios, pero Purist no se inmut�, ya que cuando les plant� cara empez� a brillar con una luz sagrada y ellos se dieron cuenta de que la Omnisciencia hab�a decidido mostrarse a �l. El Anciano Hierofante le condujo durante semanas en un viaje hacia las profundidades, hasta llegar a la m�s profunda y sagrada c�mara donde esperaba no un concepto abstracto de sabidur�a y descubrimiento, no una reliquia esculpida que requiriese de una inyecci�n de imaginaci�n para creer, sino el Supremo en persona. No se hab�a limitado a habitar esas rocas durante millones de eones; no, �l las hab�a creado. La Omnisciencia hab�a formado la inmensa capa de mineral que rodeaba el planeta para defenderse de los numerosos terrores del espacio. De esta forma, La Que Todo lo Ve proclam� haber creado el mundo. Dadas las otras verdades reveladas a Purist en ese d�a, el caballero no ten�a raz�n alguna para refutar la historia. Quiz�s la Omnisciencia era una mentirosa que se escond�a en su profunda prisi�n de piedra y no la creadora del mundo, pero Omniknight nunca m�s cuestion� su fe. Su campa�a ten�a finalmente sentido y no cabe duda de que los gloriosos poderes que atesora y dan a sus acompa�antes fuerza en el combate, son reales y van m�s all� de toda sospecha.	", R.drawable.omniknight_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(9, "Emergiendo de la agon�a del reino sagrado de Nothl, Huskar abri� sus ojos para ver al pr�digo monje de las sombras Dazzle ejecutando un profundo encantamiento sobre �l. En contra de los antiguos ritos de la Orden Dez�n, el eterno esp�ritu de Huskar hab�a sido salvado de la eternidad, pero como todos los que encuentran a Nothl, se vio irrevocablemente transformado. Ya no estaba a merced de un cuerpo mortal, su propia sangre se convirti� en una incre�ble fuente de poder; cada gota derramada regresaba con su feroz y ardiente energ�a multiplicada por diez. Sin embargo su nuevo don enfureci� a Huskar, porque en su rescate de Nothl, Dazzle le hab�a negado un lugar entre los dioses. Le hab�a denegado su propio santo sacrificio. Era un tiempo en el que los ancianos de la orden buscaban expandir su influencia y estaban de acuerdo en que Huskar ser�a una formidable herramienta en su cruzada. Pero que el convertirse en una simple arma para la orden le arrebatara su derecho de nacimiento le contrari� aun m�s. Cuando las primeras brasas de la guerra aparecieron en el horizonte, abandon� su ancestral hogar para encontrar nuevos aliados, a la vez que buscaba una causa digna de desatar el poder que su sacrificio total podr�a traer.", R.drawable.huskar_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(10, "La sagrada ciencia de la qu�mica era una tradici�n de la familia Mezclaoscura, pero ning�n Mezclaoscura hab�a mostrado nunca la creatividad, ambici�n y temeridad del joven Razzil. Sin embargo, cuando la edad adulta llam� a su puerta, dej� a un lado el negocio familiar y prob� suerte con la fabricaci�n de oro mediante la alquimia. En un acto de audacia digno de su reputaci�n, Razzil anunci� que iba a transmutar una monta�a entera en oro. Despu�s de dos d�cadas de investigaci�n, gastos y preparaci�n, fracas� estrepitosamente, vi�ndose r�pidamente encarcelado debido a la enorme destrucci�n que provoc� su experimento. No obstante, Razzil no era de los que se toman un rev�s a la ligera, y trat� de escapar para continuar su investigaci�n. Cuando su nuevo compa�ero de celda result� ser un feroz ogro, encontr� la oportunidad que necesitaba. Tras convencer al ogro de que no se lo comiese, Razzil se puso a preparar una disoluci�n para d�rsela de beber, hecha a partir del moho y el musgo que crec�an en los muros de la prisi�n. En una semana parec�a estar lista. Cuando el ogro bebi� la poci�n, una furia imparable se apoder� de �l, destruyendo los barrotes de la celda y atravesando los muros y a los guardias por igual. Pronto se encontraron perdidos en alg�n lugar del bosque que rodea la ciudad con un rastro de escombros detr�s de ellos y ninguna se�al de persecuci�n. Al terminarse el efecto del t�nico, el ogro parec�a sereno, feliz e incluso entusiasmado. Acordando trabajar juntos, la pareja parti� a recoger los materiales necesarios para intentar la transmutaci�n alqu�mica de Razzil una vez m�s.", R.drawable.alchemist_vert);
	        bdHeroDetail.insert(b);
		}
	}*/
	
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
					//file_download("http://cdn.dota2.com/apps/dota2/images/heroes/"+namePhoto+"_vert.jpg", namePhoto+"_big.jpg");
					
					// Array de cadenas para el nombre de las fotos y la descripci�n del heroe
					String[] fotos = new String[2];
					fotos[0] = namePhoto + ".png";
					fotos[1] = namePhoto + "_big.jpg";
					fotosHeroes.put(id, fotos);
					
					generarDetalleHeroe(toSave);
					
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
	



