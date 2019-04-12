package Core;

/**This class is to scale & move pupil with mouse movement
 *
 * @author Bharat Goel
 */
public class Scale {

    private double x;
    private double y;

    public Scale(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public Scale add(Scale s) {
        return new Scale(x + s.x, y + s.y);
    }

    public Scale sub(Scale s) {
        return new Scale(x - s.x, y - s.y);
    }

    public void multiply(double d) {
        x *= d;
        y *= d;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }
    
    public void normalize() {
        double size = getLength();
        multiply(1 / size);
    }
    
}
