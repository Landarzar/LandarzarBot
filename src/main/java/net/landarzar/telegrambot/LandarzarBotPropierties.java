/**
 * 
 */
package net.landarzar.telegrambot;

import java.util.logging.Level;

import net.landarzar.telegram.TelegramBotProperties;

/**
 * @author Kai Sauerwald
 *
 */
public class LandarzarBotPropierties extends TelegramBotProperties
{

	/***
	 * Gibt an in welchem LOGGING modus das ganze betrieben wird.
	 */
	public java.util.logging.Level LOG_LEVEL = Level.INFO;
	


	/***
	 * Gibt an unter welchen Pfad der Kuchen Datenbank zu finden ist.
	 */
	public String CAKE_DB_PATH = "/home/kai/git/telegram/kuchen/";
}
