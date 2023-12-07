package vttp.ssf.day19lecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day19lectureApplication implements CommandLineRunner { // Must know how to do.

	public static void main(String[] args) {
		SpringApplication.run(Day19lectureApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// To be read.
		String filePathName = "/Users/khairulimran/data/employees.json"; // We are hard coding this, but you can pass it as an argument too.

		// Read the file, and make it into a string.
		File file = new File(filePathName);

		if (!file.exists()) {
			System.err.print("File not found.");
			System.exit(1);
		}

		InputStream is = new FileInputStream(file);
		// BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); From old practice. - just for reference.

		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line = "";
			while((line = br.readLine()) != null) { // Read the file until it is complete.
				resultStringBuilder.append(line);
			}
		}

		String data = resultStringBuilder.toString();
		System.out.println(data);
	}

}
