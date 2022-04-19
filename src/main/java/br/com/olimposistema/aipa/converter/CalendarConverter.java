package br.com.olimposistema.aipa.converter;
import java.util.Calendar;
import javax.enterprise.inject.Specializes;
import br.com.caelum.vraptor.Convert;
import br.com.olimposistema.aipa.service.ConvertDateService;
import br.com.olimposistema.aipa.service.Util;

//@Alternative
//@Priority(Interceptor.Priority.APPLICATION)
@SuppressWarnings("deprecation")
@Specializes
@Convert(Calendar.class)
public class CalendarConverter extends br.com.caelum.vraptor.converter.CalendarConverter{

    @Override
    public Calendar convert(String value, Class<? extends Calendar> type) {
    	if(Util.isNullOrEmpty(value)) return null;
    	try {
    		return ConvertDateService.toCalendar(value);
    	}catch (Exception e) {
    		return super.convert(value, type);
		}
    }
}