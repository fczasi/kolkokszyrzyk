package com.example.kolkokszyrzyk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView terazGra;

    private Button[][] guziki = new Button[3][3];

    private boolean turaGracz1 = true;

    private int licznik;

    private int punktyGracz1;
    private int punktyGracz2;

    private String gracz1;
    private String gracz2;

    private TextView textViewGracz1;
    private TextView textViewGracz2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        terazGra= findViewById(R.id.znak);

        textViewGracz1 = findViewById(R.id.gracz1punkty);
        textViewGracz2 = findViewById(R.id.gracz2punkty);

        gracz1 = "X";
        gracz2 = "O";

        for (int i=0;i < 3; i++ ){
            for(int j = 0;j < 3;j++){
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                guziki[i][j] = findViewById(resID);
                guziki[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {// czekanie na kliknięcie guzika reset
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if(turaGracz1){
            ((Button) v).setText(gracz1);
            terazGra.setText(gracz2);
        }else{
            ((Button) v).setText(gracz2);
            terazGra.setText(gracz1);
        }



        licznik++;

        if(checkForWin()){
            if(turaGracz1){
                gracz1Wygrana();// koniec gry WYGRYWA GRACZ 1
                terazGra.setText(gracz1);
            }else{
                gracz2Wygrana();// koniec gry WYGRYWA GRACZ 2
                terazGra.setText(gracz1);
            }
        } else if(licznik == 9){
            remis();// koniec gry REMIS
            terazGra.setText(gracz1);
        }else{
            turaGracz1 = !turaGracz1;// nastepna tura
        }
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];// deklaracja tablicy typu String

        for (int i=0;i < 3; i++ ){// zapis tekstu z tablicy guzików do tablicy typu String
            for(int j = 0;j < 3;j++){
                field[i][j] = guziki[i][j].getText().toString();
            }
        }

        for (int i=0;i < 3; i++ ){// sprawdzenie czy wygrana w poziomie
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }

        for (int i=0;i < 3; i++ ){// sprawdzenie czy wygrana w pionie
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1])// sprawdzenie czy wygrana w pierwszym skosie
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if(field[0][2].equals(field[1][1])// sprawdzenie czy wygrana w drugim skosie
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private  void gracz1Wygrana(){
        punktyGracz1++;
        Toast.makeText(this, "Gracz 1 Wygrywa!", Toast.LENGTH_SHORT).show();
        tablicaWynikow();
        resetBoard();
    }

    private  void gracz2Wygrana(){
        punktyGracz2++;
        Toast.makeText(this, "Gracz 2 Wygrywa!", Toast.LENGTH_SHORT).show();
        tablicaWynikow();
        resetBoard();
    }

    private void remis(){
        Toast.makeText(this, "Remis!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void tablicaWynikow(){
        textViewGracz1.setText("Gracz 1 Punkty:  "+ punktyGracz1);
        textViewGracz2.setText("Gracz 2 Punkty:  "+ punktyGracz2);
    }

    private void resetBoard(){
        for (int i=0;i < 3; i++ ){
            for(int j = 0;j < 3;j++){
                guziki[i][j].setText("");
            }
        }

        licznik = 0;
        turaGracz1 = true;
    }

    private void resetGame(){
        punktyGracz1 = 0;
        punktyGracz2 = 0;
        tablicaWynikow();
    }
}