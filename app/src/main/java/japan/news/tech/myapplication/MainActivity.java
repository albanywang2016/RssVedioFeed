package japan.news.tech.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private int position = 0;
    private VideoView videoView;
    private ProgressDialog progressDialog;

    private MediaController mediaController;
    private Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.videoView);

        if(mediaController == null){
            mediaController = new MediaController(MainActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("android video example");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //start the video
        startVideo();
    }

    void startVideo() {

        //String videoUrl = "http://n24-cdn-video.id83.oke.iijcdn.jp/media/v1/pmp4/static/clear/3974243805001/6e57f5fe-ba70-4a2b-a332-884106960f1f/mid.mp4";
        //String videoUrl = "https://pc.tedcdn.com/talk/stream/2017/None/KateMarvel_2017-64k.mp4";
        //String videoUrl = "https://download.ted.com/talks/JorgeRamos_2017U-64k.mp4";
        //String videoUrl = "http://pa03e12e06.dmc.nico/vod/ht2_nicovideo/nicovideo-sm31545764_25c918868e657a2f48ebbfcfded88567e9df4436d2cbc32e46689d391e0f1494?ht2_nicovideo=69240923.k38wbh_ot8zsr_4z9k3lkoau66";
        String videoUrl = "http://pa03e12e06.dmc.nico/vod/ht2_nicovideo/nicovideo-sm31545764_25c918868e657a2f48ebbfcfded88567e9df4436d2cbc32e46689d391e0f1494?ht2_nicovideo=69240923.knffzk_ot91uj_1l25vgowyjduf";
        //String videoUrl = "http://pa015db105.dmc.nico/vod/ht2_nicovideo/nicovideo-sm31577580_50951c179c25b63c4b6c832dd0ba869fd36c9d14543f4d6f561fc7704a2ec0de?ht2_nicovideo=69240923.k776mw_ot9105_27wwgt1ogeear";
        //String videoUrl = "https://www3.nhk.or.jp/news/html/20170717/movie/k10011062421_201707172117_201707172119.html?movie=false";
        try{
            thumbnail = ThumbnailUtils.createVideoThumbnail(videoUrl, MediaStore.Images.Thumbnails.MINI_KIND);
            Uri video = Uri.parse(videoUrl);
            videoView.setVideoURI(video);
        }catch(Exception e){
            System.out.print("Error" + e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
                videoView.seekTo(position);
                videoView.start();

            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);
    }
}
