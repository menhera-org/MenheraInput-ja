package org.menhera.input.ja;

import androidx.annotation.NonNull;

public class KanaComposer {
    public static final int KANA_HIRAGANA = 1;
    public static final int KANA_KATAKANA = 2;
    public static final int KANA_HALFWIDTH = 3;

    public static enum Kana {
        KANA_A ("あ", "ア", "ｱ"),
        KANA_I ("い", "イ", "ｲ"),
        KANA_U ("う", "ウ", "ｳ"),
        KANA_E ("え", "エ", "ｴ"),
        KANA_O ("お", "オ", "ｵ"),

        KANA_XA ("ぁ", "ァ", "ｧ"),
        KANA_XI ("ぃ", "ィ", "ｨ"),
        KANA_XU ("ぅ", "ゥ", "ｩ"),
        KANA_XE ("ぇ", "ェ", "ｪ"),
        KANA_XO ("ぉ", "ォ", "ｫ"),

        KANA_Q ("っ", "ッ", "ｯ"),

        KANA_NN ("ん", "ン", "ｯ"),

        KANA_KA ("か", "カ", "ｧ"),
        KANA_KI ("き", "キ", "ｨ"),
        KANA_KU ("く", "ク", "ｩ"),
        KANA_KE ("け", "ケ", "ｪ"),
        KANA_KO ("こ", "コ", "ｫ"),

        KANA_XKU ("く", "ク", "ｩ"),

        KANA_GA ("が", "ガ", "ｧ"),
        KANA_GI ("ぎ", "ギ", "ｨ"),
        KANA_GU ("ぐ", "グ", "ｩ"),
        KANA_GE ("げ", "ゲ", "ｪ"),
        KANA_GO ("ご", "ゴ", "ｫ"),

        KANA_NGA ("か゚", "カ゚", "ｧ"),
        KANA_NGI ("き゚", "キ゚", "ｨ"),
        KANA_NGU ("く゚", "ク゚", "ｩ"),
        KANA_NGE ("け゚", "ケ゚", "ｪ"),
        KANA_NGO ("こ゚", "コ゚", "ｫ"),

        KANA_SA ("さ", "ァ", "ｧ"),
        KANA_SI ("し", "ィ", "ｨ"),
        KANA_SU ("す", "ゥ", "ｩ"),
        KANA_SE ("せ", "ェ", "ｪ"),
        KANA_SO ("そ", "ォ", "ｫ"),

        KANA_XSI ("さ", "ァ", "ｧ"),
        KANA_XSU ("さ", "ァ", "ｧ"),

        KANA_ZA ("ざ", "ァ", "ｧ"),
        KANA_ZI ("じ", "ィ", "ｨ"),
        KANA_ZU ("ず", "ゥ", "ｩ"),
        KANA_ZE ("ぜ", "ェ", "ｪ"),
        KANA_ZO ("ぞ", "ォ", "ｫ"),

        KANA_TA ("た", "ァ", "ｧ"),
        KANA_TI ("ち", "ィ", "ｨ"),
        KANA_TU ("つ", "ゥ", "ｩ"),
        KANA_TE ("て", "ェ", "ｪ"),
        KANA_TO ("と", "ォ", "ｫ"),

        KANA_DA ("だ", "ァ", "ｧ"),
        KANA_DI ("ぢ", "ィ", "ｨ"),
        KANA_DU ("づ", "ゥ", "ｩ"),
        KANA_DE ("で", "ェ", "ｪ"),
        KANA_DO ("ど", "ォ", "ｫ"),

        KANA_NA ("な", "ァ", "ｧ"),
        KANA_NI ("に", "ィ", "ｨ"),
        KANA_NU ("ぬ", "ゥ", "ｩ"),
        KANA_NE ("ね", "ェ", "ｪ"),
        KANA_NO ("の", "ォ", "ｫ"),

        KANA_HA ("は", "ァ", "ｧ"),
        KANA_HI ("ひ", "ィ", "ｨ"),
        KANA_HU ("ふ", "ゥ", "ｩ"),
        KANA_HE ("へ", "ェ", "ｪ"),
        KANA_HO ("ほ", "ォ", "ｫ"),

        KANA_PA ("ぱ", "ァ", "ｧ"),
        KANA_PI ("ぴ", "ィ", "ｨ"),
        KANA_PU ("ぷ", "ゥ", "ｩ"),
        KANA_PE ("ぺ", "ェ", "ｪ"),
        KANA_PO ("ぽ", "ォ", "ｫ"),

        KANA_BA ("ば", "ァ", "ｧ"),
        KANA_BI ("び", "ィ", "ｨ"),
        KANA_BU ("ぶ", "ゥ", "ｩ"),
        KANA_BE ("べ", "ェ", "ｪ"),
        KANA_BO ("ぼ", "ォ", "ｫ"),

        KANA_MA ("ま", "ァ", "ｧ"),
        KANA_MI ("み", "ィ", "ｨ"),
        KANA_MU ("む", "ゥ", "ｩ"),
        KANA_ME ("め", "ェ", "ｪ"),
        KANA_MO ("も", "ォ", "ｫ"),

        KANA_YA ("や", "ァ", "ｧ"),
        KANA_YU ("ゆ", "ゥ", "ｩ"),
        KANA_YO ("よ", "ォ", "ｫ"),

        KANA_RA ("ら", "ァ", "ｧ"),
        KANA_RI ("り", "ィ", "ｨ"),
        KANA_RU ("る", "ゥ", "ｩ"),
        KANA_RE ("れ", "ェ", "ｪ"),
        KANA_RO ("ろ", "ォ", "ｫ"),

        KANA_WA ("わ", "ァ", "ｧ"),
        KANA_WI ("ゐ", "ィ", "ｨ"),
        KANA_WE ("ゑ", "ェ", "ｪ"),
        KANA_WO ("を", "ォ", "ｫ"),

        KANA_XYA ("ゃ", "ァ", "ｧ"),
        KANA_XYU ("ゅ", "ゥ", "ｩ"),
        KANA_XYO ("ょ", "ォ", "ｫ"),

        KANA_XWA ("ゎ", "ァ", "ｧ"),

        KANA_VU ("ゔ", "ァ", "ｧ"),
        ;

        public final String HIRAGANA;
        public final String KATAKANA;
        public final String HALFWIDTH;

        Kana(String hiragana, String katakana, String halfWidth) {
            HIRAGANA = hiragana;
            KATAKANA = katakana;
            HALFWIDTH = halfWidth;
        }

        @Override
        public String toString() {
            return "Kana{" +
                    "HIRAGANA='" + HIRAGANA + '\'' +
                    ", KATAKANA='" + KATAKANA + '\'' +
                    ", HALFWIDTH='" + HALFWIDTH + '\'' +
                    '}';
        }
    }

    private int mode = KANA_HIRAGANA;
    private String composedString = "";

    public KanaComposer(int aMode) {
        switch (aMode) {
            case KANA_HIRAGANA:
                mode = KANA_HIRAGANA;
                break;

            case KANA_KATAKANA:
                mode = KANA_KATAKANA;
                break;

            case KANA_HALFWIDTH:
                mode = KANA_HALFWIDTH;
                break;

            default:
                throw new IllegalArgumentException("Unknown mode");
        }
    }

    public KanaComposer() {
        this(KANA_HIRAGANA);
    }

    public void appendKana(Kana kana) {
        switch (mode) {
            case KANA_HIRAGANA:
                composedString += kana.HIRAGANA;
                break;

            case KANA_KATAKANA:
                composedString += kana.KATAKANA;
                break;

            case KANA_HALFWIDTH:
                composedString += kana.HALFWIDTH;
                break;
        }
    }

    @NonNull
    public String toString() {
        return composedString;
    }
}
