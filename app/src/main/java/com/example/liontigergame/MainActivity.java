package com.example.liontigergame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

enum Player {
    NOPLAYER,ONE,TWO
}
public class MainActivity extends AppCompatActivity {
    private  final int NIMAGES=9;
    GridLayout gridLayout;
    Button btnReset;

    Player currentPlayer= Player.ONE;
    int[][] winnerRowsColumns=new int[][]{
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6}
    };
    Player[] playerChoices=new Player[9];
    boolean gameOver=false;
    int countNumInputs=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout=(GridLayout) findViewById(R.id.gridLayout);
        btnReset=(Button)findViewById(R.id.btnReset);
        for(int i=0;i<playerChoices.length;i++){
            playerChoices[i]=Player.NOPLAYER;
        }
    }
    public void imageIsTapped(View imageView){
        ImageView img=(ImageView)imageView;
        int index=Integer.valueOf(img.getTag().toString());
        if(playerChoices[index]==Player.NOPLAYER&&gameOver==false){
            countNumInputs++;
            playerChoices[index]=currentPlayer;
            if(currentPlayer==Player.ONE){
                animateImage(img,R.drawable.lion);
                currentPlayer=Player.TWO;
            }
            else if(currentPlayer==Player.TWO){
                animateImage(img,R.drawable.tiger);
                currentPlayer=Player.ONE;
            }
            whoIsWinner();
        }
        if(countNumInputs==9){
            btnReset.setVisibility(View.VISIBLE);
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"no winner",Toast.LENGTH_LONG).show();
                    gameReset();
                }
            });
        }

    }

    void  animateImage(ImageView imageView,int imgResourceId){
        imageView.setTranslationX(-2000);
        imageView.setImageResource(imgResourceId);
        imageView.animate().translationXBy(2000).alpha(1).rotationBy(3600).setDuration(500);

    }
  Player whoIsWinner(){
        Player winner=null;
      for(int[]winnerColumn:winnerRowsColumns){
         if((playerChoices[winnerColumn[0]]==playerChoices[winnerColumn[1]])
                 && (playerChoices[winnerColumn[1]]==playerChoices[winnerColumn[2]])){
             if(playerChoices[winnerColumn[0]]==Player.ONE){
                 winner=Player.ONE;
                // createDialog(R.drawable.lion,R.string.lion_winner);
                 btnReset.setVisibility(View.VISIBLE);
                 btnReset.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         gameReset();
                     }
                 });
                 gameOver=true;
                 break;
             }
             else if (playerChoices[winnerColumn[0]]==Player.TWO){
                 winner=Player.TWO;
                // createDialog(R.drawable.tiger,R.string.tiger_winner);
                 btnReset.setVisibility(View.VISIBLE);
                 btnReset.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         gameReset();
                     }
                 });
                 gameOver=true;

                 break;
             }
             else if(playerChoices[winnerColumn[0]]==null){
                 continue;
             }

         }
      }
      return  winner;
    }
    void createDialog(int imgResource,int text){
        ImageView winnerImg;
        TextView winnerText;
        Button newGameBtn;
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_view,null,false);
        winnerImg=(ImageView)dialogView.findViewById(R.id.winnerImg);
        winnerText=(TextView)dialogView.findViewById(R.id.winnerText);
        winnerImg.setImageResource(imgResource);
        winnerText.setText(text);
        builder.setView(dialogView);
        AlertDialog dialog=builder.create();
        newGameBtn=(Button)dialogView.findViewById(R.id.btnNewGame);
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameReset();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    void gameReset(){
        GridLayout gridLayout=(GridLayout)findViewById(R.id.gridLayout);
        for(int i=0;i<playerChoices.length;i++){
            playerChoices[i]=Player.NOPLAYER;
        }
        for(int i=0;i<gridLayout.getChildCount();i++){
            ImageView imageView= (ImageView) gridLayout.getChildAt(i);
           imageView.setImageDrawable(null);
           imageView.setAlpha(0.4f);
        }
        currentPlayer=Player.ONE;
        gameOver=false;
        btnReset.setVisibility(View.GONE);

    }
}