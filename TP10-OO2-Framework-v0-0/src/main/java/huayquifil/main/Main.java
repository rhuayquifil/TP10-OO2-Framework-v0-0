package huayquifil.main;

import huayquifil.framework.CargarAccionesDeArchivo;
import huayquifil.framework.Framework;

public class Main {

	public static void main(String[] args) {

		Framework framework = new Framework(new CargarAccionesDeArchivo(""));
		framework.init();
	}

}
