package org.menhera.input.ja;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MenheraInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    public static final int MODE_ALPHA = 2;
    public static final int MODE_KYOURO = 1;
    public static final int MODE_HIRAGANA = 3;
    public static final int MODE_KATAKANA = 4;
    public static final int MODE_HALFWIDTH = 5;

    static final int KEY_VERTICAL_LINE = 0x2c8;
    static final int KEY_BOTTOM_VERTICAL_LINE = 0x2cc;
    static final int KEY_BACK = -1;
    static final int KEY_FORWARD = -2;
    static final int KEY_DELETE = -3;
    static final int KEY_BACKSPACE = -4;
    static final int KEY_OPTIONS = -5;
    static final int KEY_SYMBOLS = -6;
    static final int KEY_CHANGE_KEYBOARDS = -7;
    static final int KEY_HANNDAKUTENN = 0x309a;
    static final int KEY_DAKUTENN = 0x3099;
    static final int KEY_RETURN = 0x23ce;
    static final int KEY_COMMA = 0x2c;
    static final int KEY_FULL_STOP = 0x2e;
    static final int KEY_HYPHEN = 0x2d;
    static final int KEY_CHOUONNPU = 0x30fc;
    static final int KEY_EN_DASH = 0x2013;
    static final int KEY_EM_DASH = 0x2014;
    static final int KEY_INTERPUNCT = 0xb7;
    static final int KEY_SP = 0x2423;
    static final int KEY_NBSP = 0x237d;
    static final int KEY_TAB = 0x21e5;

    static final String STR_SP = "\u0020";
    static final String STR_TAB = "\u0009";
    static final String STR_LF = "\n";
    static final String STR_NBSP = "\u00a0";

    static final int[] CONSONANTS = {104,112,109,102,116,114,115,107,119,98,118,100,122,103,121};

    private Keyboard keyboard;
    private KeyboardView keyboardView;

    private String composingText = null;
    private int mode = MODE_ALPHA;

    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
        keyboard = new Keyboard(this, R.xml.keyboard);
    }

    @Override
    public View onCreateInputView() {
        //return super.onCreateInputView();
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setKeyboard(keyboard);
        return keyboardView;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        Log.d(MenheraInputService.class.getSimpleName(), "inputType: " + info.inputType);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        ExtractedText extractedText = ic.getExtractedText(new ExtractedTextRequest(), 0);
        int startIndex = 0;
        int endIndex = 0;
        if (null != extractedText) {
            startIndex = extractedText.startOffset + extractedText.selectionStart;
            endIndex = extractedText.startOffset +  extractedText.selectionEnd;
        }
        int selectionLength = endIndex - startIndex;
        InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        switch (primaryCode) {
            case KEY_SP:
                commit();
                ic.commitText(STR_SP, 1);
                break;

            case KEY_NBSP:
                commit();
                ic.commitText(STR_NBSP, 1);
                break;

            case KEY_TAB:
                commit();
                ic.commitText(STR_TAB, 1);
                break;

            case KEY_VERTICAL_LINE:
            case KEY_BOTTOM_VERTICAL_LINE:
            case KEY_COMMA:
            case KEY_FULL_STOP:
            case KEY_HYPHEN:
            case KEY_CHOUONNPU:
            case KEY_EN_DASH:
            case KEY_EM_DASH:
            case KEY_INTERPUNCT:
                commit();
                ic.commitText(new String(Character.toChars(primaryCode)), 1);
                break;

            case KEY_FORWARD:
                if (null != composingText) {
                    commit();
                    break;
                }
                if (0 == selectionLength) {
                    ic.setSelection(endIndex + 1, endIndex + 1);
                } else {
                    ic.setSelection(endIndex, endIndex);
                }
                break;

            case KEY_BACK:
                if (null != composingText) {
                    commit();
                    break;
                }
                if (0 == selectionLength) {
                    ic.setSelection(startIndex - 1, startIndex - 1);
                } else {
                    ic.setSelection(startIndex, startIndex);
                }
                break;

            case KEY_DELETE:
                if (null == composingText) {
                    ic.deleteSurroundingText(0, 1);
                } else {
                    discardComposingText();
                }
                break;

            case KEY_BACKSPACE:
                if (null == composingText) {
                    ic.deleteSurroundingText(1, 0);
                } else {
                    discardComposingText();
                }
                break;

            case KEY_OPTIONS:
                switch (mode) {
                    case MODE_ALPHA:
                        mode = MODE_KYOURO;
                        showToast(getString(R.string.input_mode_kyouro));
                        break;

                    case MODE_KYOURO:
                        mode = MODE_HIRAGANA;
                        showToast(getString(R.string.input_mode_hiragana));
                        break;

                    case MODE_HIRAGANA:
                        mode = MODE_KATAKANA;
                        showToast(getString(R.string.input_mode_katakana));
                        break;

                    case MODE_KATAKANA:
                        mode = MODE_HALFWIDTH;
                        showToast(getString(R.string.input_mode_halfwidth));
                        break;

                    default:
                        mode = MODE_ALPHA;
                        showToast(getString(R.string.input_mode_alpha));
                }

                displayComposingText();
                break;

            case KEY_RETURN:
                if (null == composingText) {
                    //ic.commitText(STR_LF, 1);
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                    break;
                }
                commit();
                break;

            case KEY_SYMBOLS:
                commit();
                changeKeyboardLayout();
                break;

            case KEY_CHANGE_KEYBOARDS:
                commit();
                if (imeManager != null) {
                    imeManager.showInputMethodPicker();
                }
                break;

            default:
                if (null == composingText) {
                    composingText = "";
                }

                composingText += new String(Character.toChars(primaryCode));
                displayComposingText();
        }
    }

    void showToast(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    void discardComposingText() {
        composingText = null;
        InputConnection ic = getCurrentInputConnection();
        ic.setComposingText("", 1);
    }

    String getComposingText() {
        String text = "";
        if (null == composingText) {
            return text;
        }
        switch (mode) {
            case MODE_KYOURO:
                for (int i = 0; i < composingText.length(); i++) {
                    kyouroSwitch:
                    switch (composingText.codePointAt(i)) {
                        case 0x274:
                            // nn
                            text += "nn";
                            break;

                        case 81:
                        case 113:
                            // Q
                            if (i + 1 < composingText.length()) {
                                int nextCode = composingText.codePointAt(i + 1);
                                for (int j = 0; j < CONSONANTS.length; j++) {
                                    if (nextCode == CONSONANTS[j]) {
                                        String str = String.valueOf(composingText.charAt(i + 1));
                                        text += str + str;
                                        i++;
                                        break kyouroSwitch;
                                    }
                                }
                            }
                            text += "Q";
                            break;

                        case KEY_HANNDAKUTENN:
                        case KEY_DAKUTENN:
                            break;

                        default:
                            text += composingText.charAt(i);
                    }
                }
                break;

            case MODE_ALPHA:
                alphaLoop:
                for (int i = 0; i < composingText.length(); i++) {
                    int currentCode = composingText.codePointAt(i);
                    if (KEY_HANNDAKUTENN == currentCode || KEY_DAKUTENN == currentCode) {
                        continue;
                    }
                    if (i + 1 < composingText.length()) {
                        int nextCode = composingText.codePointAt(i + 1);
                        if (currentCode == nextCode) {
                            if (110 == currentCode) {
                                text += "\u0274"; // nn
                                i++;
                                continue alphaLoop;
                            }
                            for (int j = 0; j < CONSONANTS.length; j++) {
                                if (currentCode == CONSONANTS[j]) {
                                    text += "Q" + composingText.charAt(i + 1);
                                    i++;
                                    continue alphaLoop;
                                }
                            }
                        }

                    }
                    if ('q' == composingText.charAt(i)) {
                        text += "Q";
                        continue;
                    }
                    if ('y' == composingText.charAt(i) || 'Y' == composingText.charAt(i)) {
                        text += "j";
                        continue;
                    }
                    text += composingText.charAt(i);
                }
                break;

            default:

                break;
        }

        return text;
    }

    void displayComposingText() {
        String text = getComposingText();
        InputConnection ic = getCurrentInputConnection();
        ic.setComposingText(text, 1);
    }

    void commit() {
        String text = getComposingText();
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(text, 1);
        composingText = null;
    }

    void changeKeyboardLayout() {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
