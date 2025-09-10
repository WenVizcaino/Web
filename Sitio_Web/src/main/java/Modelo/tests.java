package Modelo;

import Controlador.Conexion;

public class tests {
	public static void main(String[] args) {
		Conexion test = new Conexion();
		if (test.getConnection() != null) {
			System.out.println("conectado a a la base de datos");

		} else {
			System.out.println("No conectado");
		}
	}
}
