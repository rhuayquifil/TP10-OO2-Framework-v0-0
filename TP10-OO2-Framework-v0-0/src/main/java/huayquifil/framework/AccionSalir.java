package huayquifil.framework;

public class AccionSalir implements Accion {

	@Override
	public void ejecutar() {
		System.out.println("Ejecutando AccionSalir...");
	}

	@Override
	public String nombreItemMenu() {
		return "Accion Salir";
	}

	@Override
	public String descripcionItemMenu() {
		return "Esto sale del programa...";
	}

}
