package com.example.dota2.bbdd;

import android.content.Context;
import android.database.Cursor;

import com.example.dota2.modelo.Heroe;

public class BBDDHeroe extends BBDDAbstract<Heroe, Integer>{
	
	private static final String[] BBDDHEROE_KEY_COLUMN_NAME = new String[]{"codigo"};
	private static final String[] BBDDHEROE_COLUMN_NAMES = new String[]{"codigo","nombre","rol","foto", "gusta"};
	private static final String BBDDHEROE_TABLE_NAME = "Heroe";

	
	public BBDDHeroe(Context context)
	{
		bbdd= new HeroesSQLiteHelper(context, "DBDota", null, 1);
	}
	/*
	public void insert (Heroe hero)
	{
		SQLiteDatabase bd = bbdd.getWritableDatabase();
		
		if(bd != null)
		{
				bd.execSQL("INSERT INTO Heroe (codigo ,  nombre,  rol,  foto) " +
					"VALUES (" + hero.getId() + ", '" + hero.getName() +"', '"+ hero.getRol() +"', "+ hero.getPhoto()  + ")");
		}
		
		bd.close();
	}
	
	public Heroe findById (Integer i)
	{
		SQLiteDatabase bd = bbdd.getWritableDatabase();
		Heroe hero=null;
		if (bd!=null)
		{
			Cursor c = bd.rawQuery(" SELECT codigo, nombre, rol, foto FROM Heroe WHERE codigo=?", new String[] {i+""});
			if (c.moveToFirst())
			{
				hero = new Heroe(i, c.getString(1), c.getString(2), c.getInt(3));
			}
		}
		return hero;
	}*/

	

	@Override
	public String[] getKeyColumnName() {
		return BBDDHEROE_KEY_COLUMN_NAME;
	}

	@Override
	public Heroe creaObjecto(Cursor c) {
		
		return  new Heroe(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
	}

	@Override
	public Object[] getValores(Heroe object) {
		return new Object[]{object.getId(),object.getName(),object.getRol(),object.getPhoto(), object.getGusta()};
	}

	@Override
	public String[] getNombreColumnas() {
		return BBDDHEROE_COLUMN_NAMES;
	}

	@Override
	public String nombreTabla() {
		return BBDDHEROE_TABLE_NAME;
	}

	@Override
	public String[] getKeyValues(Integer id) {
		return new String[]{id+""};
	}



	@Override
	public String[] getKeyValuesFromObject(Heroe object) {
		return new String[]{object.getId()+""};
	}
}
