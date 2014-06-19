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
		
		String desc = "Descripción por defecto";
		int id = heroe.getId();
		
		switch (id) {
		case 1:
			desc = "Como un gólem o una gárgola, Earthshaker fue uno con la tierra, pero ahora camina libremente sobre ella. A diferencia de las otras entidades, se creó a sí mismo a través de un acto de voluntad, y no sirve a ningún otro maestro. En su sueño inquieto, encerrado en una profunda fisura en la piedra, se dio cuenta de la vida que transcurría libremente por encima de él y se volvió curioso. Durante una época de temblores, los picos de Nishai fueron sacudidos, provocando derrumbamientos que cambiaron el curso de los ríos y convirtieron los valles poco profundos en abismos sin fondo. Cuando la tierra finalmente dejó de temblar, Earthshaker salió del polvo que se asentaba, apartando enormes rocas como si se tratasen de una ligera manta. Se convirtió en una bestia mortal, y se llamó a sí mismo Raigor Pezuñapétrea. Ahora sangra, respira y por lo tanto puede morir, pero su espíritu sigue siendo ese de la tierra. Su poder reside en el tótem mágico que nunca deja de lado y, el día que regrese al polvo, la tierra lo tratará como a un hijo pródigo.";
			break;
		}
		
		HeroeDetail heroeDetail = new HeroeDetail(id, desc, getFotoDetail(id));
		
		bdHeroDetail.insert(heroeDetail);
	}
	
	private String getFotoDetail(int id) {
		return (fotosHeroes != null && fotosHeroes.get(id) != null ) ? fotosHeroes.get(id)[1] : "";
	}
	
	/*public void minarDataBase(){
		if (bdHero.findById(1)==null)
		{
			Toast toast = Toast.makeText(this, "Cargando Información", Toast.LENGTH_LONG);
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
	        
	        HeroeDetail b = new HeroeDetail(1, "Como un gólem o una gárgola, Earthshaker fue uno con la tierra, pero ahora camina libremente sobre ella. A diferencia de las otras entidades, se creó a sí mismo a través de un acto de voluntad, y no sirve a ningún otro maestro. En su sueño inquieto, encerrado en una profunda fisura en la piedra, se dio cuenta de la vida que transcurría libremente por encima de él y se volvió curioso. Durante una época de temblores, los picos de Nishai fueron sacudidos, provocando derrumbamientos que cambiaron el curso de los ríos y convirtieron los valles poco profundos en abismos sin fondo. Cuando la tierra finalmente dejó de temblar, Earthshaker salió del polvo que se asentaba, apartando enormes rocas como si se tratasen de una ligera manta. Se convirtió en una bestia mortal, y se llamó a sí mismo Raigor Pezuñapétrea. Ahora sangra, respira y por lo tanto puede morir, pero su espíritu sigue siendo ese de la tierra. Su poder reside en el tótem mágico que nunca deja de lado y, el día que regrese al polvo, la tierra lo tratará como a un hijo pródigo.	", R.drawable.earthshaker_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(2, "Sven es el hijo bastardo de un Caballero de la Vigilia, nacido de un Merante Pálido y criado en las Ruinas de la Costa Sombría. Con su padre ejecutado por violar el Códice de la Vigilia y su madre rechazada por su raza salvaje, Sven cree que el honor no se encuentra en ningún orden social, sino en uno mismo. Tras cuidar a su madre durante una prolongada agonía, se ofreció como aprendiz de los Caballeros de la Vigilia, sin revelar nunca su identidad. Durante trece años estudió en la escuela de su padre, dominando el rígido código que declaró su existencia una abominación. Luego, el día en que debería haber prestado juramento, tomó la Hoja del Exiliado, destruyó el Yelmo Sagrado y quemó el Códice en la Divina Llama de la Vigilia. Se alejó de la Fortaleza de la Vigilia, siempre en solitario, siguiendo hasta la última estricta runa de su código privado. Aún sigue siendo un caballero, sí... pero un Caballero Renegado. No responde más que ante sí mismo.", R.drawable.sven_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(3, "Tras cobrar vida como un trozo de roca, los orígenes de Tiny son un misterio sobre el que especula continuamente. Ahora es un Gigante de Piedra, ¿pero qué era antes? ¿un fragmento del talón de un Gólem? ¿un pedazo sobrante del taller de un escultor de gárgolas? ¿una parte del Rostro Oracular de Garthos? Se mueve por una gran curiosidad, y viaja por todo el mundo sin descanso en busca de sus orígenes, su ascendencia y su gente. A medida que camina, aumenta de peso y de tamaño, y las fuerzas que el tiempo ejerce sobre las rocas haciéndolas más y más pequeñas causan en Tiny el efecto contrario, provocando que crezca sin cesar.", R.drawable.tiny_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(4, "Como almirante de la poderosa Armada Claddiense, Kunkka estaba a cargo de proteger las islas de su tierra natal cuando los demonios de la Catarata emprendieron una incursión en las tierras de los hombres. Tras años de pequeñas incursiones y atrevidos ataques cada vez más devastadores, la flota demoníaca llevó a todos sus buques carnívoros a la Isla Trémula. Desesperados, los Magos Suicidas de Cladd llevaron a cabo su rito definitivo, invocando a un ejército de espíritus ancestrales para proteger a su armada. Esto fue apenas suficiente para cambiar el rumbo de los acontecimientos en contra de los demonios. Mientras Kunkka los veía tomar sus embarcaciones una a una, él tuvo la satisfacción de deshacer la flota enemiga al completo con su magia ancestral. Pero en el apogeo del combate, algo en el choque entre demonios, hombres y espíritus atávicos debió despertar a una cuarta fuerza que estaba dormitando en las profundidades. Las olas se alzaron en chorros elevados en torno a los pocos navíos restantes y Maelrawn el Tentacular apareció en medio de la batalla. Sus tentáculos bambolearon a las naves, juntando a los barcos humanos y demoníacos y batiendo el agua y el viento en un furioso caos. Lo que ocurrió en el crisol de la tormenta, nadie lo puede aseverar realmente. La Catarata desapareció en el vacío, despojada de sus antiguos moradores. Kunkka es ahora el almirante de un solo barco, un fantasmagórico bote que incesantemente repite los últimos segundos de su destrucción. Si él murió en aquel choque no es posible saberlo. Ni siquiera Tidehunter, el invocador de Maelrawn, lo sabe con seguridad.	", R.drawable.kunkka_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(5, "Karroch era un chico normal y corriente. Su madre murió al dar a luz; su padre, un herrero que trabajaba para el Último Rey de Slom, murió aplastado cuando Karroch tenía cinco años. Tras eso, se vio obligado a trabajar en la casa de fieras del rey, donde creció rodeado de las bestias de la corte real: leones, monos, ciervos y cosas menos conocidas, cosas en las que apenas creía. Cuando el muchacho tenía siete años, un explorador trajo a un animal nunca antes visto. Arrastrada ante el rey entre cadenas, la bestia habló, pero su boca no se movió. Sus palabras: una súplica por la libertad. El rey se limitó a reír y ordenó a la bestia que hablase para su divertimiento. Cuando ésta se negó, la golpeó con el Cetro de la Locura y ordenó que fuese llevada de nuevo con las demás fieras. Durante los meses siguientes, Karroch trajo comida y medicinas a escondidas a la criatura herida, pero sólo logró ralentizar su empeoramiento. Sorprendentemente, la bestia habló al muchacho, y con el tiempo su vínculo se fortaleció hasta que el niño descubrió que podía mantener con ella una conversación. De hecho, ahora podía hablar con todas las criaturas de la casa de fieras del rey. La noche en que la bestia murió, la rabia se apoderó del chico. Incitó a los demás animales de la corte a rebelarse y abrió sus jaulas para que causasen estragos en los terrenos del palacio. El Rey Loco fue atacado en medio de la confusión. Durante el caos, un ciervo real se inclinó ante el muchacho que lo había liberado, y, con el Señor de las Bestias (Beastmaster) montado sobre él, saltaron los altos muros de la fortaleza y lograron escapar. Ahora como hombre, Karroch, el Señor de las Bestias, no ha perdido su habilidad de conversar con las criaturas salvajes. Se ha convertido en un guerrero en armonía con la naturaleza salvaje.", R.drawable.beastmaster_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(6, "Después de años tras la pista de un legendario Eldwurm, el Caballero Davion se encontró frente a un enemigo decepcionante: el temible Slyrak se había vuelto viejo y frágil, sus alas estaban hechas jirones, sus pocas escamas restantes afectadas por la podredumbre, sus colmillos desgastados hasta las encías y su aliento de fuego no era más amenazador que una caja de cerillas mojada. Al ver que no obtendría honor por matar al dragón, el Caballero Davion se preparó para el viaje de regreso y dejar a su viejo enemigo morir en paz. Pero una voz penetró en sus pensamientos, y Slyrak le susurró a Davion que sería un honor para él morir en combate. Davion aceptó, y se vio recompensado más allá de las expectativas por su acto de misericordia: mientras hundía su hoja en el pecho de Slyrak, el dragón atravesó la garganta de Davion con una garra. A medida que su sangre se mezclaba, Slyrak transfirió su poder a través del flujo sanguíneo, ofreciéndole al caballero toda su fuerza y siglos de sabiduría. La muerte del dragón selló su vínculo, y el Caballero Dragón (Dragon Knight) había nacido. El ancestral poder permanece dormido dentro del Caballero Dragón Davion, despertándose cuando él lo llama. O quizás es el Dragón el que llama al Caballero...", R.drawable.dragon_knight_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(7, "Perteneciente a la misma raza que Sniper y Tinker y al igual que muchos otros de la Gente Ingeniosa, Rattletrap ha sabido compensar su corta estatura con una combinación de artefactos e ingenio. Hijo del hijo de un relojero, Rattletrap fue aprendiz de ese oficio durante años antes de que la guerra descendiera de las montañas y despojase a las sencillas aldeas de unas vocaciones tan inocentes. “Tu nuevo negocio es la guerra”, dijo su moribundo padre mientras la aldea de sus ancestros yacía entre humeantes escombros. Un artesano pobre es aquel que culpa a sus herramientas, y Rattletrap nunca fue de los que buscan excusas. Tras enterrar a su padre entre las ruinas de su pueblo, emprendió su cruzada particular para transformarse en la herramienta de combate más poderosa que el mundo hubiese visto. Se prometió a sí mismo que nunca volverían a cogerle desprevenido, así que, valiéndose de su talento, construyó una armadura ensamblando piezas de relojería que, en comparación, haría que las de los caballeros de otras tierras pareciesen de hojalata. Ahora Rattletrap vive gracias a un sinfín de dispositivos; un guerrero pequeño pero mortífero, cuyas habilidades para emboscar y destruir han alcanzado niveles de eficacia casi propios de una máquina. Es un artesano de la muerte, sus mecanizaciones (letales para los desprevenidos) anuncian un nuevo amanecer en esta época de guerra. ¿Qué hora es? ¡Es la hora de Clockwerk!	", R.drawable.rattletrap_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(8, "Purist Furiatrueno era un duro luchador con mucho camino recorrido y un caballero profundamente comprometido, habiendo jurado obediencia a la orden en la que había crecido como escudero de caballeros veteranos de gran reputación. Había pasado la vida entera al servicio de la Omnisciencia, La Que Todo lo Ve. La de ellos era una sagrada lucha y tan arraigada estaba en él su labor, que nunca la cuestionó mientras tuvo fuerza para combatir y el impetuoso valor de la juventud. Pero los largos años de la cruzada iban pasando, cuando sus mayores pasaban a mejor vida y eran enterrados en penosas tumbas a los lados de caminos embarrados, cuando sus hermanos caían en combate contra ignorantes criaturas que se negaban a someterse a la Omnisciencia, cuando sus propios escuderos eran aniquilados por emboscadas, plagas y agua no potable, empezó a cuestionarse el significado de sus votos y el de toda la cruzada. Tras una profunda meditación, partió con su ejército y comenzó un largo camino de vuelta hacia los acantilados llenos de cuevas de Emauracus, y allí desafió a los monjes de la Omnisciencia. Ningún caballero los había cuestionado antes y ellos intentaron arrojarlo al foso de los sacrificios, pero Purist no se inmutó, ya que cuando les plantó cara empezó a brillar con una luz sagrada y ellos se dieron cuenta de que la Omnisciencia había decidido mostrarse a él. El Anciano Hierofante le condujo durante semanas en un viaje hacia las profundidades, hasta llegar a la más profunda y sagrada cámara donde esperaba no un concepto abstracto de sabiduría y descubrimiento, no una reliquia esculpida que requiriese de una inyección de imaginación para creer, sino el Supremo en persona. No se había limitado a habitar esas rocas durante millones de eones; no, Él las había creado. La Omnisciencia había formado la inmensa capa de mineral que rodeaba el planeta para defenderse de los numerosos terrores del espacio. De esta forma, La Que Todo lo Ve proclamó haber creado el mundo. Dadas las otras verdades reveladas a Purist en ese día, el caballero no tenía razón alguna para refutar la historia. Quizás la Omnisciencia era una mentirosa que se escondía en su profunda prisión de piedra y no la creadora del mundo, pero Omniknight nunca más cuestionó su fe. Su campaña tenía finalmente sentido y no cabe duda de que los gloriosos poderes que atesora y dan a sus acompañantes fuerza en el combate, son reales y van más allá de toda sospecha.	", R.drawable.omniknight_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(9, "Emergiendo de la agonía del reino sagrado de Nothl, Huskar abrió sus ojos para ver al pródigo monje de las sombras Dazzle ejecutando un profundo encantamiento sobre él. En contra de los antiguos ritos de la Orden Dezún, el eterno espíritu de Huskar había sido salvado de la eternidad, pero como todos los que encuentran a Nothl, se vio irrevocablemente transformado. Ya no estaba a merced de un cuerpo mortal, su propia sangre se convirtió en una increíble fuente de poder; cada gota derramada regresaba con su feroz y ardiente energía multiplicada por diez. Sin embargo su nuevo don enfureció a Huskar, porque en su rescate de Nothl, Dazzle le había negado un lugar entre los dioses. Le había denegado su propio santo sacrificio. Era un tiempo en el que los ancianos de la orden buscaban expandir su influencia y estaban de acuerdo en que Huskar sería una formidable herramienta en su cruzada. Pero que el convertirse en una simple arma para la orden le arrebatara su derecho de nacimiento le contrarió aun más. Cuando las primeras brasas de la guerra aparecieron en el horizonte, abandonó su ancestral hogar para encontrar nuevos aliados, a la vez que buscaba una causa digna de desatar el poder que su sacrificio total podría traer.", R.drawable.huskar_vert);
	        bdHeroDetail.insert(b);
	        b = new HeroeDetail(10, "La sagrada ciencia de la química era una tradición de la familia Mezclaoscura, pero ningún Mezclaoscura había mostrado nunca la creatividad, ambición y temeridad del joven Razzil. Sin embargo, cuando la edad adulta llamó a su puerta, dejó a un lado el negocio familiar y probó suerte con la fabricación de oro mediante la alquimia. En un acto de audacia digno de su reputación, Razzil anunció que iba a transmutar una montaña entera en oro. Después de dos décadas de investigación, gastos y preparación, fracasó estrepitosamente, viéndose rápidamente encarcelado debido a la enorme destrucción que provocó su experimento. No obstante, Razzil no era de los que se toman un revés a la ligera, y trató de escapar para continuar su investigación. Cuando su nuevo compañero de celda resultó ser un feroz ogro, encontró la oportunidad que necesitaba. Tras convencer al ogro de que no se lo comiese, Razzil se puso a preparar una disolución para dársela de beber, hecha a partir del moho y el musgo que crecían en los muros de la prisión. En una semana parecía estar lista. Cuando el ogro bebió la poción, una furia imparable se apoderó de él, destruyendo los barrotes de la celda y atravesando los muros y a los guardias por igual. Pronto se encontraron perdidos en algún lugar del bosque que rodea la ciudad con un rastro de escombros detrás de ellos y ninguna señal de persecución. Al terminarse el efecto del tónico, el ogro parecía sereno, feliz e incluso entusiasmado. Acordando trabajar juntos, la pareja partió a recoger los materiales necesarios para intentar la transmutación alquímica de Razzil una vez más.", R.drawable.alchemist_vert);
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
					int id = c.getInt("id");
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
					file_download("http://cdn.dota2.com/apps/dota2/images/heroes/"+namePhoto+"_vert.jpg", namePhoto+"_big.jpg");
					
					// Array de cadenas para el nombre de las fotos y la descripción del heroe
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
}
	



