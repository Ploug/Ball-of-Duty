package application;

import java.util.ArrayList;
import java.util.HashMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameObject
{

    protected Body body;
    protected Physics physics;
    protected View view;
    protected Health health;
    protected Weapon weapon;
    private int id;
    private boolean destroyed;

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

    }

    public GameObject(GameObject go)
    {
        this.id = go.getId();
        if (go.physics != null)
        {
            this.physics = new Physics(this, go.getPhysics().getTopspeed());
            this.physics.setVelocity(new Vector2(go.getPhysics().getVelocity().getX(), go.getPhysics().getVelocity().getY()));
        }
        if (go.body != null)
        {
            this.body = new Body(this, go.getBody().getPosition(), go.getBody().getHeight(), go.getBody().getWidth(),
                    go.getBody().getType());
        }
    }

    public GameObject(int id, Body.Type type)
    {
        this.id = id;
        Point2D newPoint = new Point2D(100, 100);
        this.body = new Body(this, newPoint, 50, 50, type);
    }

    public GameObject(int id)
    {
        this.id = id;
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

    public void update(GraphicsContext gc, HashMap<Integer, GameObject> objects, Image image)
    {
        if (physics != null)
        {
            physics.update(objects);
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

}
