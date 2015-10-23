package application;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Physics {
	public GameObject gameObject;
	private Vector2 velocity;
	private int speed;
	private HashSet<Vector2> directionVectors;
	private Timer timer;
	private List<CallBack> calculations;

	public Physics(GameObject gO, int speed) {
		this.gameObject = gO;
		this.speed = speed;
		this.velocity = new Vector2(0, 0);
		this.calculations = new ArrayList<>();
		directionVectors = new HashSet<>();
		timer = new Timer();
		timer.start();
	}

	public void update() {
		double secondsSinceLast = timer.getDuration() / 1000;// compensating for
																// lag
		gameObject.body.increasePosition(velocity.getX() * secondsSinceLast, velocity.getY() * secondsSinceLast);
        
		timer.reset();

		for (CallBack cb : calculations) {
			cb.call();
		}

	}

	public void update(HashMap<Integer, GameObject> characters, ArrayList<Wall> walls) {
		double secondsSinceLast = timer.getDuration() / 1000;// compensating for
																// lag

		double newPositionX = gameObject.getBody().getPosition().getX() + (velocity.getX() * secondsSinceLast);
		double newPositionY = gameObject.getBody().getPosition().getY() + (velocity.getY() * secondsSinceLast);
		BoDCharacter tempChar = new BoDCharacter(0, new Point2D.Double(newPositionX, newPositionY), 50, 50, 200);

		boolean collision = false;
		for (GameObject cha : characters.values()) {
			if (CollisionTester.collisionCircleCircle(tempChar, cha)) {
				if (cha.getId() != gameObject.getId()) {
					collision = true;
					timer.reset();
					break;
				}
			}
		}
		for (Wall wall : walls) {
			// System.out.println(wall.getID() + " " + wall.getCenter().getX() +
			// "," + wall.getCenter().getY() + " "
			// + wall.getBody().getPosition().getX() + "," +
			// wall.getBody().getPosition().getY() + "" +
			// wall.getBody().getHeight() + "" + wall.getBody().getLength());
			if (CollisionTester.collisionCircleRectangle(tempChar, wall)) {
				collision = true;
				timer.reset();
			}
		}

		if (!collision) {
			gameObject.body.increasePosition(velocity.getX() * secondsSinceLast, velocity.getY() * secondsSinceLast);
			timer.reset();
		}

		for (CallBack cb : calculations) {
			cb.call();
		}

	}

	public void addCalculation(CallBack cb) {
		calculations.add(cb);
	}

    public Vector2 getVelolicity()
    {
        return velocity;
    }

    public void updateVelocity()
    {
        velocity = Vector2.averageVector(directionVectors.toArray(new Vector2[directionVectors.size()]));
        velocity.setMagnitude(speed);
    }

    public void setSpeed(int amount)
    {
        speed = amount;
    }

    public void addDirection(Vector2 direction)
    {
        if (!directionVectors.contains(direction))
        {
            directionVectors.add(direction);
            updateVelocity();
        }

    }

    public void removeDirection(Vector2 direction)
    {
        directionVectors.remove(direction);
        if (directionVectors.isEmpty())
        {
            velocity.setMagnitude(0);
        }
        updateVelocity();
    }

    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Physics other = (Physics) obj;
        if (calculations == null)
        {
            if (other.calculations != null) return false;
        }
        else if (!calculations.equals(other.calculations)) return false;
        if (directionVectors == null)
        {
            if (other.directionVectors != null) return false;
        }
        else if (!directionVectors.equals(other.directionVectors)) return false;
        if (speed != other.speed) return false;
        if (velocity == null)
        {
            if (other.velocity != null) return false;
        }
        else if (!velocity.equals(other.velocity)) return false;
        return true;
    }
    
	// public void addVelocity(Vector2 inputVelocity)
	// {
	//
	// if (Vector2.getAddedVectors(velocity, inputVelocity).getMagnitude() <
	// maxSpeed)
	// {
	// velocity.addVector(inputVelocity);
	// }
	//
	// }
}
