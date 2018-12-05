package chainOfResponsibility;

public class LimitSupport extends Support {
	private int limit;
	public LimitSupport(String name, int limit) {
		super(name);
		this.limit = limit;
	}

	@Override
	protected boolean resolve(Trouble trouble) {
		// TODO Auto-generated method stub
		if (trouble.getNumber() < limit) {
			return true;
		} else {
			return false;
		}
	}

}
