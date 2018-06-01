package spider.task1.hangman;

import android.app.Activity;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.Random;
import android.text.TextWatcher;
public class MainActivity extends AppCompatActivity {
    Random r = new Random();
    Button check,exit,next;
    TextView txtword,txtwrong,txthangman,textView1,textView2;
    EditText txtletter;
    ConstraintLayout bg;
    Keyboard k;
    int wrong_guess_count = 0;
    int index;
    String word;
    char[] word_fg,word_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg = (ConstraintLayout) findViewById(R.id.bg);
        setupUI(findViewById(R.id.bg));

        check = (Button) findViewById(R.id.check);
        exit = (Button) findViewById(R.id.exit);
        next = (Button) findViewById(R.id.next);
        txtword = (TextView) findViewById(R.id.txtword);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        txthangman = (TextView) findViewById(R.id.txthangman);
        txtwrong = (TextView) findViewById(R.id.txtwrong);
        txtletter = (EditText) findViewById(R.id.txtletter);

        generate();

        txtletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtletter.getText().length() > 1) {
                    txtletter.setText(txtletter.getText().charAt(0) + "");
                }
            }
        });
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // bg.requestFocus();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letter = txtletter.getText().toString();
                if(letter.length()!=0) {
                    if ((letter.charAt(0) >= 65 && letter.charAt(0) <= 90) || (letter.charAt(0) >= 97 && letter.charAt(0) <= 122)) {
                        txtword.setText("");
                        int count1 = 0, count2 = 0;
                        for (int i = 0; i < word.length(); i++) {
                            if (letter.equalsIgnoreCase(word_bg[i] + "")) {
                                word_fg[i] = word_bg[i];
                                count1++;
                            }
                            if (word_fg[i] == word_bg[i]) {
                                count2++;
                            }
                            txtword.append(word_fg[i] + "");
                        }
                        if (count1 == 0) {
                            wrong_guess_count++;
                            txtwrong.setText(wrong_guess_count + "");
                        }
                        txtletter.setText("");
                        if (count2 == word.length()) {
                            bg.setBackgroundColor(Color.CYAN);
                            textView1.setText("WRONG GUESSES:");
                            txtletter.setText(txtwrong.getText());
                            txthangman.setText("YOU WON!");
                            txtwrong.setText("");
                            textView2.setText("");
                            txtletter.setEnabled(false);
                            check.setEnabled(false);
                            //exit.setVisibility(View.VISIBLE);
                            //next.setVisibility(View.VISIBLE);
                        }
                    } else {
                        txtletter.setText("");
                        Toast.makeText(getApplicationContext(), "Please Input A Letter.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please Input A Letter.", Toast.LENGTH_LONG).show();
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exit.setVisibility(View.INVISIBLE);
                //next.setVisibility(View.INVISIBLE);
                System.exit(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // exit.setVisibility(View.INVISIBLE);
               // next.setVisibility(View.INVISIBLE);
                bg.setBackgroundColor(Color.WHITE);
                textView1.setText("Guess a letter:");
                txtletter.setText("");
                txthangman.setText("HANGMAN");
                textView2.setText("Wrong Guess Count");
                txtwrong.setText("0");
                txtletter.setEnabled(true);
                check.setEnabled(true);
                generate();
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    if (txtletter.getText().length() > 1) {
                        txtletter.setText(txtletter.getText().charAt(0) + "");
                    }
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void generate(){
       //exit.setVisibility(View.INVISIBLE);
       // next.setVisibility(View.INVISIBLE);
        txtletter.setText("");
        txtword.setText("");
        wrong_guess_count=0;

        String[] words_list =
                {"INVISIBLE", "PENGUIN", "AMERICA", "TECTONIC", "INDIA",
                "SCRATCH", "GRUESOME", "QUALMS", "KINETIC", "RHYTHM",
                "NARCISSISTIC", "CASTLE", "YESTERDAY", "DIGITAL", "MASCOT",
                "DRAUGHT","USURP","ANTIQUES","REMINISCENT","BLUDGEON",
                "ZIRCONIUM","UBIQUITOUS","SUMMONED","OPPONENT","XYLOPHONE"};
        index = r.nextInt(words_list.length);
        word = words_list[index];
        word_fg = new char[word.length()];
        word_bg = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            word_fg[i] = '-';
            txtword.append(word_fg[i] + "");
        }
    }
}
