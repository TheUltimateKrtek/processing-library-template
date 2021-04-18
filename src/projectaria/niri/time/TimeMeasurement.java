package projectaria.niri.time;

public class TimeMeasurement {
  private long value;

  public TimeMeasurement(long value){
    this.setValue(value);
  }
  public TimeMeasurement(){
    this.setValue(0);
  }

  static public final TimeMeasurement now(){
    return new TimeMeasurement(System.currentTimeMillis());
  }

  public final TimeMeasurement setValue(long value){
    this.value = value < 0 ? 0 : value;
    return this;
  }

  public final long getTotalMiliseconds(){
    return this.value;
  }
  public final long getMiliseconds(){
    return this.value % 1000;
  }
  public final long getTotalSeconds(){
    return this.value / 1000;
  }
  public final long getSeconds(){
    return this.value / 1000 % 60;
  }
  public final long getTotalMinutes(){
    return this.value / 60000;
  }
  public final long getMinutes(){
    return this.value / 60000 % 60;
  }
  public final long getTotalHours(){
    return this.value / 3600000;
  }
  public final long getHours(){
    return this.value / 3600000 % 24;
  }
  public final long getDays(){
    return this.value / 86400000;
  }
  public final String getFormatedTime(String format){
    //{days}:{hoursfull}:{hours}:{minutesfull}:{minutes}:{secondsfull}:{seconds}:{milisecondsfull}:{miliseconds}

    long ms = this.getMiliseconds();
    long s = this.getSeconds();
    long m = this.getMinutes();
    long h = this.getHours();
    long d = this.getDays();

    String returnString = "";
    for(int i = 0; i < format.length(); i ++){
      try{
        if(format.charAt(i) == '{'){
          int endIndex = i;
          for(int j = i + 1; j < format.length(); j ++){
            if(format.charAt(j) == '}'){
              endIndex = j;
              break;
            }
          }
          String arg = format.substring(i + 1, endIndex).toLowerCase();
          if(arg.equals("days")){
            returnString += d;
          }
          else if(arg.equals("hoursfull")){
            returnString += h < 10 ? "0" + h : h;
          }
          else if(arg.equals("hours")){
            returnString += h;
          }
          else if(arg.equals("minutesfull")){
            returnString += m < 10 ? "0" + m : m;
          }
          else if(arg.equals("minutes")){
            returnString += m;
          }
          else if(arg.equals("secondsfull")){
            returnString += s < 10 ? "0" + s : s;
          }
          else if(arg.equals("seconds")){
            returnString += s;
          }
          else if(arg.equals("milisecondsfull")){
            if(ms < 100) returnString += "0";
            if(ms < 10) returnString += "0";
            returnString += ms;
          }
          else if(arg.equals("miliseconds")){
            returnString += ms;
          }
          i = endIndex;
        }
        else{
          returnString += format.charAt(i);
        }
      }
      catch(IndexOutOfBoundsException e){
        returnString += format.substring(i, format.length());
        System.out.println("Error at " + i);
        break;
      }
    }
    return returnString;
  }

  public final long getTimeDifference(long value){
    return TimeMeasurement.getTimeDifference(value, this);
  }
  public final long getTimeDifference(TimeMeasurement m){
    return TimeMeasurement.getTimeDifference(m, this);
  }
  public final static long getTimeDifference(long value1, long value2){
    return value1 > value2 ? value1 - value2 : value2 - value1;
  }
  public final static long getTimeDifference(long value, TimeMeasurement m){
    if(m == null) m = new TimeMeasurement();
    return TimeMeasurement.getTimeDifference(m, value);
  }
  public final static long getTimeDifference(TimeMeasurement m, long value){
    if(m == null) m = new TimeMeasurement();
    return TimeMeasurement.getTimeDifference(m, value);
  }
  public final static long getTimeDifference(TimeMeasurement m1, TimeMeasurement m2){
    if(m1 == null) m1 = new TimeMeasurement();
    if(m2 == null) m2 = new TimeMeasurement();
    return TimeMeasurement.getTimeDifference(m1.getTotalMiliseconds(), m2.getTotalMiliseconds());
  }

  public final TimeMeasurement copy(){
    return new TimeMeasurement(this.value);
  }
  public final String toString(){
    return this.getFormatedTime("{days}:{hoursfull}:{minutesfull}:{secondsfull}.{milisecondsfull} (" + this.getTotalMiliseconds() + " ms)");
  }
}
