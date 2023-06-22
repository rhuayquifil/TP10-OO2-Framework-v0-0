package huayquifil.framework;

import java.util.concurrent.Callable;

public class AdapterRun implements Callable<Accion> {

	private Accion accion;

	public AdapterRun(Accion accion) {
		this.accion = accion;
	}

	@Override
	public Accion call() throws Exception {
		// TODO Auto-generated method stub
		this.accion.ejecutar();
		return null;
	}

}
