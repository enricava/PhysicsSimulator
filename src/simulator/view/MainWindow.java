package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")

public class MainWindow extends JFrame {
	Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//INICIALIZACION DE COMPONENTES
		ControlPanel ctrlPanel = new ControlPanel(_ctrl);
		BodiesTable bTable = new BodiesTable(_ctrl);
		Viewer viewer = new Viewer(_ctrl);
		StatusBar statBar = new StatusBar(_ctrl);
		
//DIMENSIONES PREDEFINIDAS
		
		ctrlPanel.setPreferredSize(new Dimension(500, 40));
		bTable.setPreferredSize(new Dimension(500,180));
		viewer.setPreferredSize(new Dimension(500,530));
		statBar.setPreferredSize(new Dimension(500, 30));

//BTABLE + VIEWER -> CENTER BOX
		
		JPanel centerBox = new JPanel();
		centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));
		
		centerBox.add(bTable);
		centerBox.setBackground(Color.DARK_GRAY);
		centerBox.add(viewer);
		
//COMPONENTES -> MAIN PANEL
		mainPanel.add(ctrlPanel, BorderLayout.PAGE_START);
		mainPanel.add(statBar, BorderLayout.PAGE_END);
		mainPanel.add(centerBox, BorderLayout.CENTER);


//DIMENSION TIPICA DE VENTANA
		
		//setUndecorated(true);
		this.setSize(new Dimension(800, 800));
		this.setVisible(true);
		
	}

}