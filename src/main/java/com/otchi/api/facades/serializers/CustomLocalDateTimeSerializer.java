package com.otchi.api.facades.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneId.of;

public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(LocalDateTime date, JsonGenerator generator,
                          SerializerProvider serializerProvider)
            throws IOException {
        dateTimeFormatter.withZone(of("UTC"));
        generator.writeString(dateTimeFormatter.format(date));
    }

}
