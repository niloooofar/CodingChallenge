package cirtual.com.cirtualcodingchallenge.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cirtual.com.cirtualcodingchallenge.R;
import cirtual.com.cirtualcodingchallenge.Utility.Utility;
import cirtual.com.cirtualcodingchallenge.models.Person;

public class PersonDetailActivity extends AppCompatActivity {

    private TextView txtPersonName, txtPersonBirthday, txtPersonEmail, txtPersonDescription;
    private ImageView imgPerson, icCloseFriend;

    public static final String EXTRA_CURVE = "EXTRA_CURVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        boolean curve = getIntent().getBooleanExtra(EXTRA_CURVE, false);
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this)
                .inflateTransition(curve ? R.transition.curve : R.transition.move));


        txtPersonName = findViewById(R.id.txtPersonName);
        txtPersonEmail = findViewById(R.id.txtPersonEmail);
        txtPersonBirthday = findViewById(R.id.txtPersonBirthday);
        txtPersonDescription = findViewById(R.id.txtPersonDescription);
        imgPerson = findViewById(R.id.imgPerson);
        icCloseFriend = findViewById(R.id.icCloseFriend);

        Person person = (Person) getIntent().getParcelableExtra("person");

        txtPersonName.setText(person.getName());
        txtPersonBirthday.setText(Utility.getBirthdayFormat(person.getBirthday()));
        txtPersonEmail.setText(person.getEmail());
        txtPersonDescription.setText(person.getDescription());

        if (person.isBestFriend()) {
            icCloseFriend.setVisibility(View.VISIBLE);
        } else {
            icCloseFriend.setVisibility(View.GONE);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_person_white_50dp);
        requestOptions.error(R.drawable.ic_error_outline_white_50dp);
        requestOptions.circleCropTransform();

        Glide.with(this)
                .load(person.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(imgPerson);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
