package com.colmenacloud.comparador.investimentos;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.colmenacloud.comparador.investimentos.principal.Principal;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Principal principal = new Principal();
        String mensagem = principal.exibirMensagem();

        TextView textView = new TextView(this);
        textView.setText(mensagem);
        textView.setTextSize(24);
        setContentView(textView);
    }
}
