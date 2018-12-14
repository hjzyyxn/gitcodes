package proxy;

public class Printer implements Printable {
	private String name;
	public Printer() {
		heavyJob("creating Printer instance");
	}
	
	public Printer(String name) {
		this.name = name;
		heavyJob("creating Printer instance (" + name + ") ");
	}

	@Override
	public void setPrinterName(String name) {
		// TODO Auto-generated method stub
		this.name = name;

	}

	@Override
	public String getPrinterName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void print(String string) {
		// TODO Auto-generated method stub
		System.out.println("=== " + name + " ===");
		System.out.println(string);

	}
	
	private void heavyJob(String msg) {
		System.out.print(msg);
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
			System.out.print(".");
		}
		System.out.println(" finish. ");
	}

}
