/**
 * 
 */
package net.landarzar.telegrambot;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.json.JsonObject;

import org.omg.CORBA.portable.ApplicationException;

import net.landarzar.telegram.TelegramBot;
import net.landarzar.telegram.TelegramBotProperties;
import net.landarzar.telegram.model.methodes.AnswerInlineQuery;
import net.landarzar.telegram.model.methodes.GetMe;
import net.landarzar.telegram.model.methodes.SendMessage;
import net.landarzar.telegram.model.types.CallbackQuery;
import net.landarzar.telegram.model.types.ChosenInlineResult;
import net.landarzar.telegram.model.types.InlineKeyboardButton;
import net.landarzar.telegram.model.types.InlineKeyboardMarkup;
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
//				else {
//					System.err.println("SYSTEM_THREADED have to be set");
//					return;
//				}

				if (prop.getProperty("SYSTEM_TICK") != null) {
					tprop.SYSTEM_TICK = Integer.parseInt(prop.getProperty("SYSTEM_TICK"));
				} 
//				else {
//					System.err.println("SYSTEM_TICK have to be set");
//					return;
//				}

				if (prop.getProperty("NET_BOT_ID") != null) {
					tprop.NET_BOT_ID = Integer.parseInt(prop.getProperty("NET_BOT_ID"));
				} else {
					System.err.println("NET_BOT_ID have to be set");
					return;
				}

				if (prop.getProperty("NET_BOT_TOKEN") != null) {
					tprop.NET_BOT_TOKEN = prop.getProperty("NET_BOT_TOKEN");
				} else {
					System.err.println("NET_BOT_TOKEN have to be set");
					return;
				}

				if (prop.getProperty("NET_UPDATE_INTERVAL") != null) {
					tprop.NET_UPDATE_INTERVAL = Integer.parseInt(prop.getProperty("NET_UPDATE_INTERVAL"));
				} 
//				else {
//					System.err.println("NET_UPDATE_INTERVAL have to be set");
//					return;
//				}

				if (prop.getProperty("NET_BASEURL") != null) {
					tprop.NET_BASEURL = prop.getProperty("NET_BASEURL");
				} 
//				else {
//					System.err.println("NET_BASEURL have to be set");
//					return;
//				}
			}
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		TiKzBot tikzBot = new TiKzBot(tprop);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
			iqra.reply_markup = new InlineKeyboardMarkup();
			LinkedList<InlineKeyboardButton> ll = new LinkedList<>();
			ll.add(new InlineKeyboardButton("Yes", "1"));
			ll.add(new InlineKeyboardButton("No", "2"));
			LinkedList<InlineKeyboardButton> ll2 = new LinkedList<>();
			ll2.add(new InlineKeyboardButton("Yes", "3"));
			ll2.add(new InlineKeyboardButton("No", "4"));
			iqra.reply_markup.field.add(ll);
			iqra.reply_markup.field.add(ll2);
			answer.results.add(iqra);

			// iqra = new InlineQueryResultArticle();
			// iqra.id = "1";
			// iqra.title = "Test Text, too";
			// iqra.input_message_content = new InputTextMessageContent("Iam a
			// text, tooo");
			// answer.results.add(iqra);

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
		System.out.println("ChosenInLineResult" + result.query);
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
		System.out.println("CallbackQuery" + query.toString());
	}

}
