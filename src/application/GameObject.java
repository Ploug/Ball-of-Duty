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
    private boolean destroyed;
    
    public GameObject(GameObjectDTO goDTO)
    {
        destroyed = false;
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
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + (destroyed ? 1231 : 1237);
        result = prime * result + id;
        result = prime * result + ((physics == null) ? 0 : physics.hashCode());
        result = prime * result + ((view == null) ? 0 : view.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GameObject other = (GameObject) obj;
        if (body == null)
        {
            if (other.body != null) return false;
        }
        else if (!body.equals(other.body)) return false;
        if (destroyed != other.destroyed) return false;
        if (id != other.id) return false;
        if (physics == null)
        {
            if (other.physics != null) return false;
        }
        else if (!physics.equals(other.physics)) return false;
        if (view == null)
        {
            if (other.view != null) return false;
        }
        else if (!view.equals(other.view)) return false;
        return true;
    }

}
