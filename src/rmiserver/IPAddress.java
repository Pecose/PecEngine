package rmiserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class IPAddress {

	public static String getLocalName() {
		return getLocalHost().getHostName();
	}
	
	public static String getLocalIP() throws IOException {
		URL url = new URL("http://bot.whatismyipaddress.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		return in.readLine().trim();
	}
	
	public static InetAddress getLocalHost() {
		try {
			return InetAddress.getLocalHost();
		} catch (Exception e) { e.getStackTrace(); }
		return null;
	}
}
