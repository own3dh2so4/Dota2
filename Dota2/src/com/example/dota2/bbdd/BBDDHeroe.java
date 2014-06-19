package com.example.dota2.bbdd;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;

import com.example.dota2.bbdd.auxiliar.Operator;
import com.example.dota2.bbdd.auxiliar.QueryCondition;
import com.example.dota2.modelo.Heroe;

/**
 * Clase que se encarga de gestionar la tabla de heroes de la base de datos.
 * Define la tabla, sus campos y operaciones generales sobre ella.
 * @author David García
 * @author Daniel Serrano
 */
public class BBDDHeroe extends BBDDAbstract<Heroe, Integer>{
	
	private static final String[] BBDDHEROE_KEY_COLUMN_NAME = new String[]{"codigo"};
	private static final String[] BBDDHEROE_COLUMN_NAMES = new String[]{"codigo","nombre","rol","foto", "gusta"};
	private static final String BBDDHEROE_TABLE_NAME = "Heroe";

	/**
	 * Con ayuda del contexto abre la conexi�n con la base de datos.
	 * @param context Contexto de la app
	 */
	public BBDDHeroe(Context context)
	{
		bbdd= new HeroesSQLiteHelper(context, "DBDota", null, 1);
	}
	
	/**
	 * Devuelve la primary key de la tabla.
	 * @return Primary key
	 */
	@Override
	public String[] getKeyColumnName() {
		return BBDDHEROE_KEY_COLUMN_NAME;
	}

	/**
	 * Crea un objeto del timpo que puede almacenar la tabla
	 * a partir de un cursor.
	 * 
	 * @return Ojeto heroe creado.
	 */
	@Override
	public Heroe creaObjecto(Cursor c) {
		
		return  new Heroe(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
	}

	/**
	 * Crea un array con los datos de un heroe.
	 * @param Heroe del que generar el array de datos.
	 * @return Datos del heroe
	 */
	@Override
	public Object[] getValores(Heroe object) {
		return new Object[]{object.getId(),object.getName(),object.getRol(),object.getPhoto(), object.getGusta()};
	}

	/**
	 * Devuelve las columnas de la tabla.
	 * @return Columnas de la tabla.
	 */
	@Override
	public String[] getNombreColumnas() {
		return BBDDHEROE_COLUMN_NAMES;
	}

	/**
	 * Devuelve el nombre de la tabla.
	 * @return Nombre de la tabla.
	 */
	@Override
	public String nombreTabla() {
		return BBDDHEROE_TABLE_NAME;
	}

	/**
	 * 
	 */
	@Override
	public String[] getKeyValues(Integer id) {
		return new String[]{id+""};
	}

	/**
	 * Devulve las primary keys de un heroe
	 * @return Lista de primary keys
	 */
	@Override
	public String[] getKeyValuesFromObject(Heroe object) {
		return new String[]{object.getId()+""};
	}
	
	/**
	 * Devuelve los heroes marcados como favoritos
	 * @return Heroes favoritos
	 */
	public List<Heroe> buscarFavoritos(){
		List<Heroe> ret= new  Vector<Heroe>();
		QueryCondition condicion = new QueryCondition("gusta", Operator.IG, 1);
		ret = findByCondition(new QueryCondition[] {condicion});
		return ret;
	}
	
	/**
	 * Devuelve todos los heroes de la base de datos
	 * @return Lista con todos los heroes
	 */
	public List<Heroe> buscarTodos(){
		List<Heroe> ret= new  Vector<Heroe>();
		QueryCondition condicion = new QueryCondition(BBDDHEROE_COLUMN_NAMES[0], Operator.NIG, "");
		ret = findByCondition(new QueryCondition[] {condicion});
		return ret;
	}
	
	/**
	 * Devuelve los heroes que coincidan con un nombre
	 * @param nombre Nombre coincidente en la búsqueda
	 * @return Lista de heroes con coincidencia en el nombre
	 */
	public List<Heroe> buscarPorNombre(String nombre){
		List<Heroe> ret= new  Vector<Heroe>();
		QueryCondition condicion = new QueryCondition("nombre", Operator.LIK, "%"+nombre+"%");
		ret = findByCondition(new QueryCondition[] {condicion});
		return ret;
	}
	
	
}
