package application;


import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain.ServerGameObject;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject extends ServerGameObject
{
    protected Body body;
    protected Physics physics;
    protected View view;

    public GameObject(ServerGameObject parent)
    {
        this.body = new Body(this, parent.getBody().getPosition());
        this.setId(parent.getId());
        
    }
    public GameObject(int id)
    {
        this.setId(id);
        
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
