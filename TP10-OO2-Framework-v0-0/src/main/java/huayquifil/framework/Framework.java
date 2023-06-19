package huayquifil.framework;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Framework {

	private AlzarAcciones acciones;
	private HashMap<Integer, Accion> listaAcciones;

	public Framework(AlzarAcciones acciones) {
		Objects.requireNonNull(acciones);
		this.acciones = acciones;
	}

	public void init() {

		// alza las acciones
		listaAcciones = acciones.alzar();

		// alza tambien la accion de salir
		listaAcciones.put(listaAcciones.size(), new AccionSalir());

		// crea al menu
		System.out.println("Bienvenido, Que desea hacer?");
		int i;
		for (i = 0; i < listaAcciones.size(); i++) {
			System.out.println(i + 1 + ". " + listaAcciones.get(i).nombreItemMenu() + " ("
					+ listaAcciones.get(i).descripcionItemMenu() + ")");
		}

		// se pone a leer el input del usuario
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingresa un numero entero: ");
		int numeroOpcion = scanner.nextInt();
		numeroOpcion -= 1;

		if ((numeroOpcion >= 0) && (numeroOpcion < listaAcciones.size())) {
			System.out.println(listaAcciones.get(numeroOpcion).nombreItemMenu());
		} else {
			System.out.println("Opcion Invalida");
		}
	}
}
