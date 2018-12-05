package cirtual.com.cirtualcodingchallenge.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cirtual.com.cirtualcodingchallenge.HelperClasses.OnTransactionListener;
import cirtual.com.cirtualcodingchallenge.HelperClasses.SwipeController;
import cirtual.com.cirtualcodingchallenge.HelperClasses.SwipeControllerActions;
import cirtual.com.cirtualcodingchallenge.R;
import cirtual.com.cirtualcodingchallenge.Utility.Utility;
import cirtual.com.cirtualcodingchallenge.adapters.PeopleTableAdapter;
import cirtual.com.cirtualcodingchallenge.managers.FetchTableManager;
import cirtual.com.cirtualcodingchallenge.models.Person;

public class MainTableActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnTransactionListener<ArrayList<Person>>, SearchView.OnQueryTextListener {

    SwipeController swipeController = null;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PeopleTableAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FetchTableManager fetchTableManager;
    private ArrayList<Person> persons;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);
        initView();

        fetchTableManager = new FetchTableManager(this);
        fetchTableManager.fetchTableData(this);
    }

    private void initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRecyclerView = findViewById(R.id.tbl_people);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        persons = new ArrayList<>();

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fetchTableManager.fetchTableData(this);
    }

    @Override
    public void onSuccess(final ArrayList<Person> var1) {
        swipeRefreshLayout.setRefreshing(false);

        persons.clear();
        persons.addAll(var1);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);

                Person currentPerson = persons.get(position);

                if (Utility.isPersonBirthMonth(Utility.getBirthdayFormat(currentPerson.getBirthday()))) {
                    Utility.sendEmail(MainTableActivity.this, currentPerson);
                } else {
                    Toast.makeText(MainTableActivity.this, "This person birthday is not this month.You can't send email now.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onRightClicked(int position) {
                super.onRightClicked(position);

                Person currentPerson = persons.get(position);

                if (Utility.isPersonBirthMonth(Utility.getBirthdayFormat(currentPerson.getBirthday()))) {
                    Utility.sendEmail(MainTableActivity.this, currentPerson);
                } else {
                    Toast.makeText(MainTableActivity.this, "This person birthday is not this month.You can't send email.", Toast.LENGTH_LONG).show();
                }
            }
        }, var1);

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


        mAdapter = new PeopleTableAdapter(new PeopleTableAdapter.PersonClickListener() {
            @Override
            public void onPersonClick(int postion, View view, boolean curve) {
                Intent intent = new Intent(MainTableActivity.this, PersonDetailActivity.class);
                intent.putExtra("person", persons.get(postion));

                intent.putExtra(PersonDetailActivity.EXTRA_CURVE, curve);

                Bundle bundle = ActivityOptions
                        .makeSceneTransitionAnimation(MainTableActivity.this, view, view.getTransitionName())
                        .toBundle();

                startActivity(intent, bundle);
            }
        }, MainTableActivity.this, persons);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onFailure() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(MainTableActivity.this, "Couldn't refresh feed!", Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search People");
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.filterName(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.filterName(newText);
        return false;
    }
}
