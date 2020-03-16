package PP;
import robocode.*;
import robocode.util.Utils;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html
// Possível tática de batalha para o Robo, Walls, baseado em https://github.com/robo-code/robocode/blob/master/robocode.samples/src/main/java/sample/Walls.java
// Move-se pela borda externa com a arma voltada para dentro. 

public class Robozinho extends AdvancedRobot
{
	/**
	 * run: Robozinho's default behavior
	 */
	//Walls atributos
	//boolean peek; // Não vire se houver um robô lá
	//double moveAmount; // Quanto mover 

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
	//run: Mova-se pelas paredes
	//Obs: precisamos otimizar a movimentação do radar e do canhão para o centro do campo de batalha, 
	/*public void run() {
		// definindo Cores
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.orange);
		setBulletColor(Color.cyan);
		setScanColor(Color.cyan);

		Inicialize moveAmount ao máximo possível para este campo de batalha. 
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// Inicializa peek para false
		peek = false;

		turnLeft para enfrentar uma parede.
		// getHeading ()% 90 significa o restante de 
		// getHeading () dividido por 90. 
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		// Gire a pistola para virar à direita 90 graus. 
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
			// Olhe antes de virar quando ahead() for concluída. 
			peek = true;
			// Mova a parede 
			ahead(moveAmount);
			// Não olhe agora
			peek = false;
			// Gire para a próxima parede 
			turnRight(90);
		}
	}
	 */

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
	
	/*
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
		// Observe que a verificação é chamada automaticamente quando o robô está em movimento.
		// Ao chamá-lo manualmente aqui, garantimos que geramos outro evento de verificação se houver um robô na 
		// próxima parede, para que não comecemos a movê-lo até que ele acabe.
		if (peek) {
			scan();
		}
	}
	
	*/
	
	
	/*onHitRobot: Afaste-se um pouco
	 public void onHitRobot(HitRobotEvent e) {
		// Se ele estiver na nossa frente, configure-o um pouco. 
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // senão ele está atrás de nós, então avance um pouco. 
		else {
			ahead(100);
		}
	}
	*/

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
	/* Desnecessário quando o robo se baseia nas paredes para se movimentar*/
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
		turnRadarRight(evnt.getBearing());
		turnGunRight(evnt.getBearing());
		turnRight(evnt.getBearing());

	}	
}
