package genie.forms;


import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	private EditorToolBox Konsole;
	private JButton[] Buttons;
	private JButton btnTilesScreen;

	public MainMenuPanel(EditorToolBox tb){
		this.Konsole = tb;
		this.Buttons = new JButton[20];
		this.btnTilesScreen = new JButton("Choose Tiles");
		this.btnTilesScreen.setActionCommand("showPanelAddTilePanel");
		this.btnTilesScreen.addActionListener(Konsole);
		this.add(btnTilesScreen);
	}
}
