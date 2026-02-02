package moviles.pablohiguero.appcorreo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.realm.Realm;
import moviles.pablohiguero.appcorreo.Adapters.MailAdapter;
import moviles.pablohiguero.appcorreo.Model.Mail;
import moviles.pablohiguero.appcorreo.R;

public class DataFragment extends Fragment {

    private RecyclerView recyclerView;
    private MailAdapter adapter;
    private DataListener callback;
    private Realm realm;
    private List<Mail> mailList;

    public DataFragment() {
    }
    public interface DataListener {
        void sendData(Mail mail);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (DataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DataListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        realm = Realm.getDefaultInstance();
        mailList = realm.copyFromRealm(realm.where(Mail.class).findAll());
        setMailList(mailList);
        return view;
    }

    public void setMailList(List<Mail> mails) {
        MailAdapter.OnItemClickListener listener = new MailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Mail mail) {
                callback.sendData(mail);
            }
        };
        adapter = new MailAdapter(mails, listener);
        recyclerView.setAdapter(adapter);
    }
}