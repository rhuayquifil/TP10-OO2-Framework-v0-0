package huayquifil.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class AlzarAccionesDeArchivo implements AlzarAcciones {

	private String filePath;

	public AlzarAccionesDeArchivo(String filePath) {
		Objects.requireNonNull(filePath);

		if (filePath.isEmpty() || filePath.isBlank()) {
			throw new RuntimeException("path no encontrado");
		}

		this.filePath = filePath;
	}

	@Override
	public HashMap<Integer, Accion> alzar() {
		HashMap<Integer, Accion> lista = new HashMap<>();

		if (isJSONFile()) {
			return alzarDeArchivoJSON(lista);
		}
		return alzarDeArchivoProperties(lista);
	}

	private HashMap<Integer, Accion> alzarDeArchivoJSON(HashMap<Integer, Accion> lista) {
		Gson gson = new GsonBuilder().create();
		int i = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
			JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
			JsonArray jsonArray = jsonObject.getAsJsonArray("acciones");
			for (JsonElement jsonElement : jsonArray) {
				String clase = jsonElement.getAsString();
				Accion nuevaAccion = (Accion) Class.forName(clase).getDeclaredConstructor().newInstance();
				lista.put(i, nuevaAccion);
				i++;
			}
		} catch (IOException | JsonSyntaxException | JsonIOException | InstantiationException | IllegalAccessException
				| ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	private boolean isJSONFile() {
		Path path = Path.of(this.filePath);
		String fileName = path.getFileName().toString();
		return fileName.endsWith(".json");
	}

	private HashMap<Integer, Accion> alzarDeArchivoProperties(HashMap<Integer, Accion> lista) {
		Properties properties = new Properties();

		// lee el archivo de properties y va generando la lista con todas las
		// implementaciones propias del usuario del framework
		try (InputStream config = getClass().getResourceAsStream(this.filePath);) {
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
