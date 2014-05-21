package com.example.dota2.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HeroesSQLiteHelper extends SQLiteOpenHelper{

	private String sqlCreate = "CREATE TABLE Heroe (codigo INTEGER PRIMARY KEY, nombre TEXT, rol TEXT, foto TEXT, gusta INTEGER)";
	private String sqlCreateDetail = "CREATE TABLE HeroeDetail (codigo INTEGER PRIMARY KEY, descripcion TEXT, foto INTEGER)";

	
	public HeroesSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate);	
		db.execSQL(sqlCreateDetail);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Heroe");
		db.execSQL(sqlCreate);
		
	}

}
