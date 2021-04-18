package projectaria.niri.animation;

public class Ease {
  Ease(){

  }
  double getTime(double time){return time;}
  double getValue(double time, double value){return value;}

  final static class InEase extends Ease{
    InEase(){
      super();
    }
    double getTime(double time){
      return time;
    }
    double getValue(double time, double value){
      return value;
    }
  }
  final static class OutEase extends Ease{
    OutEase(){
      super();
    }
    double getTime(double time){
      return 1 - time;
    }
    double getValue(double time, double value){
      return 1 - value;
    }
  }
  final static class InOutEase extends Ease{
    InOutEase(){
      super();
    }
    double getTime(double time){
      if(time < 0.5) return 2 * time;
      return 2 * (1 - time);
      //return time;
    }
    double getValue(double time, double value){
      if(time < 0.5) return 0.5 * value;
      return 1 - 0.5 * value;
      //return value;
    }
  }
  final static class InOutReversedEase extends Ease{
    InOutReversedEase(){
      super();
    }
    double getTime(double time){
      if(time < 0.5) return 1 - 2 * time;
      return 1 - 2 * (1 - time);
    }
    double getValue(double time, double value){
      if(time < 0.5) return 0.5 - 0.5 * value;
      return 0.5 + 0.5 * value;
    }
  }
}
