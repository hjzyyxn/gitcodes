package factoryidcard;
import factory.*;

public class IDCard extends Product {
	private String owner;
	private int serial;
	IDCard(String owner, int serial) {
		System.out.println("m6ake" + owner + "(" + serial + ")" + "IDCard.");
		this.owner = owner;
		this.serial = serial;
	}
	public void use() {
		System.out.println("use" + owner + "(" + serial + ")" + "IDCard.");
	}
	public String getOwner() {
		return owner;
	}
	public int getSerial() {
		return serial;
	}

}
