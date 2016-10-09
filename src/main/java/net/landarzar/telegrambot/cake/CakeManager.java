/**
 * 
 */
package net.landarzar.telegrambot.cake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * @author Kai Sauerwald
 *
 */
public class CakeManager
{
	ArrayList<Kuchenrezept> rezepte = null;
	String path;
	boolean properly = false;

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

	public List<Kuchenrezept> getRezepte()
	{
		return rezepte;
	}

	public boolean isProper()
	{
		return properly;
	}

	public void loadData() throws Exception
	{
		ArrayList<Kuchenrezept> rezepte = new ArrayList<>();

		InputStream is = null;

		try {
			File file = new File(path);
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
				if (!(obj.containsKey("img_file") && obj.containsKey("name") && obj.containsKey("description") && obj.containsKey("source"))) {
					log.warning("Entry in CakeDB wich is incomplete");
					continue;
				}

				rezept.img_file = obj.getString("img_file");
				rezept.name = obj.getString("name");
				rezept.description = obj.getString("description");
				rezept.source = obj.getString("source");
				if (obj.containsKey("sourceLink"))
					rezept.sourceLink = obj.getString("sourceLink");
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
