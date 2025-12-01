package moviles.pablohiguero.appcorreo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import moviles.pablohiguero.appcorreo.Model.Mail;
import moviles.pablohiguero.appcorreo.R;
import moviles.pablohiguero.appcorreo.Utils.Util;
import moviles.pablohiguero.appcorreo.fragments.DataFragment;
import moviles.pablohiguero.appcorreo.fragments.DetailsFragment;

public class MainActivity extends FragmentActivity implements DataFragment.DataListener {

    private Realm realm;
    private List<Mail> mailList;
    private RealmResults<Mail> realmResults;
    private boolean isMultiPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        // Cargamos datos (Si hace falta) y luego configuramos la lista
        loadSampleData();
        realmResults = realm.where(Mail.class).findAll();
        mailList = realm.copyFromRealm(realmResults);

        // 2. Configurar DataFragment (Lista)
        DataFragment dataFragment = (DataFragment) getSupportFragmentManager().findFragmentById(R.id.dataFragment);
        if (dataFragment != null) {
            dataFragment.setMailList(mailList);
        }

        // 4. Detectar si es Tablet (Multipanel)
        View detailsContainer = findViewById(R.id.detailsFragment);
        isMultiPanel = (detailsContainer != null);
    }
    @Override
    public void sendData(Mail mail) {

        if (isMultiPanel) {
            // === CASO TABLET ===
            DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
            if (detailsFragment != null) {
                detailsFragment.renderMail(mail);
            }
        } else {
            // === CASO MÓVIL ===
            Intent intent = new Intent(this, MailContentActivity.class);
            // Pasamos los datos como Strings porque MailContentActivity espera eso
            intent.putExtra("subject", mail.getSubject());
            intent.putExtra("message", mail.getMessage());
            intent.putExtra("sender", mail.getSenderName());

            startActivity(intent);
        }
    }

    private void loadSampleData() {
        if (realm.where(Mail.class).count() == 0) {
            // --- CAMBIO: Usamos Async para evitar el error "RealmException: UI thread" ---
            realm.executeTransactionAsync(bgRealm -> {
                List<Mail> samples = Util.getDummyData();
                bgRealm.copyToRealm(samples);
            }, () -> {
                // Éxito: Actualizamos la lista local si es necesario
                realmResults = realm.where(Mail.class).findAll();
                mailList = realm.copyFromRealm(realmResults);
                DataFragment dataFragment = (DataFragment) getSupportFragmentManager().findFragmentById(R.id.dataFragment);
                if (dataFragment != null) {
                    dataFragment.setMailList(mailList);
                }
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