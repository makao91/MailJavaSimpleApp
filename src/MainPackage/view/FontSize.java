package MainPackage.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssPath (FontSize fontSize) {
        switch (fontSize) {
            case SMALL:
                return "cssStyle/fontSmall.css";
            case MEDIUM:
                return "cssStyle/fontMedium.css";
            case BIG:
                return "cssStyle/fontBig.css";
            default:
                return null;
        }
    }
}
