package application;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject
{
   
    protected Body body;
    protected Physics physics;
    protected View view;
    private int id;
    public String type;
    private boolean destroyed;
    
	public GameObject(GameObjectDTO goDTO, String type)
    {
        destroyed = false;
        Point2D.Double newPoint = new Point2D.Double(goDTO.getBody().getPoint().getX(),goDTO.getBody().getPoint().getY());
        this.body = new Body(this,newPoint,50,50);
        this.id = goDTO.getId();
        this.type = type;
        
    }
    public GameObject(int id, String type)
    {
        this.id = id;
        this.type = type;
    }
    
    public int getId()
    {
        return id;
    }

    public void update(GraphicsContext gc, Image image)
    {
        if (physics != null)
        {
            physics.update();
        }
        if (view != null)
        {
            view.draw(gc, image);
        }
    }
    
    public void update(GraphicsContext gc, HashMap<Integer, GameObject> characters, ArrayList<Wall> walls, Image image)
    {
        if (physics != null)
        {
            physics.update(characters, walls);
        }
        if (view != null)
        {
            view.draw(gc, image);
        }
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }
    public void destroy()
    {
        destroyed = true;
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
    
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
