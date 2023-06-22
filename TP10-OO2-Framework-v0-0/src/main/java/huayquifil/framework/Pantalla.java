package huayquifil.framework;

import java.util.ArrayList;
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

	private List<Accion> listaAcciones;

	public Pantalla(List<Accion> listaAcciones) {
		this.listaAcciones = listaAcciones;
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

		}

		Panel panel = new Panel();
		CheckBox check = null;
		for (Accion accion : listaAcciones) {
			// creo y agrego el check a el panel
			check = new CheckBox(accion.nombreItemMenu());
			panel.addComponent(check);

			panel.addComponent(new Label(accion.descripcionItemMenu())).setLayoutData(
					GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.END));
		}

		panel.addComponent(new Button("Confirmar", new Runnable() {
			public void run() {

				List<Callable<AdapterRun>> listaAccionesSeleccionadas = new ArrayList<Callable<AdapterRun>>();

				int i = 0;
				for (Component component : panel.getChildren()) {
					if (component instanceof CheckBox) {
						CheckBox check = (CheckBox) component;
						if (check.isChecked()) {
							Callable<AdapterRun> accionAdapter = new AdapterRun(listaAcciones.get(i));
							listaAccionesSeleccionadas.add(accionAdapter);
						}
						i++;
					}
				}

				ExecutorService executor = Executors.newFixedThreadPool(1);

				try {
					executor.invokeAll(listaAccionesSeleccionadas);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				executor.shutdown();

			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
}
