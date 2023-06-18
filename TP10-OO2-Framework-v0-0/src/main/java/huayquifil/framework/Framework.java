package huayquifil.framework;

import java.util.HashMap;
import java.util.Objects;

public class Framework {

	private CargarAcciones acciones;
	private HashMap<Integer, Accion> listaAcciones;

	public Framework(CargarAcciones acciones) {
		Objects.requireNonNull(acciones);
		this.acciones = acciones;
	}

	public void init() {
		listaAcciones = acciones.llenar();
		iniciarMenu();
	}

	private void iniciarMenu() {
		System.out.println("Bienvenido, ¿Que desea hacer?");
		int i;
		for (i = 0; i < listaAcciones.size(); i++) {
			System.out.println(i + 1 + ". " + listaAcciones.get(i).nombreItemMenu() + " ("
					+ listaAcciones.get(i).descripcionItemMenu() + ")");
		}
	}

}
