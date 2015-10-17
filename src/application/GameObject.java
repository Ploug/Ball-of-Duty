package application;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject
{
    protected Body body;
    protected Physics physics;
    protected View view;

    public GameObject()
    {

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
