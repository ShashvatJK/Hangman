package spider.task1.hackerapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.util.Random;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {
    Random r = new Random();
    Button check,exit,next;
    TextView txtword,txtwrong,txthangman,textView1,textView2,textView3,txtbest;
    EditText txtletter;
    ConstraintLayout bg;
    int wrong_guess_count = 0;
    int index;
    String word;
    char[] word_fg,word_bg;
    ImageView img;
    public static final String MY_PREFS_NAME = "BestScoreFile";

    int hack=0;
    int bestscore;

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
        txtbest = (TextView) findViewById(R.id.txtbest);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        txthangman = (TextView) findViewById(R.id.txthangman);
        txtwrong = (TextView) findViewById(R.id.txtwrong);
        txtletter = (EditText) findViewById(R.id.txtletter);

        img= (ImageView) findViewById(R.id.img);
        final SharedPreferences prefs = getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        //editor.putInt("Best Score", 8);
        //editor.apply();

        bestscore = prefs.getInt("Best Score",8);
        txtbest.setText("" + bestscore);

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

                            switch(wrong_guess_count){
                                case 1: img.setImageResource(R.drawable.step1);break;
                                case 2: img.setImageResource(R.drawable.step2);break;
                                case 3: img.setImageResource(R.drawable.step3);break;
                                case 4: img.setImageResource(R.drawable.step4);break;
                                case 5: img.setImageResource(R.drawable.step5);break;
                                case 6: img.setImageResource(R.drawable.step6);break;
                                case 7: img.setImageResource(R.drawable.step7);break;
                                case 8: img.setImageResource(R.drawable.step8);
                                        bg.setBackgroundColor(Color.BLACK);
                                        img.setBackgroundColor(Color.YELLOW);
                                        txthangman.setTextColor(Color.CYAN);
                                        textView1.setTextColor(Color.YELLOW);
                                        txtword.setTextColor(Color.RED);
                                        textView3.setTextColor(Color.RED);
                                        txtbest.setTextColor(Color.RED);
                                        txtletter.setBackgroundColor(Color.BLUE);
                                        textView1.setText("Wrong Guesses:");
                                        txthangman.setText("YOU LOST :'(");
                                        textView2.setText("");
                                        check.setEnabled(false);
                            }
                            txtwrong.setText(wrong_guess_count + "");

                        }
                        txtletter.setText("");
                        if(wrong_guess_count==8){
                            txtletter.setText(txtwrong.getText());
                            txtwrong.setText("");
                            txtletter.setEnabled(false);
                        }
                        if (count2 == word.length()){
                            if(wrong_guess_count < bestscore){
                                editor.putInt("Best Score", wrong_guess_count);
                                editor.apply();
                                bestscore=prefs.getInt("Best Score",bestscore);
                            }
                            txtbest.setText("" + bestscore);
                            bg.setBackgroundColor(Color.YELLOW);
                            textView1.setText("Wrong Guesses:");
                            txtletter.setText(txtwrong.getText());
                            txthangman.setText("YOU WON :D");
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
                //exit.setVisibility(View.INVISIBLE);
                //next.setVisibility(View.INVISIBLE);
                //bestscore = prefs.getInt("Best Score",8);
                txtbest.setText("" + bestscore);
                bg.setBackgroundColor(Color.WHITE);
                img.setBackgroundColor(Color.WHITE);
                txthangman.setTextColor(Color.BLACK);
                txtword.setTextColor(Color.BLACK);
                textView1.setTextColor(Color.BLACK);
                textView3.setTextColor(Color.GRAY);
                txtbest.setTextColor(Color.GRAY);
                txtletter.setBackgroundColor(Color.RED);
                img.setImageResource(R.drawable.step0);
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

        txthangman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wrong_guess_count==8) {
                    hack++;
                    txtword.setText("");
                    if(hack%2==1) {
                        for (int i = 0; i < word.length(); i++) {
                            txtword.append(word_bg[i] + "");
                        }
                    }else{
                        for (int i = 0; i < word.length(); i++) {
                            txtword.append(word_fg[i] + "");
                        }
                    }
                } else {
                    editor.putInt("Best Score", 8);
                    editor.apply();
                    bestscore=prefs.getInt("Best Score",8);
                    txtbest.setText("" + bestscore);
                }
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        //if (view instanceof ConstraintLayout || view instanceof Button || view instanceof ) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    if (txtletter.getText().length() > 1) {
                        txtletter.setText(txtletter.getText().charAt(0) + "");
                    }
                    return false;
                }
            });
        //}
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
        img.setBackgroundColor(Color.WHITE);

        String[] words_list =
                {"WORKAHOLIC", "PENINSULA", "ASBESTOS", "FORMIDABLE", "INDONESIA",
                 "SACRAMENTAL", "GRUESOME", "QUIVERING", "KINETIC", "RHYTHMS",
                 "NARCISSISTIC", "CRUSTACEANS", "JUDICIARY", "DIGITAL", "MARINATED",
                 "DRAUGHT","UNSOLICITED","ANTIQUES","RATIONALIZATION","BLUDGEON",
                 "ZEPHYRS","KALEIDOSCOPIC","SUMMONED","OPHTHALMOLOGY","XYLOPHONE",
                 "TERTIARY","VENEZUELA","HAPHAZARD","BENEVOLENT","RESISTANCE"
                };
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
