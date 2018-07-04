package codefathers.tripalert.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;

import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;

public class ContactsAdapter extends BaseAdapter {

    Activity activity;
    List<AppUser> contacs;
    LayoutInflater inflater;

    public ContactsAdapter(Activity activity) {
        this.activity = activity;
    }

    public ContactsAdapter(Activity activity, List<AppUser> contacs) {
        this.activity = activity;
        this.contacs = contacs;

        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return contacs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void updateRecords(List<AppUser> contacs){
        this.contacs = contacs;

        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if(view == null) {
            view = inflater.inflate(R.layout.contact_listview, viewGroup, false);

            holder = new ViewHolder();

            holder.tvName = (TextView) view.findViewById(R.id.tv_Name);
            holder.tvNumber = (TextView) view.findViewById(R.id.tv_Number);
            holder.cbCheckBox = (CheckBox) view.findViewById(R.id.cb_CheckBox);

            view.setTag(holder);
        }
        else holder = (ViewHolder)view.getTag();

        AppUser contact = contacs.get(i);
        holder.tvName.setText(contact.getUserName());
        holder.tvNumber.setText(contact.getPhoneNumber());

        if(contact.isChecked()){
            holder.cbCheckBox.setChecked(false);
        }
        else holder.cbCheckBox.setChecked(true);

        return view;
    }

    class ViewHolder {

        TextView tvName;
        TextView tvNumber;
        CheckBox cbCheckBox;
    }

}
