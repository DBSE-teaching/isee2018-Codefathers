package codefathers.tripalert.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;

public class ContactsAdapter extends BaseAdapter {

    Activity activity;
    List<AppUser> contacs;
    LayoutInflater inflater;
    boolean isFollowers;
    Map<String,String> contactsDict;

    public ContactsAdapter(Activity activity) {
        this.activity = activity;
    }
    public ContactsAdapter(Activity activity, List<AppUser> contacs, boolean isFollower) {
        this.activity = activity;
        this.contacs = contacs;
        this.isFollowers = isFollowers;
        inflater = activity.getLayoutInflater();
    }
    public ContactsAdapter(Activity activity, List<AppUser> contacs, boolean isFollowers, Map<String,String>contactsDict) {
        this.activity = activity;
        this.contacs = contacs;
        this.isFollowers = isFollowers;
        inflater = activity.getLayoutInflater();
        this.contactsDict = contactsDict;
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

            view.setTag(holder);
        }
        else holder = (ViewHolder)view.getTag();

        AppUser contact = contacs.get(i);
        if(isFollowers){
            String text = "";
            if(contactsDict.get(contact.getPhoneNumber()) !=null){
                text = contactsDict.get(contact.getPhoneNumber())+" ("+contact.getPhoneNumber()+")";
            }
            else{
                text = contact.getPhoneNumber();
            }
            holder.tvName.setText(text);
        }else{
            holder.tvName.setText(contact.getUserName()  );
            holder.tvNumber.setText(contact.getPhoneNumber());

        }

        if(!isFollowers){
            if(contact.isChecked()){
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorNextAction));
            }else{
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorCardBackGround));
            }
        }else{
            if(contact.isChecked()){
                holder.tvNumber.setText("Follows");
                holder.tvNumber.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorArrived));
            }else{
                holder.tvNumber.setText("Unfollowed");
                holder.tvNumber.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorEmergency));
            }
        }

        return view;
    }

    class ViewHolder {

        TextView tvName;
        TextView tvNumber;
    }

}
