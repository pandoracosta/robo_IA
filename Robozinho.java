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
		
		// e.getVelocity() * Math.sin(e.getHeadingRadians() - absoluteBearing) é a velocidade lateral do robo
		// note que é uma aproximaçao ja que asin(velocidade lateral/bulletSpeed) é mais ou menos igual a velocidade lateral/bulletSpeed
		// assuma que o inimigo vai continuar paralelo a velocidade lateral
		// é escolhido 13.0 para a bulletSpeed por conta da distorçao de tirar o asin
		
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - 
    		getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - 
    		absoluteBearing) / 13.0)));
		double distance = e.getDistance(); 
		
		// heuristicas de tiro a depender da distancia
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
			
		// energia baixa, diminui a forca da bala
		if(getEnergy() < 1) 
			setFire(0.1);
		else if(getEnergy() < 10) 
			setFire(1.0);
			
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		double bearing = e.getBearing(); 
    	if(getEnergy() < 100){ // energia baixa, se afasta do inimigo
        	turnRight(-bearing); 
        	ahead(100); 
    	}
   		else{
        	turnRight(360); // scan
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
	/* definir alvo ao colidir
	 * public void onHitRobot(HitRobotEvent e) {
		// robo na frente, anda pra tras
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // robo atras, chega pra frente
		else {
			ahead(100);
		}
		gunTurnAmt = normalRelativeAngle(e.getBearing() + (getHeading() - getRadarHeading()));
		setTurnGunRight(gunTurnAmt);
		setFire(1);
		setBack(50);
		
	}
	*/
	 public void onHitWall(HitWallEvent e) {
	        double bearing = e.getBearing(); // pega o bearing 
	    	turnRight(-bearing); // afasta da parede (vira o corpo)
	    	ahead(100); //anda 
	    }   
	
}
