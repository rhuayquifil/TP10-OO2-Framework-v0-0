package huayquifil.framework;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class AdapterRun implements Callable {

	private Accion accion;

	public AdapterRun(Accion accion) {
		this.accion = accion;
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		TimeUnit.MILLISECONDS.sleep(2000);
		this.accion.ejecutar();
		return null;
	}

}
