public class Position {
	private String nature;
	private int coodinateX;
	private int coodinateY;
	
	public Position(String nature, int coodinateX, int coodinateY)
	{
		this.nature = nature;
		this.coodinateX = coodinateX;
		this.coodinateY = coodinateY;
	}	
	
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public int getCoodinateX() {
		return coodinateX;
	}

	public void setCoodinateX(int coodinateX) {
		this.coodinateX = coodinateX;
	}

	public int getCoodinateY() {
		return coodinateY;
	}

	public void setCoodinateY(int coodinateY) {
		this.coodinateY = coodinateY;
	}	
}
