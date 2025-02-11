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

                // Gestione corretta della struttura dello stack in base all'indentazione
                while (stack.size() > indentLevel + 1) {
                    stack.pop(); // Rimuove gli elementi dello stack che non corrispondono più al livello di indentazione
                }

                // Assicurati che lo stack non sia vuoto prima di fare peek()
                if (stack.peek() == null) {
                    continue; // Salta la riga se non c'è una mappa nel livello attuale
                }

                String[] parts = line.trim().split(":", 2);
                if (parts.length < 2) {
                    continue; // Ignora righe non valide
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                stack.peek().put(key, value);

                if (ObjectUtils.isEmpty(value)) {
                    Map<String, Object> nestedMap = new LinkedHashMap<>();
                    stack.peek().put(key, nestedMap);
                    stack.push(nestedMap); // Aggiungi la nuova mappa allo stack per livelli successivi
                }
            }
        }
        return resultMap;
    }


}
