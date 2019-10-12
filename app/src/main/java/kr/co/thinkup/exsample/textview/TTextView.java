package kr.co.thinkup.exsample.textview;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

import kr.co.thinkup.exsample.R;

/**
 * 2019-08-02 by yh.Choi
 */
public class TTextView extends AppCompatActivity {

    private Button          text_button;
    private EditText        ttview_comma;
    private String          result="";
    private DecimalFormat   decimalFormat;

    private EditText        edit_text_keyboard_test;
    private TextInputLayout edit_text_keyboard_test_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        ttview_comma = findViewById(R.id.ttview_comma);
        edit_text_keyboard_test = findViewById(R.id.edit_text_keyboard_test);
        text_button = findViewById(R.id.text_button);
        edit_text_keyboard_test_layout = findViewById(R.id.edit_text_keyboard_test_layout);

        decimalFormat = new DecimalFormat("###,###,###");

        ttview_comma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(result)){                        // StackOverflow를 막기위해,
                    result = intToDecimalFormatString(charSequence.toString());     // 에딧텍스트의 값을 변환하여, result에 저장.
                    ttview_comma.setText(result);                                   // 결과 텍스트 셋팅.
                    ttview_comma.setSelection(result.length());                     // 커서를 제일 끝으로 보냄.
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        edit_text_keyboard_test.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//            }
//        });

        edit_text_keyboard_test.setHint("TEST");

        edit_text_keyboard_test.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edit_text_keyboard_test_layout.setError(null);
                edit_text_keyboard_test_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_text_keyboard_test.getText().toString().equals("")) {
                    edit_text_keyboard_test_layout.setError("Text should not be empty");
                }
            }
        });
    }

    public String intToDecimalFormatString(int value) {
        String val = String.valueOf(value);
        return  intToDecimalFormatString(val);
    }

    public String intToDecimalFormatString(String value) {
        return  decimalFormat.format(Double.parseDouble(value.replaceAll(",","")));
    }
}
