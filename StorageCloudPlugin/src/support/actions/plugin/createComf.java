package support.actions.plugin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class createComf {

	public static String DEFAULT_CONFIG_PATH = "conf.properties";

	public static boolean writeComfFile(String Assec) {

		Properties pro = new Properties();
		String oper_syste = System.getProperty("os.name");

		if (oper_syste.contains("Windows")) {
			oper_syste = "Windows";
		}

		pro.setProperty("operating.system", oper_syste);
		pro.setProperty("storage.cloud.token", Assec);
		FileWriter write = null;
		try {
			write = new FileWriter(DEFAULT_CONFIG_PATH);
			pro.store(write, "Author :panos");
			write.close();

			return true;

		} catch (IOException error) {
			// TODO Auto-generated catch block
			error.printStackTrace();
			return false;
		}

	}
}
