package moviles.pablohiguero.appcorreo.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import moviles.pablohiguero.appcorreo.Model.Mail; // Importa tu modelo
import moviles.pablohiguero.appcorreo.R;
import moviles.pablohiguero.appcorreo.fragments.DetailsFragment;

public class MailContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_content);

        // Recuperar datos sueltos del Intent
        if (getIntent().getExtras() != null) {
            String subject = getIntent().getStringExtra("subject");
            String sender = getIntent().getStringExtra("sender");
            String message = getIntent().getStringExtra("message");

            // TRUCO: Creamos un objeto Mail auxiliar con esos datos.
            // Usamos el constructor que tienes en tu modelo: new Mail(subject, message, sender)
            Mail tempMail = new Mail(subject, message, sender);

            // Buscar el fragmento
            DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);

            // Si el fragmento existe, le pasamos el OBJETO completo
            if (fragment != null) {
                fragment.renderMail(tempMail);
            }
        }
    }
}