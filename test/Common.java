import java.util.Calendar;

import org.junit.Test;

public class Common {
	@Test
	public void calendar(){
		Calendar c = Calendar.getInstance();
		c.set(2017, 1-1, 6, 0, 0, 0);
		System.out.println(c.getTime());
		System.out.println(Calendar.getInstance().getTime());
		System.out.println(c.after(Calendar.getInstance()));
	}
}
