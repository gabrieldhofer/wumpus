package edu.sdsmt.Hofer_Gabriel;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private int actor = 0, score = 0;
    private ArrayList<String> grid = new ArrayList<String>();
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
        grid.clear();
        grid.add("exit");
        grid.add("pit");
        grid.add("bat");
        grid.add("bow_and_quiver");
        grid.add("arrow1");
        grid.add("arrow2");
        grid.add("wumpus");
        for(int i=0;i<8;i++)
            grid.add("--");
        Collections.shuffle(grid);
        grid.add(0,"actor");
    }

    public void cheat(){
        for(int i=0;i<16;i++){
            // make pictures and set appropriate imageView with showimage
            // if is_wumpus...
            // if is_bat...
            // if is_bow_and_quiver...
            // if is_arrow1...
            // if is_arrow2...
        }
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
        // update score
        score+=1;
        ((TextView) findViewById(R.id.score)).setText("Score: "+Integer.toString(score));
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
        actor = (actor-1) + ((actor-1)/4 != actor/4 ? 4 : 0);
        refresh(actor);
    }
    public void moveRight(){
        actor = (actor+1) + ((actor+1)/4 != actor/4 ? -4 : 0);
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

/*********************************************************************/
/*                             TODO
    0. Title with
        - Name
        - cheat
        - reset
        - notification
        - bow
        - score
    1. check for obstacles after moving to new square
    2. make cheat button work
    3. make reset button work (call ramdomize)
    4.  - - -
*/









