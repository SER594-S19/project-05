package Core;

import java.awt.Color;
import java.awt.Graphics;

/**This class is to create an eye with a pupil in it to show eye movement
 *
 * @author Bharat Goel
 */
public class Eye {

    private Scale position;
    private double r1;
    private double r2;
    

	public void setR2(double r2) {
		this.r2 = r2;
	}

	public Eye(double x, double y, double r1, double r2) {
        position = new Scale(x, y);
        this.r1 = r1;
        this.r2 = r2;
    }

    public void draw(Graphics g, Scale mousePosition) {
        Scale pupilPosition = mousePosition.sub(position);
        double pupilDistanceFromCenter = Math.min(r1 - r2, pupilPosition.getLength());
        pupilPosition.normalize();
        pupilPosition.multiply(pupilDistanceFromCenter);
        pupilPosition = pupilPosition.add(position);
        
        g.setColor(Color.BLACK);
        g.fillOval((int) (position.getX() - r1 - 8), (int) (position.getY() - r1 - 8), (int) (2 * r1 + 16), (int) (2 * r1 + 16));
        
        g.setColor(Color.WHITE);
        g.fillOval((int) (position.getX() - r1), (int) (position.getY() - r1), (int) (2 * r1), (int) (2 * r1));

        g.setColor(Color.BLACK);
        g.fillOval((int) (pupilPosition.getX() - r2), (int) (pupilPosition.getY() - r2), (int) (2 * r2), (int) (2 * r2));
    }

    
}
