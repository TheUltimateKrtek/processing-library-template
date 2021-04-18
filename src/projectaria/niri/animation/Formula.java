package projectaria.niri.animation;

public class Formula{
  private boolean constrain;
  private double tension;
  
  Formula(double tension, boolean constrain){
    this.setTension(tension);
    this.constrain = constrain;
  }
  final double getValue(double time){
    if(time < 0) return 0;
    if(time > 1) return 1;
    
    if(tension == 0) return 0;
    if(tension < 0) tension = - tension;
    if(tension < 1) tension = 1 / tension;
    
    double value = this.getFormulaValue(time, tension);
    if(this.constrain) return value < 0 ? 0 : (value > 1 ? 1 : value);
    return value;
  }
  protected double getFormulaValue(double time, double tension){
    return time;
  }
  final double getTension(){
    return this.tension;
  }
  final Formula setTension(double tension){
    if(tension == 0) tension = 1;
    if(tension < 0) tension = Math.abs(tension);
    if(tension < 1) tension = 1 / tension;
    this.tension = tension;
    return this;
  }
  Formula copy(){
    return new Formula(this.getTension(), this.constrain());
  }
  final protected boolean constrain(){
    return this.constrain;
  }

  final static class HoldEasingFormula extends Formula{
    HoldEasingFormula(double tension, boolean constrain){
      super(tension, constrain);
    }
    HoldEasingFormula(boolean constrain){
      super(1, constrain);
    }
    Formula copy(){
      return new HoldEasingFormula(this.getTension(), this.constrain());
    }
    protected double getFormulaValue(double time, double tension){
      return 0;
    }
  }
  final static class LinearEasingFormula extends Formula{
    LinearEasingFormula(double tension, boolean constrain){
      super(tension, constrain);
    }
    LinearEasingFormula(boolean constrain){
      super(1, constrain);
    }
    Formula copy(){
      return new LinearEasingFormula(this.getTension(), this.constrain());
    }
    protected double getFormulaValue(double time, double tension){
      return time;
    }
  }
  final static class ExponentialEasingFormula extends Formula{
    ExponentialEasingFormula(double tension, boolean constrain){
      super(tension, constrain);
    }
    ExponentialEasingFormula(boolean constrain){
      super(1, constrain);
    }
    Formula copy(){
      return new ExponentialEasingFormula(this.getTension(), this.constrain());
    }
    protected double getFormulaValue(double time, double tension){
      if(tension == 1) return time;
      return (Math.pow(tension, time) - 1) / (tension - 1);
    }
  }
  final static class PolynomialEasingFormula extends Formula{
    PolynomialEasingFormula(double tension, boolean constrain){
      super(tension, constrain);
    }
    PolynomialEasingFormula(boolean constrain){
      super(1, constrain);
    }
    Formula copy(){
      return new PolynomialEasingFormula(this.getTension(), this.constrain());
    }
    protected double getFormulaValue(double time, double tension){
      return Math.pow(time, tension);
    }
  }
  final static class SineEasingFormula extends Formula{
    SineEasingFormula(double tension, boolean constrain){
      super(tension, constrain);
    }
    SineEasingFormula(boolean constrain){
      super(1, constrain);
    }
    Formula copy(){
      return new SineEasingFormula(this.getTension(), this.constrain());
    }
    protected double getFormulaValue(double time, double tension){
      return Math.pow(Math.sin(Math.PI / 2 * Math.pow(time, tension)), tension);
    }
  }
  final static class CircularEasingFormula extends Formula{
    CircularEasingFormula(double tension, boolean constrain){
      super(tension, constrain);
    }
    CircularEasingFormula(boolean constrain){
      super(1, constrain);
    }
    Formula copy(){
      return new CircularEasingFormula(this.getTension(), this.constrain());
    }
    protected double getFormulaValue(double time, double tension){
      return 1 - Math.pow(1 - Math.pow(time, tension), 1 / tension);
    }
  }
}