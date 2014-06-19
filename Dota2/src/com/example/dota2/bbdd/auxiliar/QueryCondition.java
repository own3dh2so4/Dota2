package com.example.dota2.bbdd.auxiliar;

/**
 * Clase que representa un condici�n de una sentencia SQL
 * @author David Garc�a
 * @author Daniel Serrano
 */
public class QueryCondition {

	private String columName;
	private Operator operacion;
	private Object objeto;
	
	public QueryCondition()
	{
		
	}
	
	/**
	 * Construye la condici�n con la columna, operador, y objeto
	 * (que contiene el dato a eveluar) de una sentencia SQL.
	 * @param cName Nombre de la columna de la tabla.
	 * @param ope Operaci�n de la condici�n {@link Operator}
	 * @param obj
	 */
	public QueryCondition(String cName, Operator ope, Object obj)
	{
		columName = cName;
		operacion = ope;
		objeto = obj;
	}

	/**
	 * Devuelve el operador de la condici�n WHERE
	 * @return Operador de la condici�n de la sentecia SQL
	 */
	public Operator getOperacion() {
		return operacion;
	}

	/**
	 * Devuelve el objeto que contiene el valor evaluado en la condici�n
	 * @return
	 */
	public Object getObjeto() {
		return objeto;
	}

	/**
	 * Devuelve el nombre de la columna que participa en la condici�n
	 * @return
	 */
	public String getColumName() {
		return columName;
	}

	

	
	
}
