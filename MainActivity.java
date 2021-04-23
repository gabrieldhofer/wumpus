/*

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

    private StateMachine stateMachine = new StateMachine();
    private int actor = 0, score = 0, bow_and_quiver=0, arrows=0;
    private ArrayList<String> GRID = new ArrayList<>();
    private ArrayList<String> CHEAT = new ArrayList<>();
    private final int[] IMGVIEW = {
            R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7,
            R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11,
            R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15
    };
    private int[] VISITED = new int[16];

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
        actor = (actor-1) + ((actor-1+4)/4 != (actor+4)/4 ? 4 : 0);
        refresh();
    }
    private void moveRight(){
        actor = (actor+1) + ((actor+1+4)/4 != (actor+4)/4 ? -4 : 0);
        refresh();
    }

    private void pickup(){
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


    /************************************************************************/
    /*                          Arrow Functions                             */
    /************************************************************************/
    private int arrows_enabled = 1;
    private void enable(){
        arrows_enabled = 1;
    }
    private void disable(){
        arrows_enabled = 0;
    }
    private void shootArrow(int room1, int room2){
        if(arrows_enabled==0) return;
        if(arrows<=0) return;
        if(GRID.get(room1)=="wumpus") {
            GRID.set(room1, "--");
            arrows-=1;
            ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
        }
        if(GRID.get(room2)=="wumpus"){
            GRID.set(room1, "--");
            arrows-=1;
            ((TextView) findViewById(R.id.arrows)).setText("Arrows: "+ arrows);
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

        Button pickup = findViewById(R.id.pickup);
        pickup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pickup();
            }
        });

        Button enable = findViewById(R.id.enable);
        enable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enable();
            }
        });

        Button disable = findViewById(R.id.disable);
        disable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                disable();
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

    }
}
