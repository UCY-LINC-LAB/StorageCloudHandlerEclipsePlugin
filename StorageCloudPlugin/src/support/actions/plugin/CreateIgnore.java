package support.actions.plugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CreateIgnore {

	public Boolean UpdateIgnore(String ignoreName, ArrayList<String> localPaths) {
		// String fileName=ignoreName;
		PrintWriter out = null;

		ReadIgnore ri = new ReadIgnore();
		if (ri.Read(ignoreName)) {
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter(ignoreName, true)));
				for (int i = 0; i < localPaths.size(); i++) {
					if (!ri.filesIgnore.containsKey(localPaths.get(i))) {
						out.println(localPaths.get(i));
					}
				}

				out.close();
			} catch (IOException e) {
				System.out.println("DEN YPARXEI");
			}
		} else {
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter(ignoreName, true)));
				for (int i = 0; i < localPaths.size(); i++) {
					if (!ri.filesIgnore.containsKey(localPaths.get(i))) {
						out.println(localPaths.get(i));
					}
				}

				out.close();
			} catch (IOException e) {
				System.out.println("DEN YPARXEI");
			}
		}

		return null;
	}

	public Boolean CreateNew(String ignoreName, ArrayList<String> localPaths) {

		try {

			PrintWriter in = new PrintWriter(ignoreName, "UTF-8");
			for (int i = 0; i < localPaths.size(); i++) {
				in.println(localPaths.get(i));
			}
			in.close();

			return true;
		} catch (IOException e) {
			return false;
		}

	}

}
