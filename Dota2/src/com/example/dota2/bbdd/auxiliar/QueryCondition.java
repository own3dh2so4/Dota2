package com.example.dota2.bbdd.auxiliar;

/**
 * Clase que representa un condición de una sentencia SQL
 * @author David García
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
	 * Construye la condición con la columna, operador, y objeto
	 * (que contiene el dato a eveluar) de una sentencia SQL.
	 * @param cName Nombre de la columna de la tabla.
	 * @param ope Operación de la condición {@link Operator}
	 * @param obj
	 */
	public QueryCondition(String cName, Operator ope, Object obj)
	{
		columName = cName;
		operacion = ope;
		objeto = obj;
	}

	/**
	 * Devuelve el operador de la condición WHERE
	 * @return Operador de la condición de la sentecia SQL
	 */
	public Operator getOperacion() {
		return operacion;
	}

	/**
	 * Devuelve el objeto que contiene el valor evaluado en la condición
	 * @return
	 */
	public Object getObjeto() {
		return objeto;
	}

	/**
	 * Devuelve el nombre de la columna que participa en la condición
	 * @return
	 */
	public String getColumName() {
		return columName;
	}

	

	
	
}
