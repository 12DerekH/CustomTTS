package com.example.customtts.ui.home;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                    t1.addSpeech("Lorem", "");
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        t2 = new TextToSpeech(root.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t2.setLanguage(Locale.GERMAN);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                //Toast.makeText(root.getContext(), toSpeak,Toast.LENGTH_SHORT).show();
                for (Object s: t1.getVoices().toArray()) {
                    System.out.println(s.toString());
                }
                //Toast.makeText(root.getContext(), t1.getVoices().toString(), Toast.LENGTH_LONG).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                t2.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                //System.out.println(ed1.getText());
            }
        });

        return root;
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