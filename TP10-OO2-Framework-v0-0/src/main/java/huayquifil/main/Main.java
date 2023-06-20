package huayquifil.main;

import huayquifil.framework.AlzarAccionesDeArchivo;
import huayquifil.framework.Framework;

public class Main {

	public static void main(String[] args) {

//		Framework framework = new Framework(new AlzarAccionesDeArchivo("/acciones.properties"));

		Framework framework = new Framework(new AlzarAccionesDeArchivo(
				"C:\\Users\\ezehu\\git\\TP10-OO2-Framework-v0-0\\TP10-OO2-Framework-v0-0\\src\\main\\resources\\acciones.json"));

		framework.init();
	}

}
