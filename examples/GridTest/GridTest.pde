import projectaria.niri.ComplexPoint;

float pointDefinitionSize = 300;
float unitScale = 50;
float pointDisplaySize = 15;
float gridMovementSensitivity = 0.1;
float shiftMovementSensitivity = 0.2;
float ctrlMovementSensitivity = 5;

ComplexPoint center = new ComplexPoint(0, 0);
ArrayList<PointStruct> points;

int themeColor = #3F7FFF;
int heldPoint = -1;

boolean ctrlPressed = false;
boolean shiftPressed = false;
boolean altPressed = false;
boolean upPressed = false;
boolean downPressed = false;
boolean leftPressed = false;
boolean rightPressed = false;

boolean lockedImaginary = false;
boolean lockedReal = false;

ComplexPoint lastMouse = new ComplexPoint(0, 0);
PFont font;
PGraphics backgroundGraphics;
ThreadBackgroundGenerator generator;

ComplexPointScreen(){
  super();
}

void setup(){
  this.points = new ArrayList<PointStruct>();

  this.font = createFont("Nirmala UI Bold", 60);
  String[] fonts = PFont.list();
  for(String f:fonts){
    /*if(f.toLowerCase().contains("Consolas"))*/ Console.println(f.toString());
  }

  this.points.add(new PointStruct(new ComplexPoint(-1, -1), #3F7FFF));

  this.generator = new ThreadBackgroundGenerator(this);
  this.generator.start();
}

ComplexPoint getResult(){
  if(this.points.size() < 1) return null;
  int hoverIndex = this.getHoveredOverValue();
  if(hoverIndex == -1) return null;
  return getResult(this.points.get(hoverIndex).p);
}
ComplexPoint getResult(ComplexPoint p){
  if(p == null) return null;
  return p.copy().log();
}
ComplexPoint[][] getResultLine(){
  float precission = 0.1;
  float min = - (float)Math.PI*3;
  float max =  (float)Math.PI*3;
  int steps = (int)((max - min) / precission);

  if(this.points.size() < 1) return null;
  int hoverIndex = this.getHoveredOverValue();
  if(hoverIndex == -1) return null;
  ComplexPoint p = this.points.get(hoverIndex).p;
  ArrayList<ComplexPoint> inputs = new ArrayList<ComplexPoint>();
  ArrayList<ComplexPoint> outputs = new ArrayList<ComplexPoint>();
  for(int i = 0; i <= steps; i ++){
    ComplexPoint input = p.copy().add(min + precission * i, 0);
    outputs.add(this.getResult(input));
    inputs.add(input.copy());
  }
  ComplexPoint[][] rl = new ComplexPoint[3][inputs.size()];
  for(int i = 0; i < rl[0].length; i ++){
    rl[0][i] = inputs.get(i);
    rl[1][i] = outputs.get(i);
  }
  rl[2] = new ComplexPoint[]{
    new ComplexPoint(this.points.get(hoverIndex).c, 0)
  };
  return rl;
}
PImage getResultImage(int width, int height, int pixelsPerValue){
  PGraphics img = createGraphics(width, height);
  img.beginDraw();

  ComplexPoint lt = this.getLeftTopValue();
  ComplexPoint rb = this.getRightBottomValue();
  ComplexPoint stepSize = rb.copy().subtract(lt).scale(1.0 / width, 1.0 / height);
  
  img.colorMode(HSB);
  img.noStroke();

  for(int x = 0; x < width; x += pixelsPerValue){
    for(int y = 0; y < height; y += pixelsPerValue){
      ComplexPoint p = this.getResult(lt.copy().add(stepSize.copy().scale(x, y)));
      float angle = (float)p.getComplexArgument();
      float distance = (float)p.getRadius();

      float hue = angle / (float)Math.PI * 255;
      float brightness = (float)Math.sqrt(distance);

      img.fill(hue, 255, brightness);
      img.rect(x, y, x + pixelsPerValue - 1, y + pixelsPerValue - 1);
      
    }
  }
  img.endDraw();
  return img;
}


void draw(PGraphics pg){
  pg.background(23);

  if(this.points.size() == 2){
    this.points.get(0).c = #3F7FFF;
    this.points.get(1).c = #3FFF7F;
  }
  else{
    for(PointStruct ps:this.points) ps.c = #3F7FFF;
  }

  pg.textFont(this.font);

  this.moveGrid();

  this.drawResultImage(pg);
  this.drawGrid(pg);
  this.drawPoints(pg);
  this.drawResult(pg);
  this.drawResultLine(pg);
  //this.drawSplitResultLine(pg);
  this.drawLog(pg);
  this.drawDefinitions(pg);
}

private void moveGrid(){
  float value = this.gridMovementSensitivity * (this.ctrlPressed ? this.ctrlMovementSensitivity : 1) * (this.shiftPressed ? this.shiftMovementSensitivity : 1) / this.unitScale * 50;
  if(this.upPressed) this.center.add(0, value);
  if(this.downPressed) this.center.add(0, - value);
  if(this.rightPressed) this.center.add(- value, 0);
  if(this.leftPressed) this.center.add(value, 0);
}

private void drawGrid(PGraphics pg){
  pg.stroke(255);

  ComplexPoint tl = this.getLeftTopValue();
  ComplexPoint br = this.getRightBottomValue();

  //int xmin = (int)Math.ceil(tl.x);
  //int xmax = (int)Math.ceil(br.x);
  //int ymin = (int)Math.ceil(tl.y);
  //int ymax = (int)Math.ceil(br.y);
  
  float gridScale = 1;

  double xmax = gridScale * (this.unsignedAbs(br.x / gridScale) + 1);
  double xmin = gridScale * this.unsignedAbs(tl.x / gridScale);
  double ymax = gridScale * (this.unsignedAbs(br.y / gridScale) + 1);
  double ymin = gridScale * this.unsignedAbs(tl.y / gridScale);

  int xlines = (int)Math.round((xmax - xmin) / gridScale);
  int ylines = (int)Math.round((ymax - ymin) / gridScale);

  for(double i = xmin; i < xmax; i += gridScale){
    pg.strokeWeight((float)i == 0 ? 2 : 0.3333);
    ComplexPoint coordinates = getDisplayCoordinates(new ComplexPoint(i, 0));
    pg.line((float)coordinates.x, 0, (float)coordinates.x, pg.height);
  }
  for(double i = ymin; i < ymax; i += gridScale){
    pg.strokeWeight(i == 0 ? 2 : 0.3333);
    ComplexPoint coordinates = getDisplayCoordinates(new ComplexPoint(0, i));
    pg.line(0, (float)coordinates.y, pg.width, (float)coordinates.y);
  }

  float textSize = (float)Math.abs(getDisplayCoordinates(new ComplexPoint(0.2, 0.2)).subtract(getDisplayCoordinates(new ComplexPoint(0, 0))).scale(gridScale).y);
  pg.fill(255);
  pg.textAlign(LEFT, TOP);
  pg.textSize(textSize * (float)gridScale);
  for(double i = xmin; i < xmax; i += gridScale){
    if(i == 0) continue;
    String s = "" + i;
    ComplexPoint coordinates = getDisplayCoordinates(new ComplexPoint(i, 0));
    coordinates.y = coordinates.y < textSize * 0.5 ? textSize * 0.5 : (coordinates.y > (pg.height - textSize * 1.5) ? (pg.height - textSize * 1.5) : coordinates.y);
    pg.text(s, (float)coordinates.x, (float)coordinates.y);
  }
  for(double i = ymin; i < ymax; i += gridScale){
    String s = " " + i;
    float textWidth = pg.textWidth(s);
    float spaceWidth = pg.textWidth(" ");
    ComplexPoint coordinates = getDisplayCoordinates(new ComplexPoint(0, i));
    coordinates.x = coordinates.x < textSize * 0.5 - spaceWidth ? textSize * 0.5 - spaceWidth : (coordinates.x > pg.width - this.pointDefinitionSize - textWidth - textSize * 0.5 ? pg.width - this.pointDefinitionSize - textWidth - textSize * 0.5 : coordinates.x);
    pg.text(s, (float)coordinates.x, (float)coordinates.y);
  }
}
private void drawPoints(PGraphics pg){
  int hoverIndex = this.getHoveredOverValue();
  pg.strokeWeight(1);
  pg.ellipseMode(CENTER);
  for(int i = points.size() - 1; i >= 0; i --){
    PointStruct p = this.points.get(i);
    ComplexPoint coordinates = this.getDisplayCoordinates(p.p);
    boolean hover = i == hoverIndex;

    pg.fill(p.c, hover ? 255 : 127);
    pg.stroke(p.c);
    pg.ellipse((float)coordinates.x, (float)coordinates.y, this.pointDisplaySize, this.pointDisplaySize);

    if(hover){
      String s = p.p.x + " + " + p.p.y + " i";
      pg.fill(p.c);
      pg.textSize(20);
      pg.textAlign(CENTER, BOTTOM);
      pg.text(s, (float)coordinates.x, (float)coordinates.y - pointDisplaySize);
    }
  }
}
private void drawResultImage(PGraphics pg){
  if(this.backgroundGraphics == null) return;
  pg.imageMode(CORNER);
  pg.image(this.backgroundGraphics, 0, 0, width - this.pointDefinitionSize, height);

  if(!new Aria.Objects.RectangleBody(width - this.pointDefinitionSize + 10, height - 20, (int)pointDefinitionSize - 20, 10, 0, 0).isPointInside(mouseX, mouseY)){
    pg.rectMode(CORNER);
    pg.noStroke();
    pg.fill(32, 213);
    pg.rect(0, 0, width - this.pointDefinitionSize, height);
  }
}
private void drawResult(PGraphics pg){
  ComplexPoint result = this.getResult();
  if(result == null) return;
  ComplexPoint coordinates = this.getDisplayCoordinates(result);
  boolean hover = coordinates.copy().subtract(mouseX, mouseY).getRadius() < (pointDisplaySize * 0.5);
  hover = hover && (this.getHoveredOverValue() < 0);
  
  pg.strokeWeight(1);
  pg.ellipseMode(CENTER);
  
  pg.stroke(255);
  pg.fill(255, hover ? 255 : 0);
  pg.ellipse((float)coordinates.x, (float)coordinates.y, this.pointDisplaySize, this.pointDisplaySize);
  if(hover){
    String s = result.x + " + " + result.y + " i";
    pg.fill(255);
    pg.textSize(20);
    pg.textAlign(CENTER, BOTTOM);
    pg.text(s, (float)coordinates.x, (float)coordinates.y - pointDisplaySize);
  }
}
private void drawResultLine(PGraphics pg){
  ComplexPoint[][] result = this.getResultLine();
  if(result == null) return;
  pg.strokeWeight(2);
  pg.stroke((int)result[2][0].x);
  pg.noFill();
  pg.beginShape();
  for(int i = 0; i < result[0].length; i ++){
    ComplexPoint displayPoint = this.getDisplayCoordinates(result[0][i]);
    pg.vertex((float)displayPoint.x, (float)displayPoint.y);
  }
  pg.endShape(OPEN);
  pg.stroke(255);
  pg.beginShape();
  for(int i = 0; i < result[1].length; i ++){
    ComplexPoint displayPoint = this.getDisplayCoordinates(result[1][i]);
    pg.vertex((float)displayPoint.x, (float)displayPoint.y);
  }
  pg.endShape(OPEN);
}
private void drawSplitResultLine(PGraphics pg){
  ComplexPoint[][] result = this.getResultLine();
  if(result == null) return;
  pg.strokeWeight(2);
  pg.stroke(255, 63, 63);
  pg.noFill();
  pg.beginShape();
  for(int i = 0; i < result[0].length; i ++){
    ComplexPoint displayOutputPoint = this.getDisplayCoordinates(result[1][i]);
    ComplexPoint displayInputPoint = this.getDisplayCoordinates(result[0][i]);
    pg.vertex((float)displayInputPoint.x, (float)displayOutputPoint.y);
  }
  pg.endShape(OPEN);
  pg.stroke(63, 63, 255);
  pg.beginShape();
  for(int i = 0; i < result[0].length; i ++){
    ComplexPoint displayOutputPoint = this.getDisplayCoordinates(result[1][i]);
    ComplexPoint displayInputPoint = this.getDisplayCoordinates(result[0][i]);
    pg.vertex((float)displayInputPoint.x, (float)displayOutputPoint.x);
  }
  pg.endShape(OPEN);
}
private void drawLog(PGraphics pg){
  pg.fill(255);
  pg.textSize(20);
  pg.textAlign(LEFT, TOP);
  pg.text("CONTROL: " + this.ctrlPressed, 0, 0);
  pg.text("SHIFT: " + this.shiftPressed, 0, 20);
  pg.text("ALT: " + this.altPressed, 0, 40);
}
private void drawDefinitions(PGraphics pg){
  float left = width - this.pointDefinitionSize;
  float right = width;
  float top = 0;
  float bottom = height;

  pg.rectMode(CORNERS);
  pg.fill(23);
  pg.noStroke();
  pg.rect(left, top, right, bottom);
  pg.stroke(255);
  pg.strokeWeight(2);
  pg.line(left, top, left, bottom);

  pg.textSize(20);
  pg.textLeading(20);
  pg.textAlign(LEFT, TOP);
  pg.strokeWeight(1);
  int hoveredOverPoint = this.getHoveredOverValue();
  for(int i = 0; i < this.points.size(); i ++){
    pg.fill(this.points.get(i).c);
    pg.text("Index: " + i + "\n" + this.points.get(i).p.x + "\n+ " + this.points.get(i).p.y + " i", left + 10, top + i * 80, right - 10, top + (i + 1) * 80);
    
    pg.stroke(255, 63);
    pg.line(left + 10, top + i * 80 - 10, right - 10, top + i * 80 - 10);
    if(hoveredOverPoint == i){
      pg.stroke(this.points.get(i).c);
      pg.line(left + 5, top + i * 80 + 5, left + 5, top + (i + 1) * 80 - 20);
    }
  }

  ComplexPoint result = this.getResult();
  if(result != null){
    ComplexPoint coordinates = this.getDisplayCoordinates(result);
    boolean hoverResult = coordinates.copy().subtract(mouseX, mouseY).getRadius() < (pointDisplaySize * 0.5);
    hoverResult = hoverResult && (this.heldPoint == -1);
    
    float resultTop = top + this.points.size() * 80;
    float resultBottom = top + (this.points.size() + 1) * 80;

    pg.fill(255);
    pg.text("Result\n" + result.x + "\n+ " + result.y + " i", left + 10, resultTop, right - 10, resultBottom);
    
    pg.stroke(255, 63);
    pg.line(left + 10, resultTop - 10, right - 10, resultTop - 10);
    if(hoverResult){
      pg.stroke(255);
      pg.line(left + 5, resultTop + 5, left + 5, resultBottom - 20);
    }
  }

  pg.noStroke();
  pg.fill(63, 127, 255);
  pg.rectMode(CORNER);
  pg.rect(left + 10, bottom - 20, (this.pointDefinitionSize - 20) * this.generator.progress, 10);

  pg.textAlign(CENTER, CENTER);
  pg.textSize(25);
  pg.fill(this.lockedImaginary ? 255 : 127);
  pg.text("Lock Imaginary Drag", pg.width - this.pointDefinitionSize * 0.5, pg.height - 65);
  pg.fill(this.lockedReal ? 255 : 127);
  pg.text("Lock Real Drag", pg.width - this.pointDefinitionSize * 0.5, pg.height - 95);
}

void mousePressed(int x, int y, int w, int h){
  if(mouseButton == LEFT) this.mousePressedLeft(x, y, w, h);
  else if(mouseButton == RIGHT) this.mousePressedRight(x, y, w, h);
  this.lastMouse.set(x, y);
}
void mousePressedLeft(int x, int y, int w, int h){
  int pointIndex = this.getHoveredOverValue();
  this.heldPoint = pointIndex;

  if(this.heldPoint != -1){
    if(this.lockedImaginary && this.lockedReal) return;
      else if(this.lockedImaginary){
        this.points.get(this.heldPoint).p.set(this.getGridCoordinates(new ComplexPoint(x, y)).x, this.points.get(this.heldPoint).p.y);
      }
      else if(this.lockedReal){
        this.points.get(this.heldPoint).p.set(this.points.get(this.heldPoint).p.x, this.getGridCoordinates(new ComplexPoint(x, y)).y);
      }
      else{
        this.points.get(this.heldPoint).p.set(this.getGridCoordinates(new ComplexPoint(x, y)));
      }
    }
  else{
    if(x > w - this.pointDefinitionSize && y < h - 50 && y > h - 80){
      this.lockedImaginary = !this.lockedImaginary;
    }
    else if(x > w - this.pointDefinitionSize && y < h - 80 && y > h - 110){
      this.lockedReal = !this.lockedReal;
    }
  }
}
void mousePressedRight(int x, int y, int w, int h){
  if(x > width - this.pointDefinitionSize) return;
  if(this.heldPoint != -1) this.heldPoint = -1;
  int pointIndex = this.getHoveredOverValue();
  if(pointIndex == -1){
    this.points.add(new PointStruct(this.getGridCoordinates(new ComplexPoint(x, y)), #3F7FFF));
    this.heldPoint = this.points.size() - 1;
  }
  else this.points.remove(pointIndex);
}
void mouseDragged(int x, int y, int w, int h){
  if(this.heldPoint == -1){
    ComplexPoint deltaMouse = this.getGridCoordinates(new ComplexPoint(x, y)).subtract(this.getGridCoordinates(this.lastMouse));
    if(this.shiftPressed) deltaMouse.scale(this.shiftMovementSensitivity);
    if(this.ctrlPressed) deltaMouse.scale(this.ctrlMovementSensitivity);
    deltaMouse.invert();
    this.center.add(deltaMouse);
  }
  else{
    ComplexPoint deltaMovement = this.getGridCoordinates(new ComplexPoint(x, y)).subtract(this.getGridCoordinates(lastMouse));

    if(this.ctrlPressed) deltaMovement.scale(this.ctrlMovementSensitivity);
    if(this.shiftPressed) deltaMovement.scale(this.shiftMovementSensitivity);

    if(this.lockedImaginary && this.lockedReal) return;
    else if(this.lockedImaginary){
      this.points.get(this.heldPoint).p.add(deltaMovement.x, 0);
    }
    else if(this.lockedReal){
      this.points.get(this.heldPoint).p.add(0, deltaMovement.y);
    }
    else{
      this.points.get(this.heldPoint).p.add(deltaMovement);
    }
  }

  this.lastMouse.set(x, y);
}
void mouseReleased(int x, int y, int w, int h){
  this.heldPoint = -1;
}
void mouseWheel(int x, int y, int w, int h, float value){
  if(this.ctrlPressed){
    if(value == 0) return;
    value = - value;
    float scale = (float)Math.pow(2, value / 4.0);
    ComplexPoint centerDistance = this.getDisplayCoordinates(this.center);
    centerDistance.subtract(x, y).scale(1 / scale).add(x, y);
    centerDistance = this.getGridCoordinates(centerDistance);
    this.center.set(centerDistance);
    this.unitScale *= scale;
  }
  else if(this.shiftPressed){
    this.center.add(value * this.gridMovementSensitivity / this.unitScale * 50, 0);
  }
  else if(this.altPressed){

  }
  else{
    this.center.add(0, - value * this.gridMovementSensitivity / this.unitScale * 50);
  }
}
void keyPressed(char c, int keyCode){
  if(keyCode == CONTROL) this.ctrlPressed = true;
  if(keyCode == SHIFT) this.shiftPressed = true;
  if(keyCode == ALT) this.altPressed = true;
  if(keyCode == UP) this.upPressed = true;
  if(keyCode == DOWN) this.downPressed = true;
  if(keyCode == LEFT) this.leftPressed = true;
  if(keyCode == RIGHT) this.rightPressed = true;
}
void keyReleased(char c, int keyCode){
  if(keyCode == CONTROL) this.ctrlPressed = false;
  if(keyCode == SHIFT) this.shiftPressed = false;
  if(keyCode == ALT) this.altPressed = false;
  if(keyCode == UP) this.upPressed = false;
  if(keyCode == DOWN) this.downPressed = false;
  if(keyCode == LEFT) this.leftPressed = false;
  if(keyCode == RIGHT) this.rightPressed = false;
}

ComplexPoint getLeftTopValue(){
  return center.copy().subtract(new ComplexPoint(this.getBottomRightDrawScreen().x, this.getBottomRightDrawScreen().y).scale(0.5 / unitScale));
}
ComplexPoint getRightBottomValue(){
  return center.copy().add(new ComplexPoint(this.getBottomRightDrawScreen().x, this.getBottomRightDrawScreen().y).scale(0.5 / unitScale));
}
ComplexPoint getTopLeftDrawScreen(){
  return new ComplexPoint(0, 0);
}
ComplexPoint getBottomRightDrawScreen(){
  return new ComplexPoint(width - this.pointDefinitionSize, height);
}
ComplexPoint getDisplayCoordinates(ComplexPoint p){
  ComplexPoint tl = this.getLeftTopValue();
  ComplexPoint rb = this.getRightBottomValue();

  double x = (p.x - tl.x) / (rb.x - tl.x) * this.getBottomRightDrawScreen().x;
  double y = height - (p.y - tl.y) / (rb.y - tl.y) * this.getBottomRightDrawScreen().y;

  return new ComplexPoint(x, y);
}
ComplexPoint getGridCoordinates(ComplexPoint p){
  ComplexPoint tl = this.getLeftTopValue();
  ComplexPoint rb = this.getRightBottomValue();

  double x = p.x * (rb.x - tl.x) / this.getBottomRightDrawScreen().x + tl.x;
  double y = (this.getBottomRightDrawScreen().y - p.y) * (rb.y - tl.y) / this.getBottomRightDrawScreen().y + tl.y;

  return new ComplexPoint(x, y);
}
int getHoveredOverValue(){
  if(this.heldPoint != -1) return heldPoint;
  for(int i = 0; i < points.size(); i ++){
    PointStruct p = this.points.get(i);
    ComplexPoint coordinates = this.getDisplayCoordinates(p.p);
    ComplexPoint dist = coordinates.copy().subtract(mouseX, mouseY);
    if(dist.x * dist.x + dist.y * dist.y < this.pointDisplaySize * this.pointDisplaySize){
      return i;
    }
  }
  return -1;
}
private int unsignedAbs(double number){
  boolean neg = number < 0;
  return (int)Math.abs(number) * (neg ? -1 : 1);
}
double round(double d, int dec){
  return (double)Math.round(d * dec) / dec;
}

static class PointStruct{
  ComplexPoint p;
  int c;

  PointStruct(ComplexPoint p, int c){
    this.p = p;
    this.c = c;
  }
}

static class ThreadBackgroundGenerator extends Thread{
  float progress = 0;
  int pixelsPerValue = 2;

  ThreadBackgroundGenerator(){
    super();
  }

  protected void run(){
    while(true){
      ComplexPoint lt = new ComplexPoint(0, 0);
      ComplexPoint rb = new ComplexPoint(width, height);
      ComplexPoint size = rb.copy()
      
      int width = (int)size.x;
      int height = (int)size.y;

      ComplexPoint stepSize = rb.copy().subtract(lt).scale(1.0 / width, 1.0 / height);

      if(width <= 0 || height <= 0) continue;

      PGraphics img = createGraphics(width, height);
      img.beginDraw();

      img.colorMode(HSB);
      img.noStroke();
      
      int totalOperations = (int)Math.ceil(width / (float)pixelsPerValue) * (int)Math.ceil(height / (float)pixelsPerValue);
      int finishedOperations = 0;

      for(int x = 0; x < width; x += pixelsPerValue){
        for(int y = 0; y < height; y += pixelsPerValue){
          ComplexPoint p = this.screen.getResult(lt.copy().add(stepSize.copy().scale(x, height - y)));
          float angle = (float)p.getComplexArgument();
          float distance = (float)p.getRadius();

          distance *= 0.00392156862;
          distance = (32 * distance) / (31 * distance + 1);
          distance *= 255;

          float hue = (angle + TWO_PI) % TWO_PI / (float)Math.PI * 0.5 * 255;
          float brightness = distance;

          img.fill(hue, 255, brightness);
          img.rect(x, y, x + pixelsPerValue - 1, y + pixelsPerValue - 1);
          
          finishedOperations ++;
          this.progress = (float)finishedOperations / totalOperations;
        }
      }

      img.endDraw();

      this.setResult(img);
      this.screen.backgroundGraphics = img;
    }
  }
}
