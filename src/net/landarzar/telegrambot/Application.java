/**
 * 
 */
package net.landarzar.telegrambot;

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
		TelegramBotProperties tprop = new TelegramBotProperties();

		// Loading Properties;
		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filename = "config.properties";
			input = TelegramBotProperties.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
			} else {

				prop.load(input);

				if (prop.getProperty("SYSTEM_THREADED") != null) {
					tprop.SYSTEM_THREADED = Boolean.parseBoolean(prop.getProperty("SYSTEM_THREADED"));
				}
				// else {
				// System.err.println("SYSTEM_THREADED have to be set");
				// return;
				// }

				if (prop.getProperty("SYSTEM_TICK") != null) {
					tprop.SYSTEM_TICK = Integer.parseInt(prop.getProperty("SYSTEM_TICK"));
				}
				// else {
				// System.err.println("SYSTEM_TICK have to be set");
				// return;
				// }

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
				// else {
				// System.err.println("NET_UPDATE_INTERVAL have to be set");
				// return;
				// }

				if (prop.getProperty("NET_BASEURL") != null) {
					tprop.NET_BASEURL = prop.getProperty("NET_BASEURL");
				}
				// else {
				// System.err.println("NET_BASEURL have to be set");
				// return;
				// }
			}
		} finally {
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
			LandarzarBot bot = new LandarzarBot(tprop);
			

            ConsoleReader reader = new ConsoleReader();

            reader.setPrompt("prompt> ");
            reader.addCompleter(new StringsCompleter("help", "start", "status", "stop", "send", "quit", "exit", "clear"));
            
            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            while ((line = reader.readLine()) != null) {
                    out.println("======>\"" + line + "\"");
                
                out.flush();
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("clear")) {
                    reader.clearScreen();
                }
            }


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
