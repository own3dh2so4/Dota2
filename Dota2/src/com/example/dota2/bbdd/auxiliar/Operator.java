package com.example.dota2.bbdd.auxiliar;

/**
 * Enumerado para definir las operaciones SQL
 * MEN("<")
 * MENIG("<=")
 * IG("=")
 * MAY(">")
 * MAYIG(">=")
 * LIK("LIKE")
 * NIG("<>")
 * 
 * @author David García
 * @author Daniel Serrano
 */
public enum Operator {
	MEN("<"),
	MENIG("<="),
	IG("="),
	MAY(">"),
	MAYIG(">="),
	LIK("LIKE"),
	NIG("<>");
	
	private String representacion;
	
	Operator (String rep) {
		representacion=rep;
	}
	
	public String toString() {
		return representacion;
	}
}