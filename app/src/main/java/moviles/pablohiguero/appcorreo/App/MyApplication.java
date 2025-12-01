package moviles.pablohiguero.appcorreo.App;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import moviles.pablohiguero.appcorreo.Model.Mail;

public class MyApplication extends Application {
    public static AtomicInteger MailID= new AtomicInteger();
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        if (results.size()>0){
            return new AtomicInteger(results.max("IdMail").intValue());
        }else{
            return  new AtomicInteger(0);
        }
    }
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        MailID= getIdByTable(realm, Mail.class);
        realm.close();
    }
    private void setUpRealmConfig() {
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }


}
