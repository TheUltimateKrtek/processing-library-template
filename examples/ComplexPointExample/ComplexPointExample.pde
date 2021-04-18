import projectaria.niri.*;

void setup(){
  ComplexPoint a = new ComplexPoint(1, 2);
  ComplexPoint b = new ComplexPoint(3, 4);
  
  //Static addition
  ComplexPoint addition = ComplexPoint.add(a, b);
  println("(" + a.x + "+" + a.y + "i) + (" +  + a.x + "+" + a.y + "i) = " + addition.x + "+" + addition.y + "i");
  
  //Static subtraction
  ComplexPoint subtraction = ComplexPoint.subtract(a, b);
  println("(" + a.x + "+" + a.y + "i) - (" +  + a.x + "+" + a.y + "i) = " + subtraction.x + "+" + subtraction.y + "i");
  
  //Static multiplication
  ComplexPoint multiplication = ComplexPoint.multiply(a, b);
  println("(" + a.x + "+" + a.y + "i) * (" +  + a.x + "+" + a.y + "i) = " + multiplication.x + "+" + multiplication.y + "i");
  
  //Static division
  ComplexPoint division = ComplexPoint.divide(a, b);
  println("(" + a.x + "+" + a.y + "i) / (" +  + a.x + "+" + a.y + "i) = " + division.x + "+" + division.y + "i");
  
  //Static simple multiplication / scaling
  //If a=xa+ba*i and b=xb+yb*i, then result=(xa+xb)+(ya+yb)*i
  ComplexPoint scale = ComplexPoint.scale(a, b);
  println(a.x + "*" + b.x + " + " + a.y + "*" + b.y + " = " + scale.x + "+" + scale.y + "i");
  
  //Static power
  ComplexPoint power = ComplexPoint.pow(a, b);
  println("(" + a.x + "+" + a.y + "i) ^ (" +  + a.x + "+" + a.y + "i) = " + division.x + "+" + division.y + "i");
}
