package org.esiea.festicore.service;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import org.esiea.festicore.LogManager;
import org.esiea.festicore.Reservation;
import org.esiea.festicore.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JsonDataManager {

    private static final String DATA_FILE = "data/users.json";
    private static final String RES_FILE = "data/reservations.json";
    private final ObjectMapper mapper;
    Logger logger = LogManager.getLogger();

    public JsonDataManager() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveUsers(List<User> users) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(DATA_FILE), users);
        logger.info("Saved users to " + DATA_FILE + " (count=" + (users==null?0:users.size()) + ")");
    }

    public List<User> loadUsers() throws IOException {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            logger.info("Data file not found: " + DATA_FILE + ", returning empty user list.");
            return new ArrayList<>();
        }

        return mapper.readValue(
                file,
                new TypeReference<List<User>>() {}
        );
    }

    public void saveReservations(Map<String, Reservation> reservations) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(RES_FILE), reservations);
    }

    public Map<String, Reservation> loadReservations() throws IOException {
        File f = new File(RES_FILE);
        if (!f.exists()) return new HashMap<>();
        return mapper.readValue(f, new TypeReference<Map<String, Reservation>>() {});
    }

}
