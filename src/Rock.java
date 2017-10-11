
import java.util.Random;

public class Rock {
	
	private int xPos;
	private int yPos;
	Random rnd = new Random();
	
	public Rock() {
		generateLocation();
	}
	
	public Rock(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void generateLocation() {
		int temp = rnd.nextInt(550);
		setxPos(round(temp));
		
		
		temp = rnd.nextInt(300);
		setyPos(round(temp));
	}
	
	public int round(int x) {
		return (x+49)/50 * 50;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public int hashCode() {
		final int prime = 5;
		int result = 1;
		result = prime * result + this.xPos;
		result = prime * result + this.yPos;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rock other = (Rock) obj;
		if (xPos != other.xPos)
			return false;
		if (yPos != other.yPos)
			return false;
		return true;
	}
	
	

}
