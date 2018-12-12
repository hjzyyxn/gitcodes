package state;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SafeFrame extends Frame implements ActionListener, Context {
	private TextField textClock = new TextField(60);
	private TextArea textScreen = new TextArea(10, 60);
	private Button buttonUse = new Button("use jinku");
	private Button buttonAlarm = new Button("push alarm");
	private Button buttonPhone = new Button("call");
	private Button buttonExit = new Button("exit");
	
	private State state = DayState.getInstance();
	
	public SafeFrame(String title) {
		super(title);
		setBackground(Color.lightGray);
		setLayout(new BorderLayout());
		add(textClock, BorderLayout.NORTH);
		textClock.setEditable(false);
		add(textScreen, BorderLayout.CENTER);
		textScreen.setEditable(false);
		Panel panel = new Panel();
		panel.add(buttonUse);
		panel.add(buttonAlarm);
		panel.add(buttonPhone);
		panel.add(buttonExit);
		add(panel, BorderLayout.SOUTH);
		pack();
		show();
		buttonUse.addActionListener(this);
		buttonAlarm.addActionListener(this);
		buttonPhone.addActionListener(this);
		buttonExit.addActionListener(this);
	}

	@Override
	public void setClock(int hour) {
		// TODO Auto-generated method stub
		String clockstring = " now time ";
		if (hour < 10) {
			clockstring += "0" + hour + ":00";
		} else {
			clockstring += hour + ":00";
		}
		System.out.println(clockstring);
		textClock.setText(clockstring);
		state.doClock(this, hour);

	}

	@Override
	public void changeState(State state) {
		// TODO Auto-generated method stub
		System.out.println("from " + this.state + " to " + state +".");
		this.state = state;

	}

	@Override
	public void callSecurityCenter(String msg) {
		// TODO Auto-generated method stub
		textScreen.append("call! " + msg + "\n");

	}

	@Override
	public void recordLog(String msg) {
		// TODO Auto-generated method stub
		textScreen.append("record... " + msg + "\n");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.toString());
		if (e.getSource() == buttonUse) {
			state.doUse(this);
		} else if (e.getSource() == buttonAlarm) {
			state.doAlarm(this);
		} else if (e.getSource() == buttonPhone) {
			state.doPhone(this);
		} else if (e.getSource() == buttonExit) {
			System.exit(0);
		} else {
			System.out.println("?");
		}

	}

}
