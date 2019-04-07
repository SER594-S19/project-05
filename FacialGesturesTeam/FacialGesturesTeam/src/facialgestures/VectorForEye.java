package facialgestures;

public class VectorForEye {

   private double x;
   private double y;

   public VectorForEye(double x, double y) {
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
   
   public VectorForEye addNew(VectorForEye vector) {
       return new VectorForEye(x + vector.x, y + vector.y);
   }

   public VectorForEye substract(VectorForEye vector) {
       return new VectorForEye(x - vector.x, y - vector.y);
   }

   public void multiply(double val) {
       x = x * val;
       y = y * val;
   }

   public double getLength() {
       return Math.sqrt(x * x + y * y);
   }
   
   public void normalize() {
       double length = getLength();
       multiply(1 / length);
   }
   
}