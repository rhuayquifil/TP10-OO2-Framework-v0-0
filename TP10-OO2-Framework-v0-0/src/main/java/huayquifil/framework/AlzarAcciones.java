package huayquifil.framework;

import java.util.HashMap;

public interface AlzarAcciones {
	HashMap<Integer, Accion> alzar();

	int alzarMaxThreads();
}
