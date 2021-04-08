package edu.sdsmt.Hofer_Gabriel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int actor = 0, pit, bat, bow_and_quiver, arrow1, arrow2, wumpus;
    private int IMGVIEW[] = {
            R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7,
            R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11,
            R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15
    };
    private int VISITED[] = new int[16];

    public void init(){
        // initialize visited array to not-visited except for (0,0)
        for(int i=0;i<16;i++) VISITED[i]=0;
        VISITED[0]=1;
        place_objects_randomly();
    }

    public void place_objects_randomly(){
        actor = 0; // actor always starts at (0,0), the other objects are random
    }

    public String what_is_here(int row, int col){
        return "nothing yet";
    }

    public void mark_visited_squares(){
        for(int i=0;i<16;i++) {
            if (VISITED[i]!=0) {
                ImageView img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.wumpus4);
            }
        }
    }

    public void refresh(int loc){
        // all squares to white
        for(int i=0;i<16;i++) {
            ImageView img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus1);
        }
        // set any visited squares to green (for now)
        mark_visited_squares();
        VISITED[loc]=1;
        // place actor
        int image = R.drawable.wumpus5;
        ImageView img = findViewById(IMGVIEW[loc]);
        img.setImageResource(image);
    }

    public void moveUp(){
        actor = (actor-4+16)%16;
        refresh(actor);
    }
    public void moveDown(){
        actor = (actor+4+16)%16;
        refresh(actor);
    }
    public void moveLeft(){
        actor = (actor-1+16)%16;
        refresh(actor);
    }
    public void moveRight(){
        actor = (actor+1+16)%16;
        refresh(actor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Button btnMoveUp = (Button) findViewById(R.id.moveUp);
        btnMoveUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveUp();
            }
        });

        Button btnMoveDown = (Button) findViewById(R.id.moveDown);
        btnMoveDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveDown();
            }
        });

        Button btnMoveLeft = (Button) findViewById(R.id.moveLeft);
        btnMoveLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveLeft();
            }
        });

        Button btnMoveRight = (Button) findViewById(R.id.moveRight);
        btnMoveRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveRight();
            }
        });

    }
}
