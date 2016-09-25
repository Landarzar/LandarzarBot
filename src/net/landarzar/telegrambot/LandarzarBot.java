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
public class LandarzarBot extends TelegramBot
{
	/**
	 * @param prop
	 *            Einstellungen für den Server
	 */
	public LandarzarBot(TelegramBotProperties prop)
	{
		super(prop);
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
	
	public void start(){
		super.StartBot();
	}
	

	public void stop(){
		super.StopBot();
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
