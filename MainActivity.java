/*
Mark off what items are complete, and put a P if partially complete. If 'P' include how to test what is working for partial credit below the checklist line.

        You must “reasonably” complete the lower tiers before gaining points for the later tiers. By “reasonably,” I can reasonably assume you honestly thought it was complete.

        2a and 2b are the same tier. They are noted only for category clarity.

        __ Tierless: rotation (-4pt each item missed) *	12

        1) Layout *	12
        _x_ Game area exists
        _x_ Move buttons
        _x_ Title with your name, cheat, reset, notification, bow, score there


        2a: Game Area *	16
        _x_ Player there
        _x_ 4x4 grid of evenly sized
        _x_ Exit room clearly recolored when reached
        _x_ Player is a black dot (Stickman)


        2b: Game logic 	30
        _x_ Correct starting state
        _x_ Random placement of items
        _x_ Movement
        _x_ Game over note with pit and wumpus
        _x_ Cheat and reset works
        __ Pickup events

        * These have additional restrictions to gain full points 

        The grade you compute is the starting point for course staff, who reserve the right to change the grade if they disagree with your assessment and to deduct points for other issues they may encounter, such as errors in the submission process, naming issues, etc.

*/
package edu.sdsmt.Hofer_Gabriel;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private int actor = 0, score = 0, bow_and_quiver=0, arrows=0;
    private ArrayList<String> GRID, CHEAT;
    private final int[] IMGVIEW = {
            R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7,
            R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11,
            R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15
    };
    private int[] VISITED = new int[16];

    public MainActivity() {
        GRID = new ArrayList<>();
        CHEAT = new ArrayList<>();
    }

    private void init(){
        // initialize visited array to not-visited except for (0,0)
        for(int i=0;i<16;i++) VISITED[i]=0;
        VISITED[0]=1;
        place_objects_randomly();
        score=bow_and_quiver=arrows=0;
        ((TextView) findViewById(R.id.bow_and_quiver)).setText("Bow and Quiver: "+ bow_and_quiver);
        ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
        ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
        ((TextView) findViewById(R.id.notifications)).setText("Notifications: ");
        ((TextView) findViewById(R.id.score)).setText("Score: "+ score);
        for(int i=1;i<16;i++) {
            ImageView img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus1);
        }
        ImageView img = findViewById(IMGVIEW[0]);
        img.setImageResource(R.drawable.wumpus5);
    }

    private void place_objects_randomly(){
        actor = 0; // actor always starts at (0,0), the other objects are random
        GRID.clear();
        GRID.add("exit");
        GRID.add("pit");
        GRID.add("bat");
        GRID.add("bow_and_quiver");
        GRID.add("arrow1");
        GRID.add("arrow2");
        GRID.add("wumpus");
        for(int i=0;i<8;i++)
            GRID.add("--");
        Collections.shuffle(GRID);
        GRID.add(0,"actor");
        CHEAT = GRID;
    }

    private void cheat(){
        ImageView img;
        for(int i=0;i<16;i++) {
            if(CHEAT.get(i).equals("exit")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.exit);
            } else if(CHEAT.get(i).equals("pit")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.pit);
            } else if(CHEAT.get(i).equals("bat")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.bat);
            } else if(CHEAT.get(i).equals("bow_and_quiver")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.bow_quiver);
            } else if(CHEAT.get(i).equals("arrow1")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.arrow);
            } else if(CHEAT.get(i).equals("arrow2")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.arrow);
            } else if(CHEAT.get(i).equals("wumpus")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.wumpus);
            }
        }
    }

    private void mark_visited_squares(){
        for(int i=0;i<16;i++) {
            if (VISITED[i]!=0) {
                ImageView img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.wumpus4);
            }
        }
    }

    private boolean check_for_obstacles_and_stuff(int i){
        ImageView img;
        if(GRID.get(i).equals("exit")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.exit);
            // NOTIFY GAME OVER
            ((TextView) findViewById(R.id.notifications)).setText("Notifications: Winner Winner Chicken Dinner");
            return true;
        } else if(GRID.get(i).equals("pit")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.pit);
            // NOTIFY GAME OVER
            ((TextView) findViewById(R.id.notifications)).setText("Notifications: Game Over");
            return true;
        } else if(GRID.get(i).equals("bat")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.bat);
            return true;
        } else if(GRID.get(i).equals("bow_and_quiver")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.bow_quiver);
            //bow_and_quiver+=1;
            //((TextView) findViewById(R.id.bow_and_quiver)).setText("Bow and Quiver: "+ bow_and_quiver);
            return true;
        } else if(GRID.get(i).equals("arrow1")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.arrow);
            //arrows+=1;
            //((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
            return true;
        } else if(GRID.get(i).equals("arrow2")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.arrow);
            //arrows+=1;
            //((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
            return true;
        } else if(GRID.get(i).equals("wumpus")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus);
            // NOTIFY GAME OVER
            ((TextView) findViewById(R.id.notifications)).setText("Notifications: Game Over");

            return true;
        }
        return false;
    }

    private void refresh(){
        // update score
        score+=1;
        ((TextView) findViewById(R.id.score)).setText("Score: "+ score);

        // all squares to white
        for(int i=0;i<16;i++) {
            ImageView img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus1);
        }

        // set any visited squares to green (for now)
        mark_visited_squares();
        VISITED[actor]=1;

        // check for obstacles and stuff
        if(check_for_obstacles_and_stuff(actor)) return;

        // place actor
        int image = R.drawable.wumpus5;
        ImageView img = findViewById(IMGVIEW[actor]);
        img.setImageResource(image);
    }

    private void moveUp(){
        actor = (actor-4+16)%16;
        refresh();
    }
    private void moveDown(){
        actor = (actor+4+16)%16;
        refresh();
    }
    private void moveLeft(){
        actor = ((actor-1) + ((actor-1)/4 != actor/4 ? 4 : 0) + 16) % 16;
        refresh();
    }
    private void moveRight(){
        actor = ((actor+1) + ((actor+1)/4 != actor/4 ? -4 : 0) + 16) % 16;
        refresh();
    }

    private void pickup(){
        if(GRID.get(actor).equals("arrow1") ||
                GRID.get(actor).equals("arrow2") ||
                GRID.get(actor).equals("bow_and_quiver")){
            ImageView img = findViewById(IMGVIEW[actor]);
            img.setImageResource(R.drawable.wumpus4);
        }
        switch(GRID.get(actor)){
            case "arrow1":
                arrows+=1;
                ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
                break;
            case "arrow2":
                arrows+=1;
                ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
                break;
            case "bow_and_quiver":
                bow_and_quiver+=1;
                ((TextView) findViewById(R.id.bow_and_quiver)).setText("Bow and Quiver: "+ bow_and_quiver);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Button btnMoveUp = findViewById(R.id.moveUp);
        btnMoveUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveUp();
            }
        });

        Button btnMoveDown = findViewById(R.id.moveDown);
        btnMoveDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveDown();
            }
        });

        Button btnMoveLeft = findViewById(R.id.moveLeft);
        btnMoveLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveLeft();
            }
        });

        Button btnMoveRight = findViewById(R.id.moveRight);
        btnMoveRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveRight();
            }
        });

        Button btnCheat = findViewById(R.id.cheat);
        btnCheat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cheat();
            }
        });

        Button btnReset = findViewById(R.id.reset);
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                init();
            }
        });

        Button pickup = findViewById(R.id.pickup);
        pickup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pickup();
            }
        });
    }
}


