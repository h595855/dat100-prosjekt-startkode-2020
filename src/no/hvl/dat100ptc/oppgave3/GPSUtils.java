package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] breddegrad = new double [gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			
			breddegrad[i] = gpspoints[i].getLatitude();
			
		}
		return breddegrad;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] lengdegrad = new double [gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			
			lengdegrad [i] = gpspoints[i].getLongitude();
		
		}
		
		return lengdegrad;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d, a, b;
		double latitude1, longitude1, latitude2, longitude2;
		double O, Æ, Y, X;

		// TODO - START
		latitude1 = gpspoint1.getLatitude();
		latitude2 = gpspoint2.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		longitude2= gpspoint2.getLongitude();
		
		O = toRadians(latitude1);
		Æ = toRadians(latitude2);
		
		Y = toRadians(latitude2-latitude1);
		X = toRadians(longitude2-longitude1);
		
		a = pow(sin(Y/2),2) + cos(O) * cos(Æ) * pow(sin(X/2),2);
		b = 2 * atan2(sqrt(a),sqrt((1-a)));
		d = R * b;
		
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		double avstand = distance(gpspoint1, gpspoint2);
		
		secs = gpspoint2.getTime();
		secs -= gpspoint1.getTime();
		
		speed = (avstand/secs) * 3.6;
		
		return speed;
	

	}

	public static String formatTime(int secs) {
		
		String timestr;
		String TIMESEP = ":";
		
		int hh = secs / 3600;
		int mm = secs / 60 % 60;
		int ss = secs % 3600 % 60;
		
		String strhh = String.format("%02d", hh);
		String strmm = String.format("%02d", mm);
		String strss = String.format("%02d", ss);
	
		timestr = String.format("  " + strhh + TIMESEP +  strmm  + TIMESEP + strss);
		
		return timestr;
		
		
		
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		
		String str = String.format("%"+TEXTWIDTH+".2f", d);
		
		return str;
		
		
	}
}
