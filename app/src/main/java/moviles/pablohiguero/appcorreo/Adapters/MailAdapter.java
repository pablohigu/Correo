package moviles.pablohiguero.appcorreo.Adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import moviles.pablohiguero.appcorreo.Model.Mail;
import moviles.pablohiguero.appcorreo.R;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder> {

    private List<Mail> mailList;
    private OnItemClickListener listener;

    public MailAdapter(List<Mail> mailList, OnItemClickListener listener) {
        this.mailList = mailList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mail, parent, false);
        return new MailViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MailViewHolder holder, int position) {
        holder.assignData(mailList.get(position));
    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }

    public static class MailViewHolder extends RecyclerView.ViewHolder {
        TextView iconText, subject, message, sender;
        private OnItemClickListener listener;

        public MailViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            iconText = itemView.findViewById(R.id.icon_text);
            subject = itemView.findViewById(R.id.textView_subject);
            message = itemView.findViewById(R.id.textView_message);
            sender = itemView.findViewById(R.id.textView_sender);
        }

        public void assignData( Mail mail) {
            subject.setText(mail.getSubject());
            message.setText(mail.getMessage());
            sender.setText(mail.getSenderName());

            if (mail.getSenderName() != null && !mail.getSenderName().isEmpty()) {
                iconText.setText(String.valueOf(mail.getSenderName().charAt(0)));
            } else {
                iconText.setText("?");
            }
            String colorHex = mail.getColor();
            // gris por defecto
            if (colorHex == null || colorHex.isEmpty()) {
                colorHex = "#CCCCCC";
            }
            if (!colorHex.startsWith("#")) {
                colorHex = "#" + colorHex;
            }
            try {
                int color = Color.parseColor(colorHex);
                iconText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
            } catch (IllegalArgumentException e) {
                iconText.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(mail);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Mail mail);
    }
}