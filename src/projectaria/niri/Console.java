package projectaria.niri;

public class Console{
  static ConsoleColor foregroundColor;
  static ConsoleColor backgroundColor;
  static private boolean autoSetColors = false;

  static public class Color4{
    static public final ConsoleColor black         = new ConsoleColor(0,   0,   0  ).lock();
    static public final ConsoleColor red           = new ConsoleColor(205, 49,  49 ).lock();
    static public final ConsoleColor green         = new ConsoleColor(13,  188, 121).lock();
    static public final ConsoleColor yellow        = new ConsoleColor(229, 229, 16 ).lock();
    static public final ConsoleColor blue          = new ConsoleColor(36,  114, 200).lock();
    static public final ConsoleColor magenta       = new ConsoleColor(188, 63,  188).lock();
    static public final ConsoleColor cyan          = new ConsoleColor(17,  168, 205).lock();
    static public final ConsoleColor light_gray    = new ConsoleColor(229, 229, 229).lock();
    static public final ConsoleColor gray          = new ConsoleColor(102, 102, 102).lock();
    static public final ConsoleColor light_red     = new ConsoleColor(241, 76,  76 ).lock();
    static public final ConsoleColor light_green   = new ConsoleColor(35,  209, 139).lock();
    static public final ConsoleColor light_yellow  = new ConsoleColor(245, 245, 67 ).lock();
    static public final ConsoleColor light_blue    = new ConsoleColor(59,  142, 234).lock();
    static public final ConsoleColor light_magenta = new ConsoleColor(214, 112, 214).lock();
    static public final ConsoleColor light_cyan    = new ConsoleColor(41,  184, 219).lock();
    static public final ConsoleColor white         = new ConsoleColor(255, 255, 255).lock();
  }
  static public class ConsoleColor{
    protected int r, g, b;
    private boolean isLocked = false;

    public ConsoleColor(int r, int g, int b){
      this.set(r, g, b);
    }

    public ConsoleColor set(int r, int g, int b){
      if(this.isLocked) return this;
      this.r = r < 0 ? 0 : (r > 255 ? 255 : r);
      this.g = g < 0 ? 0 : (g > 255 ? 255 : g);
      this.b = b < 0 ? 0 : (b > 255 ? 255 : b);
      return this;
    }
    public ConsoleColor lock(){
      this.isLocked = true;
      return this;
    }

    public int getColor(){
      return (255 << 24) + (this.r << 16) + (this.g << 8) + this.r;
    }
    public int getRed(){
      return this.r;
    }
    public int getGreen(){
      return this.g;
    }
    public int getBlue(){
      return this.b;
    }
    public ConsoleColor setRed(int r){
      if(this.isLocked) return this;
      this.r = r < 0 ? 0 : (r > 255 ? 255 : r);
      return this;
    }
    public ConsoleColor setGreen(int r){
      if(this.isLocked) return this;
      this.g = g < 0 ? 0 : (g > 255 ? 255 : g);
      return this;
    }
    public ConsoleColor setBlue(int r){
      if(this.isLocked) return this;
      this.b = b < 0 ? 0 : (b > 255 ? 255 : b);
      return this;
    }
    String getPartialCode(){
      return this.r + ";" + this.g + ";" + this.b;
    }
    String getPartialForegroundCode(){
      return "38;2;" + getPartialCode();
    }
    String getPartialBackgroundCode(){
      return "48;2;" + getPartialCode();
    }
  }

  static private String transformString(Object s, ConsoleColor backgroundColor, ConsoleColor foregroundColor){
    String resetString = "\033[0m";
    if(foregroundColor == null && backgroundColor == null){
      return s.toString();
    }
    if(foregroundColor == null && backgroundColor != null){
      return "\033[" + backgroundColor.getPartialBackgroundCode() + "m" + s + resetString;
    }
    if(foregroundColor != null && backgroundColor == null){
      return "\033[" + foregroundColor.getPartialForegroundCode() + "m" + s + resetString;
    }
    if(foregroundColor != null && backgroundColor != null){
      return "\033[" + backgroundColor.getPartialBackgroundCode() + ";" + foregroundColor.getPartialForegroundCode() + "m" + s + resetString;
    }
    return s.toString();
  }
  
  static public void setColors(ConsoleColor backgroundColor, ConsoleColor foregroundColor){
    Console.foregroundColor = foregroundColor;
    Console.backgroundColor = backgroundColor;
  }

  static public void print(Object s){
    Console.print(s, Console.backgroundColor, Console.foregroundColor, false);
  }
  static public void print(Object s, ConsoleColor backgroundColor, ConsoleColor foregroundColor){
    Console.print(s, backgroundColor, foregroundColor, Console.autoSetColors);
  }
  static public void print(Object s, ConsoleColor backgroundColor, ConsoleColor foregroundColor, boolean set){
    if(set) Console.setColors(backgroundColor, foregroundColor);
    System.out.print(Console.transformString(s, backgroundColor, foregroundColor));
  }
  static public void print(Object s, int br, int bg, int bb, int fr, int fg, int fb){
    Console.print(s, br, bg, bb, fr, fg, fb, Console.autoSetColors);
  }
  static public void print(Object s, int br, int bg, int bb, int fr, int fg, int fb, boolean set){
    Console.print(s, new ConsoleColor(br, bg, bb), new ConsoleColor(fr, fg, fb), set);
  }

  static public void println(Object s){
    Console.println(s, Console.backgroundColor, Console.foregroundColor, false);
  }
  static public void println(){
    Console.println("", Console.backgroundColor, Console.foregroundColor, false);
  }
  static public void println(Object s, ConsoleColor backgroundColor, ConsoleColor foregroundColor){
    Console.println(s, backgroundColor, foregroundColor, Console.autoSetColors);
  }
  static public void println(Object s, ConsoleColor backgroundColor, ConsoleColor foregroundColor, boolean set){
    if(set) Console.setColors(backgroundColor, foregroundColor);
    System.out.println(Console.transformString(s, backgroundColor, foregroundColor));
  }
  static public void println(Object s, int br, int bg, int bb, int fr, int fg, int fb){
    Console.println(s, br, bg, bb, fr, fg, fb, Console.autoSetColors);
  }
  static public void println(Object s, int br, int bg, int bb, int fr, int fg, int fb, boolean set){
    Console.println(s, new ConsoleColor(br, bg, bb), new ConsoleColor(fr, fg, fb), set);
  }

  static public String getString(Object s){
    return Console.getString(s, Console.backgroundColor, Console.foregroundColor);
  }
  static public String getString(Object s, int br, int bg, int bb, int fr, int fg, int fb){
    return Console.getString(s, new ConsoleColor(br, bg, bb), new ConsoleColor(fr, fg, fb));
  }
  static public String getString(Object s, ConsoleColor backgroundColor, ConsoleColor foregroundColor){
    return Console.transformString(s, backgroundColor, foregroundColor);
  }

  static void setAutoSetColors(boolean autoSetColors){
    Console.autoSetColors = autoSetColors;
  }
  static boolean getAutoSetColors(){
    return Console.autoSetColors;
  }
}