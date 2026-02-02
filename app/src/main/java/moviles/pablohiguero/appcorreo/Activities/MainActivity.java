package moviles.pablohiguero.appcorreo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

import io.realm.Realm;
import moviles.pablohiguero.appcorreo.Model.Mail;
import moviles.pablohiguero.appcorreo.R;
import moviles.pablohiguero.appcorreo.Utils.Util;
import moviles.pablohiguero.appcorreo.fragments.DataFragment;
import moviles.pablohiguero.appcorreo.fragments.DetailsFragment;

public class MainActivity extends FragmentActivity implements DataFragment.DataListener {

    private Realm realm;
    private boolean isMultiPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Realm and load data before creating the view.
        realm = Realm.getDefaultInstance();
        loadSampleData();

        setContentView(R.layout.activity_main);

        DataFragment dataFragment = (DataFragment) getSupportFragmentManager().findFragmentById(R.id.dataFragment);
        View detailsContainer = findViewById(R.id.detailsFragment);
        isMultiPanel = (detailsContainer != null);
    }

    @Override
    public void sendData(Mail mail) {
        if (isMultiPanel) {
            DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
            if (detailsFragment != null) {
                detailsFragment.renderMail(mail);
            }
        } else {
            Intent intent = new Intent(this, MailContentActivity.class);
            intent.putExtra("subject", mail.getSubject());
            intent.putExtra("message", mail.getMessage());
            intent.putExtra("sender", mail.getSenderName());
            startActivity(intent);
        }
    }

    private void loadSampleData() {
        if (realm.where(Mail.class).count() == 0) {
            realm.executeTransaction(r -> {
                r.copyToRealm(Util.getDummyData());
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
