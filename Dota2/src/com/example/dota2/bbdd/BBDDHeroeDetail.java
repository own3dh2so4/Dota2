package com.example.dota2.bbdd;

import android.content.Context;
import android.database.Cursor;

import com.example.dota2.modelo.HeroeDetail;

public class BBDDHeroeDetail extends BBDDAbstract<HeroeDetail, Integer>{

	private static final String[] BBDDHERODETAIL_KEY_COLUMN_NAME = new String[]{"codigo"};
	private static final String[] BBDDHERODETAIL_COLUMN_NAMES = new String[]{"codigo","descripcion","foto"};
	private static final String BBDDHERODETAIL_TABLE_NAME = "HeroeDetail";
	
	public BBDDHeroeDetail(Context context)
	{
		bbdd = new HeroesSQLiteHelper(context, "DBDota", null, 1);
	}
	
	

	@Override
	public String[] getKeyValues(Integer id) {
		return new String[]{id+""};
	}

	@Override
	public String[] getKeyColumnName() {
		return BBDDHERODETAIL_KEY_COLUMN_NAME;
	}

	@Override
	public HeroeDetail creaObjecto(Cursor c) {
		return new HeroeDetail(c.getInt(0), c.getString(1),  c.getString(2));
	}

	@Override
	public Object[] getValores(HeroeDetail object) {
		return new Object[]{object.getId(), object.getDescripcion(),object.getPhoto()};
	}

	@Override
	public String[] getNombreColumnas() {
		return BBDDHERODETAIL_COLUMN_NAMES;
	}

	@Override
	public String nombreTabla() {
		return BBDDHERODETAIL_TABLE_NAME;
	}

	@Override
	public String[] getKeyValuesFromObject(HeroeDetail object) {
		return new String[]{object.getId()+""};
	}
	
	
}
