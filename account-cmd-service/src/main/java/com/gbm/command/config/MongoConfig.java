package com.gbm.command.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.core.convert.converter.Converter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoConfig {

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(
				List.of(new OffsetDateTimeReadConverter(), new OffsetDateTimeWriteConverter()));
	}

	public static class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {
		@Override
		public OffsetDateTime convert(Date date) {
			return date.toInstant().atOffset(ZoneOffset.UTC);
		}
	}

	public static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {
		@Override
		public Date convert(OffsetDateTime offsetDateTime) {
			return Date.from(offsetDateTime.toInstant());
		}
	}

}
