/**
 * 
 */
package net.landarzar.telegrambot.cake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import net.landarzar.telegram.model.methodes.Method;
import net.landarzar.telegram.model.methodes.SendMessage;
import net.landarzar.telegram.model.methodes.SendPhoto;
import net.landarzar.telegram.model.types.Message;
import net.landarzar.telegram.model.types.Update;

/**
 * @author Kai Sauerwald
 *
 */
public class CakeManager
{
	ArrayList<Kuchenrezept> rezepte = null;
	String path;
	boolean properly = false;
	Random rnd = new Random();

	Logger log = Logger.getLogger("TelegramBot");

	/**
	 * 
	 */
	public CakeManager(String path)
	{
		this.path = path;
	}

	public Kuchenrezept getRezept(String name)
	{
		if (rezepte == null)
			return null;

		for (Kuchenrezept r : rezepte) {
			if (r.name.equals(name))
				return r;
		}

		return null;
	}

	public String getPath()
	{
		return path;
	}

	public List<Kuchenrezept> getRezepte()
	{
		return rezepte;
	}

	public boolean isProper()
	{
		return properly;
	}

	public List<Method> handleCakeRequest(Message msg, Update update)
	{
		ArrayList<Method> m = new ArrayList<>();

		if (rezepte.size() != 0) {
			String[] strs = msg.text.split(" ", 1);

			Kuchenrezept rzpt = rezepte.get(rnd.nextInt(rezepte.size()));

			m.add(new SendMessage(Long.toString(msg.chat.id), rzpt.name));

			SendPhoto pm = new SendPhoto(Long.toString(msg.chat.id), "NOMNOMNOM!!");
			pm.photo_file = new File(getPath() + "/" + rzpt.img_file);

			m.add(pm);
		} else {

			m.add(new SendMessage(Long.toString(msg.chat.id), "Heute gibts keinen Kuchen"));
		}

		return m;
	}

	public void loadData() throws Exception
	{
		ArrayList<Kuchenrezept> rezepte = new ArrayList<>();

		InputStream is = null;

		try {
			File file = new File(path + "/kuchen.json");
			if (file == null || !file.exists())
				throw new FileNotFoundException(path);
			is = new FileInputStream(file);
			JsonReader reader = Json.createReader(is);

			JsonArray array = reader.readArray();

			if (array == null)
				throw new Exception("Filecontent for Kuchendata invalid");

			for (JsonValue jsonValue : array) {
				if (!(jsonValue instanceof JsonObject)) {
					log.warning("Get Invalid Entry in CakeDB");
					continue;
				}

				JsonObject obj = (JsonObject) jsonValue;

				Kuchenrezept rezept = new Kuchenrezept();
				if (!obj.containsKey("img_file")) {
					log.warning("Entry in CakeDB wich is incomplete (img_file)");
					continue;
				}
				if (!obj.containsKey("name")) {
					log.warning("Entry in CakeDB wich is incomplete (name)");
					continue;
				}
				if (!obj.containsKey("description")) {
					log.warning("Entry in CakeDB wich is incomplete (description)");
					continue;
				}
				if (!obj.containsKey("source")) {
					log.warning("Entry in CakeDB wich is incomplete (source)");
					continue;
				}

				rezept.img_file = obj.getString("img_file");
				rezept.name = obj.getString("name");
				rezept.description = obj.getString("description");
				rezept.source = obj.getString("source");
				if (obj.containsKey("sourceLink"))
					rezept.sourceLink = obj.getString("sourceLink");

				rezepte.add(rezept);
			}
		} catch (Exception e) {
			properly = false;
			throw e;
		} finally {
			if (is != null)
				is.close();
		}

		this.rezepte = rezepte;
		properly = true;
	}

}
