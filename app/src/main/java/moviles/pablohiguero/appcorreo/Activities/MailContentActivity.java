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
        if (getIntent().getExtras() != null) {
            String subject = getIntent().getStringExtra("subject");
            String sender = getIntent().getStringExtra("sender");
            String message = getIntent().getStringExtra("message");
            Mail tempMail = new Mail(subject, message, sender);
            DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
            if (fragment != null) {
                fragment.renderMail(tempMail);
            }
        }
    }
}