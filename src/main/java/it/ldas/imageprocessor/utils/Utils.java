package it.ldas.imageprocessor.utils;

public class Utils {

    public static boolean isImageTypeValid(String imgType) {
        return imgType != null && (imgType.equals("image/png") || imgType.equals("image/jpeg"));
    }

}
