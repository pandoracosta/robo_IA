package PAN;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Robo2 - a robot by (your name here)
 */
public class Robo2 extends Robot{


	public void run() {
       
		
        // Robot main loop
        while(true) {
			ahead(100); //anda 100 pixels
    		turnGunRight(360); //scan
    		back(75); //volta 75 pixels
    		turnGunRight(360); //scan            			
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        // Replace the next line with any behavior you would like
		
 		double distance = e.getDistance();
       
		if(distance > 400) { //se tiver longe diminui a forca
			fire(1.0);
		}
		else if (distance < 200) { //se tiver perto aumenta
			fire(3.5);
		}
		if(getEnergy() < 1) { //energia MUITO baixa, diminui muito a força
			fire(0.1);
		}
		else if (getEnergy() < 10) { //energia baixa, diminui a forca
			fire(1.0);
		}
    }
	public void onHitRobot(HitRobotEvent e) {
		// robo na frente, anda pra tras
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // robo atras, chega pra frente
		else {
			ahead(100);
		}
	}
    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        back(10);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        double bearing = e.getBearing(); // pega o bearing da parede
    	turnRight(-bearing); // afasta da parede
    	ahead(100); //anda 
    }   
}
