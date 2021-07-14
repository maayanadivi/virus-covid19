//alice aidlin 206448326
//maayan nadivi 208207068
package Simulation;

public class Clock {
//----------attr----------
	private static long time=1;
	private static int ticks_per_day=1;
	
//----------methods----------	
	public static long now() {
		return time;
	}
	
	public static int get_ticks_per_day() {
		return ticks_per_day;
	}
	
	public static void set_ticks_per_day(int t) {
		ticks_per_day=t;
	}
	
	public static void nextTick() {
		time= time+1;
	}
	public long calculate_days(long start) {
		long sum= time-start;
		sum=sum/ticks_per_day;
		return sum+1;
	}
}

