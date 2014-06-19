package com.example.dota2.modelo;

/**
 * Clase que representa la información particular de un heroe
 * @author David García
 * @author Daniel Serrano
 */
public class HeroeDetail {
	private int id;
	private String descripcion;
	private String photo;
	
	/**
	 * Construye el objeto con los detalles del heroe con id
	 * @param id Identificador único del heroe
	 * @param desc Descripción del heroe
	 * @param photo Nombre de la foto del detalle del heroe
	 */
	public HeroeDetail(int id, String desc, String photo)
	{
		this.setId(id);
		this.setDescripcion(desc);
		this.setPhoto(photo);
	}

	/**
	 * Obtiene el identificador del heroe asociado
	 * @return ID del heroe
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asigna el id del heroe asociado
	 * @param id ID del heroe asociado
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el nombre de la foto
	 * @return Nombre de la foto
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * Asigna el nombre de la foto.
	 * @param photo Nombre de la foto
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
