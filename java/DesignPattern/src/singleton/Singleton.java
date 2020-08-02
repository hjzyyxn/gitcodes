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

/*public class Singleton {
	private static volatile Singleton singleton;
	private Singleton() {}
	public static Singleton getInstance() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}*/

/*public class Singleton {
	private Singleton() {}
	
	private static class SingletonInstance {
		private static final Singleton INSTANCE = new Singleton();
	}
	
	public static Singleton getInstance() {
		return SingletonInstance.INSTANCE;
	}
}*/
