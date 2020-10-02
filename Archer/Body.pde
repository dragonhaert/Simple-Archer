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
  
  boolean overlaps(Body other)
  {
    float dx = Math.abs(position.x - other.position.x);
    float dy = Math.abs(position.y - other.position.y);
    return (dx < this.width/2 + other.width/2) && (dy < this.height/2 + other.height/2);
  }
  
  boolean overlaps(PVector vector)
  {
    boolean xOverlap = vector.x > this.position.x && vector.x < this.position.x + width;
    boolean yOverlap = vector.y > this.position.y && vector.y < this.position.y + height;
    return xOverlap && yOverlap;
  }
  
  void drawBody(int r, int g, int b)
  {
    fill(r,g,b);
    noStroke();
    rect(position.x, position.y, this.width, this.height);
  }
  
}
