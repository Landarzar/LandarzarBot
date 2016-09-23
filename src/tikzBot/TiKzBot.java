/**
 * 
 */
package tikzBot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.json.JsonObject;

import net.landarzar.telegram.TelegramBot;
import net.landarzar.telegram.TelegramBotProperties;
import net.landarzar.telegram.model.methodes.AnswerInlineQuery;
import net.landarzar.telegram.model.methodes.GetMe;
import net.landarzar.telegram.model.methodes.SendMessage;
import net.landarzar.telegram.model.types.CallbackQuery;
import net.landarzar.telegram.model.types.ChosenInlineResult;
import net.landarzar.telegram.model.types.InlineQuery;
import net.landarzar.telegram.model.types.Message;
import net.landarzar.telegram.model.types.Update;
import net.landarzar.telegram.model.types.inline.InlineQueryResult;
import net.landarzar.telegram.model.types.inline.InlineQueryResultArticle;
import net.landarzar.telegram.model.types.inline.InputTextMessageContent;

/**
 * @author Kai Sauerwald
 *
 */
public class TiKzBot extends TelegramBot
{

	public static void main(String[] args)
	{
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT  %5$s%6$s%n");
		Logger log = Logger.getLogger("TelegramBot");
		log.setLevel(Level.ALL);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		handler.setLevel(Level.ALL);
		log.addHandler(handler);

		TelegramBotProperties tprop = new TelegramBotProperties(275871224, "AAGgkiiX0ddEQMH_RhNR5biZUoXpv8ciEjA");

		// Loading Properties;
		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filename = "config.properties";
			input = TelegramBotProperties.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
			} else {

				// // load a properties file from class path, inside static
				// method
				// prop.load(input);
				//
				// // get the property value and print it out
				// System.out.println(prop.getProperty("database"));
				// System.out.println(prop.getProperty("dbuser"));
				// System.out.println(prop.getProperty("dbpassword"));
			}
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		TiKzBot wb2 = new TiKzBot(tprop);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wb2.conn.enque(new GetMe((mr, j) -> {
			System.out.println(j.toString());
		}));
	}

	/**
	 * @param prop
	 *            Einstellungen für den Server
	 */
	public TiKzBot(TelegramBotProperties prop)
	{
		super(prop);

		this.StartBot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.landarzar.wb2.TelegramBot#onMessage(net.landarzar.wb2.model.Message,
	 * net.landarzar.wb2.model.Update)
	 */
	@Override
	protected void onMessage(Message msg, Update update)
	{
		// TODO Auto-generated method stub
		System.out.println("[Message] " + msg.message_id + " " + msg.from.first_name);

		SendMessage sm = new SendMessage(Long.toString(msg.chat.id), "Hello World");
		this.methodEnqueue(sm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.landarzar.wb2.TelegramBot#onEditedMessage(net.landarzar.wb2.model.
	 * Message, net.landarzar.wb2.model.Update)
	 */
	@Override
	protected void onEditedMessage(Message msg, Update update)
	{
		System.out.println("[Edited Message] " + msg.message_id + " " + msg.from.first_name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.landarzar.wb2.TelegramBot#onInlineQuery(net.landarzar.wb2.model.
	 * InlineQuery, net.landarzar.wb2.model.Update)
	 */
	@Override
	protected void onInlineQuery(InlineQuery query, Update update)
	{
		System.out.println("[InlineQuery] " + query.id + " from " + query.from.first_name);

		if (!query.query.isEmpty()) {
			AnswerInlineQuery answer = new AnswerInlineQuery((mr, b) -> {
				if (!b) {
					System.out.println("Error Code: " + mr.getErrorCode() + ", Beschreibung: " + mr.getErrorDescrition());
				}
			});
			answer.inline_query_id = query.id;

			InlineQueryResultArticle iqra = new InlineQueryResultArticle();
			iqra.id = "0";
			iqra.title = "Test Text";
			iqra.input_message_content = new InputTextMessageContent("Iam a text");
			answer.results.add(iqra);
			iqra = new InlineQueryResultArticle();
			iqra.id = "1";
			iqra.title = "Test Text, too";
			iqra.input_message_content = new InputTextMessageContent("Iam a text, tooo");
			answer.results.add(iqra);

			this.methodEnqueue(answer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.landarzar.wb2.TelegramBot#onChosenInlineResult(net.landarzar.wb2.
	 * model.ChosenInlineResult, net.landarzar.wb2.model.Update)
	 */
	@Override
	protected void onChosenInlineResult(ChosenInlineResult result, Update update)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.landarzar.wb2.TelegramBot#onCallbackQuery(net.landarzar.wb2.model.
	 * CallbackQuery, net.landarzar.wb2.model.Update)
	 */
	@Override
	protected void onCallbackQuery(CallbackQuery query, Update update)
	{
		// TODO Auto-generated method stub

	}

}
