package projectaria.niri.time;

public class Stopwatch {
  private long value, lastMeasured;
  private boolean isRunning;
  private double rate;

  public Stopwatch(double rate){
    this.rate = rate <= 0 ? 1 : rate;
    this.value = 0;
    this.lastMeasured = 0;
    this.isRunning = false;
  }
  public Stopwatch(){
    this.rate = 1;
    this.value = 0;
    this.lastMeasured = 0;
    this.isRunning = false;
  }

  public final boolean isRunning(){
    return this.isRunning;
  }
  public final boolean isPaused(){
    return !this.isRunning && this.value != 0;
  }
  public final boolean isReset(){
    return !this.isRunning && this.value == 0;
  }

  public final TimeMeasurement getTime(){
    this.update();
    return new TimeMeasurement(this.value);
  }
  public final double getRate(){
    return this.rate;
  }
  public final Stopwatch resume(){
    this.update();
    this.isRunning = true;
    this.lastMeasured = System.currentTimeMillis();
    return this;
  }
  public final Stopwatch pause(){
    this.update();
    this.isRunning = false;
    return this;
  }
  public final Stopwatch stop(){
    this.isRunning = false;
    this.value = 0;
    return this;
  }
  public final Stopwatch update(){
    if(!this.isRunning()) return this;
    long newMeasurement = System.currentTimeMillis();
    this.value += (newMeasurement - this.lastMeasured) * this.rate;
    this.lastMeasured = newMeasurement;
    return this;
  }
  public final Stopwatch setTime(long value){
    return this.setTime(new TimeMeasurement(value));
  }
  public final Stopwatch setTime(TimeMeasurement atm){
    if(atm == null) return this;
    this.update();
    this.value = atm.getTotalMiliseconds();
    return this;
  }
  public final Stopwatch setTimeFlow(float rate){
    this.update();
    this.rate = rate <= 0 ? 1 : rate;
    return this;
  }
}
