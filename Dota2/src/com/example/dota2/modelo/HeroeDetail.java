package com.example.dota2.modelo;

public class HeroeDetail {
	private int id;
	private String descripcion;
	private String photo;
	
	public HeroeDetail(int id, String desc, String photo)
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
