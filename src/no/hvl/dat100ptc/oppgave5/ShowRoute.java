package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		
		return ystep;
		
	}

	public void showRouteMap(int ybase) {

		int[] xTab = new int[gpspoints.length];
		int[] yTab = new int[gpspoints.length];
		int minX = (int)( GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints))* xstep());
		int maxY = (int)( GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints))* ystep());
		setColor(0,250,0);
		
		for(int i = 0; i < gpspoints.length; i++) {
			double bredde = gpspoints[i].getLatitude();
			double lengde = gpspoints[i].getLongitude();
			xTab[i] = (int)(lengde * xstep());
			yTab[i] = (int)(bredde * ystep());
			fillCircle(xTab[i] - minX  + MARGIN, maxY - yTab[i] + MARGIN, 4);	
			
		}
		for(int i = 1; i < gpspoints.length; i++) {
			drawLine(xTab[i-1] - minX + MARGIN, maxY - yTab[i-1] + MARGIN, xTab[i] - minX + MARGIN, maxY - yTab[i] + MARGIN );
		}
	
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;
		String tid = "Total Time:      " + GPSUtils.formatTime(gpscomputer.totalTime());
		String dis = "Total Distance:  " + GPSUtils.formatDouble(gpscomputer.totalDistance()) + " m";
		String ele = "Total Elevation: " + GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m";
		String max = "Max Speed:       " + GPSUtils.formatDouble(gpscomputer.maxSpeed()) + " km/h";
		String arg = "Average Speed:   " + GPSUtils.formatDouble(gpscomputer.averageSpeed()) + " km/h";
		String eng = "Energy:          " + GPSUtils.formatDouble(gpscomputer.totalKcal(80.0)) + " kcal";
		
		setColor(0,0,0);
		setFont("Courier",12);
		drawString(tid, TEXTDISTANCE, TEXTDISTANCE);
		drawString(dis, TEXTDISTANCE, TEXTDISTANCE*2);
		drawString(ele, TEXTDISTANCE, TEXTDISTANCE*3);
		drawString(max, TEXTDISTANCE, TEXTDISTANCE*4);
		drawString(arg, TEXTDISTANCE, TEXTDISTANCE*5);
		drawString(eng, TEXTDISTANCE, TEXTDISTANCE*6);
		
		
	}

}
