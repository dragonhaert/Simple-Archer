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
  
  void drawArrow()
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
  
  PVector getHead()
  {
    return head;
  }
  
  PVector getRear()
  {
    return rear;
  }
  
}
