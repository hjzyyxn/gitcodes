package formatter;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

public class MyFormatterRegistrar implements FormatterRegistrar {
	
	private String datePattern;
	public MyFormatterRegistrar(String datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public void registerFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		registry.addFormatter(new LocalDateFormatter(datePattern));

	}

}
