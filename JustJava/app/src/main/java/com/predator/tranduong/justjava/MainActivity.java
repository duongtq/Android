package com.predator.tranduong.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private int quantity = 0;
    private CheckBox whippedCream;
    private CheckBox chocolate;
    private Button emailButton;

    private String priceMessage = "";
    private String addedWhippedCream = "";
    private String addedChocolate = "";
    private String customerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int calculatePrice(int price, int quantity)
    {
        return quantity*price;
    }

    public void submitOrder(View view)
    {
        // Each cup of coffee costs 5$
        // displayPrice(quantity * 5);
        int totalPrice = calculatePrice(5,  quantity);

        EditText nameField = (EditText) findViewById(R.id.name_field);

        customerName = nameField.getText().toString();

        if ( customerName.equalsIgnoreCase(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter customer's name.",
                    Toast.LENGTH_SHORT);

            toast.show();
        }

        whippedCream = (CheckBox)findViewById(R.id.cream_checkbox);
        chocolate    = (CheckBox)findViewById(R.id.chocolate_checkbox);

        if ( whippedCream.isChecked() == true )
        {
            totalPrice = totalPrice + (2*quantity);
            addedWhippedCream = "\nAdded whipped cream.";
        }

        if ( chocolate.isChecked() == true )
        {
            totalPrice = totalPrice + (4*quantity);
            addedChocolate = "\nAdded chocolate.";
        }

        priceMessage = "Customer's name: " + customerName + "\nTotal cost: $" + totalPrice + addedWhippedCream + addedChocolate + "\nThank you.\n";
        displayMessage(priceMessage);
    }

    public void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void displayPrice(int number)
    {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void increment(View view)
    {
        quantity = quantity + 1;
        display(quantity);
        // displayPrice(quantity * 5);
    }

    public void decrement(View view)
    {
        if ( quantity <= 0 )
        {
            return;
        }
        else {
            quantity = quantity - 1;
            display(quantity);
            // displayPrice(quantity * 5);
        }
    }

    public void displayMessage(String message)
    {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void sendEmail(View view)
    {
        String subject = "JustJava order for " + customerName;
                emailButton = (Button) findViewById(R.id.email_button);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,
                subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        intent.setType("message/rcf822");
        startActivity(Intent.createChooser(intent, "Choose an Email client"));
    }
}
