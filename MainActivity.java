/*
https://www.geeksforgeeks.org/how-to-create-landscape-layout-in-android-studio/
https://stackoverflow.com/questions/29458189/android-studio-layout-land-folder-not-appearing
https://stackoverflow.com/questions/52547657/the-layout-layout-in-layout-has-no-declaration-in-the-base-layout-folder-erro


Mark off what items are complete, and put a P if partially complete. If 'P' include how to test what is working for partial credit below the checklist line.

You must “reasonably” complete the lower tiers before gaining points for the later tiers. By “reasonably,” I can reasonably assume you honestly thought it was complete.

1a and 1b are the same tier. They are noted only for category clarity.

Tierless: State machine*	24
_x_ State machine framework is present
_P_ Framework controls bow, arrows, pickup
_P_ Framework controls exit or not
_P_ End conditions


_X_ Tierless: rotation (-4 each item missed) *	8


1a: Layout *	24
_x_ Arrow buttons
_P_ Player select exit and opens
        (I'm not sure what this means..)
_x_ Majority of area the game area
_x_ Aspect kept, and resize to new devices works
_x_ Different layout for portrait and landscape
        (created layout-land.xml file in the res/layout-land/ directory)

1b: Game logic	30
_x_ Bat event (loss of bow, arrows, and new position)
_x_ Rooms start hidden, and then revealed
_x_ Shooting the arrow components (Wumpus and loss)
_P_ Player select works *
    (didn't get the extended floating point action button to work. However,
    I still created a couple of floating point action buttons for changing
    the player icon.)

2 Tier: End game	14
__ End dialog exists
__ Both end dialogs open at right time
__ End activity score/loss reason correct (50% each)


3: extensions 	30
   Extension 1: 10pts images: objects on the grid are images (wumpus, arrow, bat, etc.)
Etc.

* These have additional restrictions to gain full points 

The grade you compute is the starting point for course staff, who reserve the right to change the grade if they disagree with your assessment and to deduct points for other issues they may encounter, such as errors in the submission process, naming issues, etc.


*/
package edu.sdsmt.Hofer_Gabriel;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button closeButton;
    AlertDialog.Builder builder;


    private StateMachine stateMachine = new StateMachine();
    private int[] ARROW = new int[16];
    private int actor = 0, score = 0;
    private ArrayList<String> GRID = new ArrayList<>();
    private ArrayList<String> CHEAT = new ArrayList<>();
    private final int[] IMGVIEW = {
            R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7,
            R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11,
            R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15
    };
    private int[] VISITED = new int[16];
    private String player = "wumpus5";

    private void init(){
        // initialize visited array to not-visited except for (0,0)
        for(int i=0;i<16;i++) VISITED[i]=0;
        VISITED[0]=1;

        // reset arrows array
        for(int i=0;i<16;i++) ARROW[i]=0;

        place_objects_randomly();
        score=0;
        stateMachine.lose_bow_and_quiver_arrows();
        ((TextView) findViewById(R.id.bow_and_quiver)).setText("Bow and Quiver: "+ stateMachine.get_bow_and_quiver());
        ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ stateMachine.get_arrows());
        ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ stateMachine.get_arrows());
        ((TextView) findViewById(R.id.notifications)).setText("Notifications: ");
        ((TextView) findViewById(R.id.score)).setText("Score: "+ score);
        for(int i=1;i<16;i++) {
            ImageView img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus1);
        }
        ImageView img = findViewById(IMGVIEW[0]);

        switch(player){
            case "wumpus5":
                img.setImageResource(R.drawable.wumpus5);
                break;
            case "dot":
                img.setImageResource(R.drawable.dot);
                break;
            case "square":
                img.setImageResource(R.drawable.square);
                break;
        }
        // reset statemachine
        stateMachine = new StateMachine();
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
        GRID.add(0,"--");
        CHEAT = GRID;
    }

    private void cheat(){
        ImageView img;
        for(int i=0;i<16;i++) {
            if(GRID.get(i).equals("exit")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.exit);
            } else if(GRID.get(i).equals("pit")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.pit);
            } else if(GRID.get(i).equals("bat")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.bat);
            } else if(GRID.get(i).equals("bow_and_quiver")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.bow_quiver);
            } else if(GRID.get(i).equals("arrow1")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.arrow);
            } else if(GRID.get(i).equals("arrow2")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.arrow);
            } else if(GRID.get(i).equals("wumpus")){
                img = findViewById(IMGVIEW[i]);
                img.setImageResource(R.drawable.wumpus);
            }
        }
    }

    private void mark_visited_squares(){
        ImageView img;
        for(int i=0;i<16;i++) {
            if (VISITED[i]!=0) {
                switch(GRID.get(i)){
                    case "--":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.wumpus4);
                        break;
                    case "pit":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.pit);
                        break;
                    case "bat":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.bat);
                        break;
                    case "arrow1":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.arrow);
                        break;
                    case "arrow2":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.arrow);
                        break;
                    case "wumpus":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.wumpus);
                        break;
                    case "bow_and_quiver":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.bow_quiver);
                        break;
                    case "exit":
                        img = findViewById(IMGVIEW[i]);
                        img.setImageResource(R.drawable.exit);
                        break;
                }
                //ImageView img = findViewById(IMGVIEW[i]);
                //img.setImageResource(R.drawable.wumpus4);
            }
        }
    }

    public void goto_random_place(){
        Random rdm = new Random();
        actor = rdm.nextInt(101) % 16;
        stateMachine.lose_bow_and_quiver_arrows();
        refresh();
    }

    private void gameover(){
        Context context = getApplicationContext();
        CharSequence text = "Dialog: Game Over";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
            gameover();
            return true;
        } else if(GRID.get(i).equals("bat")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.bat);
            goto_random_place();
            return true;
        } else if(GRID.get(i).equals("bow_and_quiver")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.bow_quiver);
            return true;
        } else if(GRID.get(i).equals("arrow1")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.arrow);
            return true;
        } else if(GRID.get(i).equals("arrow2")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.arrow);
            return true;
        } else if(GRID.get(i).equals("wumpus")){
            img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus);
            // NOTIFY GAME OVER
            ((TextView) findViewById(R.id.notifications)).setText("Notifications: Game Over");
            gameover();
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
        VISITED[0]=1;
        VISITED[actor]=1;

        // pickup bow_andd_quiver or arrow if possible
        pickup();

        // get the right player
        int image = R.drawable.wumpus5;
        switch(player){
            case "wumpus5":
                image = R.drawable.wumpus5;
                break;
            case "dot":
                image = R.drawable.dot;
                break;
            case "square":
                image = R.drawable.square;
                break;
        }

        // display obstacles/objects instead of player
        if(check_for_obstacles_and_stuff(actor)) return;
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
        actor = (actor-1) + ((actor-1+4)/4 != (actor+4)/4 ? 4 : 0);
        refresh();
    }
    private void moveRight(){
        actor = (actor+1) + ((actor+1+4)/4 != (actor+4)/4 ? -4 : 0);
        refresh();
    }

    private void pickup() {
        switch(GRID.get(actor)) {
            case "arrow1":
                if (stateMachine.get_bow_and_quiver() > 0 && ARROW[actor]==0) // can only pickup arrow if has bow and quiver
                    stateMachine.found_arrow();
                ARROW[actor]=1;
                ((TextView) findViewById(R.id.arrows)).setText("Arrows: " + stateMachine.get_arrows());
                break;
            case "arrow2":
                if (stateMachine.get_bow_and_quiver() > 0 && ARROW[actor]==0)  // can only pickup arrow if has bow and quiver
                    stateMachine.found_arrow();
                ARROW[actor]=1;
                ((TextView) findViewById(R.id.arrows)).setText("Arrows: " + stateMachine.get_arrows());
                break;
            case "bow_and_quiver":
                stateMachine.found_bow();
                ((TextView) findViewById(R.id.bow_and_quiver)).setText("Bow and Quiver: " + stateMachine.get_bow_and_quiver());
                break;
        }
    }

    /************************************************************************/
    /*                          Arrow Functions                             */
    /************************************************************************/
    private void shootArrow(int room1, int room2){
        if(stateMachine.get_arrows_enabled()==0) return;
        if(stateMachine.get_arrows() <=0) return;
        if(GRID.get(room1).equals("wumpus")) {
            ((TextView) findViewById(R.id.notifications)).setText("Notifications: shot wumpus");

            GRID.set(room1, "--");
            ImageView img = findViewById(IMGVIEW[room1]);
            img.setImageResource(R.drawable.wumpus1);
            stateMachine.arrow_shot();
            ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ stateMachine.get_arrows());
        }
        if(GRID.get(room2).equals("wumpus")){
            ((TextView) findViewById(R.id.notifications)).setText("Notifications: shot wumpus");

            GRID.set(room2, "--");
            ImageView img = findViewById(IMGVIEW[room2]);
            img.setImageResource(R.drawable.wumpus1);
            stateMachine.arrow_shot();
            ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ stateMachine.get_arrows());
        }
    }
    private void upArrow(){
        int room1 = (actor-4+16)%16;
        int room2 = (room1-4+16)%16;
        shootArrow(room1, room2);
        refresh();
    }
    private void leftArrow(){
        int room1 = (actor-1) + ((actor-1+4)/4 != (actor+4)/4 ? 4 : 0);
        int room2 = (room1-1) + ((room1-1+4)/4 != (room1+4)/4 ? 4 : 0);
        shootArrow(room1, room2);
        refresh();
    }
    private void downArrow(){
        int room1 = (actor+4+16)%16;
        int room2 = (room1+4+16)%16;
        shootArrow(room1, room2);
        refresh();
    }
    private void rightArrow(){
        int room1 = (actor+1) + ((actor+1+4)/4 != (actor+4)/4 ? -4 : 0);
        int room2 = (room1+1) + ((room1+1+4)/4 != (room1+4)/4 ? -4 : 0);
        shootArrow(room1, room2);
        refresh();
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

        Button enable = findViewById(R.id.enable);
        enable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stateMachine.enableArrows();
            }
        });

        Button disable = findViewById(R.id.disable);
        disable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stateMachine.disableArrows();
            }
        });

        /************************************************************************/
        /*                          Arrow Button & Events                       */
        /************************************************************************/
        Button upArrow = findViewById(R.id.upArrow);
        upArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                upArrow();
            }
        });
        Button leftArrow = findViewById(R.id.leftArrow);
        leftArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                leftArrow();
            }
        });
        Button downArrow = findViewById(R.id.downArrow);
        downArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                downArrow();
            }
        });
        Button rightArrow = findViewById(R.id.rightArrow);
        rightArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                rightArrow();
            }
        });

        /************************************************************************/
        /*                          Floating Action Button                      */
        /************************************************************************/
        /* https://stackoverflow.com/questions/31111431/android-vertically-expanded-floating-action-button */
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                player = "dot";
                ImageView img = findViewById(IMGVIEW[actor]);
                img.setImageResource(R.drawable.dot);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                player = "square";
                ImageView img = findViewById(IMGVIEW[actor]);
                img.setImageResource(R.drawable.square);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                player = "wumpus5";
                ImageView img = findViewById(IMGVIEW[actor]);
                img.setImageResource(R.drawable.wumpus5);
            }
        });


        /************************************************************************/
        /*                          Floating Action Button                      */
        /************************************************************************/

        closeButton = (Button) findViewById(R.id.button);
        builder = new AlertDialog.Builder(this);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to close this application ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });





    }
}
