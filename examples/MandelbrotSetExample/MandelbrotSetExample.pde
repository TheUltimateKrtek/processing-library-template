//Not working

import projectaria.niri.ComplexPoint;

//Pixels pet unit
float GRID_SCALE = 100;

//Maximum loops
int LOOP_COUNT = 1000;

//Values for updates
int lastWidth, lastHeight;

//Calculated values
int[][] values;

//Image rendered from values
PImage image;

void setup(){
  size(640, 480);
  surface.setResizable(true);
  
  lastWidth = width;
  lastHeight = height;
}

void draw(){
  background(0);
  
  if(lastWidth != width || lastHeight != height || values == null){
    println(width, lastWidth);
    println(height, lastHeight);
    println(values);
    recalculate();
  }
  
  imageMode(CORNERS);
  image(image, 0, 0, width, height);
  
  drawData();
  drawGrid();
}

void drawData(){
  ComplexPoint mouseInGridPosition = displayPointToGridPoint(mouseX, mouseY);
  fill(255);
  textSize(20);
  textAlign(LEFT, TOP);
  //Draw data
  try{
    text(values[mouseY][mouseX], 0, 0);
  }
  catch(Exception e){
    
  }
  text("Mouse X: " + mouseInGridPosition.x, 0, 20);
  text("Mouse Y: " + mouseInGridPosition.y, 0, 40);
}
void drawGrid(){
  ComplexPoint max = displayPointToGridPoint(width, height).floor();
  ComplexPoint min = displayPointToGridPoint(0, 0).ceil();
  
  stroke(255);
  strokeWeight(1);
  for(int x = (int)min.x; x <= (int)max.x; x ++){
    for(int y = (int)min.y; y <= (int)max.y; y ++){
      PVector point = gridPointToDisplayPoint(new ComplexPoint(x, y));
      line(point.x, 0, point.x, height);
      line(0, point.y, width, point.y);
    }
  }
  
  strokeWeight(4);
  PVector point = gridPointToDisplayPoint(new ComplexPoint(0, 0));
  line(point.x, 0, point.x, height);
  line(0, point.y, width, point.y);
}



void recalculate(){
  lastWidth = width;
  lastHeight = height;
  
  //Evaluate each pixel
  values = new int[height][width];
  for(int y = 0; y < values.length; y ++){
    for(int x = 0; x < values[y].length; x ++){
      values[y][x] = evaluate(x, y);
    }
  }
  
  //Render an image from values
  image = createImage(values.length, values[0].length, ARGB);
  image.loadPixels();
  for(int y = 0; y < image.width; y ++){
    for(int x = 0; x < image.height; x ++){
      image.pixels[x + y * image.width] = getColor(values[y][x]);
    }
  }
  image.updatePixels();
}
int evaluate(float pixelx, float pixely){
  ComplexPoint p = displayPointToGridPoint(pixelx, pixely);
  ComplexPoint value = new ComplexPoint();
  int count = 0;
  while(count < LOOP_COUNT && p.getDistanceFromOriginSq() < 4){
    value.multiply(value).add(p);
    count ++;
  }
  return count;
}




int getColor(int value){
  return lerpColor(0x00000000, 0xFFFFFFFF, value * 255.0 / LOOP_COUNT);
}
ComplexPoint displayPointToGridPoint(float pixelx, float pixely){
  return new ComplexPoint(pixelx, pixely).subtract(width * 0.5, height * 0.5).scale(1 / GRID_SCALE);
}
PVector gridPointToDisplayPoint(ComplexPoint p){
  p = p.copy().scale(GRID_SCALE).add(width * 0.5, height * 0.5);
  return new PVector((float)p.x, (float)p.y);
}
