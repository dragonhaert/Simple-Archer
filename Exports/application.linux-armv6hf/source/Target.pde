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
  
  boolean collide(PVector test)
  {
    return test.dist(pos) <= 0.5 * radius;
  }
  
  void newPos()
  {
    float newX = radius + (float) Math.random() * (0.1 * width);
    float newY = radius + (float) Math.random() * (height - 2*radius);
    pos = new PVector(newX, newY);
  }
  
  void drawTarget()
  {
    noStroke();
    fill(255,0,0);
    ellipse(pos.x, pos.y, radius, radius);
    fill(255,255,255);
    ellipse(pos.x, pos.y, 0.7 * radius, 0.7 * radius);
    fill(255,0,0);
    ellipse(pos.x, pos.y, 0.4 * radius, 0.4 * radius);
  }
  
}
