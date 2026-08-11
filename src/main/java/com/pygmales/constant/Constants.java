package com.pygmales.constant;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Constants {
    public static final List<String> LANGUAGES = new LinkedList<>(List.of("English", "русский"));
    public static final Map<Locale, String> LANGUAGE_MAP = Map.of(
             Locale.forLanguageTag("en"),"English",
             Locale.forLanguageTag("ru"),"русский"
    );
}
