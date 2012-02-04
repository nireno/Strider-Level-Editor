/**
 * 
 */
package genie.forms;

import genie.lwjgl.slick2d.AddTileState;
import genie.lwjgl.slick2d.Genie;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import utils.FileUtils;
import utils.XMLDocFilter;

/**
 * @author Josh
 *
 */

public class EditorToolBox extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		Genie game;
		private JFileChooser fileChooser;
		private JButton[] buttons;
		private JMenuBar editorMenuBar; //this is the jmenu option
		private JMenu editorMenu; //this is the menu for the editorMenu as described above
		private JMenuItem mnuLoad;
		private JMenu mnuSubMenuSave; //brings up a dialogue for save menu.
		private JMenuItem mnuExit; //this is the exit menuOption in the menubar.
		private JMenuItem mnuSave; //this is the Save menuOption in the SubMenuSave.
		private JMenuItem mnuSaveAs; //this is the SaveAs menuOption in the SubMenuSave.
		private JPanel pnlTilePanel;
		private JPanel pnlMainMenu;
		private JPanel temp;
		private HashMap<String,JPanel> panels; // this hashmap will hold all panels.
		private static final int n = 20;
		private ArrayList<BufferedImage> arrlTileObjects;
		
		
	public EditorToolBox(Genie game){
		initFileChooser();
		this.game = game;
		this.setPreferredSize(new Dimension(300, 600));
	    this.pnlMainMenu = new MainMenuPanel(this);
		buttons = new JButton[n];
		panels = new HashMap<String, JPanel>(); //this is a hashMap used to store all panels for editorToolBox
		this.arrlTileObjects = loadTilePalette(new File("res/images/tiles.png")); //this assigns the arraylist of tiles to the object in editortoolbox
		this.pnlTilePanel = new TilePanel(this, arrlTileObjects); //test to check if a SpritePanel is added
		this.createMenuOptions();
		
		/*
		 * this code below places the panel Screens into a Hashmap which will be retrieved once an event
		 * asks for it. The Result would be that a temporary variable called 'temp' will be assigned to it
		 * and the contentpane of the JFrame is set to this temp. the final result is that the panel will contain
		 * the appropriate objects displayed into it and also will be visible for interaction on the screen.
		 * 
		 */
		panels.put("showPanelMainMenu", pnlMainMenu);
		panels.put("showPanelAddTilePanel", pnlTilePanel);

        temp = pnlMainMenu;
        temp.setVisible(true); //initialises the code to set temp to the main menu and make it visible.
        
		
		this.setContentPane(temp);//this line sets the visible panel to the frame's content pane. from this moment, the panel becomes visible on the screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
		
		private void initFileChooser()
		{
			fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new XMLDocFilter());
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String actioncmd = e.getActionCommand();
			
		/* the code below checks the actioncommand's name. If the actioncommand's name
		 * starts with any of the following comparisons, then a specific action will occur.
		 * eg: showPanel1 will run in code below because it starts with showPanel.
		 */
		
			/* commands are required to set an actionCommand which would be
			 * picked up in this area of code. 
			 */
			if(actioncmd.startsWith("showPanel")){
				temp.setVisible(false);
				temp = panels.get(actioncmd);
				if(temp == null)
				{
					System.out.println(actioncmd + " is not in hashmap");
					return;
				}
				temp.setVisible(true);
				this.setContentPane(temp); //*******************LINE ADDED
			}
//			else if(actioncmd.equals("addMario")){
//			temp.setVisible(false);
//			temp = pnlTilePanel;
//			temp.setVisible(true);
//			this.setContentPane(temp);
//			}
			else if(actioncmd.equals("loadCommand"))
			{
				int result = fileChooser.showOpenDialog(this);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					game.loadLevel(file.getAbsolutePath());
				}
			}
			else if(actioncmd.matches("OverwriteLevelSave"))
			{
				/*Show a FileChooser if no save location has yet been specified */
				if(game.getSavePath() == null)
				{
					int result = fileChooser.showSaveDialog(this);
					if(result == JFileChooser.APPROVE_OPTION)
					{
						File file = fileChooser.getSelectedFile();
						String path = file.getAbsolutePath();
						if(!FileUtils.getExtension(file).equals(FileUtils.xml))
						{
							path += "." + FileUtils.xml;
						}
						game.setSavePath(path);
						if(!game.saveLevel()) System.err.println("Save failed!");
					}
					else
					{
						System.err.println("Invalid path provided.");
					}
				}
				else
				{
					game.saveLevel();
				}
			}
			else if(actioncmd.startsWith("ExitCommand")){
				System.exit(0);
			}
			else if(actioncmd.startsWith("tile")){
				String temp = e.getActionCommand();
				int i = Integer.parseInt(temp.substring(4)); //this line returns the integer of the number in the string
				((AddTileState)game.getAddTileState()).setSelectedTile(i);
			}
		}
		private void createMenuOptions(){
			this.mnuSubMenuSave = new JMenu("Save...");
			this.mnuSave = new JMenuItem("Save Progress");
			this.mnuSave.setActionCommand("OverwriteLevelSave");
			this.mnuSave.addActionListener(this);
			this.mnuSubMenuSave.add(mnuSave);
			this.mnuSaveAs = new JMenuItem("Save New Level File");
			this.mnuSaveAs.setActionCommand("SaveAsNewFile");
			this.mnuSubMenuSave.add(mnuSaveAs);
			this.editorMenuBar = new JMenuBar(); //this creates a menuBar
			this.editorMenu = new JMenu("Level"); //this creates a menu which would be inserted onto the menubar
			this.editorMenuBar.add(editorMenu); //this attaches the menu onto the menuBar.
			this.mnuLoad = new JMenuItem("Load Level");
			this.mnuLoad.setActionCommand("loadCommand");
			this.mnuLoad.addActionListener(this);
			this.mnuExit = new JMenuItem("Exit"); //this line creates a menu Item, (A entry to be inserted within the main item on the menuBar)
			this.mnuExit.setActionCommand("ExitCommand");
			this.mnuExit.addActionListener(this);
			this.setJMenuBar(editorMenuBar); //this line attaches the menuBar onto 'this' class. (this is only possible since the EditorToolBox class is a JFrame object)
			this.editorMenu.add(mnuLoad);
			this.editorMenu.add(mnuSubMenuSave);
			this.editorMenu.addSeparator();
			this.editorMenu.add(mnuExit); //this line adds the MenuItem to the Menu. once this is done, a subdirectory appears below.
			
			this.editorMenu.setVisible(true);
			this.editorMenuBar.setVisible(true);
			this.mnuExit.setVisible(true);	
		}
		private ArrayList <BufferedImage> loadTilePalette(File filImageSheet){
			ArrayList <BufferedImage> arrlTilePalette;
			BufferedImage buffiTileSheet;
			int sheetWidth;
			int imageWidth;
			int imageHeight;
			
			try{
				buffiTileSheet = ImageIO.read((filImageSheet));
			}catch (IOException e){ e.printStackTrace(); buffiTileSheet = null;}
			
			arrlTilePalette = new ArrayList <BufferedImage>();
			 sheetWidth = buffiTileSheet.getWidth();
			 imageWidth = sheetWidth / 11;
			 imageHeight = buffiTileSheet.getHeight();
			 
			for(int i = 0; i < 11; i++)
			{
				int sx1 = i * imageWidth; //sx1 is the left of buffered image.
				int sx2 = (i+1) * imageWidth; // sx2 is the right of the buffered image.
				int sy1 = 0; // beginning of the 
				int sy2 = imageHeight;
				
				BufferedImage buffiTemp = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphTemp = buffiTemp.createGraphics();
				graphTemp.drawImage(buffiTileSheet, 0, 0, imageWidth,imageHeight,sx1,sy1,sx2,sy2,null);
				
				arrlTilePalette.add(buffiTemp);
				
			}
			return arrlTilePalette;
		}
	}
