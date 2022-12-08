package com.example.customtts.ui.home;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.customtts.R;
import com.example.customtts.databinding.FragmentHomeBinding;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextToSpeech t1;
    TextToSpeech t2;
    //final MediaPlayer mp = MediaPlayer.create(this, R.raw.voice_gaster_1_new);;
    SoundPool pl = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText ed1 =(EditText)root.findViewById(R.id.enterText);
        Button b1 = (Button)root.findViewById(R.id.enterButton);

        t1 = new TextToSpeech(root.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.addSpeech("dolor", "raw/voice_gaster_1.wav");
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        t2 = new TextToSpeech(root.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t2.setLanguage(Locale.UK);
                    //t2.setSpeechRate((float) 0.8);
                    t2.setPitch(0.1f);
                }
            }
        });

        pl.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                // The onLoadComplet method is called when a sound has completed loading.
                soundPool.play(sampleId, 1f, 1f, 0, 0, 1f);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();

                //Toast.makeText(root.getContext(), toSpeak,Toast.LENGTH_SHORT).show();

                //t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                playTTS(root, toSpeak);
                try {
                    playBeeps(root);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //t2.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);

            }
        });

        return root;
    }

    public void playTTS(View root, String toSpeak)
    {
        t2.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
    }

    public void playBeeps(View root) throws InterruptedException {

        Runnable runnable = new Runnable() {
            public void run() {

                while (t2.isSpeaking()) {
                    int randInt = (int) (Math.random() * (7 - 1 + 1) + 1);
                    switch (randInt) {
                        case 1:
                            int sound1 = pl.load(root.getContext(), R.raw.voice_gaster_1, 0);
                            break;
                        case 2:
                            pl.load(root.getContext(), R.raw.voice_gaster_2, 0);
                            break;
                        case 3:
                            pl.load(root.getContext(), R.raw.voice_gaster_3, 0);
                            break;
                        case 4:
                            pl.load(root.getContext(), R.raw.voice_gaster_4, 0);
                            break;
                        case 5:
                            pl.load(root.getContext(), R.raw.voice_gaster_5, 0);
                            break;
                        case 6:
                            pl.load(root.getContext(), R.raw.voice_gaster_6, 0);
                            break;
                        case 7:
                            pl.load(root.getContext(), R.raw.voice_gaster_7, 0);
                            break;
                    }
                    pl.load(root.getContext(),R.raw.three_quater_seconds_of_silence,1);
                    pl.load(root.getContext(),R.raw.three_quater_seconds_of_silence,1);


                    if(!t2.isSpeaking()){
                        break;
                    }
                    System.out.println("T2 is speaking");
                }
            }
        };

        Thread mythread = new Thread(runnable);
        mythread.start();
        /*
        while(t2.isSpeaking()){
            mythread.start();
            mythread.sleep(100);
            wait(100);
        }*/
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            //t1.shutdown();
        }
        if(t2 !=null){
            t2.stop();
            //t1.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

