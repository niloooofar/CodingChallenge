package cirtual.com.cirtualcodingchallenge.Utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cirtual.com.cirtualcodingchallenge.models.Person;

public class Utility {

    public static String getBirthdayFormat(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        String month = " ";
        String day = " ";
        String year = " ";
        try {
            Date birtDate = format.parse(timeStamp);
            String birthStr = birtDate.toString();
            String[] parts = birthStr.split(" ");
            month = parts[1];
            day = parts[2];
            year = parts[5];
            return month + " " + day + " " + year;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isPersonBirthMonth(String birthdayFormatStr) {
        Date date = new Date();
        String dateStr = date.toString();
        String month = dateStr.split(" ")[1];

        if (birthdayFormatStr.split(" ")[0].equals(month)) {
            return true;
        } else {
            return false;
        }
    }

    public static void sendEmail(Context context, Person person) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:" + person.getEmail()));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Congraculation");
        intent.putExtra(Intent.EXTRA_TEXT, "Happy birthday " + person.getName() + ". Wish you the best.");
        context.startActivity(Intent.createChooser(intent, "Send email..."));
    }
}
