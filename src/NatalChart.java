import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NatalChart {
	
	private int[][] degrees = new int [2][11];
	private String[] signs = new String[11];
	private ArrayList<int[]> aspects = new ArrayList<int[]>();
	private String rising;

	NatalChart(String date, String time) throws FileNotFoundException {
		
		// reads ephemerides and calculates degrees and signs for each planet based on birthday
		String month = date.substring(0, 2);
		String day = date.substring(2, 4);
		String year = date.substring(4) + ".txt";
		
		if(month.equals("01")){
			month = "January";
		}
		else if(month.equals("02")){
			month = "February";
		}
		else if(month.equals("03")){
			month = "March";
		}
		else if(month.equals("04")){
			month = "April";
		}
		else if(month.equals("05")){
			month = "May";
		}
		else if(month.equals("06")){
			month = "June";
		}
		else if(month.equals("07")){
			month = "July";
		}
		else if(month.equals("08")){
			month = "August";
		}
		else if(month.equals("09")){
			month = "September";
		}
		else if(month.equals("10")){
			month = "October";
		}
		else if(month.equals("11")){
			month = "November";
		}
		else if(month.equals("12")){
			month = "December";
		}
		
		Scanner myScanner = new Scanner(new File("ephemerides/"+year));
		String next = myScanner.next();
		while(!next.equalsIgnoreCase(month)){
			next = myScanner.next();
		}
		while(!next.equals(day)){
			next = myScanner.next();
		}
		next = myScanner.next();
		for(int i = 0; i<11; i++){
			
			next = myScanner.next();
			degrees[0][i] = Integer.parseInt(next.substring(0, 2));
			degrees[1][i] = Integer.parseInt(next.substring(4));
			signs[i] = next.substring(2, 4);
		}
		myScanner.close();
		
		// creates aspects based on the degrees and signs
		String[] planets = {"sun", "moon",  "mercury", "venus", "mars", "jupiter", "saturn", "uranus", "neptune", "pluto", "node"};
		int degDiff;
		for(int i = 0; i < planets.length; i++)
		{
			for(int j = 0; j < planets.length; j++)
			{
				if(i < j)
				{
					degDiff = Math.abs(getDegrees(planets[i], getSign(planets[i]))-getDegrees(planets[j], getSign(planets[j])));
					for(int k = 0; k <= 8; k++)
					{
						if(degDiff == k || degDiff == 360-k)
						{
							int[] aspect = {i, j, 0};
							if(!aspects.contains(aspect))
							{
								aspects.add(aspect);
							}
						}
					}
					for(int k = 54; k <= 66; k++)
					{
						if(degDiff == k || degDiff == 360-k)
						{
							int[] aspect = {i, j, 1};
							if(!aspects.contains(aspect))
							{
								aspects.add(aspect);
							}
						}
					}
					for(int k = 82; k <= 98; k++)
					{
						if(degDiff == k || degDiff == 360-k)
						{
							int[] aspect = {i, j, 2};
							if(!aspects.contains(aspect))
							{
								aspects.add(aspect);
							}
						}
					}
					for(int k = 112; k <= 128; k++)
					{
						if(degDiff == k || degDiff == 360-k)
						{
							int[] aspect = {i, j, 3};
							if(!aspects.contains(aspect))
							{
								aspects.add(aspect);
							}
						}
					}
					for(int k = 172; k <= 188; k++)
					{
						if(degDiff == k || degDiff == 360-k)
						{
							int[] aspect = {i, j, 4};
							if(!aspects.contains(aspect))
							{
								aspects.add(aspect);
							}
						}
					}
				}
			}
		}
		
		// reads rising table to determine rising sign and degree
		String[][][] risingDegs = new String[12][31][26];
		int monthIndex = 0, dayIndex = 0;
		myScanner = new Scanner(new File("ascendant.txt"));
		while(myScanner.hasNext()){
			next = myScanner.next();
			if(next.equalsIgnoreCase("January")){
				monthIndex = 0;
			}
			else if(next.equalsIgnoreCase("February")){
				monthIndex = 1;
			}
			else if(next.equalsIgnoreCase("March")){
				monthIndex = 2;
			}
			else if(next.equalsIgnoreCase("April")){
				monthIndex = 3;
			}
			else if(next.equalsIgnoreCase("May")){
				monthIndex = 4;
			}
			else if(next.equalsIgnoreCase("June")){
				monthIndex = 5;
			}
			else if(next.equalsIgnoreCase("July")){
				monthIndex = 6;
			}
			else if(next.equalsIgnoreCase("August")){
				monthIndex = 7;
			}
			else if(next.equalsIgnoreCase("September")){
				monthIndex = 8;
			}
			else if(next.equalsIgnoreCase("October")){
				monthIndex = 9;
			}
			else if(next.equalsIgnoreCase("November")){
				monthIndex = 10;
			}
			else if(next.equalsIgnoreCase("December")){
				monthIndex = 11;
			}
			else if(next.length() == 2 && !next.equalsIgnoreCase("am") && !next.equalsIgnoreCase("pm"))
			{
				if(next.substring(0,1).equalsIgnoreCase("0"))
				{
					dayIndex = Integer.parseInt(next.substring(1))-1;
				}
				else
				{
					dayIndex = Integer.parseInt(next)-1;
				}
			}
			else if(next.equalsIgnoreCase("am"))
			{
				boolean notLast2 = false, notLast1 = false;
				risingDegs[monthIndex][dayIndex][0] = (monthIndex+1) + "";
				risingDegs[monthIndex][dayIndex][1] = (dayIndex+1) + "";
				if(monthIndex == 0 && dayIndex == 30)
				{
					notLast2 = true;
				}
				else if(monthIndex == 4 && dayIndex == 30)
				{
					notLast2 = true;
				}
				else if(monthIndex == 5 && dayIndex == 29)
				{
					notLast2 = true;
				}
				else if(monthIndex == 6 && dayIndex == 29)
				{
					notLast1 = true;
				}
				else if(monthIndex == 9 && dayIndex == 30)
				{
					notLast2 = true;
				}
				else if(monthIndex == 10 && dayIndex == 29)
				{
					notLast2 = true;
				}
				else if(monthIndex == 11 && dayIndex == 29)
				{
					notLast1 = true;
				}
				if(notLast1)
				{
					risingDegs[monthIndex][dayIndex+1][0] = (monthIndex+1) + "";
					risingDegs[monthIndex][dayIndex+1][1] = (dayIndex+2) + "";
				}
				else if(!notLast2)
				{
					risingDegs[monthIndex][dayIndex+1][0] = (monthIndex+1) + "";
					risingDegs[monthIndex][dayIndex+1][1] = (dayIndex+2) + "";
					risingDegs[monthIndex][dayIndex+2][0] = (monthIndex+1) + "";
					risingDegs[monthIndex][dayIndex+2][1] = (dayIndex+3) + "";
				}
				
				for(int i = 2; i < 26; i++)
				{
					next = myScanner.next();
					risingDegs[monthIndex][dayIndex][i] = next;
					if(notLast1)
					{
						risingDegs[monthIndex][dayIndex+1][i] = next;
					}
					else if(!notLast2)
					{
						risingDegs[monthIndex][dayIndex+1][i] = next;
						risingDegs[monthIndex][dayIndex+2][i] = next;
					}
					if(i == 13)
					{
						next = myScanner.next();
						next = myScanner.next();
					}
				}
			}
		}
		myScanner.close();
		String currRis, nextRis;
		int hourIndex;
		month = date.substring(0, 2);
		if(month.substring(0,1).equalsIgnoreCase("0"))
		{
			monthIndex = Integer.parseInt(month.substring(1))-1;
		}
		else
		{
			monthIndex = Integer.parseInt(month)-1;
		}
		if(day.substring(0,1).equalsIgnoreCase("0"))
		{
			dayIndex = Integer.parseInt(day.substring(1))-1;
		}
		else
		{
			dayIndex = Integer.parseInt(day)-1;
		}
		if(time.substring(0,2).equalsIgnoreCase("00"))
		{
			if(dayIndex == 0)
			{
				if(monthIndex == 0)
				{
					monthIndex = 11;
					dayIndex = 30;
				}
				else
				{
					monthIndex--;
					switch(monthIndex)
					{
						case 0:
							dayIndex = 30;
							break;
						case 1:
							dayIndex = 28;
							break;
						case 2:
							dayIndex = 30;
							break;
						case 3:
							dayIndex = 29;
							break;
						case 4:
							dayIndex = 30;
							break;
						case 5:
							dayIndex = 29;
							break;
						case 6:
							dayIndex = 30;
							break;
						case 7:
							dayIndex = 30;
							break;
						case 8:
							dayIndex = 29;
							break;
						case 9:
							dayIndex = 30;
							break;
						case 10:
							dayIndex = 29;
							break;
					}
				}
			}
			else
			{
				dayIndex--;
			}
			hourIndex = 24;
		}
		else if(time.substring(0,1).equalsIgnoreCase("0"))
		{
			hourIndex = Integer.parseInt(time.substring(1,2));
		}
		else
		{
			hourIndex = Integer.parseInt(time.substring(0,2));
		}
		currRis = risingDegs[monthIndex][dayIndex][hourIndex+1];
		if(hourIndex == 24)
		{
			switch(monthIndex)
			{
				case 0:
					if(dayIndex == 30)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 1:
					if(dayIndex == 28)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 2:
					if(dayIndex == 30)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 3:
					if(dayIndex == 29)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 4:
					if(dayIndex == 30)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 5:
					if(dayIndex == 29)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 6:
					if(dayIndex == 30)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 7:
					if(dayIndex == 30)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 8:
					if(dayIndex == 29)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 9:
					if(dayIndex == 30)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 10:
					if(dayIndex == 29)
					{
						monthIndex++;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
				case 11:
					if(dayIndex == 30)
					{
						monthIndex = 0;
						dayIndex = 0;
					}
					else
					{
						dayIndex++;
					}
					break;
			}
			hourIndex = 1;
		}
		else
		{
			hourIndex++;
		}
		nextRis = risingDegs[monthIndex][dayIndex][hourIndex+1];
		
		//calculate rising
		int min, currRisDeg, nextRisDeg, deg;
		if(time.substring(2,3).equalsIgnoreCase("0"))
		{
			min = Integer.parseInt(time.substring(3));
		}
		else
		{
			min = Integer.parseInt(time.substring(2));
		}
		if(currRis.substring(2,3).equalsIgnoreCase("0"))
		{
			currRisDeg = Integer.parseInt(currRis.substring(3));
		}
		else
		{
			currRisDeg = Integer.parseInt(currRis.substring(2));
		}
		if(nextRis.substring(2,3).equalsIgnoreCase("0"))
		{
			nextRisDeg = Integer.parseInt(nextRis.substring(3));
		}
		else
		{
			nextRisDeg = Integer.parseInt(nextRis.substring(2));
		}
		if(currRis.substring(0,2).equalsIgnoreCase(nextRis.substring(0,2)))
		{
			deg = currRisDeg + Math.abs(currRisDeg - nextRisDeg)*min/60;
			if(deg < 10)
			{
				rising = currRis.substring(0,2) + "0" + deg;
			}
			else
			{
				rising = currRis.substring(0,2) + deg;
			}
		}
		else
		{
			deg = (nextRisDeg + 30 - currRisDeg)*min/60;
			if(currRisDeg + deg > 29)
			{
				deg = deg-(30-currRisDeg);
				if(deg < 10)
				{
					rising = nextRis.substring(0,2) + "0" + deg;
				}
				else
				{
					rising = nextRis.substring(0,2) + deg;
				}
			}
			else
			{
				if(deg < 10)
				{
					rising = currRis.substring(0,2) + "0" + deg;
				}
				else
				{
					rising = currRis.substring(0,2) + deg;
				}
			}
		}
	}
	
	// returns the degree the specific planet is at without regards to the sign
	int getDegrees(String planet){
		if(planet.equalsIgnoreCase("Sun")){
			return degrees[0][0];
		}
		else if(planet.equalsIgnoreCase("Moon")){
			return degrees [0][1];
		}
		else if(planet.equalsIgnoreCase("Mercury")){
			return degrees [0][2];
		}
		else if(planet.equalsIgnoreCase("Venus")){
			return degrees [0][3];
		}
		else if (planet.equalsIgnoreCase("Mars")){
			return degrees [0][4];
		}
		else if (planet.equalsIgnoreCase("Jupiter")){
			return degrees [0][5];
		}
		else if (planet.equalsIgnoreCase("Saturn")){
			return degrees [0][6];
		}
		else if (planet.equalsIgnoreCase("Uranus")){
			return degrees [0][7];
		}
		else if (planet.equalsIgnoreCase("Neptune")){
			return degrees [0][8];
		}
		else if (planet.equalsIgnoreCase("Pluto")){
			return degrees [0][9];
		}
		else if (planet.equalsIgnoreCase("Node")){
			return degrees [0][10];
		}
		return -1;
	}
	
	// returns the degree the specific planet is with regards to the sign
	int getDegrees(String planet, String sign){
		if(planet.equalsIgnoreCase("Sun")){
			return degrees[0][0] + signConvert(sign);
		}
		else if(planet.equalsIgnoreCase("Moon")){
			return degrees [0][1] + signConvert(sign);
		}
		else if(planet.equalsIgnoreCase("Mercury")){
			return degrees [0][2] + signConvert(sign);
		}
		else if(planet.equalsIgnoreCase("Venus")){
			return degrees [0][3] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Mars")){
			return degrees [0][4] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Jupiter")){
			return degrees [0][5] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Saturn")){
			return degrees [0][6] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Uranus")){
			return degrees [0][7] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Neptune")){
			return degrees [0][8] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Pluto")){
			return degrees [0][9] + signConvert(sign);
		}
		else if (planet.equalsIgnoreCase("Node")){
			return degrees [0][10] + signConvert(sign);
		}
		return -1;
	}
	
	// returns the sign the specified planet is in
	String getSign(String planet){
		if(planet.equalsIgnoreCase("Sun")){
			return signs[0];
		}
		else if(planet.equalsIgnoreCase("Moon")){
			return signs[1];
		}
		else if(planet.equalsIgnoreCase("Mercury")){
			return signs[2];
		}
		else if(planet.equalsIgnoreCase("Venus")){
			return signs[3];
		}
		else if (planet.equalsIgnoreCase("Mars")){
			return signs[4];
		}
		else if (planet.equalsIgnoreCase("Jupiter")){
			return signs[5];
		}
		else if (planet.equalsIgnoreCase("Saturn")){
			return signs[6];
		}
		else if (planet.equalsIgnoreCase("Uranus")){
			return signs[7];
		}
		else if (planet.equalsIgnoreCase("Neptune")){
			return signs[8];
		}
		else if (planet.equalsIgnoreCase("Pluto")){
			return signs[9];
		}
		else if (planet.equalsIgnoreCase("Node")){
			return signs[10];
		}
		return null;
	}
	
	// returns the beginning degree of the specified sign in regards to the zodiac
	private int signConvert(String sign){
		if(sign.equalsIgnoreCase("ar"))
			return 180;
		else if(sign.equalsIgnoreCase("ta"))
			return 210;
		else if(sign.equalsIgnoreCase("ge"))
			return 240;
		else if(sign.equalsIgnoreCase("cn"))
			return 270;
		else if(sign.equalsIgnoreCase("le"))
			return 300;
		else if(sign.equalsIgnoreCase("vi"))
			return 330;
		else if(sign.equalsIgnoreCase("li"))
			return 0;
		else if(sign.equalsIgnoreCase("sc"))
			return 30;
		else if(sign.equalsIgnoreCase("sg"))
			return 60;
		else if(sign.equalsIgnoreCase("cp"))
			return 90;
		else if(sign.equalsIgnoreCase("aq"))
			return 120;
		else if(sign.equalsIgnoreCase("pi"))
			return 150;
		return 0;
	}
	
	// returns the list of aspects
	ArrayList<int[]> getAspects(){return aspects;}
	
	int getRisingDeg()
	{
		if(rising.substring(2,3).equalsIgnoreCase("0"))
		{
			return Integer.parseInt(rising.substring(3));
		}
		return Integer.parseInt(rising.substring(2));
	}
	
	int getRisingDeg(String sign){return getRisingDeg() + signConvert(getRisingSign());}
	
	String getRisingSign(){return rising.substring(0,2);}
}
