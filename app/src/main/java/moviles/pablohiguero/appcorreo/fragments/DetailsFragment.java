package moviles.pablohiguero.appcorreo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import moviles.pablohiguero.appcorreo.Model.Mail;
import moviles.pablohiguero.appcorreo.R;

public class DetailsFragment extends Fragment {

    private TextView subjectTextView, senderTextView, messageTextView;

    public DetailsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        subjectTextView = view.findViewById(R.id.subjectTextView);
        senderTextView = view.findViewById(R.id.senderTextView);
        messageTextView = view.findViewById(R.id.messageTextView);
        return view;
    }
    public void renderMail(Mail mail) {
        if (mail != null) {
            subjectTextView.setText("Subject: " + mail.getSubject());
            senderTextView.setText("From: " + mail.getSenderName());
            messageTextView.setText(mail.getMessage());
        }
    }
}