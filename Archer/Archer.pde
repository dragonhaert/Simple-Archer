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

void setup()
{
  width = 800;
  height = 640;
  size(800, 640);
  smooth();
  
  arrows = new ArrayList();
  target = new Target(width/2, height/2);
  
  bowman = new PVector(0.85 * width, 0.6 * height);
  bowmanSize = 20;
  offset = 30;
  
  legalRegion = new Body(bowman.x - offset - 8 * bowmanSize, bowman.y - 4 * bowmanSize, (int)(width - bowman.x + offset + 8 * bowmanSize), (int)(height - bowman.y + 4 * bowmanSize)); 
}

void draw()
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

void drawTree(float x, float y)
{
  stroke(2);
  //trunk
  float trunkSize = 20;
  fill(200, 150, 0);
  rect(x - 0.5 * trunkSize, y - 2 * trunkSize, trunkSize, 2 * trunkSize);
  
  //leaves
  fill(0,150,0);
  triangle(x - trunkSize, y - 2 * trunkSize, x + trunkSize, y - 2 * trunkSize, x, y - 7 * trunkSize);
  triangle(x - trunkSize, y - 4 * trunkSize, x + trunkSize, y - 4 * trunkSize, x, y - 8 * trunkSize);
}

void bg()
{
  background(0, 225, 225); 
  noStroke();
  float horizonLine = 0.65 * height;
  
  //ground
  fill(0,200,0);
  rect(0, horizonLine, width, height - horizonLine);
  
  //background
  drawTree(0.05 * width, horizonLine);
  drawTree(0.16 * width, horizonLine);
  drawTree(0.28 * width, horizonLine);
  drawTree(0.41 * width, horizonLine);
  drawTree(0.51 * width, horizonLine);
  drawTree(0.67 * width, horizonLine);
  drawTree(0.8 * width, horizonLine);
  drawTree(0.97 * width, horizonLine);
  drawTree(0.23 * width, horizonLine + 5);
  drawTree(0.56 * width, horizonLine + 5);
  drawTree(0.74 * width, horizonLine + 5);
  drawTree(0.91 * width, horizonLine + 5);
  drawTree(0.1 * width, horizonLine + 10);
  drawTree(0.34 * width, horizonLine + 10);
  drawTree(0.45 * width, horizonLine + 10);
  drawTree(0.61 * width, horizonLine + 10);
  drawTree(0.85 * width, horizonLine + 10);
  
  //player body
  strokeWeight(3);
  stroke(0);
  
  //torso
  line(bowman.x, bowman.y, bowman.x, bowman.y + 3 * bowmanSize);
  
  //arms
  line(bowman.x, bowman.y + 0.6 * bowmanSize, bowman.x - offset - 1.8 * bowmanSize, bowman.y);
  
  line(bowman.x + 0.5 * offset, bowman.y + bowmanSize, bowman.x - offset - 0.2 * bowmanSize, bowman.y + bowmanSize);
  line(bowman.x, bowman.y + 0.7 * bowmanSize, bowman.x + 0.5 * offset, bowman.y + bowmanSize);

  //legs
  line(bowman.x, bowman.y + 3 * bowmanSize, bowman.x - 0.6 * bowmanSize, bowman.y + 7 * bowmanSize);
  line(bowman.x, bowman.y + 3 * bowmanSize, bowman.x + 0.4 * bowmanSize, bowman.y + 7.3 * bowmanSize);
  
  //head
  fill(0);
  noStroke();
  ellipse(bowman.x, bowman.y - 1.2 * bowmanSize, 2 * bowmanSize, 2.4 * bowmanSize);
  
  //bow
  noFill();
  stroke(150, 75, 0);
  arc(bowman.x - offset , bowman.y + bowmanSize, 4 * bowmanSize, 7 * bowmanSize, 0.6 * PI, 1.4 * PI, PIE);
}

void mousePressed()
{
  legalPress = legalRegion.overlaps(new PVector(mouseX,mouseY));
  if (legalPress)
  {
    newHead = new PVector(mouseX, mouseY);
  }
}

void mouseReleased()
{
  if(legalPress)
  {
    newRear = new PVector(mouseX, mouseY);
    arrows.add(new Arrow(newHead, newRear, 3 * (float) Math.sqrt(newHead.dist(newRear)) ));
  }
}
