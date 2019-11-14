import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 8477298665346108156L;
	private Color team;
	private Point2D position;
	private String login;
	
	public Color getTeam(){ return team; }
	public void setTeam(Color team){ this.team = team; }
	public Point2D getPosition(){ return position; }
	public void setPosition(Point2D position){ this.position = position; }
	public String getLogin(){ return login; }
	public void setLogin(String login){ this.login = login; }
	
	public Player(String login, Color team, Point2D position) {
		this.login = login;
		this.team = team;
		this.position = position;
	}

}
