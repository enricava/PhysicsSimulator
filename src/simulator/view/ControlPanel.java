package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.model.Body;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel{

	private final String[] _opciones = { "Nlug: Newton's law of universal gravitation", "Ng: No gravity",
	"Ftcg: Falling to center gravity" };
	
	private Controller _ctrl;

	private JButton _btnOpenFile, _gButton, _playButton, _stopButton, _exitButton;
	private JSpinner _spinner, _delay;
	private JTextField _deltaText;
	private JToolBar _toolBar;
	private volatile Thread _thread;

	ControlPanel(Controller ctrl) {
		
		_ctrl = ctrl;
		
		_toolBar = new JToolBar();
		_btnOpenFile = new JButton();
		_gButton = new JButton();
		_playButton = new JButton();
		_stopButton = new JButton();
		_exitButton = new JButton();
		_deltaText = new JTextField("1000");
		_spinner = new JSpinner(new SpinnerNumberModel(5000, 0, 20000, 5000));
		_delay = new JSpinner(new SpinnerNumberModel(1,0,1000,1));
		
		initGUI();
	}

	private void initGUI() {
		
//OPERACIONES BASICAS DEL PANEL DE CONTROL		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
// BUTTON FILE CHOOSER (1)
		_btnOpenFile.setIcon(new ImageIcon("resources/icons/open.png"));
		_btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenFileAction();
			}
		});
		_btnOpenFile.setToolTipText("Seleccionar archivo de entrada");

// GRAVITY BUTTON (2)
		_gButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		_gButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gButtonAction();
			}
		});

		_gButton.setToolTipText("Select gravity law");

// PLAY BUTTON (3)
		_playButton.setIcon(new ImageIcon("resources/icons/run.png"));
		_playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { // Desactivar botones
				playButtonAction();
			}

		});
		_playButton.setToolTipText("Run simulation");

// STOP BUTTON (4)
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { // Desactivar botones
				if (_thread != null) {
					_thread.interrupt();
					_thread = null;
				}
				return;
			}
		});
		_stopButton.setToolTipText("Stop simulation");
		
// DELTA-TIME (6)

// EXIT BUTTON (7)
		_exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { // Desactivar botones
				System.exit(0);
			}
		});
		_exitButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				_exitButton.setToolTipText("Exit");
			}
		});
		
//BUTTONS->TOOLBAR
		
		_toolBar.add(_btnOpenFile); 	//(1)
		_toolBar.addSeparator();
		_toolBar.add(_gButton); 		//(2)
		_toolBar.addSeparator();
		_toolBar.add(_playButton); 		//(3)
		_toolBar.addSeparator();
		_toolBar.add(_stopButton); 		//(4)
		_toolBar.addSeparator();
		
		JLabel delay = new JLabel("Delay: ");
		_toolBar.add(delay);
		_toolBar.addSeparator();
		_toolBar.add(_delay);
		_toolBar.addSeparator();
		
		JLabel steps = new JLabel("Steps: "); 	//Etiqueta (5)
		_toolBar.add(steps);
		_toolBar.addSeparator();
		_toolBar.add(_spinner); 
		_toolBar.addSeparator();//(5)
		
		JLabel deltaLabel = new JLabel("Delta-time: "); 	//Etiqueta (6)
		_toolBar.add(deltaLabel);
		_toolBar.addSeparator();
		_toolBar.add(_deltaText);
		_toolBar.addSeparator();//(6)
		
		JLabel emptySpace = new JLabel();
		emptySpace.setPreferredSize(new Dimension(100, 10));
		_toolBar.add(emptySpace);
		
		_toolBar.add(_exitButton); 		//(7)
		
//TOOLBAR->JFRAME
		this.add(_toolBar);
	}

/********************************
* 	ACCIONES DE LOS BOTONES
*********************************/

	public void btnOpenFileAction() {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setDialogTitle("Seleccion de archivos");
		filechooser.setSize(120, 60);
		filechooser.setVisible(true);
		
		//SOLO EN EL ORDENADOR DE ENRIQUE CAVANILLAS
		filechooser.setCurrentDirectory(new File("D:/eclipse/workbenches/PRACTICA 2/PhysicsSimulator-Interface/resources/examples"));
		
		int ret = filechooser.showOpenDialog(ControlPanel.this);

		if (ret == JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(ControlPanel.this,
					"Se ha seleccionado el archivo:" + filechooser.getSelectedFile());
			_ctrl.reset();
			try {
				_ctrl.loadBodies(new FileInputStream(filechooser.getSelectedFile()));
			} catch (FileNotFoundException e1) {
				System.out.println("Incorrect file");
			}
		}

		else
			JOptionPane.showMessageDialog(ControlPanel.this, "No se ha seleccionado ning�n archivo");
	}
	
	public void gButtonAction() {
		try {
			String res = (String) JOptionPane.showInputDialog(ControlPanel.this, "Select gravity law to be used:",
					"Gravity law chooser", JOptionPane.PLAIN_MESSAGE, null, _opciones, _opciones[0]);

			for (int i = 0; i < _opciones.length; i++) {
				if (res.equals(_opciones[i])) {
					_ctrl.setGravityLaws(_ctrl.getGravityLawsFactory().getInfo().get(i));
					i = _opciones.length;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ControlPanel.this, "No se ha cambiado la ley");
		}
		
	}
	
	public void playButtonAction() {
		try {
			double dTime = 0;
			dTime = Double.parseDouble(_deltaText.getText());
			if(dTime <= 0) throw new NumberFormatException();
			
			_ctrl.setDeltaTime(dTime);
			buttons_State(false);
			
			_thread = new Thread() {
				public void run() {
					run_sim((int)_spinner.getValue(), (int) _delay.getValue());
					buttons_State(true);
					_thread = null;
				}
			};
			_thread.start();
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Invalid delta time");
			_deltaText.requestFocus();
		}
	}

//RUN_SIM
	private void run_sim(int n, long delay) {
		while(n > 0 && _thread != null) {
			try {
				_ctrl.run(1);
			} catch(Exception e) {
				SwingUtilities.invokeLater(new Runnable () {
					public void run() {
						JOptionPane.showMessageDialog(ControlPanel.this, e.getMessage());
					}
				});
				
			}
			try {
				Thread.sleep(delay);
			} catch(Exception e) {
				return;
			}
			n--;
		}
	}
	
	public void buttons_State(boolean b) {
		_btnOpenFile.setEnabled(b);
		_gButton.setEnabled(b);
		_playButton.setEnabled(b);
		_exitButton.setEnabled(b);
		_spinner.setEnabled(b);
		_deltaText.setEnabled(b);
	}

	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_deltaText.setText(Double.toString(dt));
			}
		});
	}
	
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_deltaText.setText(Double.toString(dt));
			}
		});
	}
	
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_deltaText.setText(Double.toString(dt));
			}
		});
	}
	
	public void onBodyAdded(List<Body> bodies, Body b) {}
	public void onAdvance(List<Body> bodies, double time) {}
	public void onGravityLawChanged(String gLawsDesc) {}

}