package PP;
import robocode.*;
import robocode.util.Utils;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

public class Robozinho extends AdvancedRobot
{
	/**
	 * run: Robozinho's default behavior
	 */
	public void atira(ScannedRobotEvent e) {
		double forca = decideForca(e);
		setFire(forca);
	}
	
	public double decideForca(ScannedRobotEvent e) {
		double forca; 
		if(getOthers() == 1) {
			forca = 2.0;
		}
		else {
			forca = 3.0;
		}
		//se a distancia for grande reduz a forca
		if (e.getDistance() > 400) {
			forca = 1.0;
		}
		// cc aumenta a forca
		else if (e.getDistance() < 200) {
			forca = 3.0;
		}
		
		//se a energia tiver baixa reduz a forca
		if (getEnergy() < 10) {
			forca = 1.0;
		}
		else if (getEnergy() < 1) {
			forca = 0.1;
		}
		
		return Math.min(e.getEnergy()/4, forca);
	}
	public void run() {
	
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// radar rotaciona infinitamente quando nao ha movimento agendado
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
	
		double angleToEnemy = getHeadingRadians() + e.getBearingRadians();
		double turnToEnemy = Utils.normalRelativeAngle(angleToEnemy - getRadarHeadingRadians());
	
		double extraTurn = Math.atan(36.0/ e.getDistance())*(turnToEnemy >= 0 ? 1 : -1);
		setTurnRadarRightRadians (turnToEnemy + extraTurn);

		// fica perpendicular ao inimigo
		setTurnRadarRightRadians(turnToEnemy + extraTurn); //radar vira
		setTurnRight(e.getBearing() + 90); // corpo do robo vira
		
		// atira
		double forca = decideForca(e);
		fire(forca);

	
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
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
