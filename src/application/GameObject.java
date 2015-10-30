package application;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameObject implements Observable
{

    protected Body body;
    protected Physics physics;
    protected View view;
    protected Health health;
    protected Weapon weapon;
    private int id;
    private boolean destroyed;
    private Set<Observer> observers;

    public GameObject(GameObjectDTO goDTO)
    {

        Point2D newPoint = new Point2D(goDTO.getBody().get_point().getX(), goDTO.getBody().get_point().getY());

        Body.Type bodyType = null;
        BodyDTO bodyDTO = goDTO.getBody();
        if (bodyDTO.get_type() == bodyDTO.getCIRCLE())
        {
            bodyType = Body.Type.CIRCLE;
        }
        else if (bodyDTO.get_type() == bodyDTO.getRECTANGLE())
        {
            bodyType = Body.Type.RECTANGLE;
        }
        this.body = new Body(this, newPoint, 50, 50, bodyType);
        this.id = goDTO.getId();
        observers = new HashSet<Observer>();

    }

    public GameObject(GameObject go)
    {
        this.id = go.getId();
        if (go.physics != null)
        {
            this.physics = new Physics(this, go.physics.getTopspeed());
            this.physics.setVelocity(new Vector2(go.physics.getVelocity().getX(), go.physics.getVelocity().getY()));
        }
        if (go.body != null)
        {
            this.body = new Body(this, go.body.getPosition(), go.body.getHeight(), go.body.getWidth(),
                    go.body.getType());
        }
        observers = new HashSet<Observer>();
    }

    public GameObject(int id, Body.Type type)
    {
        this.id = id;
        Point2D newPoint = new Point2D(100, 100);
        this.body = new Body(this, newPoint, 50, 50, type);
        observers = new HashSet<Observer>();
    }

    public GameObject(int id)
    {
        this.id = id;
        observers = new HashSet<Observer>();
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

    public void updateWithCollision(GraphicsContext gc, ConcurrentMap<Integer, GameObject> objects, Image image)
    {
        if (physics != null)
        {
            physics.updateWithCollision(objects);
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
        destroyed = true; // possibly send out callback to listeners (which would be map)
    }

    public Weapon getWeapon()
    {
        return weapon;
    }

    public void setWeapon(Weapon weapon)
    {
        this.weapon = weapon;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GameObject other = (GameObject) obj;
        if (id != other.id) return false;
        return true;
    }

    @Override
    public void notifyObservers(Object arg)
    {
        for(Observer obs : observers)
        {
            obs.update(this, null);
        }
        
    }

    @Override
    public void notifyObservers()
    {
        // TODO Auto-generated method stub
        for(Observer obs : observers)
        {
            obs.update(this, null);
        }
    }

    @Override
    public void registerObserver(Observer obs)
    {
        observers.add(obs);
        
    }

    @Override
    public void unregisterObserver(Observer obs)
    {
        observers.remove(obs);
        
    }

}
