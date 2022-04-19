package br.com.olimposistema.aipa.converter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.enterprise.context.Dependent;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import br.com.olimposistema.aipa.service.ConvertDateService;

@Dependent
public class CustomCalendarGsonConverter implements JsonDeserializer<Calendar>, JsonSerializer<Calendar> {

	@Override
	public JsonElement serialize(Calendar calendar, Type typeOfSrc, JsonSerializationContext context) {
		String dateString = new SimpleDateFormat(ConvertDateService.formatIso).format(ConvertDateService.toDate(calendar)); 
		return new JsonPrimitive(dateString); 
	}

	@Override
	public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		try { 
			if (json.isJsonNull()) return null;
			return ConvertDateService.toCalendar(json.getAsString());
		} catch (ParseException e) { 
			throw new JsonSyntaxException(json.getAsString(), e); 
		} 
	}

}
