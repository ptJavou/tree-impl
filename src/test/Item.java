package test;

public class Item implements Comparable<Item> {

	private int id;
	private String name;
	private double value;
	
	
	public Item(int id, String name, double value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	@Override
	public int compareTo(Item o) {
		if(this.id == o.getId())
			return 0;
		else if(this.id < o.getId())
			return -1;
				
		return 1;
	}

	

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", value=" + value + "]";
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public double getValue() {
		return value;
	}



	public void setValue(double value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
