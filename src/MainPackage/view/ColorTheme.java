package MainPackage.view;

public enum ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath (ColorTheme colorTheme) {
        switch (colorTheme) {
            case LIGHT:
                return "cssStyle/themeLight.css";
            case DEFAULT:
                return "cssStyle/themeDefault.css";
            case DARK:
                return "cssStyle/themeDark.css";
            default:
                return null;
        }
    }
}
