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

    public void place_objects_randomly(){
        actor = 0;
    }
    public String what_is_here(int row, int col){
        return "nothing yet";
    }

    public void moveUp(){
        int loc = (actor-4+16)%16;
        actor = loc;
    }
    public void moveDown(){
        int loc = (actor+4+16)%16;

        /*****************************************************/
        // Toast
        Context context = getApplicationContext();
        CharSequence text = "MoveDown";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        /*****************************************************/


        /*
            New idea.
            1. relace all tiles with empty
            2. place actor at loc_ation
         */

        for(int i=0;i<16;i++) {
            ImageView img = findViewById(IMGVIEW[i]);
            img.setImageResource(R.drawable.wumpus1);
        }

        int image = R.drawable.wumpus2;
        ImageView img = findViewById(IMGVIEW[loc]);
        img.setImageResource(image);

        // get the image
        /*
        String imgStrActor = "imageView" + Integer.toString(actor);
        String imgStrNext = "imageView" + Integer.toString(loc);

        int resIDActor = getResources().getIdentifier(imgStrActor, "id", "edu.sdsmt.Hofer_Gabriel");
        int resIDNext = getResources().getIdentifier(imgStrNext, "id", "edu.sdsmt.Hofer_Gabriel");

        ImageView imgActor = (ImageView) findViewById(resIDActor);
        ImageView imgNext = (ImageView) findViewById(resIDNext);
        ImageView temp = new ImageView(null);

        // swap images using TEMP variable
        temp.setImageDrawable(imgActor.getDrawable());
        imgActor.setImageDrawable(imgNext.getDrawable());
        imgNext.setImageDrawable(temp.getDrawable());
        */




        actor = loc;
    }
    public void moveLeft(){
        int loc = (actor-1+16)%16;

        actor = loc;
    }
    public void moveRight(){
        int loc = (actor+1+16)%16;

        actor = loc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
