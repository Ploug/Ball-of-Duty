package application;

import org.datacontract.schemas._2004._07.ball_of_duty_server.ServerGameObject;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject extends ServerGameObject
{
    protected Body body;
    protected Physics physics;
    protected View view;
    private int id;
    private ServerGameObject parent;

    public GameObject(ServerGameObject parent)
    {
        this.id = parent.getId();
        this.body.setPosition(parent.getBody().getPosition());
        
    }
    public GameObject(int id)
    {
        this.id = id;
        
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

    public int getID()
    {
        return id;
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
