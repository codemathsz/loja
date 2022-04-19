package br.com.olimposistema.aipa.converter.jpa;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YearMonthDateJPAConverter implements AttributeConverter<java.time.YearMonth, java.sql.Timestamp> {

	  @Override
	  public java.sql.Timestamp convertToDatabaseColumn(java.time.YearMonth entityValue) {
	    return entityValue == null ? null : java.sql.Timestamp.valueOf(entityValue.atDay(1).atTime(0,0));
	  }

	  @Override
	  public java.time.YearMonth convertToEntityAttribute(java.sql.Timestamp dbValue) {
	    return dbValue == null ? null : YearMonth.from(
                Instant
                .ofEpochMilli(dbValue.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );
	  }
}
