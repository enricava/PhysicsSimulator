package simulator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.model.Body;

import simulator.control.Controller;
import simulator.misc.Vector;

@SuppressWarnings("serial")
public class Viewer extends JComponent implements SimulatorObserver {

	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;

	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white, 2), "Viewer",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));

		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;

		// KEY LISTENER
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					break;
				case '=':
					autoScale();
					break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				}
				repaint();
			}

			// METHODS WE DONT NEED
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}

		});

		// MOUSE LISTENER
		addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			public void mouseClicked(MouseEvent e) {
				_showHelp = !_showHelp;
				repaint();
			}

			// METHODS WE DONT NEED
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});

	}

//PAINT COMPONENT
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;

//CROSS
		gr.setColor(Color.WHITE);
		gr.drawLine(_centerX - 5, _centerY, _centerX + 5, _centerY); // LINEA HORIZONTAL
		gr.drawLine(_centerX, _centerY - 5, _centerX, _centerY + 5); // LINEA VERTICAL

//BODIES
		
		Color colores[] = new Color[4];
		colores[0] = Color.WHITE;
		colores[1] = Color.ORANGE;
		colores[2] = Color.MAGENTA;
		colores[3] = Color.RED;
		
		int i = 0;
		for (Body b : _bodies) {
			gr.setColor(colores[i]);
			Vector position = b.getPosition();
			double x = position.coordinate(0);
			double y = position.coordinate(1);
			
			gr.fillOval(_centerX + (int) (x / _scale), _centerY - (int) (y / _scale), 6, 6); // (X, Y, ANCHO, ALTO)
			gr.drawString(b.getId(), _centerX + (int) (x / _scale) + 10, _centerY - (int) (y / _scale) + 10); 
			
			if(i < 4) i++;
			else i = 0;
		}

//SHOWHELP
		if (_showHelp == true) {
			gr.setColor(Color.WHITE);
			gr.drawString("h: toggle help, +: zoom-in, -: zoom-out, =: fit", 20, 35);
			gr.drawString("Scaling ratio: " + _scale, 20, 50);
		}
	}

	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector p = b.getPosition();
			for (int i = 0; i < p.dim(); i++)
				max = Math.max(max, Math.abs(b.getPosition().coordinate(i)));
		}
		double size = Math.max(1.0, Math.min((double) getWidth(), (double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}

//UPDATE VIEW
	private void updateView(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				_bodies = bodies;
				autoScale();
				repaint();
			}
		});
	}

//OBSERVER METHODS THAT NEED UPDATING
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		updateView(bodies);
	}

	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		updateView(bodies);
	}

	public void onBodyAdded(List<Body> bodies, Body b) {
		updateView(bodies);
	}

	// ONLY NEEDS REPAINTING
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				_bodies = bodies;
				repaint();
			}
		});
	}

//OBSERVER METHODS THAT DONT NEED UPDATING
	public void onDeltaTimeChanged(double dt) {
	}

	public void onGravityLawChanged(String gLawsDesc) {
	}
}