package th.co.nxp.framework.common.persistence.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*
 * @Author: Taechapon Himarat (Su)
 * @Create: Jul 21, 2018
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
	
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return Optional.ofNullable(localDateTime)
			.map(Timestamp::valueOf)
			.orElse(null);
	}
	
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		return Optional.ofNullable(timestamp)
			.map(Timestamp::toLocalDateTime)
			.orElse(null);
	}
	
}
