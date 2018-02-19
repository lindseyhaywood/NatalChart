import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FirstAttempt {
	static Toolkit tk = Toolkit.getDefaultToolkit();
	private static final int WIDTH = (int)(tk.getScreenSize().getWidth()*0.9);
	private static final int HEIGHT = (int)(tk.getScreenSize().getHeight()*0.9);
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					try{
						createAndShowGUI();
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
			}
		);
	}
	
	private static void createAndShowGUI() throws IOException {
		JFrame frame = new ImageFrame(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}

class ImageFrame extends JFrame{
	static Toolkit tk = Toolkit.getDefaultToolkit();
	private static final int fWidth = (int)(tk.getScreenSize().getWidth()*0.9);
	private static final int fHeight = (int)(tk.getScreenSize().getHeight()*0.9);
	private boolean natalChartCalled = false;
	
	//constructor
	public ImageFrame(int width, int height) throws IOException{
		
		this.setTitle("CAP 3027 2017 Term Project - The Night Sky - Lindsey Haywood");
		this.setSize(width, height);
		
		addMenu();
		
		//instructions image
		class Instructions extends JPanel
		{
			private BufferedImage instructions;
			private int instructionsW;
			private int instructionsH;
			private int instructionsx;
			private int instructionsy;
			
			Instructions() throws IOException
			{
				instructions = ImageIO.read(new File("img/coverimage.png"));
				instructionsW = (int)(fHeight*0.8*instructions.getWidth()/instructions.getHeight());
				instructionsH = (int)(fHeight*0.8);
				instructionsx = instructionsW/6;
				instructionsy = instructionsH/16;
			}
			
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				super.paintComponent(g);
				
				setBackground(Color.BLACK);
				g2d.drawImage(instructions, instructionsx, instructionsy, instructionsW, instructionsH, null);
			}
		}
		
		this.add(new Instructions());
		this.setVisible(true);
	}
	
	
	private void addMenu(){
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem natalChartItem = new JMenuItem("Natal Chart");
		natalChartItem.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				natalChartCalled = true;
				try
				{
					natalChart();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		
		fileMenu.add(natalChartItem);
		
		JMenuItem saveItem = new JMenuItem("Save Chart");
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(natalChartCalled)
				{
					saveChart();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You have to create a natal chart first!");
				}
			}
		});
		
		fileMenu.add(saveItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		fileMenu.add(exitItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
	}
	
	
	//natal chart
	public void natalChart() throws IOException{
		
		class Display extends JPanel implements ActionListener
		{
			//global variables
			private int pWidth;
			private int pHeight;
			private JButton button;
			private NatalChart chart;
			private boolean newChart;
			private String birthday;
			private String birthtime;
			private Timer tm;
			
			//global variables for the natal chart
			private BufferedImage chartBack;
			private BufferedImage chartFront;
			private double chartScale;
			private int chartW;
			private int chartBackx;
			private int chartBacky;
			private AffineTransform chartAT;
			private int radius;
			private int risingDeg;
			private int rotateAmt;
			private int currentRotateAmt;
			
			//global variables for the table and aspect table
			private BufferedImage table;
			private BufferedImage aspectTable;
			private int tableW;
			private int tableH;
			private int tablex;
			private int tabley;
			private int aspectTableW;
			private int aspectTableH;
			private int aspectTablex;
			private int aspectTabley;
			
			//global variables for aspects
			private BufferedImage conjunction;
			private BufferedImage opposition;
			private BufferedImage trine;
			private BufferedImage square;
			private BufferedImage sextile;
			
			private int aspectW;
			private int aspectH;
			int[][] aspectCoordinates;
			
			private int planetW;
			
			//global variables for planet images
			private BufferedImage sun;
			private BufferedImage moon;
			private BufferedImage mercury;
			private BufferedImage venus;
			private BufferedImage mars;
			private BufferedImage jupiter;
			private BufferedImage saturn;
			private BufferedImage uranus;
			private BufferedImage neptune;
			private BufferedImage pluto;
			private BufferedImage node;
			
			//global variables for the zodiac sign image that correlates to a specific planet
			private BufferedImage sunSign;
			private BufferedImage moonSign;
			private BufferedImage mercurySign;
			private BufferedImage venusSign;
			private BufferedImage marsSign;
			private BufferedImage jupiterSign;
			private BufferedImage saturnSign;
			private BufferedImage uranusSign;
			private BufferedImage neptuneSign;
			private BufferedImage plutoSign;
			private BufferedImage nodeSign;
			private BufferedImage risingSign;
			
			//global variables for number images
			private BufferedImage blank;
			private BufferedImage zero;
			private BufferedImage one;
			private BufferedImage two;
			private BufferedImage three;
			private BufferedImage four;
			private BufferedImage five;
			private BufferedImage six;
			private BufferedImage seven;
			private BufferedImage eight;
			private BufferedImage nine;
			private BufferedImage degree;
			
			//global variables for planet x coordinate
			private int sunx;
			private int moonx;
			private int mercuryx;
			private int venusx;
			private int marsx;
			private int jupiterx;
			private int saturnx;
			private int uranusx;
			private int neptunex;
			private int plutox;
			private int nodex;
			
			//global variables for planet y coordinate
			private int suny;
			private int moony;
			private int mercuryy;
			private int venusy;
			private int marsy;
			private int jupitery;
			private int saturny;
			private int uranusy;
			private int neptuney;
			private int plutoy;
			private int nodey;
			
			//global variables for planet degrees
			private int sunDeg;
			private int moonDeg;
			private int mercuryDeg;
			private int venusDeg;
			private int marsDeg;
			private int jupiterDeg;
			private int saturnDeg;
			private int uranusDeg;
			private int neptuneDeg;
			private int plutoDeg;
			private int nodeDeg;
			
			//global variables to update planet degrees
			private int newSunDeg;
			private int newMoonDeg;
			private int newMercuryDeg;
			private int newVenusDeg;
			private int newMarsDeg;
			private int newJupiterDeg;
			private int newSaturnDeg;
			private int newUranusDeg;
			private int newNeptuneDeg;
			private int newPlutoDeg;
			private int newNodeDeg;
			
			//global variable for width, height, and x coordinate of the zodiac sign image that correlates to a specific planet
			private int signW;
			private int signH;
			private int signx;
			
			//global variable for y coordinate of the zodiac sign image that correlates to a specific planet
			private int sunSigny;
			private int moonSigny;
			private int mercurySigny;
			private int venusSigny;
			private int marsSigny;
			private int jupiterSigny;
			private int saturnSigny;
			private int uranusSigny;
			private int neptuneSigny;
			private int plutoSigny;
			private int nodeSigny;
			private int risingSigny;
			
			private int degW;
			private int degH;
			
			//global variables for degrees of each planet's zodiac sign
			private int sunSignDeg;
			private int moonSignDeg;
			private int mercurySignDeg;
			private int venusSignDeg;
			private int marsSignDeg;
			private int jupiterSignDeg;
			private int saturnSignDeg;
			private int uranusSignDeg;
			private int neptuneSignDeg;
			private int plutoSignDeg;
			private int nodeSignDeg;
			private int risingSignDeg;
			
			//defines initial image coordinates
			public Display() throws IOException
			{
				//variable set-up
				pWidth = (int)(fWidth*0.99);
				pHeight = (int)(fHeight*0.93);
				chart = new NatalChart("06301996", "0154");
				newChart = false;
				tm = new Timer(50, this);
				
				chartBack = ImageIO.read(new File("img/chart/chartBack.png"));
				chartFront = ImageIO.read(new File("img/chart/chartFront1.png"));
				chartScale = (double)pHeight*0.8/chartBack.getWidth();
				chartW = (int)(pHeight*0.8);
				chartBackx = (3*pWidth-2*chartW)/4;
				chartBacky = (pHeight-chartW)/2;
				chartAT = AffineTransform.getTranslateInstance(chartBackx, chartBacky);
				chartAT.scale(chartScale, chartScale);
				radius = (int)(chartW/3.5);
				risingDeg = chart.getRisingDeg(chart.getRisingSign());
				if(risingDeg < 180 && risingDeg != 180)
				{
					rotateAmt = 180 + risingDeg;
				}
				else if(risingDeg != 180)
				{
					rotateAmt = risingDeg - 180;
				}
				chartAT.rotate(Math.toRadians(rotateAmt), chartBack.getWidth()/2, chartBack.getHeight()/2);
				
				table = ImageIO.read(new File("img/table/table.png"));
				aspectTable = ImageIO.read(new File("img/aspects/AspectChart.png"));
				tableW = (int)(pHeight*0.4*table.getWidth()/table.getHeight());
				tableH = (int)(pHeight*0.4);
				aspectTableW = (int)(pHeight*0.4*aspectTable.getWidth()/aspectTable.getHeight());
				aspectTableH = (int)(pHeight*0.4);
				tablex = (pWidth-2*tableW)/4;
				aspectTablex = tablex;
				tabley = (int) ((pHeight-tableH-aspectTableH)/5);
				aspectTabley = (int) (0.5*pHeight);
				
				conjunction = ImageIO.read(new File("img/aspects/conjunction.png"));
				opposition = ImageIO.read(new File("img/aspects/opposition.png"));
				trine = ImageIO.read(new File("img/aspects/trine.png"));
				square = ImageIO.read(new File("img/aspects/square.png"));
				sextile = ImageIO.read(new File("img/aspects/sextile.png"));
				
				aspectW = (int)(aspectTableW*conjunction.getWidth()/aspectTable.getWidth());
				aspectH = (int)(aspectTableH*conjunction.getHeight()/aspectTable.getHeight());
				
				aspectCoordinates = new int[2][11];
				for(int i = 0; i < 11; i++)
				{
					aspectCoordinates[0][i] = aspectTablex + aspectW*(i+1);
					aspectCoordinates[1][i] = aspectTabley + aspectH*(i+1);
				}
				
				planetW = chartW/32;
				
				sun = ImageIO.read(new File("img/planets/sun.png"));
				moon = ImageIO.read(new File("img/planets/moon.png"));
				mercury = ImageIO.read(new File("img/planets/mercury.png"));
				venus = ImageIO.read(new File("img/planets/venus.png"));
				mars = ImageIO.read(new File("img/planets/mars.png"));
				jupiter = ImageIO.read(new File("img/planets/jupiter.png"));
				saturn = ImageIO.read(new File("img/planets/saturn.png"));
				uranus = ImageIO.read(new File("img/planets/uranus.png"));
				neptune = ImageIO.read(new File("img/planets/neptune.png"));
				pluto = ImageIO.read(new File("img/planets/pluto.png"));
				node = ImageIO.read(new File("img/planets/node.png"));
				
				sunSign = ImageIO.read(new File("img/table/" + chart.getSign("sun") + ".png"));
				moonSign = ImageIO.read(new File("img/table/" + chart.getSign("moon") + ".png"));
				mercurySign = ImageIO.read(new File("img/table/" + chart.getSign("mercury") + ".png"));
				venusSign = ImageIO.read(new File("img/table/" + chart.getSign("venus") + ".png"));
				marsSign = ImageIO.read(new File("img/table/" + chart.getSign("mars") + ".png"));
				jupiterSign = ImageIO.read(new File("img/table/" + chart.getSign("jupiter") + ".png"));
				saturnSign = ImageIO.read(new File("img/table/" + chart.getSign("saturn") + ".png"));
				uranusSign = ImageIO.read(new File("img/table/" + chart.getSign("uranus") + ".png"));
				neptuneSign = ImageIO.read(new File("img/table/" + chart.getSign("neptune") + ".png"));
				plutoSign = ImageIO.read(new File("img/table/" + chart.getSign("pluto") + ".png"));
				nodeSign = ImageIO.read(new File("img/table/" + chart.getSign("node") + ".png"));
				risingSign = ImageIO.read(new File("img/table/" + chart.getRisingSign() + ".png"));
				
				blank = ImageIO.read(new File("img/table/numbers/blank.png"));
				zero = ImageIO.read(new File("img/table/numbers/zero.png"));
				one = ImageIO.read(new File("img/table/numbers/one.png"));
				two = ImageIO.read(new File("img/table/numbers/two.png"));
				three = ImageIO.read(new File("img/table/numbers/three.png"));
				four = ImageIO.read(new File("img/table/numbers/four.png"));
				five = ImageIO.read(new File("img/table/numbers/five.png"));
				six = ImageIO.read(new File("img/table/numbers/six.png"));
				seven = ImageIO.read(new File("img/table/numbers/seven.png"));
				eight = ImageIO.read(new File("img/table/numbers/eight.png"));
				nine = ImageIO.read(new File("img/table/numbers/nine.png"));
				degree = ImageIO.read(new File("img/table/numbers/degree.png"));
				
				sunDeg = chart.getDegrees("sun", chart.getSign("sun"));
				moonDeg = chart.getDegrees("moon", chart.getSign("moon"));
				mercuryDeg = chart.getDegrees("mercury", chart.getSign("mercury"));
				venusDeg = chart.getDegrees("venus", chart.getSign("venus"));
				marsDeg = chart.getDegrees("mars", chart.getSign("mars"));
				jupiterDeg = chart.getDegrees("jupiter", chart.getSign("jupiter"));
				saturnDeg = chart.getDegrees("saturn", chart.getSign("saturn"));
				uranusDeg = chart.getDegrees("uranus", chart.getSign("uranus"));
				neptuneDeg = chart.getDegrees("neptune", chart.getSign("neptune"));
				plutoDeg = chart.getDegrees("pluto", chart.getSign("pluto"));
				nodeDeg = chart.getDegrees("node", chart.getSign("node"));
				
				int degRot = 0;
				
				if(risingDeg != 180)
				{
					degRot = risingDeg - 180;
				}
				if(sunDeg - degRot > 0 || sunDeg - degRot < 360)
				{
					sunDeg = sunDeg - degRot;
				}
				else if(sunDeg - degRot < 0)
				{
					sunDeg = 360 - Math.abs(sunDeg - degRot);
				}
				else
				{
					sunDeg = sunDeg - degRot - 360;
				}
				if(moonDeg - degRot > 0 || moonDeg - degRot < 360)
				{
					moonDeg = moonDeg - degRot;
				}
				else if(moonDeg - degRot < 0)
				{
					moonDeg = 360 - Math.abs(moonDeg - degRot);
				}
				else
				{
					moonDeg = moonDeg - degRot - 360;
				}
				if(mercuryDeg - degRot > 0 || mercuryDeg - degRot < 360)
				{
					mercuryDeg = mercuryDeg - degRot;
				}
				else if(sunDeg - degRot < 0)
				{
					mercuryDeg = 360 - Math.abs(mercuryDeg - degRot);
				}
				else
				{
					mercuryDeg = mercuryDeg - degRot - 360;
				}
				if(venusDeg - degRot > 0 || venusDeg - degRot < 360)
				{
					venusDeg = venusDeg - degRot;
				}
				else if(venusDeg - degRot < 0)
				{
					venusDeg = 360 - Math.abs(venusDeg - degRot);
				}
				else
				{
					venusDeg = venusDeg - degRot - 360;
				}
				if(marsDeg - degRot > 0 || marsDeg - degRot < 360)
				{
					marsDeg = marsDeg - degRot;
				}
				else if(marsDeg - degRot < 0)
				{
					marsDeg = 360 - Math.abs(marsDeg - degRot);
				}
				else
				{
					marsDeg = marsDeg - degRot - 360;
				}
				if(jupiterDeg - degRot > 0 || jupiterDeg - degRot < 360)
				{
					jupiterDeg = jupiterDeg - degRot;
				}
				else if(jupiterDeg - degRot < 0)
				{
					jupiterDeg = 360 - Math.abs(jupiterDeg - degRot);
				}
				else
				{
					jupiterDeg = jupiterDeg - degRot - 360;
				}
				if(saturnDeg - degRot > 0 || saturnDeg - degRot < 360)
				{
					saturnDeg = saturnDeg - degRot;
				}
				else if(saturnDeg - degRot < 0)
				{
					saturnDeg = 360 - Math.abs(saturnDeg - degRot);
				}
				else
				{
					saturnDeg = saturnDeg - degRot - 360;
				}
				if(uranusDeg - degRot > 0 || uranusDeg - degRot < 360)
				{
					uranusDeg = uranusDeg - degRot;
				}
				else if(uranusDeg - degRot < 0)
				{
					uranusDeg = 360 - Math.abs(uranusDeg - degRot);
				}
				else
				{
					uranusDeg = uranusDeg - degRot - 360;
				}
				if(neptuneDeg - degRot > 0 || neptuneDeg - degRot < 360)
				{
					neptuneDeg = neptuneDeg - degRot;
				}
				else if(neptuneDeg - degRot < 0)
				{
					neptuneDeg = 360 - Math.abs(neptuneDeg - degRot);
				}
				else
				{
					neptuneDeg = neptuneDeg - degRot - 360;
				}
				if(plutoDeg - degRot > 0 || plutoDeg - degRot < 360)
				{
					plutoDeg = plutoDeg - degRot;
				}
				else if(plutoDeg - degRot < 0)
				{
					plutoDeg = 360 - Math.abs(plutoDeg - degRot);
				}
				else
				{
					plutoDeg = plutoDeg - degRot - 360;
				}
				if(nodeDeg - degRot > 0 || nodeDeg - degRot < 360)
				{
					nodeDeg = nodeDeg - degRot;
				}
				else if(nodeDeg - degRot < 0)
				{
					nodeDeg = 360 - Math.abs(nodeDeg - degRot);
				}
				else
				{
					nodeDeg = nodeDeg - degRot - 360;
				}
				
				//variable set-up: x coordinates of planet images
				sunx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(sunDeg)))+(chartW/2);
				moonx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(moonDeg)))+(chartW/2);
				mercuryx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(mercuryDeg)))+(chartW/2);
				venusx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(venusDeg)))+(chartW/2);
				marsx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(marsDeg)))+(chartW/2);
				jupiterx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(jupiterDeg)))+(chartW/2);
				saturnx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(saturnDeg)))+(chartW/2);
				uranusx = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(uranusDeg)))+(chartW/2);
				neptunex = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(neptuneDeg)))+(chartW/2);
				plutox = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(plutoDeg)))+(chartW/2);
				nodex = chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(nodeDeg)))+(chartW/2);
				
				//variable set-up: y coordinates of planet images
				suny = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(sunDeg)))+(chartW/2);
				moony = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(moonDeg)))+(chartW/2);
				mercuryy = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(mercuryDeg)))+(chartW/2);
				venusy = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(venusDeg)))+(chartW/2);
				marsy = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(marsDeg)))+(chartW/2);
				jupitery = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(jupiterDeg)))+(chartW/2);
				saturny = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(saturnDeg)))+(chartW/2);
				uranusy = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(uranusDeg)))+(chartW/2);
				neptuney = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(neptuneDeg)))+(chartW/2);
				plutoy = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(plutoDeg)))+(chartW/2);
				nodey = chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(nodeDeg)))+(chartW/2);
				
				newSunDeg = sunDeg;
				newMoonDeg = moonDeg;
				newMercuryDeg = mercuryDeg;
				newVenusDeg = venusDeg;
				newMarsDeg = marsDeg;
				newJupiterDeg = jupiterDeg;
				newSaturnDeg = saturnDeg;
				newUranusDeg = uranusDeg;
				newNeptuneDeg = neptuneDeg;
				newPlutoDeg = plutoDeg;
				newNodeDeg = nodeDeg;
				
				//variable set-up: sign image width, height, and x coordinate
				signW = (int)(tableW*sunSign.getWidth()/table.getWidth());
				signH = (int)(tableH*sunSign.getHeight()/table.getHeight());
				signx = tablex + (tableW*2/3);
				
				//variable set-up: y coordinates of zodiac sign images that correlate to planets
				sunSigny = tabley + (tableH/14);
				moonSigny = tabley + (tableH*2/14);
				mercurySigny = tabley + (tableH*3/14);
				venusSigny = tabley + (tableH*4/14);
				marsSigny = tabley + (tableH*5/14);
				jupiterSigny = tabley + (tableH*6/14);
				saturnSigny = tabley + (tableH*7/14);
				uranusSigny = tabley + (tableH*8/14);
				neptuneSigny = tabley + (tableH*9/14);
				plutoSigny = tabley + (tableH*10/14);
				nodeSigny = tabley + (tableH*11/14);
				risingSigny = tabley + (tableH*12/14);
				
				degW = (int)(tableW*zero.getWidth()/table.getWidth());
				degH = (int)(tableH*zero.getHeight()/table.getHeight());
				
				//variable set-up: get degrees for each planet's sign
				sunSignDeg = chart.getDegrees("sun");
				moonSignDeg = chart.getDegrees("moon");
				mercurySignDeg = chart.getDegrees("mercury");
				venusSignDeg = chart.getDegrees("venus");
				marsSignDeg = chart.getDegrees("mars");
				jupiterSignDeg = chart.getDegrees("jupiter");
				saturnSignDeg = chart.getDegrees("saturn");
				uranusSignDeg = chart.getDegrees("uranus");
				neptuneSignDeg = chart.getDegrees("neptune");
				plutoSignDeg = chart.getDegrees("pluto");
				nodeSignDeg = chart.getDegrees("node");
				risingSignDeg = chart.getRisingDeg();
				
				// button action listener that updates the current natal chart
				setLayout(null);
				button = new JButton("Create New Natal Chart");
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						boolean isValid;
						birthday = JOptionPane.showInputDialog("Birth date? (mmddyyyy, no slashes!)");
						birthtime = JOptionPane.showInputDialog("Birth time? (hhmm, military EST)");
						
						isValid = checkDateAndTime(birthday, birthtime);
						if(isValid)
						{
							newChart = true;
							try
							{
								chart = new NatalChart(birthday, birthtime);
							}
							catch (FileNotFoundException e1)
							{
								e1.printStackTrace();
							}
							
							if(rotateAmt != 0)
							{
								rotateAmt = 360 - rotateAmt;
							}
							
							risingDeg = chart.getRisingDeg(chart.getRisingSign());
							
							if(risingDeg < 180 && risingDeg != 180)
							{
								rotateAmt += 180 + risingDeg;
							}
							else if(risingDeg != 180)
							{
								rotateAmt += risingDeg - 180;
							}
							if(rotateAmt > 360)
							{
								rotateAmt -= 360;
							}
							currentRotateAmt = 0;
							
							newSunDeg = chart.getDegrees("sun", chart.getSign("sun"));
							newMoonDeg = chart.getDegrees("moon", chart.getSign("moon"));
							newMercuryDeg = chart.getDegrees("mercury", chart.getSign("mercury"));
							newVenusDeg = chart.getDegrees("venus", chart.getSign("venus"));
							newMarsDeg = chart.getDegrees("mars", chart.getSign("mars"));
							newJupiterDeg = chart.getDegrees("jupiter", chart.getSign("jupiter"));
							newSaturnDeg = chart.getDegrees("saturn", chart.getSign("saturn"));
							newUranusDeg = chart.getDegrees("uranus", chart.getSign("uranus"));
							newNeptuneDeg = chart.getDegrees("neptune", chart.getSign("neptune"));
							newPlutoDeg = chart.getDegrees("pluto", chart.getSign("pluto"));
							newNodeDeg = chart.getDegrees("node", chart.getSign("node"));
							
							int degRot = 0;
							if(risingDeg != 180)
							{
								degRot = risingDeg - 180;
							}
							if(newSunDeg - degRot > 0 || newSunDeg - degRot < 360)
							{
								newSunDeg = newSunDeg - degRot;
							}
							else if(newSunDeg - degRot < 0)
							{
								newSunDeg = 360 - Math.abs(newSunDeg - degRot);
							}
							else
							{
								newSunDeg = newSunDeg - degRot - 360;
							}
							if(newMoonDeg - degRot > 0 || newMoonDeg - degRot < 360)
							{
								newMoonDeg = newMoonDeg - degRot;
							}
							else if(newMoonDeg - degRot < 0)
							{
								newMoonDeg = 360 - Math.abs(newMoonDeg - degRot);
							}
							else
							{
								newMoonDeg = newMoonDeg - degRot - 360;
							}
							if(newMercuryDeg - degRot > 0 || newMercuryDeg - degRot < 360)
							{
								newMercuryDeg = newMercuryDeg - degRot;
							}
							else if(newMercuryDeg - degRot < 0)
							{
								newMercuryDeg = 360 - Math.abs(newMercuryDeg - degRot);
							}
							else
							{
								newMercuryDeg = newMercuryDeg - degRot - 360;
							}
							if(newVenusDeg - degRot > 0 || newVenusDeg - degRot < 360)
							{
								newVenusDeg = newVenusDeg - degRot;
							}
							else if(newVenusDeg - degRot < 0)
							{
								newVenusDeg = 360 - Math.abs(newVenusDeg - degRot);
							}
							else
							{
								newVenusDeg = newVenusDeg - degRot - 360;
							}
							if(newMarsDeg - degRot > 0 || newMarsDeg - degRot < 360)
							{
								newMarsDeg = newMarsDeg - degRot;
							}
							else if(newMarsDeg - degRot < 0)
							{
								newMarsDeg = 360 - Math.abs(newMarsDeg - degRot);
							}
							else
							{
								newMarsDeg = newMarsDeg - degRot - 360;
							}
							if(newJupiterDeg - degRot > 0 || newJupiterDeg - degRot < 360)
							{
								newJupiterDeg = newJupiterDeg - degRot;
							}
							else if(newJupiterDeg - degRot < 0)
							{
								newJupiterDeg = 360 - Math.abs(newJupiterDeg - degRot);
							}
							else
							{
								newJupiterDeg = newJupiterDeg - degRot - 360;
							}
							if(newSaturnDeg - degRot > 0 || newSaturnDeg - degRot < 360)
							{
								newSaturnDeg = newSaturnDeg - degRot;
							}
							else if(newSaturnDeg - degRot < 0)
							{
								newSaturnDeg = 360 - Math.abs(newSaturnDeg - degRot);
							}
							else
							{
								newSaturnDeg = newSaturnDeg - degRot - 360;
							}
							if(newUranusDeg - degRot > 0 || newUranusDeg - degRot < 360)
							{
								newUranusDeg = newUranusDeg - degRot;
							}
							else if(newUranusDeg - degRot < 0)
							{
								newUranusDeg = 360 - Math.abs(newUranusDeg - degRot);
							}
							else
							{
								newUranusDeg = newUranusDeg - degRot - 360;
							}
							if(newNeptuneDeg - degRot > 0 || newNeptuneDeg - degRot < 360)
							{
								newNeptuneDeg = newNeptuneDeg - degRot;
							}
							else if(newNeptuneDeg - degRot < 0)
							{
								newNeptuneDeg = 360 - Math.abs(newNeptuneDeg - degRot);
							}
							else
							{
								newNeptuneDeg = newNeptuneDeg - degRot - 360;
							}
							if(newPlutoDeg - degRot > 0 || newPlutoDeg - degRot < 360)
							{
								newPlutoDeg = newPlutoDeg - degRot;
							}
							else if(newPlutoDeg - degRot < 0)
							{
								newPlutoDeg = 360 - Math.abs(newPlutoDeg - degRot);
							}
							else
							{
								newPlutoDeg = newPlutoDeg - degRot - 360;
							}
							if(newNodeDeg - degRot > 0 || newNodeDeg - degRot < 360)
							{
								newNodeDeg = newNodeDeg - degRot;
							}
							else if(newNodeDeg - degRot < 0)
							{
								newNodeDeg = 360 - Math.abs(newNodeDeg - degRot);
							}
							else
							{
								newNodeDeg = newNodeDeg - degRot - 360;
							}
							
							try {
								sunSign = ImageIO.read(new File("img/table/" + chart.getSign("sun") + ".png"));
								moonSign = ImageIO.read(new File("img/table/" + chart.getSign("moon") + ".png"));
								mercurySign = ImageIO.read(new File("img/table/" + chart.getSign("mercury") + ".png"));
								venusSign = ImageIO.read(new File("img/table/" + chart.getSign("venus") + ".png"));
								marsSign = ImageIO.read(new File("img/table/" + chart.getSign("mars") + ".png"));
								jupiterSign = ImageIO.read(new File("img/table/" + chart.getSign("jupiter") + ".png"));
								saturnSign = ImageIO.read(new File("img/table/" + chart.getSign("saturn") + ".png"));
								uranusSign = ImageIO.read(new File("img/table/" + chart.getSign("uranus") + ".png"));
								neptuneSign = ImageIO.read(new File("img/table/" + chart.getSign("neptune") + ".png"));
								plutoSign = ImageIO.read(new File("img/table/" + chart.getSign("pluto") + ".png"));
								nodeSign = ImageIO.read(new File("img/table/" + chart.getSign("node") + ".png"));
								risingSign = ImageIO.read(new File("img/table/" + chart.getRisingSign() + ".png"));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							sunSignDeg = chart.getDegrees("sun");
							moonSignDeg = chart.getDegrees("moon");
							mercurySignDeg = chart.getDegrees("mercury");
							venusSignDeg = chart.getDegrees("venus");
							marsSignDeg = chart.getDegrees("mars");
							jupiterSignDeg = chart.getDegrees("jupiter");
							saturnSignDeg = chart.getDegrees("saturn");
							uranusSignDeg = chart.getDegrees("uranus");
							neptuneSignDeg = chart.getDegrees("neptune");
							plutoSignDeg = chart.getDegrees("pluto");
							nodeSignDeg = chart.getDegrees("node");
							risingSignDeg = chart.getRisingDeg();
							
							newChart = true;
							tm.start();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Invalid Input!");
						}
					}
				});
				button.setBounds(0, pHeight-(int)(pHeight*0.05), pWidth, (int)(pHeight*0.05));
				add(button);
			}
			
			//draws natal chart and other table images to the screen
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				super.paintComponent(g);
				
				setBackground(Color.BLACK);
				
				//table zodiac sign images for each planet
				g2d.drawImage(table, tablex, tabley, tableW, tableH, null);
				g2d.drawImage(sunSign, signx, sunSigny, signW, signH, null);
				g2d.drawImage(moonSign, signx, moonSigny, signW, signH, null);
				g2d.drawImage(mercurySign, signx, mercurySigny, signW, signH, null);
				g2d.drawImage(venusSign, signx, venusSigny, signW, signH, null);
				g2d.drawImage(marsSign, signx, marsSigny, signW, signH, null);
				g2d.drawImage(jupiterSign, signx, jupiterSigny, signW, signH, null);
				g2d.drawImage(saturnSign, signx, saturnSigny, signW, signH, null);
				g2d.drawImage(uranusSign, signx, uranusSigny, signW, signH, null);
				g2d.drawImage(neptuneSign, signx, neptuneSigny, signW, signH, null);
				g2d.drawImage(plutoSign, signx, plutoSigny, signW, signH, null);
				g2d.drawImage(nodeSign, signx, nodeSigny, signW, signH, null);
				g2d.drawImage(risingSign, signx, risingSigny, signW, signH, null);
				
				//table degree images for each planet
				g2d.drawImage(degree, signx-signW/2, sunSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, moonSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, mercurySigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, venusSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, marsSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, jupiterSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, saturnSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, uranusSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, neptuneSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, plutoSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, nodeSigny, degW, degH, null);
				g2d.drawImage(degree, signx-signW/2, risingSigny, degW, degH, null);
				
				//table degree digit images
				g2d.drawImage(getDigitImg(sunSignDeg, false), signx-signW/2-degW, sunSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(moonSignDeg, false), signx-signW/2-degW, moonSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(mercurySignDeg, false), signx-signW/2-degW, mercurySigny, degW, degH, null);
				g2d.drawImage(getDigitImg(venusSignDeg, false), signx-signW/2-degW, venusSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(marsSignDeg, false), signx-signW/2-degW, marsSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(jupiterSignDeg, false), signx-signW/2-degW, jupiterSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(saturnSignDeg, false), signx-signW/2-degW, saturnSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(uranusSignDeg, false), signx-signW/2-degW, uranusSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(neptuneSignDeg, false), signx-signW/2-degW, neptuneSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(plutoSignDeg, false), signx-signW/2-degW, plutoSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(nodeSignDeg, false), signx-signW/2-degW, nodeSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(risingSignDeg, false), signx-signW/2-degW, risingSigny, degW, degH, null);
				
				g2d.drawImage(getDigitImg(sunSignDeg, true), signx-signW/2-degW*2, sunSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(moonSignDeg, true), signx-signW/2-degW*2, moonSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(mercurySignDeg, true), signx-signW/2-degW*2, mercurySigny, degW, degH, null);
				g2d.drawImage(getDigitImg(venusSignDeg, true), signx-signW/2-degW*2, venusSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(marsSignDeg, true), signx-signW/2-degW*2, marsSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(jupiterSignDeg, true), signx-signW/2-degW*2, jupiterSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(saturnSignDeg, true), signx-signW/2-degW*2, saturnSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(uranusSignDeg, true), signx-signW/2-degW*2, uranusSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(neptuneSignDeg, true), signx-signW/2-degW*2, neptuneSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(plutoSignDeg, true), signx-signW/2-degW*2, plutoSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(nodeSignDeg, true), signx-signW/2-degW*2, nodeSigny, degW, degH, null);
				g2d.drawImage(getDigitImg(risingSignDeg, true), signx-signW/2-degW*2, risingSigny, degW, degH, null);
				
				//aspect table image
				g2d.drawImage(aspectTable, aspectTablex, aspectTabley, aspectTableW, aspectTableH, null);
				
				//natal chart images
				g2d.drawImage(chartBack, chartAT, null);
				g2d.drawImage(chartFront, chartBackx, chartBacky, chartW, chartW, null);
				
				//drawing the aspect lines on the chart
				//and the aspect symbols for the aspect table
				g2d.setStroke(new BasicStroke(3));
				for(int[] aspect: chart.getAspects())
				{
					if(aspect[2] == 0)
					{
						g2d.drawImage(conjunction, aspectCoordinates[0][aspect[0]], aspectCoordinates[1][aspect[1]], aspectW, aspectH, null);
					}
					else if(aspect[2] == 1)
					{
						g2d.setColor(Color.GREEN);
						g2d.drawLine(aspectDegToCart(aspect[0], true), aspectDegToCart(aspect[0], false), aspectDegToCart(aspect[1], true), aspectDegToCart(aspect[1], false));
						g2d.drawImage(sextile, aspectCoordinates[0][aspect[0]], aspectCoordinates[1][aspect[1]], aspectW, aspectH, null);
					}
					else if(aspect[2] == 2)
					{
						g2d.setColor(Color.RED);
						g2d.drawLine(aspectDegToCart(aspect[0], true), aspectDegToCart(aspect[0], false), aspectDegToCart(aspect[1], true), aspectDegToCart(aspect[1], false));
						g2d.drawImage(square, aspectCoordinates[0][aspect[0]], aspectCoordinates[1][aspect[1]], aspectW, aspectH, null);
					}
					else if(aspect[2] == 3)
					{
						g2d.setColor(Color.BLUE);
						g2d.drawLine(aspectDegToCart(aspect[0], true), aspectDegToCart(aspect[0], false), aspectDegToCart(aspect[1], true), aspectDegToCart(aspect[1], false));
						g2d.drawImage(trine, aspectCoordinates[0][aspect[0]], aspectCoordinates[1][aspect[1]], aspectW, aspectH, null);
					}
					else if(aspect[2] == 4)
					{
						g2d.setColor(Color.MAGENTA);
						g2d.drawLine(aspectDegToCart(aspect[0], true), aspectDegToCart(aspect[0], false), aspectDegToCart(aspect[1], true), aspectDegToCart(aspect[1], false));
						g2d.drawImage(opposition, aspectCoordinates[0][aspect[0]], aspectCoordinates[1][aspect[1]], aspectW, aspectH, null);
					}
					
				}
				
				//natal chart planet images (the images that animate)
				g2d.drawImage(sun, sunx, suny, planetW, planetW, null);
				g2d.drawImage(moon, moonx, moony, planetW, planetW, null);
				g2d.drawImage(mercury, mercuryx, mercuryy, planetW, planetW, null);
				g2d.drawImage(venus, venusx, venusy, planetW, planetW, null);
				g2d.drawImage(mars, marsx, marsy, planetW, planetW, null);
				g2d.drawImage(jupiter, jupiterx, jupitery, planetW, planetW, null);
				g2d.drawImage(saturn, saturnx, saturny, planetW, planetW, null);
				g2d.drawImage(uranus, uranusx, uranusy, planetW, planetW, null);
				g2d.drawImage(neptune, neptunex, neptuney, planetW, planetW, null);
				g2d.drawImage(pluto, plutox, plutoy, planetW, planetW, null);
				g2d.drawImage(node, nodex, nodey, planetW, planetW, null);
			}
			
			//updates planet image coordinates based on new natal chart
			public void actionPerformed(ActionEvent e)
			{
				if(newChart)
				{
					remove(button);
					if(sunDeg == newSunDeg)
						if(moonDeg == newMoonDeg)
							if(mercuryDeg == newMercuryDeg)
								if(venusDeg == newVenusDeg)
									if(marsDeg == newMarsDeg)
										if(jupiterDeg == newJupiterDeg)
											if(saturnDeg == newSaturnDeg)
												if(uranusDeg == newUranusDeg)
													if(neptuneDeg == newNeptuneDeg)
														if(plutoDeg == newPlutoDeg)
															if(nodeDeg == newNodeDeg)
																if(currentRotateAmt == rotateAmt)
																	newChart = false;
					
					if(sunDeg != newSunDeg){
						if(sunDeg < newSunDeg)
							sunDeg++;
						else
							sunDeg--;
						sunx = degToCart(sunDeg, true);
						suny = degToCart(sunDeg, false);
					}
					if(moonDeg != newMoonDeg){
						if(moonDeg < newMoonDeg)
							moonDeg++;
						else
							moonDeg--;
						moonx = degToCart(moonDeg, true);
						moony = degToCart(moonDeg, false);
					}
					if(mercuryDeg != newMercuryDeg){
						if(mercuryDeg < newMercuryDeg)
							mercuryDeg++;
						else
							mercuryDeg--;
						mercuryx = degToCart(mercuryDeg, true);
						mercuryy = degToCart(mercuryDeg, false);
					}
					if(venusDeg != newVenusDeg){
						if(venusDeg < newVenusDeg)
							venusDeg++;
						else
							venusDeg--;
						venusx = degToCart(venusDeg, true);
						venusy = degToCart(venusDeg, false);
					}
					if(marsDeg != newMarsDeg){
						if(marsDeg < newMarsDeg)
							marsDeg++;
						else
							marsDeg--;
						marsx = degToCart(marsDeg, true);
						marsy = degToCart(marsDeg, false);
					}
					if(jupiterDeg != newJupiterDeg){
						if(jupiterDeg < newJupiterDeg)
							jupiterDeg++;
						else
							jupiterDeg--;
						jupiterx = degToCart(jupiterDeg, true);
						jupitery = degToCart(jupiterDeg, false);
					}
					if(saturnDeg != newSaturnDeg){
						if(saturnDeg < newSaturnDeg)
							saturnDeg++;
						else
							saturnDeg--;
						saturnx = degToCart(saturnDeg, true);
						saturny = degToCart(saturnDeg, false);
					}
					if(uranusDeg != newUranusDeg){
						if(uranusDeg < newUranusDeg)
							uranusDeg++;
						else
							uranusDeg--;
						uranusx = degToCart(uranusDeg, true);
						uranusy = degToCart(uranusDeg, false);
					}
					if(neptuneDeg != newNeptuneDeg){
						if(neptuneDeg < newNeptuneDeg)
							neptuneDeg++;
						else
							neptuneDeg--;
						neptunex = degToCart(neptuneDeg, true);
						neptuney = degToCart(neptuneDeg, false);
					}
					if(plutoDeg != newPlutoDeg){
						if(plutoDeg < newPlutoDeg)
							plutoDeg++;
						else
							plutoDeg--;
						plutox = degToCart(plutoDeg, true);
						plutoy = degToCart(plutoDeg, false);
					}
					if(nodeDeg != newNodeDeg){
						if(nodeDeg < newNodeDeg)
							nodeDeg++;
						else
							nodeDeg--;
						nodex = degToCart(nodeDeg, true);
						nodey = degToCart(nodeDeg, false);
					}
					if(currentRotateAmt != rotateAmt){
						currentRotateAmt++;
						chartAT.rotate(Math.toRadians(1), chartBack.getWidth()/2, chartBack.getHeight()/2);
					}
					else
					{
						if(risingDeg < 180 && risingDeg != 180)
						{
							rotateAmt = 180 + risingDeg;
							currentRotateAmt = rotateAmt;
						}
						else if(risingDeg != 180)
						{
							rotateAmt = risingDeg - 180;
							currentRotateAmt = rotateAmt;
						}
					}
				}
				else
				{
					tm.stop();
					add(button);
				}
				repaint();
			}
			
			private boolean isDigit(String input)
			{
				if(input.equals("0")){return true;}
				else if(input.equals("1")){return true;}
				else if(input.equals("2")){return true;}
				else if(input.equals("3")){return true;}
				else if(input.equals("4")){return true;}
				else if(input.equals("5")){return true;}
				else if(input.equals("6")){return true;}
				else if(input.equals("7")){return true;}
				else if(input.equals("8")){return true;}
				else if(input.equals("9")){return true;}
				return false;
			}
			
			private boolean checkDateAndTime(String birthday, String time)
			{
				// check if day input is proper length,
				// then if all are digits,
				// then if digits are in proper range
				if(birthday.length() != 8){return false;}
				for(int i = 0; i < birthday.length(); i++)
				{
					if(!isDigit(birthday.substring(i,i+1))){return false;}
				}
				int month, day, year;
				if(birthday.substring(0,2).equals("00")){return false;}
				else if(birthday.substring(0,1).equals("0"))
				{
					month = Integer.parseInt(birthday.substring(1,2));
				}
				else
				{
					month = Integer.parseInt(birthday.substring(0,2));
				}
				if(birthday.substring(2,4).equals("00")){return false;}
				else if(birthday.substring(2,3).equals("0"))
				{
					day = Integer.parseInt(birthday.substring(3,4));
				}
				else
				{
					day = Integer.parseInt(birthday.substring(2,4));
				}
				if(birthday.substring(4,5).equals("0"))
				{
					return false;
				}
				else
				{
					year = Integer.parseInt(birthday.substring(4,8));
				}
				switch(month)
				{
					case 1:
						if(day > 31){return false;}
						else{break;}
					case 2:
						if(day > 29){return false;}
						else{break;}
					case 3:
						if(day > 31){return false;}
						else{break;}
					case 4:
						if(day > 30){return false;}
						else{break;}
					case 5:
						if(day > 31){return false;}
						else{break;}
					case 6:
						if(day > 30){return false;}
						else{break;}
					case 7:
						if(day > 31){return false;}
						else{break;}
					case 8:
						if(day > 31){return false;}
						else{break;}
					case 9:
						if(day > 30){return false;}
						else{break;}
					case 10:
						if(day > 31){return false;}
						else{break;}
					case 11:
						if(day > 30){return false;}
						else{break;}
					case 12:
						if(day > 31){return false;}
						else{break;}
					default:
						return false;
				}
				if(year < 1900 || year > 2030){return false;}
				
				// check if time input is proper length,
				// then if all are digits,
				// then if digits are in proper range
				if(time.length() != 4){return false;}
				for(int i = 0; i < time.length(); i++)
				{
					if(!isDigit(time.substring(i,i+1))){return false;}
				}
				int hour, min;
				if(time.substring(0,2).equals("00")){hour = 0;}
				else if(time.substring(0,1).equals("0"))
				{
					hour = Integer.parseInt(time.substring(1,2));
				}
				else
				{
					hour = Integer.parseInt(time.substring(0,2));
				}
				if(time.substring(2,4).equals("00")){min = 0;}
				else if(time.substring(3,4).equals("0"))
				{
					min = Integer.parseInt(time.substring(3));
				}
				else
				{
					min = Integer.parseInt(time.substring(2));
				}
				if(hour > 23){return false;}
				if(min > 59){return false;}
				return true;
			}
			
			//returns degree to x,y coordinates to plot in panel as planets animate
			private int degToCart(int deg, boolean xory){
				if(xory)
					return chartBackx-(planetW/2) + (int)(radius*Math.cos(Math.toRadians(deg)))+chartW/2;
				return chartBacky-(planetW/2) - (int)(radius*Math.sin(Math.toRadians(deg)))+chartW/2;
			}
			
			//returns final degree to x,y coordinates to plot in panel
			private int aspectDegToCart(int planet, boolean xory){
				switch(planet)
				{
					case 0:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newSunDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newSunDeg)))+chartW/2+planetW/2;
					case 1:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newMoonDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newMoonDeg)))+chartW/2+planetW/2;
					case 2:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newMercuryDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newMercuryDeg)))+chartW/2+planetW/2;
					case 3:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newVenusDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newVenusDeg)))+chartW/2+planetW/2;
					case 4:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newMarsDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newMarsDeg)))+chartW/2+planetW/2;
					case 5:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newJupiterDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newJupiterDeg)))+chartW/2+planetW/2;
					case 6:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newSaturnDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newSaturnDeg)))+chartW/2+planetW/2;
					case 7:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newUranusDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newUranusDeg)))+chartW/2+planetW/2;
					case 8:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newNeptuneDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newNeptuneDeg)))+chartW/2+planetW/2;
					case 9:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newPlutoDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newPlutoDeg)))+chartW/2+planetW/2;
					case 10:
						if(xory)
							return chartBackx-(planetW/2) + (int)(radius*0.95*Math.cos(Math.toRadians(newNodeDeg)))+chartW/2+planetW/2;
						return chartBacky-(planetW/2) - (int)(radius*0.95*Math.sin(Math.toRadians(newNodeDeg)))+chartW/2+planetW/2;
				}
				return 0;
			}
			
			//returns which image to use based on the degree
			private BufferedImage getDigitImg(int deg, boolean lftOrRght)
			{
				String temp = "" + deg;
				if(!lftOrRght)
				{
					if(temp.length() == 2)
					{
						switch(Integer.parseInt(temp.substring(1)))
						{
							case 0:
								return zero;
							case 1:
								return one;
							case 2:
								return two;
							case 3:
								return three;
							case 4:
								return four;
							case 5:
								return five;
							case 6:
								return six;
							case 7:
								return seven;
							case 8:
								return eight;
							case 9:
								return nine;
						}
					}
					switch(Integer.parseInt(temp.substring(0,1)))
					{
						case 0:
							return zero;
						case 1:
							return one;
						case 2:
							return two;
						case 3:
							return three;
						case 4:
							return four;
						case 5:
							return five;
						case 6:
							return six;
						case 7:
							return seven;
						case 8:
							return eight;
						case 9:
							return nine;
					}
				}
				else if(lftOrRght && temp.length() == 2)
				{
					switch(Integer.parseInt(temp.substring(0,1)))
					{
						case 0:
							return zero;
						case 1:
							return one;
						case 2:
							return two;
						case 3:
							return three;
						case 4:
							return four;
						case 5:
							return five;
						case 6:
							return six;
						case 7:
							return seven;
						case 8:
							return eight;
						case 9:
							return nine;
					}
				}
				return blank;
			}
		}
		this.add(new Display());
		this.setVisible(true);
	}
	
	//save natal chart
	public void saveChart(){
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		printAll(g);
		try{
			File outputFile = new File("saved.png");
			javax.imageio.ImageIO.write(image, "png", outputFile);
		}
		catch (IOException e){
			JOptionPane.showMessageDialog(ImageFrame.this, "Error saving file", "oops!", JOptionPane.ERROR_MESSAGE );
		}
	}	
}