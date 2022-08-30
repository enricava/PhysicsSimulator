package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

//INITGUI
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		_currTime = new JLabel("Time: 0");
		_numOfBodies = new JLabel("Bodies: 0");
		_currLaws = new JLabel("Laws: Newton's Universal law of Gravitation (default)");
		
		_currTime.setPreferredSize(new Dimension(150, 20));
		_numOfBodies.setPreferredSize(new Dimension(150, 20));
		_currLaws.setPreferredSize(new Dimension(400, 20));
		
		this.add(_currTime);
		this.add(_numOfBodies);
		this.add(_currLaws);

		this.setVisible(true);
	}
	
	
//OBSERVER METHODS WILL UPDATE THE PANEL
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText("Time: " + time);
			}
		});
	}
	public void onBodyAdded(List<Body> bodies, Body b) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText("Bodies: " + bodies.size());
			}
		});
		}
	public void onGravityLawChanged(String gLawsDesc) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currLaws.setText("Laws: " + gLawsDesc);
			}
		});
	}
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText("Time: " + time);
				_numOfBodies.setText("Bodies: " + bodies.size());
				_currLaws.setText("Laws: " + gLawsDesc);
			}
		});
	}

//OBSERVER METHODS THAT DONT UPDATE THE PANEL
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {}
	public void onDeltaTimeChanged(double dt) {}

}
