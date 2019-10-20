package errorlogs;

import java.io.FileOutputStream;
import java.io.IOException;

public class Logs {
	public static void insertException(Exception e) {
		try {
			FileOutputStream fOutputStream = new FileOutputStream("logs.txt", true);
			fOutputStream.write((e.toString()+"\n").getBytes());
			fOutputStream.flush();
			fOutputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
