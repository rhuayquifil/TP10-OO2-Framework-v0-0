package huayquifil.framework;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class CargarAccionesDeArchivo implements CargarAcciones {

	private String path;

	public CargarAccionesDeArchivo(String path) {
		Objects.requireNonNull(path);

		if (path.isEmpty() || path.isBlank()) {
			throw new RuntimeException("path no encontrado");
		}

		this.path = path;
	}

	@Override
	public HashMap<Integer, Accion> llenar() {
		Properties properties = new Properties();

		HashMap<Integer, Accion> lista = new HashMap<>();

		try (InputStream config = getClass().getResourceAsStream(this.path);) {
			properties.load(config);

			String archivo = properties.getProperty("acciones");

			String[] clases = archivo.split("; ");

			int i = 0;

			for (String string : clases) {
				Accion nuevaAccion = (Accion) Class.forName(string).getDeclaredConstructor().newInstance();
				lista.put(i, nuevaAccion);
				i++;
			}

		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println(e.getMessage());
		}

		return lista;
	}

}
