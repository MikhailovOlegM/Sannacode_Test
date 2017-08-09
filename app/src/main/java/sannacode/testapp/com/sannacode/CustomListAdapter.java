package sannacode.testapp.com.sannacode;

import java.util.ArrayList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {


  private final Activity context;
  private final ArrayList<String> contactID;
  //private final ArrayList<String> phone;
  private final ArrayList<Contact> contacts;
  private final Integer imageId;


  public CustomListAdapter(Activity context, ArrayList<Contact> contacts, Integer imageId, ArrayList<String> contactID) {
    super(context, R.layout.list_single, contactID);
    this.context = context;
    this.contacts = contacts;
    this.imageId = imageId;
    this.contactID = contactID;
  }

  public void add(Contact contact, String ID) {
    contacts.add(contact);
    contactID.add(ID);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    View rowView= inflater.inflate(R.layout.list_single, null, true);
    TextView nameText = (TextView) rowView.findViewById(R.id.name);
    TextView phoneText = (TextView) rowView.findViewById(R.id.number);

    ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
    nameText.setText(contacts.get(position).getName());
    phoneText.setText(contacts.get(position).getEmail());

    imageView.setImageResource(imageId);
    return rowView;
  }

  public void remote(int position) {

  }
}
