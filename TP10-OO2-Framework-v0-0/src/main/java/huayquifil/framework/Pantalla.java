package huayquifil.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBox;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Pantalla {

	private HashMap<Integer, Accion> listaAcciones;
	private List<Accion> arrayListaAcciones;
	private int maxThreads;

	private AlzarAcciones acciones;

//	public Pantalla(List<Accion> listaAcciones) {
//		this.listaAcciones = listaAcciones;
//		this.maxThreads = -1;
//	}

	public Pantalla(AlzarAcciones acciones) {
		this.acciones = acciones;

		this.listaAcciones = acciones.alzar();
		this.listaAcciones.put(listaAcciones.size(), new AccionSalir());
	}

	private List<Accion> arrayListDeAcciones() {
		ArrayList<Accion> acciones = new ArrayList<>();

		for (int i = 0; i < listaAcciones.size(); i++) {
			acciones.add(listaAcciones.get(i));
		}
		return acciones;
	}

	public void mostrar() {

		DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
		Screen screen = null;
		Window window = new BasicWindow("Bienvenido, ¿Que desea hacer?");
		WindowBasedTextGUI textGUI = null;

		try {
			screen = terminalFactory.createScreen();
			screen.startScreen();
			textGUI = new MultiWindowTextGUI(screen);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Panel panel = new Panel();
		CheckBox check = null;
		for (Accion accion : arrayListDeAcciones()) {

			// creo y agrego el check con el nombre y un label a el panel
			check = new CheckBox(accion.nombreItemMenu());

			panel.addComponent(check);

			panel.addComponent(new Label(accion.descripcionItemMenu())).setLayoutData(
					GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.END));
		}

		panel.addComponent(new Button("Confirmar", new Runnable() {

			public void run() {

				// creo una lista de adaptadores
				List<Callable<AdapterRun>> listaAccionesSeleccionadas = new ArrayList<Callable<AdapterRun>>();

				// se recorren los componentes del panel para verificar el estado de los check
				int i = 0;
				for (Component component : panel.getChildren()) {

					i = verificarCheck(listaAccionesSeleccionadas, i, component);
				}

				// se ejecutan las acciones
				ejecutarAcciones(listaAccionesSeleccionadas);

			}

			private int verificarCheck(List<Callable<AdapterRun>> listaAccionesSeleccionadas, int i,
					Component component) {

				if (component instanceof CheckBox) {
					CheckBox check = (CheckBox) component;

					if (check.isChecked()) {
						// se crea un adaptador de la accion y se agrega a la lista
						Callable<AdapterRun> accionAdapter = new AdapterRun(listaAcciones.get(i));
						listaAccionesSeleccionadas.add(accionAdapter);
					}
					i++;
				}
				return i;
			}

			private void ejecutarAcciones(List<Callable<AdapterRun>> listaAccionesSeleccionadas) {
				try {
					if (acciones.alzarMaxThreads() == -1) {
						for (Callable<AdapterRun> callable : listaAccionesSeleccionadas) {
							callable.call();
						}
					} else {
						ExecutorService executor = Executors.newFixedThreadPool(acciones.alzarMaxThreads());

						executor.invokeAll(listaAccionesSeleccionadas);

						executor.shutdown();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

//	public Pantalla(List<Accion> listaAcciones, int maxThreads) {
//		this.listaAcciones = listaAcciones;
//		this.maxThreads = maxThreads;
//	}

//	public void mostrar() {
//
//		DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
//		Screen screen = null;
//		Window window = new BasicWindow("Bienvenido, ¿Que desea hacer?");
//		WindowBasedTextGUI textGUI = null;
//
//		try {
//			screen = terminalFactory.createScreen();
//			screen.startScreen();
//			textGUI = new MultiWindowTextGUI(screen);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Panel panel = new Panel();
//		CheckBox check = null;
//		for (Accion accion : listaAcciones) {
//
//			// creo y agrego el check con el nombre y un label a el panel
//			check = new CheckBox(accion.nombreItemMenu());
//
//			panel.addComponent(check);
//
//			panel.addComponent(new Label(accion.descripcionItemMenu())).setLayoutData(
//					GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.END));
//		}
//
//		panel.addComponent(new Button("Confirmar", new Runnable() {
//
//			public void run() {
//
//				// creo una lista de adaptadores
//				List<Callable<AdapterRun>> listaAccionesSeleccionadas = new ArrayList<Callable<AdapterRun>>();
//
//				// se recorren los componentes del panel para verificar el estado de los check
//				int i = 0;
//				for (Component component : panel.getChildren()) {
//
//					if (component instanceof CheckBox) {
//						CheckBox check = (CheckBox) component;
//
//						if (check.isChecked()) {
//							// se crea un adaptador de la accion y se agrega a la lista
//							Callable<AdapterRun> accionAdapter = new AdapterRun(listaAcciones.get(i));
//							listaAccionesSeleccionadas.add(accionAdapter);
//						}
//						i++;
//					}
//				}
//
//				if (maxThreads == -1) {
//					for (Callable<AdapterRun> callable : listaAccionesSeleccionadas) {
//						try {
//							callable.call();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				} else {
//					ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
//
//					try {
//						executor.invokeAll(listaAccionesSeleccionadas);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					executor.shutdown();
//				}
//
//			}
//		}));
//
//		window.setComponent(panel);
//		textGUI.addWindowAndWait(window);
//	}
}
