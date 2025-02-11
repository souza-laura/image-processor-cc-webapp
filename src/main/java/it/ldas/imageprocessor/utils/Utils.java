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
        Map<String, Object> resultMap = new LinkedHashMap<>(); // linkedHashMap per mantenere l'ordine
        Deque<Map<String, Object>> stack = new ArrayDeque<>(); // stack per gestire i livelli di annidamento

        if (!ObjectUtils.isEmpty(metadata)) {
            stack.push(resultMap); // Inizializza lo stack con la mappa principale

            String[] lines = metadata.split("\n");
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue; // Ignora righe vuote
                }

                // Conta il numero di spazi all'inizio della riga per determinare il livello di annidamento
                int indentLevel = line.length() - line.trim().length();

                // Rimuovi le mappe inutili dallo stack in base al livello di annidamento
                while (stack.size() > indentLevel + 1) {
                    stack.pop();
                }

                // Dividi la riga in chiave e valore
                String[] parts = line.trim().split(":", 2);
                String key = parts[0].trim();
                String value = (parts.length > 1) ? parts[1].trim() : null;

                // Se il valore è null, inizializza una nuova mappa per l'annidamento
                if (value == null) {
                    Map<String, Object> nestedMap = new LinkedHashMap<>();
                    stack.peek().put(key, nestedMap); // Aggiungi la nuova mappa alla mappa corrente
                    stack.push(nestedMap); // Aggiungi la nuova mappa allo stack
                } else {
                    // Altrimenti, aggiungi la coppia chiave-valore alla mappa corrente
                    stack.peek().put(key, value);
                }
            }
        }
        return resultMap;
    }

}
