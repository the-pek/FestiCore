package org.esiea.festicore;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataManager {

    private static final String DATA_FILE = "data/users.json";
    private final ObjectMapper mapper;

    public JsonDataManager() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveUsers(List<User> users) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(DATA_FILE), users);
    }

    public List<User> loadUsers() throws IOException {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        return mapper.readValue(
                file,
                new TypeReference<List<User>>() {}
        );
    }
}
