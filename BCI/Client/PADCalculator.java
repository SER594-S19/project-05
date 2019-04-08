package BCI.Client;

import java.util.concurrent.ThreadLocalRandom;

public class PADCalculator {
	
	public static double pleasure=1;
	public static double arousal = 0.5;
	public static int heartRate = 80;
	public static double laugh =0;
	public static double raiseBrow =0;
	public static double furrowBrow =0;
	public static double smile = 0;
	public static double clench=0;
	public static double mediatation=0.5;
	public static double frustration =0.5;
	public static double stexcitement =0.5;
	public static double ltexcitement =0.5;
	
	public static void calculatePA() {
		pleasure=0;
		arousal=0;
//		if(heartRate >=60 && heartRate<=100) {
//			pleasure +=0.1;	
//		}
//		if(heartRate >100 && heartRate<=140) {
//			arousal +=0.1;
//			pleasure -=0.05;
//			
//		}
//		if(heartRate >140 && heartRate<=190) {
//			arousal +=0.3;
//		}
//		if(laugh > 0.5) {
//			pleasure +=0.2;
//			arousal += 0.05;
//		}
//		if(raiseBrow ==1) {
//			arousal += 0.2;
//		}
//		if(furrowBrow == 1) {
//			pleasure -= 0.25;
//		}
//		if(excitement > 0.5) {
//			pleasure+=0.1;
//			arousal -=0.05;
//		}
//		if(smile > 0.5) {
//			pleasure+=0.1;
//			arousal -=0.1;
//		}
//		if(clench > 0.5) {
//			arousal +=0.1;
//			pleasure-=0.1;
//		}
		if(stexcitement>0.5 && ltexcitement>0.5) {
			pleasure=ThreadLocalRandom.current().nextDouble(0.5, 1);
			arousal=ThreadLocalRandom.current().nextDouble(0.1, 0.5);
		}
		 if(mediatation>0.5) {
			pleasure=ThreadLocalRandom.current().nextDouble(0.5, 1);
			arousal=ThreadLocalRandom.current().nextDouble(0.5, 1);
		}
		 if(frustration>0.5) {
			pleasure=ThreadLocalRandom.current().nextDouble(0.1, 0.3);
			arousal=ThreadLocalRandom.current().nextDouble(0.5, 1);
		}
		pleasure=pleasure<0?0.1:pleasure;
		pleasure=pleasure>1?0.9:pleasure;
		arousal=arousal<0?0.1:arousal;
		arousal=arousal>1?0.9:arousal;
	}
}
