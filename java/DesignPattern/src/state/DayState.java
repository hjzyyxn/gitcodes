package state;

public class DayState implements State {
	private static DayState singleton = new DayState();
	private DayState() {
		
	}
	public static State getInstance() {
		return singleton;
	}

	@Override
	public void doClock(Context context, int hour) {
		// TODO Auto-generated method stub
		if (hour < 9 || 17 <= hour) {
			context.changeState(NightState.getInstance());
		}
	}

	@Override
	public void doUse(Context context) {
		// TODO Auto-generated method stub
		context.recordLog("use jinku(day)");

	}

	@Override
	public void doAlarm(Context context) {
		// TODO Auto-generated method stub
		context.callSecurityCenter("push bell(day)");

	}

	@Override
	public void doPhone(Context context) {
		// TODO Auto-generated method stub
		context.callSecurityCenter("call(day)");

	}
	public String toString() {
		return "[day]";
	}

}
