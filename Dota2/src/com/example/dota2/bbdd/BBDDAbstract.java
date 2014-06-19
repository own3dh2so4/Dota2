package com.example.dota2.bbdd;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dota2.bbdd.auxiliar.Operator;
import com.example.dota2.bbdd.auxiliar.QueryCondition;

/**
 * Clase abstracta paraproveer a los objetos de la capacidad
 * de operar sus datos sobre la base de datos (patr�n Abstract Mapped)
 * @author David Garc�a
 * @author Daniel Serrano
 *
 * @param <T> Tipo del objeto que va a operar contra la base de datos.
 * @param <K> Tipo de la clave primaria del objeto que se va a usar.
 */
public abstract class BBDDAbstract<T,K> {

	// Proveedor de la base de datos de heroes
	protected HeroesSQLiteHelper bbdd;
	
	/**
	 * M�todo para poder insertar un nuevo elemento en la tabla
	 * @param object Elemento a insertar
	 */
	public void insert(T object)
	{
		SQLiteDatabase bd = bbdd.getWritableDatabase();
		
		if (bd != null)
		{
			String exec= "INSERT INTO ";
			exec= exec+ nombreTabla() + " (";
			String[] nombreColumnas = getNombreColumnas();
			exec= exec+nombreColumnas[0]+" ";
			for (int i =1; i<nombreColumnas.length; i++)
			{
				exec= exec+ "," + nombreColumnas[i]+" ";
			}
			exec=exec+") VALUES (";
			Object[] valoresInsertar = getValores(object);
			exec = exec+"'"+valoresInsertar[0]+"'";
			for (int i =1; i<valoresInsertar.length; i++)
			{
				exec= exec+ ",'" + valoresInsertar[i]+"' ";
			}
			exec=exec+")";
			bd.execSQL(exec);
		}
		bd.close();
	}
	
	/**
	 * M�todo para buscar elementos que cumplan una condici�n.
	 * @param conditions Grupo de condiciones para encontrar los elementos.
	 * @return Lista de elementos encontrados que cumplen las condiciones.
	 */
	public List<T> findByCondition(QueryCondition[] conditions)
	{
		List<T> result = new ArrayList<T>();
		SQLiteDatabase bd = bbdd.getWritableDatabase();
		String[] nombreColumnas= getNombreColumnas();
		String columnNamesWithComas = nombreColumnas[0];
		for (int i=1; i<nombreColumnas.length; i++)
		{
			columnNamesWithComas= columnNamesWithComas+" ,"+ nombreColumnas[i];
		}
		String[] conditionStr = new String[conditions.length];
		String[] valores = new String[conditions.length];
		for (int i=0; i<conditionStr.length; i++)
		{
			conditionStr[i] = conditions[i].getColumName() + " "
								+ conditions[i].getOperacion().toString() + " ?";
			valores[i] = conditions[i].getObjeto().toString();
		} 
		String condition= conditionStr[0];
		for (int i=1; i<conditionStr.length; i++)
		{
			condition=condition+" "+conditionStr[i];
		}
		String query= "SELECT "+ columnNamesWithComas+ " FROM " + nombreTabla() +" WHERE " + condition;
		Cursor c = bd.rawQuery(query, valores);
		if (c.moveToFirst())
		{
			result.add(creaObjecto(c));
			while(c.moveToNext())
				result.add(creaObjecto(c));
		}
		c.close();
		return result;
	}
	
	/**
	 * M�todo que devuelve el elemento con la clave primaria id si existe.
	 * @param id Clave primaria de elemento buscado.
	 * @return Elemento con buscado.
	 */
	public T findById(K id) {
		T result       = null;		
		String[] keyNameColum = getKeyColumnName(); 
		String[] keyValues = getKeyValues(id);
		QueryCondition[] condition = new QueryCondition[keyNameColum.length];
		for (int i=0; i< keyNameColum.length; i++)
		{
			condition[i]=new QueryCondition(keyNameColum[i],
									Operator.IG, keyValues[i]);
		}
		List<T> listaClave = findByCondition(condition);
		if (!listaClave.isEmpty())
			result= listaClave.get(0);
		return result;
	}
	
	/**
	 * M�todo para actualizar los datos de un elemento existente.
	 * @param object Elemento a modificar.
	 */
	public void update(T object)
	{
		SQLiteDatabase bd = bbdd.getWritableDatabase();
		String[] atributos = getNombreColumnas();
		String atributosComa = atributos[0];
		for (int i=1; i<atributos.length; i++)
			atributosComa= atributosComa+ ", "+ atributos[i];
		Object[] valores = getValores(object);
		String valoresComa =  atributos[0]+ " ='"+valores[0]+"'";
		for (int i=1; i<valores.length; i++)
		{
			valoresComa= valoresComa +" ,"+ atributos[i]+ " ='"+valores[i]+"'";
		}
		String[] clave = getKeyColumnName();
		String[] valoresClave = getKeyValuesFromObject(object);
		String valorClave = clave[0]+ "=" + valoresClave[0];
		for (int i=1; i<valoresClave.length; i++)
		{
			valorClave = valorClave + " ," + clave[i]+ "= '" + valoresClave[i]+"'";
		}
		String exec = "UPDATE "+ nombreTabla() + "  SET "+ valoresComa +" WHERE "+ valorClave;
		bd.execSQL(exec);
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public abstract String[] getKeyValuesFromObject(T object);

	public abstract String[] getKeyValues(K id);

	public abstract String[] getKeyColumnName() ;

	public abstract T creaObjecto(Cursor c);

	public abstract Object[] getValores(T object);

	public abstract String[] getNombreColumnas();
	
	public abstract String nombreTabla() ;

}
