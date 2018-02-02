package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //figure out if the user wants whipped cream
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        //figure out if the user wants chocolate
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //Find the user's name
        EditText editText = (EditText) findViewById(R.id.name_field);
        String value = editText.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary (price, hasWhippedCream, hasChocolate, value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject) + value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
if (intent.resolveActivity(getPackageManager()) !=null){
    startActivity(intent);
}
   }

    /** calculates price of the order
     *
     * @return total price
     */
    private int calculatePrice (boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;

        if (addWhippedCream){
            basePrice += 1;
        }

        if (addChocolate){
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    /**
     * Creates the summary od order
     * @param price of the order
     * @param addWhippedCream checks if whipped cream is added
     * @param addChocolate checks if chocolate is added
     * @return text summary
     */
    private String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String addName) {
        String priceMessage = getString(R.string.name_1) + addName;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total)+ "$" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment (View view) {
        if (quantity == 100){
            Toast.makeText(this, getString(R.string.not_more_than_100), Toast.LENGTH_SHORT).show();
        } else {
        quantity = quantity + 1;
        displayQuantity (quantity);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement (View view) {

        if (quantity == 1) {
            Toast.makeText(this,getString(R.string.not_less_than_1) , Toast.LENGTH_SHORT).show();
        } else  {
            quantity = quantity - 1;
            displayQuantity (quantity);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
