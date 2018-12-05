package chainOfResponsibility;

public class OddSupport extends Support {
	public OddSupport(String name) {
		super(name);
	}

	@Override
	protected boolean resolve(Trouble trouble) {
		// TODO Auto-generated method stub
		if (trouble.getNumber() % 2 == 1) {
			return true;
		} else {
			return false;
		}
		
	}

}
