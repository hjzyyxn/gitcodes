package singleton;

public class Singleton {
	private static Singleton singleton = new Singleton();
	private Singleton() {
		System.out.println("create a new instance.");
	}
	public static Singleton getInstance() {
		return singleton;
	}
}
