package com.example.dota2.modelo;

public class HeroeDetail {
	private int id;
	private String descripcion;
	private int photo;
	
	public HeroeDetail(int id, String desc, int photo)
	{
		this.setId(id);
		this.setDescripcion(desc);
		this.setPhoto(photo);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPhoto() {
		return photo;
	}

	public void setPhoto(int photo) {
		this.photo = photo;
	}
}
