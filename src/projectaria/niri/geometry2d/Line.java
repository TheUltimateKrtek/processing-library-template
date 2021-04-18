package projectaria.niri.geometry2d;

import projectaria.niri.math.ComplexPoint;

public class Line {
  private ComplexPoint origin, vector;

  Line(double ox, double oy, double vx, double vy){
    this.origin = new ComplexPoint(ox, oy);
    this.vector = new ComplexPoint(vx, vy);
  }

  public final Line copy(){
    return new Line(this.origin.x, this.origin.y, this.vector.x, this.vector.y);
  }
  public final ComplexPoint getOrigin(){
    return this.origin;
  }
  public final ComplexPoint getVector(){
    return this.vector;
  }
  public final ComplexPoint getEndPoint(){
    return this.origin.copy().add(this.vector);
  }
  
  public final double getAngle(){
    return this.vector.getAngle();
  }
  public final double getDistanceFrom(double x, double y){
    return Math.abs(new ComplexPoint(x, y).subtract(this.origin).rotate(- this.vector.getAngle()).y);
  }
  public final double getDistanceFrom(ComplexPoint p){
    if(p == null) p = new ComplexPoint();
    return this.getDistanceFrom(p.x, p.y);
  }
  
  public final Line translate(double x, double y){
    this.origin.add(x, y);
    return this;
  }
  public final Line translate(ComplexPoint p){
    if(p == null) return this;
    return this.translate(p.x, p.y);
  }
  public final Line rotate(double x, double y, double a){
    this.vector.rotate(a);
    return this;
  }
  public final Line rotate(ComplexPoint p, double a){
    if(p == null) return this;
    return this.translate(p.x, p.y);
  }

  public final boolean isOnLine(double x, double y, double error){
    return this.getDistanceFrom(x, y) < Math.abs(error);
  }
  public final boolean isOnLine(ComplexPoint p, double error){
    if(p == null) p = new ComplexPoint();
    return this.isOnLine(p.x, p.y, error);
  }
  
  public final boolean hasZeroVector(){
    return this.vector.x == 0 && this.vector.y == 0;
  }
  public final ComplexPoint getIntersectionWithXAxis(){
    if(this.vector.y == 0) return null;
    return new ComplexPoint(this.origin.x - this.origin.y / this.vector.y * this.vector.x, 0);
  }
  public final ComplexPoint getIntersectionWithYAxis(){
    if(this.vector.x == 0) return null;
    return new ComplexPoint(0, this.origin.y - this.origin.x / this.vector.x * this.vector.y);
  }
  public final ComplexPoint getIntersection(Line l){
    if(l == null) return null;
    if(l == this) return this.origin.copy();
    if(l.hasZeroVector() || this.hasZeroVector()) return null;
    return l.copy().translate(this.origin).rotate(0, 0, this.vector.getAngle()).getIntersectionWithXAxis().rotateAround(this.origin, this.vector.getAngle()).add(this.origin);
  }

  /*public final boolean equals(Line line){
    
  }*/

  public final boolean isParallel(Line line, double error){
    if(line == null) return false;
    if(line == this) return true;
    if(line.hasZeroVector() || this.hasZeroVector()) return false;
    double angleDifference = (this.getVector().getAngle() - this.getVector().getAngle() + Math.PI * 2) % Math.PI;
    return angleDifference < error || angleDifference > Math.PI - error;
  }
  

  //TODO: Project point onto line
  //TODO: Reflect point over a line
  //TODO: Equals [Line]
  //TODO: Get intersection [Ray]
  //TODO: Get intersection [LineSegment]
  //TODO: Convert to Ray
  //TODO: Convert to LineSegment
  
}
