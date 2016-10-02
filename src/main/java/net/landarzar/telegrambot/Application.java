/**
 * 
 */
package net.landarzar.telegrambot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;
import net.landarzar.telegram.TelegramBotProperties;

/**
 * @author Kai Sauerwald
 *
 */
public class Application
{
	public static TelegramBotProperties loadProperties() throws IOException
	{
		LandarzarBotPropierties tprop = new LandarzarBotPropierties();

		// Loading Properties;
		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filename = "config.properties";
			FileInputStream fis = new FileInputStream(filename);
			input = fis;
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
			} else {

				prop.load(input);

				if (prop.getProperty("SYSTEM_THREADED") != null) {
					tprop.SYSTEM_THREADED = Boolean.parseBoolean(prop.getProperty("SYSTEM_THREADED"));
				}

				if (prop.getProperty("SYSTEM_TICK") != null) {
					tprop.SYSTEM_TICK = Integer.parseInt(prop.getProperty("SYSTEM_TICK"));
				}

				if (prop.getProperty("NET_BOT_ID") != null) {
					tprop.NET_BOT_ID = Integer.parseInt(prop.getProperty("NET_BOT_ID"));
				} else {
					throw new InvalidParameterException(("NET_BOT_ID have to be set"));
				}

				if (prop.getProperty("NET_BOT_TOKEN") != null) {
					tprop.NET_BOT_TOKEN = prop.getProperty("NET_BOT_TOKEN");
				} else {
					throw new InvalidParameterException(("NET_BOT_TOKEN have to be set"));
				}

				if (prop.getProperty("NET_UPDATE_INTERVAL") != null) {
					tprop.NET_UPDATE_INTERVAL = Integer.parseInt(prop.getProperty("NET_UPDATE_INTERVAL"));
				}

				if (prop.getProperty("NET_BASEURL") != null) {
					tprop.NET_BASEURL = prop.getProperty("NET_BASEURL");
				}

				if (prop.getProperty("LOG_LEVEL") != null) {
					tprop.LOG_LEVEL = Level.parse(prop.getProperty("LOG_LEVEL"));
				}
			}
		} finally {
			if (input != null)
				input.close();
		}

		return tprop;
	}

	public static void main(String[] args)
	{
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT  %5$s%6$s%n");
		Logger log = Logger.getLogger("TelegramBot");
		log.setLevel(Level.ALL);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		handler.setLevel(Level.ALL);
		log.addHandler(handler);

		try {
			TelegramBotProperties tprop = loadProperties();
			tprop.SYSTEM_THREADED = true;
			LandarzarBot bot = new LandarzarBot(tprop);

			bot.start();

			// ConsoleReader reader = new ConsoleReader();
			//
			// reader.setPrompt("prompt> ");
			// reader.addCompleter(new StringsCompleter("help", "start",
			// "status", "stop", "send", "quit", "exit", "clear"));
			//
			// String line;
			// PrintWriter out = new PrintWriter(reader.getOutput());
			//
			// while ((line = reader.readLine()) != null) {
			//
			// if (line.equalsIgnoreCase("help")) {
			// out.println("Verfügbare Befehle:");
			// out.println("help:\tZeigt diese Hilfe");
			// out.println("start:\tStartet den Server");
			// out.println("status:\tZeigt den Status des Servers an");
			// out.println("stop:\tStoppt den Server");
			// out.println("send:\tSendet eine Nachricht");
			// out.println("quit:\tBeendet das Programm");
			// out.println("exit:\tBeendet das Programm");
			// out.println("clear:\tConsole leeren");
			// out.flush();
			// }
			// else if (line.equalsIgnoreCase("status")) {
			// out.println("Alive: " + bot.isRunning());
			// out.flush();
			// }
			// else if (line.equalsIgnoreCase("start")) {
			// bot.start();
			// }
			// else if (line.equalsIgnoreCase("stop")) {
			// bot.stop();
			// }
			// else if (line.equalsIgnoreCase("quit") ||
			// line.equalsIgnoreCase("exit")) {
			// break;
			// }
			// else if (line.equalsIgnoreCase("clear")) {
			// reader.clearScreen();
			// } else {
			// out.println("======> Unbekannt: \"" + line + "\"");
			// out.flush();
			// }
			// }

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
