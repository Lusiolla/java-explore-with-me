package ru.practicum.explorewithme.dataio;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CustomDateTimeSerializer(Class t) {
        super(t);
    }

    public CustomDateTimeSerializer() {
        this(null);
    }

    @Override
    public void serialize(
            LocalDateTime localDateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(formatter.format(localDateTime));
    }
}
