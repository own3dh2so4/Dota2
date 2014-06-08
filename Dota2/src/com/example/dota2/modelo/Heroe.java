package com.example.dota2.modelo;



public class Heroe {

	private int id;
	private String name;
	private String photo;
	private String rol;
	private int gusta;
	
	public Heroe (int id, String nombre, String rol, String foto, int gusta)
	{
		setId(id);
		setName(nombre);
		setPhoto(foto);
		setRol(rol);
		this.setGusta(gusta);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGusta() {
		return gusta;
	}

	public void setGusta(int gusta) {
		this.gusta = gusta;
	}

}
