package it.ldas.imageprocessor.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {

    public static boolean isImageTypeValid(String imgType) {
        return imgType != null && (imgType.equals("image/png") || imgType.equals("image/jpeg"));
    }

    public static Map<String, Object> parseMetadata(String metadata) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Deque<Map<String, Object>> stack = new ArrayDeque<>();
        stack.push(resultMap);

        if (!ObjectUtils.isEmpty(metadata)) {
            String[] lines = metadata.split("\n");
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                int indentLevel = line.length() - line.trim().length();

                // gestione corretta della struttura in base all'indentazione
                while (stack.size() > indentLevel + 1) {
                    stack.pop(); // rimuove gli elementi che non corrispondono più al livello di indentazione
                }

                if (stack.peek() == null) {
                    continue; // salta la riga se non c'è una mappa nel livello attuale
                }

                String[] parts = line.trim().split(":", 2);
                if (parts.length < 2) {
                    continue; // ignora righe non valide
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                stack.peek().put(key, value);

                if (ObjectUtils.isEmpty(value)) {
                    Map<String, Object> nestedMap = new LinkedHashMap<>();
                    stack.peek().put(key, nestedMap);
                    stack.push(nestedMap); // nuova mappa per livelli successivi
                }
            }
        }
        return resultMap;
    }


}
