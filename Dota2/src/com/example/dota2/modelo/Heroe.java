package com.example.dota2.modelo;

/**
 * Clase que representa un heroe del juego Dota 2
 * @author David García
 * @author Daniel Serrano
 */
public class Heroe {

	private int id;
	private String name;
	// La foto que aparece en la lista.
	private String photo;
	// Papel que desempeña en el juego.
	private String rol;
	// Si pertenece a la lista de favoritos.
	private int gusta;
	
	/**
	 * Crea un nuevo objeto heroe con los datos dados.
	 * @param id Identificador único
	 * @param nombre Nombre del heroe
	 * @param rol Papel desempeñado
	 * @param foto Foto de la lista
	 * @param gusta Si es favorito o no
	 */
	public Heroe (int id, String nombre, String rol, String foto, int gusta)
	{
		setId(id);
		setName(nombre);
		setPhoto(foto);
		setRol(rol);
		this.setGusta(gusta);
	}

	/**
	 * Devuelve el nombre del heroe
	 * @return Nombre del heroe
	 */
	public String getName() {
		return name;
	}

	/**
	 * Asigna el nombre al heroe
	 * @param name Nombre nuevo del heroe
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el nombre de la foto sin la extensión
	 * @return Nombre de la foto
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * Asigna el nombre de la foto.
	 * @param photo Nombre de la foto sin extensión
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * Devuelve el papel desempeñado por el heroe
	 * @return Rol en el juego
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Asigna un rol al heroe
	 * @param rol Nuevo rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el identificador del heroe
	 * @return ID del heroe
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asigna el id del heroe
	 * @param id Nuevo id del heroe
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene si es un heroe marcado como favorito
	 * @return 0 no es favorito, 1 si.
	 */
	public int getGusta() {
		return gusta;
	}

	/**
	 * Asigna si es favorito o no
	 * @param gusta 0 no favorito, 1 si.
	 */
	public void setGusta(int gusta) {
		this.gusta = gusta;
	}

}
