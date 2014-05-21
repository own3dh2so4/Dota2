package com.example.dota2.bbdd.auxiliar;

public enum Operator {
	MEN("<"),MENIG("<="),IG("="),MAY(">"),MAYIG(">="), LIK("LIKE"),NIG("<>");
	
	private String representacion;
	
	Operator (String rep)
	{
		representacion=rep;
	}
	
	public String toString()
	{
		return representacion;
	}
}