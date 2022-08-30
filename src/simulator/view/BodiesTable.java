package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class BodiesTable extends JPanel {

	private JTable _tbl;
	private BodiesTableModel _tblModel;

	private JScrollPane _scp;

	public BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies",
				TitledBorder.LEFT, TitledBorder.TOP));
		_tblModel = new BodiesTableModel(ctrl);
		initGUI();
	}

	private void initGUI() {
		/*
		 * //INICIALIZAMOS TABLA DEL TAMANO DEL MODELO (FILAS, COLUMNAS) _tbl = new
		 * JTable(_tblModel.getRowCount(), _tblModel.getColumnCount());
		 * 
		 * //VALORES DE LA TABLA for(int i = 0; i < _tblModel.getRowCount(); i++) {
		 * for(int j = 0; i < _tblModel.getColumnCount(); j++)
		 * _tbl.setValueAt(_tblModel.getValueAt(i, j), i, j); }
		 */
//INICIALIZAMOS SCROLL PANEL CON LA TABLA

		_tbl = new JTable(_tblModel);

		//CODIGO DE INTERNET PARA COLOCAR LOS HEADERS A LA IZQ
		((DefaultTableCellRenderer) _tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

		_scp = new JScrollPane(_tbl);

		this.add(_scp, BorderLayout.CENTER);
		this.setVisible(true);
	}

}
