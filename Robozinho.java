package RR;
import robocode.*;
import robocode.util.*;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/JuniorRobot.html

/**
 * Robo3 - a robot by (your name here)
 */
public class Robo3 extends AdvancedRobot
{
	/**
	 * run: Robo3's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// Some color codes: blue, yellow, black, white, red, pink, brown, grey, orange...
		// Sets these colors (robot parts): body, gun, radar, bullet, scan_arc
		//setColors(orange, blue, white, yellow, black);

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - 
    		getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - 
    		absoluteBearing) / 13.0)));
		double distance = e.getDistance(); 
    	if(distance > 800) 
        	setFire(5);
    	else if(distance > 600 && distance <= 800)
        	setFire(4);
    	else if(distance > 400 && distance <= 600)
        	setFire(3);
   		else if(distance > 200 && distance <= 400)
        	setFire(2);
    	else if(distance < 200)
        	setFire(1);
		if(getEnergy() < 1) 
			setFire(0.1);
		else if(getEnergy() < 10) 
			setFire(1.0);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet() {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall() {
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
