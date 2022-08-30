package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private List<Body> _bodies;
	private String[] _colNames = { "Id", "Mass", "Position", "Velocity", "Acceleration" };

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public int getRowCount() {
		return _bodies.size();
	}

	public int getColumnCount() {
		return 5;
	}


	public String getColumnName(int column) {
		return _colNames[column];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: return _bodies.get(rowIndex).getId();
		case 1: return _bodies.get(rowIndex).getMass();
		case 2: return _bodies.get(rowIndex).getPosition();
		case 3: return _bodies.get(rowIndex).getVelocity();
		case 4: return _bodies.get(rowIndex).getAcceleration();
		default: return null;
		}
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
//CUSTOM PRIVATE METHOD TO UPDATE TABLE
	private void updateTable(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable () {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}
	
//METHODS THAT REQUIRE UPDATE
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) { updateTable(bodies);}
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) { updateTable(bodies);}
	public void onBodyAdded(List<Body> bodies, Body b) { updateTable(bodies);}
	public void onAdvance(List<Body> bodies, double time) { updateTable(bodies);}
	
	
//METHODS THAT DONT REQUIRE UPDATE
	public void onDeltaTimeChanged(double dt) {}
	public void onGravityLawChanged(String gLawsDesc) {}
}
