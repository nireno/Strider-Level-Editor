package genie.forms;

import genie.XMLdb.dbInterfaces.Tile;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

;

public final class TilePanel extends JPanel {

	private JButton[] buttons;
	private EditorToolBox Konsole;
	private JButton btnMainMenu;

  public TilePanel(EditorToolBox Konsole){
	  this.Konsole = Konsole;
	  this.btnMainMenu = new JButton("Main Menu");
	  this.btnMainMenu.setActionCommand("showPanelMainMenu");
	  this.btnMainMenu.addActionListener(Konsole);
	    
		createButtons(new String[]{"A","B","C","D","e","ff", "Niren", "Joshua"});
		
	}
  public TilePanel(EditorToolBox Konsole, ArrayList <BufferedImage> X){
	  this.Konsole = Konsole;
	  this.btnMainMenu = new JButton("Main Menu");
	  this.btnMainMenu.setActionCommand("showPanelMainMenu");
	  this.btnMainMenu.addActionListener(Konsole);
	  
	  createButtons(X);
  }
  private void createButtons(String[] StrNames){
/* this method is supposed to create buttons on a JPanel
 * which depends on the names of the different sprites.
 * it will continue to create buttons and attach them to
 * this classes' panel. until there are no more names
 * to assign to the buttons.
 */
	buttons = new JButton[StrNames.length];
	
	 for(int i=0; i<StrNames.length; i++){
	  this.buttons[i] = new JButton(StrNames[i]); //create a brand new button and store it in an array.
	  this.buttons[i].setActionCommand("addSprite"+ i); //this line will set the actioncommand's name.
	  this.buttons[i].addActionListener(Konsole); //this line adds the listener to the Konsole(UserToolBox)
	  this.add(buttons[i]); //this line adds the button to the SpritePanel's JPanel.
	  
	 }
	 this.add(btnMainMenu);
	 }
  private void createButtons(ArrayList <BufferedImage> arrlTileList){
	  /* this method is supposed to create buttons on a JPanel
	   * which depends on the different Tile pictures on each
	   * sprite. Action listeners are set up in such a way that
	   * that when the buttons are created, then the listeners are set
	   * with a name 'tileX' where x is a number.
	   */
	  JButton temp;
	  for(int i=0; i< arrlTileList.size(); i++){
		  temp = new JButton(new ImageIcon(arrlTileList.get(i)));
		  temp.setActionCommand("tile"+ i);
		  temp.addActionListener(Konsole);
		  this.add(temp);
	  }
	  this.add(btnMainMenu);
  }
}
