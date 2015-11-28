import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;

public class Sudoku {

	private static final int N = 9;
	
	private JFrame frmMainFrame;
	
	private JPanel pnlNumberSelector;
	private JPanel pnlPuzzle;
	private JPanel pnlMain;
	private JPanel pnlInfo;
	private JPanel pnlButtonNavigation;
	
	
	private JButton[] btnNumberSelectors = new JButton[9];
	private JButton[] btnNumbers = new JButton[81];
	private JButton btnSelection;	
	
	private ImageIcon[] buttonImages = new ImageIcon[10];
	private ImageIcon[] buttonRolloverImages = new ImageIcon[10];
	private ImageIcon[] buttonPressedImages = new ImageIcon[10];
	private ImageIcon[] buttonDisabledImages = new ImageIcon[10];
	
	private int[][] puzzle = new int[9][9];
	
	
	public static void main(String[] args) throws IOException {
		Sudoku window = new Sudoku();
		window.frmMainFrame.setVisible(true);
	}

	public Sudoku() throws IOException {
		
		frmMainFrame = new JFrame("Sudoku");
		frmMainFrame.setResizable(false);
		frmMainFrame.setBounds(100, 100, 900, 600);
		frmMainFrame.setLayout(null);
		frmMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BufferedImage backgroundImage = ImageIO.read(new File("./resources/BackgroundSmallLogo.png"));
		frmMainFrame.setContentPane(new JLabel(new ImageIcon(backgroundImage)));
		
		pnlNumberSelector = createNumberSelector();
		pnlNumberSelector.setVisible(false);
		pnlPuzzle = createPuzzle();
		pnlPuzzle.setVisible(false);
		try {
			pnlMain = createMainMenu();
			pnlMain.setVisible(true);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		pnlButtonNavigation = createButtonNavigation();
		pnlButtonNavigation.setVisible(false);
		
		pnlInfo = createInfoPanel();
		pnlInfo.setVisible(false);
		
		
		
		frmMainFrame.add(pnlNumberSelector);
		frmMainFrame.add(pnlPuzzle);
		frmMainFrame.add(pnlMain);
		frmMainFrame.add(pnlInfo);
		frmMainFrame.add(pnlButtonNavigation);
		
	
		}
	
	private JPanel createButtonNavigation() {
		
		JPanel pnlButtonNavigation = new JPanel();
		
		JButton btnGoBack = new JButton("To Main");
		btnGoBack.setBounds(75, 150, 100, 50);
		btnGoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlPuzzle.setVisible(false);
				pnlButtonNavigation.setVisible(false);
				pnlMain.setVisible(true);
				
			}
			
		});
		
		
		pnlButtonNavigation.setBackground(Color.WHITE);
				
		pnlButtonNavigation.setLayout(null);
		pnlButtonNavigation.setBounds(0, 0, 200, 600);
		
		pnlButtonNavigation.add(btnGoBack);
		
		return pnlButtonNavigation;
	}

	private JPanel createInfoPanel()
	{
		String infoText = "<html><center><h1>Coding</h1><h3>Sang Tan Le</h3><h1>GUI</h1><h3>Daniel Waters</h3>"
				+ "<h1>Music</h1><h3>longzijun<br>https://longzijun.wordpress.com/</center></html>";
		
		JPanel pnlInfo = new JPanel();
		JButton btnReturn = new JButton("Return");
		JTextPane txtCredits = new JTextPane();
		txtCredits.setBounds(90, 150, 700, 350);
		txtCredits.setContentType("text/html");
		txtCredits.setText(infoText);
		
				
		pnlInfo.setLayout(null);
		pnlInfo.setBounds(0, 0, 900, 600);
		
		ImageIcon smallBackgroundIcon = new ImageIcon("./resources/BackgroundSmallLogo.png");
		
		JLabel background = new JLabel();
		background.setIcon(smallBackgroundIcon);
		background.setBounds(0, 0, 900, 600);
		
				
		ImageIcon returnImage = new ImageIcon("./resources/Return.png");
		
		
		btnReturn.setBounds(410, 500, 60, 60);
		btnReturn.setIcon(returnImage);
		
		btnReturn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlInfo.setVisible(false);
				pnlMain.setVisible(true);
			}
		});
		
		pnlInfo.add(btnReturn);		
		pnlInfo.add(txtCredits);
		pnlInfo.add(background);
		
		return pnlInfo;
		
	}
	
	private JPanel createNumberSelector()
	{
		JPanel pnlNumberSelector = new JPanel();
		pnlNumberSelector.setLayout(new GridLayout(3,3));
		pnlNumberSelector.setBounds(700, 240, 125, 125);
		
		String btnLabels[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
				
		for (int i = 0; i < btnLabels.length; i++)
		{
			btnNumberSelectors[i] = new JButton(btnLabels[i]);
			btnNumberSelectors[i].addActionListener(new CheckSelection());
			setButton(btnNumberSelectors[i], i + 1);
			pnlNumberSelector.add(btnNumberSelectors[i]);
		}
		
		return pnlNumberSelector;
	}
	
	private JPanel createMainMenu() throws UnsupportedAudioFileException 
	{

		
        try {
        	AudioInputStream audioBackgroundStream = AudioSystem.getAudioInputStream(new File("./resources/backgroundMusic.wav"));			
    		Clip clipBackgroundMusic = AudioSystem.getClip( );
			clipBackgroundMusic.open(audioBackgroundStream);
	        clipBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
	        clipBackgroundMusic.start( );
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon backgroundIcon = new ImageIcon("./resources/MainBackground.png");

		
		JLabel background = new JLabel();
		background.setIcon(backgroundIcon);
		background.setBounds(0, 0, 900, 600);
		
        
        
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 900, 600);
		
		
		
		
		
		JButton btnCreatePuzzle = new JButton("Create Puzzle");
		JButton btnPlayPuzzle = new JButton("Play Puzzle");
		
		
		JButton btnInfoSelect = new JButton("Info");
		ImageIcon infoImage = new ImageIcon("./resources/Info.png");
		btnInfoSelect.setIcon(infoImage);
		
		

	
		btnCreatePuzzle.setBounds(350, 325, 150, 40);
		btnPlayPuzzle.setBounds(350, 400, 150, 40);
		btnInfoSelect.setBounds(600, 500, 33, 35);
		btnInfoSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlMain.setVisible(false);
				pnlInfo.setVisible(true);
			}
		});
		
		btnPlayPuzzle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlMain.setVisible(false);
				pnlButtonNavigation.setVisible(true);
				try {
					frmMainFrame.remove(pnlPuzzle);
					pnlPuzzle = createPuzzle();
					frmMainFrame.add(pnlPuzzle);
					pnlPuzzle.setVisible(true);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		
			
		

			
		
		pnlMain.add(btnCreatePuzzle);
		pnlMain.add(btnPlayPuzzle);
		pnlMain.add(btnInfoSelect);
		pnlMain.add(background);

		
				
		
	
		return pnlMain;
		
	}
	
	
	private JPanel createPuzzle() throws IOException 
	{
			
		JPanel pnlPuzzle = new JPanel();
		pnlPuzzle.setLayout(new GridLayout(9,9));
		pnlPuzzle.setBounds(230, 100, 400, 400);
		
		puzzle = readFile();
		
		String temp = "";
		
		int counter = 0;
		
		for(int i = 0; i < 9; i++)
		{
			
			for(int j = 0; j < 9; j++)
			{
				btnNumbers[counter] = new JButton(temp+puzzle[i][j]);
				btnNumbers[counter].addActionListener(new CheckButton());
				if (!btnNumbers[counter].getText().equalsIgnoreCase("0"))
				{
					btnNumbers[counter].setEnabled(false);
				}
				setButton(btnNumbers[counter], puzzle[i][j], counter);
				pnlPuzzle.add(btnNumbers[counter++]);
				temp = "";
				
			}
		}
	
	
		return pnlPuzzle;
		
	}
	
	private JButton setButton(JButton button, int value, int location)
	{
		final String[] buttonImageList = {"./resources/Button0.png", "./resources/Button1.png", "./resources/Button2.png", 
				"./resources/Button3.png", "./resources/Button4.png", "./resources/Button5.png", "./resources/Button6.png",
				 "./resources/Button7.png", "./resources/Button8.png", "./resources/Button9.png"};
			for (int i = 0; i < buttonImageList.length; i++)
				buttonImages[i] = new ImageIcon(buttonImageList[i]);
		
			final String[] buttonRolloverImageList = {"./resources/Button0Rollover.png", "./resources/Button1Rollover.png", "./resources/Button2Rollover.png", "./resources/Button3Rollover.png",
				"./resources/Button4Rollover.png", "./resources/Button5Rollover.png", "./resources/Button6Rollover.png", "./resources/Button7Rollover.png",
				"./resources/Button8Rollover.png", "./resources/Button9Rollover.png"};
			for (int i = 0; i < buttonRolloverImageList.length; i++)
				buttonRolloverImages[i] = new ImageIcon(buttonRolloverImageList[i]);
		
			final String[] buttonPressedImageList = {"./resources/Button0Pressed.png", "./resources/Button1Pressed.png", "./resources/Button2Pressed.png", "./resources/Button3Pressed.png",
				"./resources/Button4Pressed.png", "./resources/Button5Pressed.png", "./resources/Button6Pressed.png", "./resources/Button7Pressed.png",
				"./resources/Button8Pressed.png", "./resources/Button9Pressed.png"};
			for (int i = 0; i < buttonPressedImageList.length; i++)
				buttonPressedImages[i] = new ImageIcon(buttonPressedImageList[i]);
			
			final String[] buttonDisabledImageList = {"./resources/Button1Disabled.png", "./resources/Button1Disabled.png", "./resources/Button2Disabled.png", "./resources/Button3Disabled.png",
					"./resources/Button4Disabled.png", "./resources/Button5Disabled.png", "./resources/Button6Disabled.png", "./resources/Button7Disabled.png",
					"./resources/Button8Disabled.png", "./resources/Button9Disabled.png"};
				for (int i = 0; i < buttonPressedImageList.length; i++)
					buttonDisabledImages[i] = new ImageIcon(buttonDisabledImageList[i]);
					
			final String[] puzzleBorders = 
			{"3,3,1,1", "3,1,1,1", "3,1,1,3", "3,3,1,1", "3,1,1,1", 
				"3,1,1,3", "3,3,1,1", "3,1,1,1", "3,1,1,3",
			 "1,3,1,1", "1,1,1,1", "1,1,1,3", "1,3,1,1", "1,1,1,1",
			 	"1,1,1,3", "1,3,1,1", "1,1,1,1", "1,1,1,3",
			 "1,3,3,1", "1,1,3,1", "1,1,3,3", "1,3,3,1", "1,1,3,1",
			 	"1,1,3,3", "1,3,3,1", "1,1,3,1", "1,1,3,3",
			 "3,3,1,1", "3,1,1,1", "3,1,1,3", "3,3,1,1", "3,1,1,1",
			 	"3,1,1,3", "3,3,1,1", "3,1,1,1", "3,1,1,3",
			 "1,3,1,1", "1,1,1,1", "1,1,1,3", "1,3,1,1", "1,1,1,1",
			 	"1,1,1,3", "1,3,1,1", "1,1,1,1", "1,1,1,3",
			 "1,3,3,1", "1,1,3,1", "1,1,3,3", "1,3,3,1", "1,1,3,1",
			 	"1,1,3,3", "1,3,3,1", "1,1,3,1", "1,1,3,3",
			 "3,3,1,1", "3,1,1,1", "3,1,1,3", "3,3,1,1" ,"3,1,1,1",
			 	"3,1,1,3", "3,3,1,1", "3,1,1,1", "3,1,1,3",
		 	 "1,3,1,1", "1,1,1,1", "1,1,1,3", "1,3,1,1", "1,1,1,1",
		 	 	"1,1,1,3", "1,3,1,1", "1,1,1,1", "1,1,1,3",
		 	 "1,3,3,1", "1,1,3,1", "1,1,3,3", "1,3,3,1", "1,1,3,1",
		 	 	"1,1,3,3", "1,3,3,1", "1,1,3,1", "1,1,3,3"			 			
				};
		
		
		String[] border = puzzleBorders[location].split(",");
		button.setBorder(new MatteBorder(Integer.parseInt(border[0]), 
				Integer.parseInt(border[1]), Integer.parseInt(border[2]), 
				Integer.parseInt(border[3]), Color.BLACK));
		
		
		button.setIcon(buttonImages[value%10]);
		button.setRolloverIcon(buttonRolloverImages[value%10]);
		button.setPressedIcon(buttonPressedImages[value%10]);
		button.setDisabledIcon(buttonDisabledImages[value%10]);
		
		button.setRolloverEnabled(true);
		
				
		
		return button;
	}
	
	private JButton setButton(JButton button, int value)
	{
		final String[] buttonImageList = {"./resources/Button0.png", "./resources/Button1.png", "./resources/Button2.png", 
				"./resources/Button3.png", "./resources/Button4.png", "./resources/Button5.png", "./resources/Button6.png",
				 "./resources/Button7.png", "./resources/Button8.png", "./resources/Button9.png"};
			for (int i = 0; i < buttonImageList.length; i++)
				buttonImages[i] = new ImageIcon(buttonImageList[i]);
		
			final String[] buttonRolloverImageList = {"./resources/Button0Rollover.png", "./resources/Button1Rollover.png", "./resources/Button2Rollover.png", "./resources/Button3Rollover.png",
				"./resources/Button4Rollover.png", "./resources/Button5Rollover.png", "./resources/Button6Rollover.png", "./resources/Button7Rollover.png",
				"./resources/Button8Rollover.png", "./resources/Button9Rollover.png"};
			for (int i = 0; i < buttonRolloverImageList.length; i++)
				buttonRolloverImages[i] = new ImageIcon(buttonRolloverImageList[i]);
		
			final String[] buttonPressedImageList = {"./resources/Button0Pressed.png", "./resources/Button1Pressed.png", "./resources/Button2Pressed.png", "./resources/Button3Pressed.png",
				"./resources/Button4Pressed.png", "./resources/Button5Pressed.png", "./resources/Button6Pressed.png", "./resources/Button7Pressed.png",
				"./resources/Button8Pressed.png", "./resources/Button9Pressed.png"};
			for (int i = 0; i < buttonPressedImageList.length; i++)
				buttonPressedImages[i] = new ImageIcon(buttonPressedImageList[i]);
		
			final String[] puzzleBorders = 
				{"0,0,0,0", "5,5,1,1", "5,1,1,1", "5,1,1,5", "1,5,1,1", "1,1,1,1", 
					"1,1,1,5", "1,5,5,1", "1,1,5,1", "1,1,5,5" 	
					};
			
			
			String[] border = puzzleBorders[value].split(",");
			button.setBorder(new MatteBorder(Integer.parseInt(border[0]), 
					Integer.parseInt(border[1]), Integer.parseInt(border[2]), 
					Integer.parseInt(border[3]), Color.BLACK));
			
			
			
		button.setIcon(buttonImages[value%10]);
		button.setRolloverIcon(buttonRolloverImages[value%10]);
		button.setPressedIcon(buttonPressedImages[value%10]);
		
		button.setRolloverEnabled(true);
		
				
		
		return button;
	}
	
	
	
	
	public class CheckSelection implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			for (int i = 0; i < btnNumberSelectors.length; i++)
			{
				if(e.getSource()== btnNumberSelectors[i]) 
				{
					pnlNumberSelector.setVisible(false);
					btnSelection.setRolloverIcon(buttonRolloverImages[Integer.parseInt(e.getActionCommand())]);
					btnSelection.setIcon(buttonImages[Integer.parseInt(e.getActionCommand())]);
					btnSelection.setPressedIcon(buttonPressedImages[Integer.parseInt(e.getActionCommand())]);
					btnSelection.setText(e.getActionCommand());
					
					btnSelection.setRolloverEnabled(true);
					btnSelection.setEnabled(false);
					btnSelection.setEnabled(true);
					btnSelection = null;
				}
			}		
		}
	}
	
	public class CheckButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			if ((btnSelection == null))
			{
				btnSelection = (JButton) e.getSource();
				btnSelection.setIcon(buttonRolloverImages[Integer.parseInt(e.getActionCommand())]);
				pnlNumberSelector.setVisible(true);
			}			
			
		}
	}
	
	public static int[][] readFile() throws IOException 
	{
		FileReader file = new FileReader("puzzle.txt");
		BufferedReader reader = new BufferedReader(file);
		
		int[][] table = new int[11][11];
		String[] parts = new String[11];
		String[] line = new String[11];
			
		for (int row = 0; row < N; row++)
		{
			line[row] = reader.readLine();
		}
		
		for (int row = 0; row < N;row++)
			for (int col = 0; col < N; col++)
			{
				parts = line[row].split(",");
				table[row][col] = Integer.parseInt(parts[col]);
			}		
			
		file.close();
			
		return (table);

	}
	
	
}





