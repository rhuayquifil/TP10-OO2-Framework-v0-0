package huayquifil.main;

import huayquifil.framework.AlzarAccionesDeArchivo;
import huayquifil.framework.Framework;

public class Main {

	public static void main(String[] args) {

		Framework framework = new Framework(new AlzarAccionesDeArchivo("/acciones.properties"));
		framework.init();
	}

}
