/*
 MP3  Reindeer Rumble

 Names     Period

 Requirements

 1. This list                                         5
 2. Program runs (It will be graded on the network)  15
 3. Data File                                         5
 4. Two Conditionals (if, else, switch)               5
 5. Four arrays (one for each type)                  10
 6. Three loops (while, for, do)                     15
 60  //If it runs
 Additional points

 1, Additional array  (5 points each type)            5-15
 (must be meaningful)
 number of wins by each reindeer
 number of losses by each reindeer
 name of previous opponent

 2  Additional conditional (5 points each kind)       5-15
 when to allow Rudolph to substitute (random)
 when to have an image appear on the screen
 when to play a sound
 different graphic depending on who wins
 determine which player goes first

 3. Additional loop                                   5-15  45
 show list of wins/ losses on the screen
 show list of previous opponent on the screen

 Other (Suggestions)  You must have at least 80 from above before you get
 any credit below

 1. Scoring scheme                                    5
 2. The Grinch or Santa interrupts                    5
 the game and take points from the
 leading team.
 3. Additional Graphics Pictures                      5
 4. Description (commentary) of the game              5
 5  Change order of input (random)                    5
 6. Winner/loser screen                               5
 7. Input players names                               5
 8  Rudolph substitutes for another player            5
 9. Change character names/skill/experience           5
 10. Save winner list                                  5
 11.Trivia quiz                                       5
 12.Skill challenge                                   5
 13.Assign players to teams at random.                3
 14.Give each team a magical key to cast spells       3
 on the other team.


 To keep tuned up for there Christmas work, the Reindeer have
 a game to practice.  Each captain chooses a player to compete 
 one on one for each round.  When all four players on each team 
 have had a turn another round begins.

 */
/*
 Data file:
 Name      Experience Skill

 Dancer       6        5.6
 Dasher       1        9.8
 Prancer      5        7.6
 Vixen        5        4.7
 Comet        6        5.6
 Cupid        3        9.8
 Donner       5        4.6
 Blitzen      4        3.7
 Rudolph      0       14.3
 */
import static java.lang.System.out;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import java.util.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.io.*; //for files						
import java.net.*;
import javax.sound.midi.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import java.util.Vector;
//import jmidi.*;

//DO NOT CHANGE ANYTHING FROM LINES 85 THROUGH 102		
public class mp3 {
	public static void main(String args[]) throws IOException {
		Windows W = new Windows(); // Make a window
		W.setSize(800, 600);
		W.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		W.mainInput(); // Call the window function
	}// end main
} // class

class Windows extends JFrame// implements MidiChannel //inherits from Frame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1354517852232990505L;
	// DO NOT CHANGE ANYTHING FROM LINES 85 THROUGH 102
	// declare variables global
	String playerName1 = "", // Team 1
			playerName2 = ""; // Team2
	String winner = "";

	ArrayList<String> robotName = new ArrayList<String>(); // all players 0..3
															// Team 1
	// 8 Rudolph
	ArrayList<String> msgs = new ArrayList<String>();
	ArrayList<Integer> experience = new ArrayList<Integer>();
	ArrayList<Double> skill = new ArrayList<Double>();
	ArrayList<String> hadTurn = new ArrayList<String>();
	ArrayList<String> team1 = new ArrayList<String>();
	ArrayList<String> team2 = new ArrayList<String>();
	// String pic1[] =new String[60];

	int count = 0, height = 0, width = 0, score1 = 0, // Team 1
			score2 = 0; // Team 2
	double power1 = 0, // Team 1
			power2 = 0; // Team 2

	// sounds

	// static AudioClip TieSound,t1Sound,t2Sound,ErrorSound,
	// HadTurnSound,santaSound;
	// int santaInt = 1;
	// static Image santa;
	String comment = "";
	String lastControl = null;
	String WinTeam = "", loseTeam = "";
	int countAllPlayers = 0, playersPerTeam = 0;
	//n:\\Computer Science\\mp3\\mp3 Data\\mp3.data
	//C:\\Users\\messageofdeath\\Desktop\\mp3.data
	private static String path = "C:\\Users\\messageofdeath\\Desktop\\mp3.data";
	public void mainInput() throws IOException // This is the main program
	{
		int player1 = 0, // Team 1
		player2 = 0, // Team 2
		random1 = 0, random2 = 0;
		char playAgain = 'Y';
		int commentCount = 0;
		// Loop control variables.
		// int x=0,turn=0;
		// santa = Toolkit.getDefaultToolkit().getImage(
		// "c:\\warrs\\spl4.jpg");
		// Opening Screen Use JOptionPane.messageBox
		File file = new File(path);
		if(!file.exists()) {			
			out.println("File does not exist. Creating....");
			file.getParentFile().mkdirs();	
			file.createNewFile();
			out.println("File created.");
			copy("", file);
		}
		Scanner robotFile = new Scanner(file);
		countAllPlayers = getLines(path);

		for (int x = 0; x < countAllPlayers; x++) {
			String[] line = robotFile.nextLine().split(";");
			robotName.add(line[0]);
			out.println(line[0]);
			experience.add(Integer.parseInt(line[1]));
			skill.add(Double.parseDouble(line[2]));
		}
		playersPerTeam = countAllPlayers / 2; // two teams = 8/2 = 4 players per
												// team
		if(playersPerTeam % 2 == 1) {
			out.println("Amount per team: " + playersPerTeam);
		}else{
			JOptionPane.showMessageDialog(null, "Teams are not balanced! (Will close)", "Robot Smash v1.0", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		// //**********************************************
		this.setBackground(Color.WHITE);
		this.setTitle("Robot Smash v1.0 - made by messageofdeath");
		this.setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(800, 600));
		this.pack();
		this.setLocationRelativeTo(null);
		this.addMouseListener(new mouseListener());
		this.setVisible(true);// paint
		
		// //do
		// //Turn Loop (ends at 7 player turns)
		// {
		//
		// Write the reindeer names, experience, skill value and score
		// on the in an inputbox under the team names.
		//
		//
		// // int countAllPlayers = x;
		//
		// // int playersPerTeam = countAllPlayers/2; //two teams = 14/2 = 7
		// players per team
		// String Team1 =
		// "    Team 1     \n" +
		// "Experience  Skill  Name \n" +
		// "==============================\n" ;
		// for (x=0;x<playersPerTeam;x++)
		// {
		//
		// Team1 = Team1 + hadTurn[x] + " "+(char)(x+65)+ ".   " +
		// experience[x]+"          " + skill[x] + "        " +
		// deerName[x]+ "\n";
		// }
		// Team1 += "\n  Team 1 choose a player letter";
		//
		// String Team2 = //do Team 2 like Team 1 above
		//
		//
		// char choice1 = ' '; //Team 1 choice
		//
		// String retFromJPane = "";
		// do //Choose Team 1 player Goes first
		// {
		// retFromJPane = JOptionPane.showInputDialog(Team1,"A-G");
		// choice1 = retFromJPane.charAt(0);
		// player1 = (int)(choice1 - 65); //change player1 to number
		// if (player1 <0 || player1 >playersPerTeam) // error NOT 0 - 6
		// {
		// Team1 += "\n Choose A-G!\n"; //add to inputbox
		// }
		// else
		// {
		// while(turn <7); //ends one round
		// // 2 Additional conditional (5 points each kind)
		// if...// Team 1 wins
		// if ...// Team 2 wins
		//
		// /*}
		// while (playAgain
		// /* End the turn loop (ends at 7 player turns)
		// End the game loop (ends when?)
		//
		// Winner/Loser screen (Play again)
		 
	}
	
	public enum Sound {
		
		Explode("EXPLODE"),
		Gunshot("GUNSHOT"),
		Laser("LASER"),
		War("WAR");
		
		private String sound = null;

		Sound(String msg) {
			sound = msg;
		}

		public String getSound() {
			return sound;
		}
	}
	
	  public static synchronized void playSound(final Sound sound) {
		  try {
			    AudioInputStream stream;
			    AudioFormat format;
			    DataLine.Info info;
			    Clip clip;
			
			    stream = AudioSystem.getAudioInputStream(mp3.class.getResourceAsStream(sound.getSound() + ".WAV"));
			    format = stream.getFormat();
			    info = new DataLine.Info(Clip.class, format);
			    clip = (Clip) AudioSystem.getLine(info);
			    clip.open(stream);
			    clip.start();
			}
			catch (Exception e) {
			    //whatevers
			}
		    //N:\\Computer Science\\mp3\\mp3 Data\\" + url
	  }
	
	public void startGame() {
		//TODO Complete Game
		int x = 0;
		 do {
			 if(!teamPickingScreen() && !isOpenScreen()) {
				 
			 }
			// if (hadTurn[player1] == 'X') //X means already had a turn
			// {
			// Team1 += "\n" + firstName[player1] + " " +
			// lastName[player1] +
			// " already had a turn\n";
			// player1 = -1; //deny exit from this loop
			//
			// // HadTurnSound.play();
			// // pause(1000);
			// }
			// else
			// {
			// hadTurn[player1] = 'X'; //Give player X (already had a turn)
			// }
			// }
			// }
			// while(player1 <0 || player1 >playersPerTeam-1); // like a wall a play
			// can't go
			//
			// char choice2 = ' '; //Team 2 choice
			// // unless A-G is entered
			// do //Choose Team 2 player like Team 1 above
			//
			// /*
			// Make power1 and power2 random numbers based on
			// experience and skill of the players.
			// */
			int random1 = 4;
			int random2 = 2;
			power1 = random1 * 6 / 5;
			power2 = random2 * 6 / 5;
			// //1, Additional array (5 points each type)
			// powerList[player1] =...
			//
			// /*
			// Conditional that determines if Power1 is greater
			// Power2. (Player 1 wins the turn.)
			// */ if (power1 > power2)
			// {
			// // Add 1 to winning team's Score
			// score1 +=
			// savepower = power1;
			// winner = firstName[player1] + " " + lastName[player1] +
			// " of Team 1 scores a goal by a herioc effort."+
			// " bringing Team 1's score to " score1;
			// // Add 1 to the losing reindeer's skill.
			// //5 65
			// // play sound
			// }
			this.repaint();// executes paint() method
			x++;
			// // 2 Additional conditional (5 points each kind)
			// if...// Team 1 wins
			// if ...// Team 2 wins
			//
			// /*}
			// while (playAgain
			// /* End the turn loop (ends at 7 player turns)
			// End the game loop (ends when?)
			//
			// Winner/Loser screen (Play again)
		 }while(x < playersPerTeam);
	}
	
	public boolean isTeam1Picking = false;
	public boolean isTeam2Picking = false;
	public int team1Amount = 0;
	public int team2Amount = 0;
	
	public class actionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getID() == 0) {
				
			}
		}
	}

	boolean buttonEnabled = false;
	public class mouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if(x >= 683 && x <= 785) {
				if(y >= 544 && y <= 585 && isOpenScreen() == true) {
					//Continue Button (Go to Team Picking Screen)
					isOpenScreen = false;
					teamPickingScreen = true;
					playSound(Sound.Gunshot);
					playSound(Sound.Gunshot);
					repaint();
				}
				if(y >= 544 && y <= 585 && teamPickingScreen() == true &&  buttonEnabled == true) {
					//Continue Button (Start Game)
					teamPickingScreen = false;
					buttonEnabled = false;
					startGame();
					playSound(Sound.War);
					repaint();
				}
			}
			if(x >= 483 && x <= 625 && y >= 544 && y <= 584 && teamPickingScreen() == true) {
				//Reset Players Button
				team1.clear();
				team2.clear();
				team1Amount = 0;
				team2Amount = 0;
				isTeam1Picking = true;
				isTeam2Picking = false;
				playSound(Sound.Explode);
				repaint();
			}
			if(x >= 28 && x <= 250 && y >= 145 && y <= 554 && teamPickingScreen() == true) {
				//Areas of name selection
				int id = 0;
				String name = "";
				// Choice 1
				if(y >= 145 && y <= 167) {
					id = 0;
					name = robotName.get(id);
				}
				// Choice 2
				if(y >= 168 && y <= 190) {
					id = 1;
					name = robotName.get(id);
				}
				// Choice 3
				if(y >= 191 && y <= 213) {
					id = 2;
					name = robotName.get(id);
				}
				// Choice 4
				if(y >= 214 && y <= 235) {
					id = 3;
					name = robotName.get(id);
				}
				// Choice 5
				if(y >= 236 && y <= 258) {
					id = 4;
					name = robotName.get(id);
				}
				// Choice 6
				if(y >= 259 && y <= 278) {
					id = 5;
					name = robotName.get(id);
				}
				// Choice 7
				if(y >= 279 && y <= 301) {
					id = 6;
					name = robotName.get(id);
				}
				// Choice 8
				if(y >= 302 && y <= 324) {
					id = 7;
					name = robotName.get(id);
				}
				// Choice 9
				if(y >= 325 && y <= 347) {
					id = 8;
					name = robotName.get(id);
				}
				// Choice 10
				if(y >= 348 && y <= 370) {
					id = 9;
					name = robotName.get(id);
				}
				// Choice 11
				if(y >= 371 && y <= 393) {
					id = 10;
					name = robotName.get(id);
				}
				// Choice 12
				if(y >= 394 && y <= 416) {
					id = 11;
					name = robotName.get(id);
				}
				// Choice 13
				if(y >= 417 && y <= 439) {
					id = 12;
					name = robotName.get(id);
				}
				// Choice 14
				if(y >= 440 && y <= 462) {
					id = 13;
					name = robotName.get(id);
				}
				// Choice 15
				if(y >= 463 && y <= 485) {
					id = 14;
					name = robotName.get(id);
				}
				// Choice 16
				if(y >= 486 && y <= 508) {
					id = 15;
					name = robotName.get(id);
				}
				// Choice 17
				if(y >= 509 && y <= 531) {
					id = 16;
					name = robotName.get(id);
				}
				// Choice 18
				if(y >= 532 && y <= 554) {
					id = 17;
					name = robotName.get(id);
				}
				
				if(team1Amount == playersPerTeam) {
					isTeam1Picking = false;
					isTeam2Picking = true;
				}
				if(team2Amount == playersPerTeam) {
					isTeam2Picking = false;
				}
				if(event.getButton() == MouseEvent.BUTTON1) {
					if(isTeam1Picking == true) {
						if(team1Amount <= playersPerTeam && !team1.contains(name) && !team2.contains(name)) {
							team1.add(name);
							team1Amount++;
							isTeam1Picking = false;
							isTeam2Picking = true;
							playSound(Sound.Laser);
							repaint();
						}
					}
					if(isTeam2Picking == true) {
						if(team2Amount <= playersPerTeam && !team2.contains(robotName.get(id)) && !team1.contains(name)) {
							team2.add(name);
							team2Amount++;
							isTeam1Picking = true;
							isTeam2Picking = false;
							playSound(Sound.Laser);
							repaint();
						}
					}
				}
				if(event.getButton() == MouseEvent.BUTTON3) {
					int i = getPosition(name);
					if(i != -1) {
						int exp = experience.get(i);
						double skillLevel = skill.get(i);
						JOptionPane.showMessageDialog(null, "----["+name+"]----\nExperience: " + exp 
								+ "\nSkill: "+skillLevel);
					}
				}
				if(team2Amount == playersPerTeam) {
					buttonEnabled = true;
				}
			}
			out.println(x + " " + y);
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			
		}

		@Override
		public void mouseExited(MouseEvent event) {
			
		}

		@Override
		public void mousePressed(MouseEvent event) {
			
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			
		}
		
	}
	
	public static void copy(String line, File file) throws IOException {
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line + "\n");
		bw.close();
	}
	
	public static int getLines(String path) throws IOException {
		@SuppressWarnings("resource")
		InputStream is = new BufferedInputStream(new FileInputStream(path));
	    byte[] c = new byte[1024];
	    int count = 0;
	    int readChars = 0;
	    boolean empty = true;
	    while ((readChars = is.read(c)) != -1) {
	  	  empty = false;
	        for (int i = 0; i < readChars; ++i) {
	            if (c[i] == '\n')
	                ++count;
	        }
	    }
	    return (count == 0 && !empty) ? 1 : count;
	}
	boolean teamPickingScreen = false;
	public boolean teamPickingScreen() {
		return teamPickingScreen;
	}
	
	boolean isOpenScreen = false;//TODO change to true
	public boolean isOpenScreen() {
		return isOpenScreen;
	}
	
	public void background(Color color) {
		super.setBackground(color);
	}

	public void paint(Graphics pen) // Draws the window
	{
		// print headings
		Graphics2D g2 = (Graphics2D) pen;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(isOpenScreen() == true) {
			Screen screen = new Screen(pen);
			//Background
			screen.setBackground(Color.BLACK);
			//Button 1 (Continue button, goes to Team picking screen)
			screen.createButton(Color.WHITE, Color.BLACK, new Font("Arial", Font.BOLD, 20), "Continue", 684, 545, 690, 571, 100, 40);
			//Title
			screen.setFont(new Font("Arial", Font.BOLD, 48));
			screen.createRectangle(Color.DARK_GRAY, 86, 55, 637, 55, true);//Background
			screen.drawString(Color.RED, "Welcome to Robot Smash!", 103, 100);
			screen.createRectangle(Color.ORANGE, 86, 55, 637, 5, true);//Top Part of Rectangle
			screen.createRectangle(Color.ORANGE, 86, 105, 637, 5, true);//Bottom
			screen.createRectangle(Color.ORANGE, 86, 60, 5, 45, true);//Left
			screen.createRectangle(Color.ORANGE, 718, 60, 5, 45, true);//Right
			//Description
			screen.setFont(new Font("Arial", Font.BOLD, 20));
			screen.drawString(Color.ORANGE, "This program has been made by messageofdeath aka Chris Villarreal!!", 80, 150);
			screen.drawString(Color.ORANGE, "To play this game you will need 2 players. After this screen you will pick", 62, 172);
			screen.drawString(Color.ORANGE, "your teams then you will fight to the death in an epic battle of the ages!", 62, 194);
			screen.drawString(Color.ORANGE, "Once you click the 'Continue' button you will select your team. To select", 62, 216);
			screen.drawString(Color.ORANGE, "your team members just left click there name. Once team 1 selects 1 player", 62, 238);
			screen.drawString(Color.ORANGE, "then team 2 picks a player and it keeps alternating. It will show this on", 62, 260);
			screen.drawString(Color.ORANGE, "the top left of the screen in the gold box. To check the stats of a player", 62, 282);
			screen.drawString(Color.ORANGE, "just right click the name in the team picking screen. A player's experience", 62, 304);
			screen.drawString(Color.ORANGE, "goes up every 4 games. There skill goes up every game.", 62, 326);
			screen.drawString(Color.BLUE, "The creator of this game thinks its the best looking(maybe not best playable).", 38, 400);
		}
		if(teamPickingScreen() == true) {
			Screen screen = new Screen(pen);
			if(isTeam1Picking == false && team1.size() == 0)
				isTeam1Picking = true;
			//Background
			screen.setBackground(Color.BLACK);
			//Retangles
			screen.createRectangle(Color.BLUE, 20, 140, 230, 415, false);//Player Box
			screen.createRectangle(Color.BLUE, 273, 240, 230, 415 / 2 + 5, false);//Team1 Box
			screen.createRectangle(Color.BLUE, 530, 240, 230, 415 / 2 + 5, false);//Team2 Box
			screen.createRectangle(Color.ORANGE, 259, 140, 150, 70, true);//Team Notifier Box
			//Button 1 (Continue)
			screen.createButton(Color.WHITE, Color.BLACK, new Font("Arial", Font.BOLD, 20), "Continue", 684, 545, 690, 571, 100, 40);
			//Button 2 (Reset Players)
			screen.createButton(Color.WHITE, Color.BLACK, new Font("Arial", Font.BOLD, 20), "Reset Players", 484, 545, 490, 571, 142, 40);
			//Title
			screen.setFont(new Font("Arial", Font.BOLD, 48));
			screen.createRectangle(Color.DARK_GRAY, 86, 55, 637, 55, true);//Background
			screen.drawString(Color.RED, "Welcome to Robot Smash!", 103, 100);
			screen.createRectangle(Color.ORANGE, 86, 55, 637, 5, true);//Top Part of Rectangle
			screen.createRectangle(Color.ORANGE, 86, 105, 637, 5, true);//Bottom
			screen.createRectangle(Color.ORANGE, 86, 60, 5, 45, true);//Left
			screen.createRectangle(Color.ORANGE, 718, 60, 5, 45, true);//Right
			//Numbers
			int y = 167;
			int num = 1;
			//GO BearKats
			screen.setFont(new Font("Arial", Font.BOLD, 48));
			screen.drawString(Color.WHITE, "Go", 425, 150);
			screen.drawString(Color.BLUE, "Bear", 500, 150);
			screen.drawString(Color.ORANGE, "Kats!", 610, 150);
			//Picking Your Teams!
			screen.setFont(new Font("Arial", Font.BOLD, 20));
			screen.drawString(Color.ORANGE, "Pick your teams!", 425, 190);
			//Team1 and Team 2 (Over team boxes)
			screen.drawString(Color.ORANGE, "Team 1", 353, 230);
			screen.drawString(Color.ORANGE, "Team 2", 607, 230);
			//Team1 and Team 2 (Next to Player Box)
			screen.setFont(new Font("Arial", Font.BOLD, 30));
			if(isTeam1Picking == true) 
				screen.drawString(Color.BLUE, "Team 1", 283, 185);
			else if(isTeam2Picking == true)
				screen.drawString(Color.BLUE, "Team 2", 283, 185);
			//Amount of Players per team (Under player Boxes)
			screen.setFont(new Font("Arial", Font.BOLD, 20));
			screen.drawString(Color.RED, (team1.size()) + "/" + playersPerTeam, 370, 480);
			screen.drawString(Color.GREEN, (team2.size()) + "/" + playersPerTeam, 626, 480);
			//Robot Names
			for(String name : robotName) {
				Color color = Color.ORANGE;
				if(team1.contains(name)) {
					color = Color.RED;
				}else if(team2.contains(name)) {
					color = Color.GREEN;
				}else{
					color = Color.WHITE;
				}
				screen.drawString(color, num + ".) " +name, 30, y);
				y += 22;
				num++;
			}
			num = 1;
			y = 265;
			for(String name : team1) {
				screen.drawString(Color.RED, num + ".) " +name, 282, y);
				y += 22;
				num++;
			}
			num = 1;
			y = 265;
			for(String name : team2) {
				screen.drawString(Color.GREEN, num + ".) " +name, 537, y);
				y += 22;
				num++;
			}
		}
		if(!isOpenScreen() && !teamPickingScreen()) {//TODO Complete Game Screen
			Screen screen = new Screen(pen);
			//Background
			screen.setBackground(Color.BLACK);
			//Title
			screen.setFont(new Font("Arial", Font.BOLD, 48));
			screen.createRectangle(Color.DARK_GRAY, 145, 45, 500, 55, true);//Background
			screen.drawString(Color.RED, "Robot Smash v1.0!", 180, 90);
			screen.createRectangle(Color.ORANGE, 145, 45, 500, 5, true);//Top Part of Rectangle
			screen.createRectangle(Color.ORANGE, 145, 95, 500, 5, true);//Bottom
			screen.createRectangle(Color.ORANGE, 145, 50, 5, 45, true);//Left
			screen.createRectangle(Color.ORANGE, 640, 50, 5, 45, true);//Right
			//Team1 and Team 2 (Over team boxes)
			screen.createRectangle(Color.ORANGE, 329, 140, 150, 70, true);//Team Notifier Box
			//Team1 and Team 2 (Next to Player Box)
			screen.setFont(new Font("Arial", Font.BOLD, 30));
			if(lastControl == null || lastControl.equals("2")) {
				screen.drawString(Color.BLUE, "Team 1", 353, 185);
			}
			else if(lastControl.equalsIgnoreCase("1")) {
				screen.drawString(Color.BLUE, "Team 2", 353, 185);
			}
			//MessageBox
			int y = 568;
			int num = 0;
			screen.setFont(new Font("Arial", Font.BOLD, 20));
			screen.createRectangle(Color.DARK_GRAY, 26, 430, 500, 150, true);
			int amount = 0;
			for(String msg : msgs) {
				if(amount < 6) {
					screen.drawString(Color.WHITE, msg, 36, y);
					y-= 22;
					amount++;
				}
			}
			//Players Used Board
			screen.createRectangle(Color.BLUE, 550, 140, 230, 415, false);//Player Box
			y = 167;
			num = 1;
			screen.drawString(Color.PINK, "Players Used", 560, 130);
			for(String name : hadTurn) {
				Color color = Color.ORANGE;
				if(team1.contains(name)) {
					color = Color.RED;
				}else if(team2.contains(name)) {
					color = Color.GREEN;
				}else{
					color = Color.WHITE;
				}
				screen.drawString(color, num + ".) " +name, 560, y);
				y += 22;
				num++;
			}
			//Available Players Board
			//Score Board
			screen.createRectangle(Color.WHITE, 40, 120, 225, 115, true);//Main Box
			screen.createRectangle(Color.ORANGE, 40, 152, 225, 5, true);//Underline of Score
			screen.createRectangle(Color.ORANGE, 148, 156, 5, 79, true);//Middleline of Score Board
			screen.setFont(new Font("Arial", Font.BOLD, 35));
			screen.drawString(Color.BLUE, "Score", 100, 150);
			screen.setFont(new Font("Arial", Font.BOLD, 23));
			screen.drawString(Color.RED, "Team 1", 52, 180);
			screen.drawString(Color.GREEN, "Team 2", 169, 180);
			screen.drawString(Color.BLACK, score1 + "", 52, 200);
			screen.drawString(Color.BLACK, score2 + "", 169, 200);
			//Buttons
			
		}

		/*for (int x = 0; x < score1 * 3; x++) {
			pen.drawString("]]]]]]]]]]]]]]", 210, x * 5 + 150);
		}*/

		// Do the loop to draw Team 1 picture
		// int left = 0; // distance from left
		// int top = 200; //distance from top
		//
		// int size = (int)power1/4;
		// drawPicture(left,top,size);
	}
	
	public int getPosition(String name) {
		int x = 0;
		while(x < robotName.size()) {
			if(robotName.get(x).equalsIgnoreCase(name))
				return x;
			x++;
		}
		return -1;
	}
	

	/*public void drawPicture(Graphics pen) {
		char lt;// get color
		for (int v = 0; v < height; v++)
			for (int h = 0; h < pic1[v].length(); h++) {
				lt = pic1[v].charAt(h);
				switch (lt) {
				case ':':
					pen.setColor(Color.lightGray);
					break;
				case 'D':
					pen.setColor(Color.gray);
					break;
				case 'W':
					pen.setColor(Color.white);
					break;
				case 'R':
					pen.setColor(Color.red);
					break;
				case 'E':
					pen.setColor(Color.pink);
					break;
				case 'Y':
					pen.setColor(Color.yellow);
					break;
				// Add for other colors here
				}
				if (lt != ' ')
					drawPixel(h * size + left, v * size + top, size, pen);

			}
	}*/
	
	public class Screen {
		
		public Graphics pen;
		
		public Screen(Graphics pen) {
			this.pen = pen;
		}
		
		public void setFont(Font font) {
			pen.setFont(font);
		}
		
		public void drawString(Color color, String str, int x, int y) {
			pen.setColor(color);
			pen.drawString(str, x, y);
		}
		
		public void createButton(Color background, Color text, Font font, String str, int xBackground, int yBackground, int xStr, int yStr, int size1, int size2) {
			pen.setColor(background);
			pen.fill3DRect(xBackground, yBackground, size1, size2, true);
			pen.setFont(font);
			pen.setColor(text);
			pen.drawString(str, xStr, yStr);
		}
		
		public void createRectangle(Color color, int x, int y, int width, int length, boolean raised) {
			pen.setColor(color);
			pen.fill3DRect(x, y, width, length, raised);
		}
		
		public void setBackground(Color color) {
			pen.setColor(color);
			pen.fill3DRect(0, 20, 900, 900, false);
		}
	}

	void drawPixel(int x, int y, int s, Graphics G) // draws a single point
	{
		for (int h = 0; h < s; h++)
			G.drawLine(x, h + y, x + s, h + y);
	}

	public static void pause(long r) {
		try {
			Thread.sleep(r);
		} catch (Exception e) {
			out.println(" sleep error " + e);
		}
	}
} // end class
// Klein ISD Acceptable use policy:
// 1. Student developed games and other programming projects may not include
// profane references to a deity,
// obscene language, drug abuse or paraphernalia, images of suicide or criminal
// violence, suggested or
// explicit sexuality, partial/full/frontal nudity, or any subject matter
// inappropriate for young adults,
// and any content that may offend the moral standards of the community are
// strictly prohibited. Students
// must receive teacher approval on game and project design before developing
// content within a class
// assignment or project.
//
// 2. All games developed, viewed, or shown/demonstrated on a Klein ISD
// computer, and/or as part of Klein ISD
// assignments and projects must meet the Entertainment Software Rating Board
// rating for Early Childhood or
// Everyone.
// (ESRB: http://www.esrb.org/ratings/ratings_guide.jsp#rating_categories)
//
// 3. Additionally, student created games and projects may not contain content
// that is categorized by the
// following ESRB content descriptors: Alcohol Reference, Animated Blood, Blood,
// Blood and Gore, Crude Humor,
// Drug Reference, Fantasy Violence, Intense Violence, Language, Lyrics, Mature
// Humor, Nudity, Partial Nudity,
// Real Gambling, Sexual Content, Sexual Themes, Sexual Violence, Simulated
// Gambling, and Strong Language.
//

