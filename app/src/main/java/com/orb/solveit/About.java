package com.orb.solveit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView webLink;
        ImageView fb_link,twitter_link;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        webLink= findViewById(R.id.dev_web);

        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bappy.cf"));
                About.this.startActivity(intent);
            }
        });

        fb_link= findViewById(R.id.fb_llink);
        fb_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/ohidurbappy"));
                About.this.startActivity(intent);
            }
        });

        twitter_link= findViewById(R.id.twitter_llink);
        twitter_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/ohidurbappy"));
                About.this.startActivity(intent);
            }
        });

    }
}
