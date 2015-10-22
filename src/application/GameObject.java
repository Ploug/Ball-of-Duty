package application;


import java.awt.geom.Point2D;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject
{
    protected Body body;
    protected Physics physics;
    protected View view;
    private int id;

    public GameObject(GameObjectDTO goDTO)
    {
        Point2D.Double newPoint = new Point2D.Double(goDTO.getBody().getPoint().getX(),goDTO.getBody().getPoint().getY());
        this.body = new Body(this,newPoint);
        this.id = goDTO.getId();
        
    }
    public GameObject(int id)
    {
        this.id = id;
        
    }
    
    public int getId()
    {
        return id;
    }

    public void update(GraphicsContext gc)
    {
        if (physics != null)
        {
            physics.update();
        }
        if (view != null)
        {
            view.draw(gc);
        }
    }


    public void destroy()
    {

    }

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;
    }

    public Physics getPhysics()
    {
        return physics;
    }

    public void setPhysics(Physics physics)
    {
        this.physics = physics;
    }

    public View getView()
    {
        return view;
    }

    public void setView(View view)
    {
        this.view = view;
    }
}
