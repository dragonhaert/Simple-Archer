import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class archer_game extends PApplet {

//Global Variables
static final float FPS = 30;

Body legalRegion;
boolean legalPress = false;

PVector newHead;
PVector newRear;
ArrayList<Arrow> arrows;
Target target;

PVector bowman;
int bowmanSize;
float offset;

int score = -1;

public void setup()
{
  width = 800;
  height = 640;
  
  
  
  arrows = new ArrayList();
  target = new Target(width/2, height/2);
  
  bowman = new PVector(0.85f * width, 0.6f * height);
  bowmanSize = 20;
  offset = 30;
  
  legalRegion = new Body(bowman.x - offset - 8 * bowmanSize, bowman.y - 4 * bowmanSize, (int)(width - bowman.x + offset + 8 * bowmanSize), (int)(height - bowman.y + 4 * bowmanSize)); 
}

public void draw()
{
  frameRate(FPS);
  bg();
  
  target.drawTarget();
  if (mousePressed)
  {
    stroke(0,0,0);
    strokeWeight(2);
    line(newHead.x, newHead.y, mouseX, mouseY);
  }
  
  if (arrows.size() > 0)
  {
    for (int i = arrows.size() - 1; i >= 0; i--)
    {
      Arrow a = arrows.get(i);
      a.vMove();
      a.drawArrow();
      if ( target.collide(a.getHead()) )
      {
        score++;
        println("Score!\nTotal: " + score);
        target.newPos();
      }
    }
  }
  
}

public void drawTree(float x, float y)
{
  stroke(2);
  //trunk
  float trunkSize = 20;
  fill(200, 150, 0);
  rect(x - 0.5f * trunkSize, y - 2 * trunkSize, trunkSize, 2 * trunkSize);
  
  //leaves
  fill(0,150,0);
  triangle(x - trunkSize, y - 2 * trunkSize, x + trunkSize, y - 2 * trunkSize, x, y - 7 * trunkSize);
  triangle(x - trunkSize, y - 4 * trunkSize, x + trunkSize, y - 4 * trunkSize, x, y - 8 * trunkSize);
}

public void bg()
{
  background(0, 225, 225); 
  noStroke();
  float horizonLine = 0.65f * height;
  
  //ground
  fill(0,200,0);
  rect(0, horizonLine, width, height - horizonLine);
  
  //background
  drawTree(0.05f * width, horizonLine);
  drawTree(0.16f * width, horizonLine);
  drawTree(0.28f * width, horizonLine);
  drawTree(0.41f * width, horizonLine);
  drawTree(0.51f * width, horizonLine);
  drawTree(0.67f * width, horizonLine);
  drawTree(0.8f * width, horizonLine);
  drawTree(0.97f * width, horizonLine);
  drawTree(0.23f * width, horizonLine + 5);
  drawTree(0.56f * width, horizonLine + 5);
  drawTree(0.74f * width, horizonLine + 5);
  drawTree(0.91f * width, horizonLine + 5);
  drawTree(0.1f * width, horizonLine + 10);
  drawTree(0.34f * width, horizonLine + 10);
  drawTree(0.45f * width, horizonLine + 10);
  drawTree(0.61f * width, horizonLine + 10);
  drawTree(0.85f * width, horizonLine + 10);
  
  //player body
  strokeWeight(3);
  stroke(0);
  
  //torso
  line(bowman.x, bowman.y, bowman.x, bowman.y + 3 * bowmanSize);
  
  //arms
  line(bowman.x, bowman.y + 0.6f * bowmanSize, bowman.x - offset - 1.8f * bowmanSize, bowman.y);
  
  line(bowman.x + 0.5f * offset, bowman.y + bowmanSize, bowman.x - offset - 0.2f * bowmanSize, bowman.y + bowmanSize);
  line(bowman.x, bowman.y + 0.7f * bowmanSize, bowman.x + 0.5f * offset, bowman.y + bowmanSize);

  //legs
  line(bowman.x, bowman.y + 3 * bowmanSize, bowman.x - 0.6f * bowmanSize, bowman.y + 7 * bowmanSize);
  line(bowman.x, bowman.y + 3 * bowmanSize, bowman.x + 0.4f * bowmanSize, bowman.y + 7.3f * bowmanSize);
  
  //head
  fill(0);
  noStroke();
  ellipse(bowman.x, bowman.y - 1.2f * bowmanSize, 2 * bowmanSize, 2.4f * bowmanSize);
  
  //bow
  noFill();
  stroke(150, 75, 0);
  arc(bowman.x - offset , bowman.y + bowmanSize, 4 * bowmanSize, 7 * bowmanSize, 0.6f * PI, 1.4f * PI, PIE);
}

public void mousePressed()
{
  legalPress = legalRegion.overlaps(new PVector(mouseX,mouseY));
  if (legalPress)
  {
    newHead = new PVector(mouseX, mouseY);
  }
}

public void mouseReleased()
{
  if(legalPress)
  {
    newRear = new PVector(mouseX, mouseY);
    arrows.add(new Arrow(newHead, newRear, 3 * (float) Math.sqrt(newHead.dist(newRear)) ));
  }
}
class Arrow
{
  private PVector head;
  private PVector rear;
  private float speed;
  private PVector velocity;
  
  Arrow(PVector h, PVector b, float spd)
  {
    head = new PVector(h.x,h.y);
    rear = new PVector(b.x,b.y);
    
    speed = spd;
    float x = head.x - rear.x;
    float y = head.y - rear.y;
    float scale = (float) Math.sqrt(x * x + y * y) / speed;
    velocity = new PVector(x/scale, y/scale);
  }
  
  public void vMove()
  {
    rear.x += velocity.x;
    rear.y += velocity.y;
    head.x += velocity.x;
    head.y += velocity.y;
  }
  
  public void drawArrow()
  {
    stroke(0,0,0);
    strokeWeight(2);
    line(head.x, head.y, rear.x, rear.y);
    
    //arrow tip
    float tipSize = 5;
    float x1 = head.x + velocity.x * tipSize/speed;
    float y1 = head.y + velocity.y * tipSize/speed;
    float x2 = head.x + velocity.y * tipSize/speed;
    float y2 = head.y - velocity.x * tipSize/speed;
    float x3 = head.x - velocity.y * tipSize/speed;
    float y3 = head.y + velocity.x * tipSize/speed;
    fill(100,100,100);
    strokeWeight(1);
    triangle(x1,y1,x2,y2,x3,y3); 
  }
  
  public PVector getHead()
  {
    return head;
  }
  
  public PVector getRear()
  {
    return rear;
  }
  
}
class Body
{
  private PVector position;
  private int width;
  private int height;
  
  Body(PVector pos, int w, int h)
  {
    position = new PVector((int) pos.x, (int) pos.y);
    width = w;
    height = h;
  }
  
  Body(float x, float y, int w, int h)
  {
    position = new PVector(x, y);
    width = w;
    height = h;
  }
  
  public boolean overlaps(Body other)
  {
    float dx = Math.abs(position.x - other.position.x);
    float dy = Math.abs(position.y - other.position.y);
    return (dx < this.width/2 + other.width/2) && (dy < this.height/2 + other.height/2);
  }
  
  public boolean overlaps(PVector vector)
  {
    boolean xOverlap = vector.x > this.position.x && vector.x < this.position.x + width;
    boolean yOverlap = vector.y > this.position.y && vector.y < this.position.y + height;
    return xOverlap && yOverlap;
  }
  
  public void drawBody(int r, int g, int b)
  {
    fill(r,g,b);
    noStroke();
    rect(position.x, position.y, this.width, this.height);
  }
  
}
class Target
{
  private PVector pos;
  static final int radius = 50;
  
  public Target(float x, float y)
  {
    pos = new PVector(x,y);
  }
  
  public Target(PVector p)
  {
    pos = new PVector(p.x, p.y);
  }
  
  public boolean collide(PVector test)
  {
    return test.dist(pos) <= 0.5f * radius;
  }
  
  public void newPos()
  {
    float newX = radius + (float) Math.random() * (0.1f * width);
    float newY = radius + (float) Math.random() * (height - 2*radius);
    pos = new PVector(newX, newY);
  }
  
  public void drawTarget()
  {
    noStroke();
    fill(255,0,0);
    ellipse(pos.x, pos.y, radius, radius);
    fill(255,255,255);
    ellipse(pos.x, pos.y, 0.7f * radius, 0.7f * radius);
    fill(255,0,0);
    ellipse(pos.x, pos.y, 0.4f * radius, 0.4f * radius);
  }
  
}
  public void settings() {  size(800, 640);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "archer_game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
