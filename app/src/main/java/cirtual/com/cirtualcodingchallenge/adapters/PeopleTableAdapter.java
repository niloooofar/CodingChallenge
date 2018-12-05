package cirtual.com.cirtualcodingchallenge.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import cirtual.com.cirtualcodingchallenge.R;
import cirtual.com.cirtualcodingchallenge.Utility.Utility;
import cirtual.com.cirtualcodingchallenge.models.Person;

public class PeopleTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context context;
    public PeopleTableAdapter.PersonClickListener clickListener;
    private ArrayList<Person> friends;
    private ArrayList<Person> copyList = new ArrayList<>();

    public PeopleTableAdapter(PersonClickListener clickListener, Context context, ArrayList<Person> friends) {
        this.clickListener = clickListener;
        this.context = context;
        this.friends = friends;
        copyList.addAll(friends);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case Person.FRIEND_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_view_holder, parent, false);
                return new PersonViewHolder(view);

            case Person.BEST_FRIEND_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_friend_view_holder, parent, false);
                return new BestFriendViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Person currentPerson = friends.get(position);

        switch (currentPerson.getType()) {

            case Person.FRIEND_TYPE:
                ((PersonViewHolder) holder).bindPerson(currentPerson);
                break;

            case Person.BEST_FRIEND_TYPE:
                ((BestFriendViewHolder) holder).bindPerson(currentPerson);
                break;
        }
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtBirthday;
        private TextView txtEmail;
        private TextView txtDescription;
        private ImageView imgPerson;

        PersonViewHolder(View v) {
            super(v);

            txtName = v.findViewById(R.id.txtName);
            txtBirthday = v.findViewById(R.id.txtBirthday);
            txtEmail = v.findViewById(R.id.txtEmail);
            txtDescription = v.findViewById(R.id.txtDescription);
            imgPerson = v.findViewById(R.id.imgPerson);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onPersonClick(getAdapterPosition(), imgPerson, true);
                    }
                }
            });
        }

        void bindPerson(Person person) {

            txtName.setText(person.getName());

            String birthday = Utility.getBirthdayFormat(person.getBirthday());
            txtBirthday.setText(birthday);

            if (Utility.isPersonBirthMonth(birthday)) {
                txtBirthday.setTextColor(Color.RED);
            } else {
                txtBirthday.setTextColor(Color.BLACK);
            }

            txtEmail.setText(person.getEmail());
            txtDescription.setText(person.getDescription());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_person_black_50dp);
            requestOptions.error(R.drawable.ic_error_outline_black_50dp);
            requestOptions.circleCropTransform();

            Glide.with(context)
                    .load(person.getImageUrl())
                    .apply(requestOptions)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgPerson);
        }
    }

    public class BestFriendViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtBirthday;
        private TextView txtEmail;
        private TextView txtDescription;
        private ImageView imgPerson;
        private ImageView icCloseFriend;

        BestFriendViewHolder(View v) {
            super(v);

            txtName = v.findViewById(R.id.txtName);
            txtBirthday = v.findViewById(R.id.txtBirthday);
            txtEmail = v.findViewById(R.id.txtEmail);
            txtDescription = v.findViewById(R.id.txtDescription);
            imgPerson = v.findViewById(R.id.imgPerson);
            icCloseFriend = v.findViewById(R.id.icCloseFriend);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onPersonClick(getAdapterPosition(), imgPerson, false);
                    }
                }
            });
        }

        void bindPerson(Person person) {

            txtName.setText(person.getName());

            String birthday = Utility.getBirthdayFormat(person.getBirthday());
            txtBirthday.setText(birthday);

            if (Utility.isPersonBirthMonth(birthday)) {
                txtBirthday.setTextColor(Color.YELLOW);
            } else {
                txtBirthday.setTextColor(Color.WHITE);
            }

            txtEmail.setText(person.getEmail());
            txtDescription.setText(person.getDescription());

            if (person.isBestFriend()) {
                icCloseFriend.setVisibility(View.VISIBLE);
            } else {
                icCloseFriend.setVisibility(View.GONE);
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_person_black_50dp);
            requestOptions.error(R.drawable.ic_error_outline_black_50dp);
            requestOptions.circleCropTransform();

            Glide.with(context)
                    .load(person.getImageUrl())
                    .apply(requestOptions)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgPerson);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Person person = friends.get(position);
        if (person.isBestFriend()) {
            person.setType(Person.BEST_FRIEND_TYPE);
            return Person.BEST_FRIEND_TYPE;
        } else {
            person.setType(Person.FRIEND_TYPE);
            return Person.FRIEND_TYPE;
        }
    }

    public void filterName(String queryText) {

        friends.clear();

        if (queryText.isEmpty()) {
            friends.addAll(copyList);
        } else {

            for (Person person : copyList) {
                if (person.getName().toLowerCase().contains(queryText.toLowerCase())) {
                    friends.add(person);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface PersonClickListener {
        void onPersonClick(int postion, View view, boolean curve);
    }
}
