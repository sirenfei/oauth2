package com.faithbj.oauth.as.utils;

public class Jackson2Mapper {
    private static com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

    public static String write(Object input) throws Exception {
        return mapper.writeValueAsString(input);
    }

    public static <T> T read(String input, Class<T> type) throws Exception {
        return mapper.readValue(input, type);
    }
    
}
