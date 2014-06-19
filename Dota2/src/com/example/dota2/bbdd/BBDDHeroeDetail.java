package com.example.dota2.bbdd;

import android.content.Context;
import android.database.Cursor;

import com.example.dota2.modelo.HeroeDetail;

/**
 * Clase que se encarga de gestionar la tabla de detalles de los heroes
 * de la base de datos.
 * Define la tabla, sus campos y operaciones generales sobre ella.
 * @author David García
 * @author Daniel Serrano
 */
public class BBDDHeroeDetail extends BBDDAbstract<HeroeDetail, Integer>{

	private static final String[] BBDDHERODETAIL_KEY_COLUMN_NAME = new String[]{"codigo"};
	private static final String[] BBDDHERODETAIL_COLUMN_NAMES = new String[]{"codigo","descripcion","foto"};
	private static final String BBDDHERODETAIL_TABLE_NAME = "HeroeDetail";
	
	/**
	 * Con ayuda del contexto abre la conexi�n con la base de datos.
	 * @param context Contexto de la app
	 */
	public BBDDHeroeDetail(Context context)
	{
		bbdd = new HeroesSQLiteHelper(context, "DBDota", null, 1);
	}

	@Override
	public String[] getKeyValues(Integer id) {
		return new String[]{id+""};
	}

	/**
	 * Devuelve la primary key de la tabla.
	 * @return Primary key
	 */
	@Override
	public String[] getKeyColumnName() {
		return BBDDHERODETAIL_KEY_COLUMN_NAME;
	}

	/**
	 * Crea un objeto del timpo que puede almacenar la tabla
	 * a partir de un cursor.
	 * 
	 * @return Ojeto heroe creado.
	 */
	@Override
	public HeroeDetail creaObjecto(Cursor c) {
		return new HeroeDetail(c.getInt(0), c.getString(1),  c.getString(2));
	}

	/**
	 * Crea un array con los datos de un heroe.
	 * @param Heroe del que generar el array de datos.
	 * @return Datos del heroe
	 */
	@Override
	public Object[] getValores(HeroeDetail object) {
		return new Object[]{object.getId(), object.getDescripcion(),object.getPhoto()};
	}

	/**
	 * Devuelve las columnas de la tabla.
	 * @return Columnas de la tabla.
	 */
	@Override
	public String[] getNombreColumnas() {
		return BBDDHERODETAIL_COLUMN_NAMES;
	}

	/**
	 * Devuelve el nombre de la tabla.
	 * @return Nombre de la tabla.
	 */
	@Override
	public String nombreTabla() {
		return BBDDHERODETAIL_TABLE_NAME;
	}

	/**
	 * Devulve las primary keys de un heroeDetail
	 * @return Lista de primary keys
	 */
	@Override
	public String[] getKeyValuesFromObject(HeroeDetail object) {
		return new String[]{object.getId()+""};
	}
}
