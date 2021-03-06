/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

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
    boolean hasWhip;
    boolean hasChocolate;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //Find out if the user wants whipped cream
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhip = whippedCheckBox.isChecked();


        // Retrieves status of CheckBox
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Retrieves Name Entry given in EditText view
        EditText nameEditText = (EditText) findViewById(R.id.name_view);
        String name = nameEditText.getText().toString();

        int price = calculatePrice(hasWhip, hasChocolate);
        //priceMessage is the entire message and order summary
        String priceMessage = createOrderSummary(price, hasWhip, hasChocolate, name);

        // Send order to email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Create summary of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate added
     * @param price of the order
     * @param name is the entered text in the name EditText
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
     String longMessage = "Name: " + name;
            longMessage += "\n Add whipped cream? " + addWhippedCream;
            longMessage += "\n Add chocolate? " + addChocolate;
            longMessage += "\nQuantity: " + quantity;
            longMessage += "\nTotal: $" + price;
            longMessage += "\nThank you!";
     return longMessage;
    }




    /**
 * Calculates the price of the order.
  * @return total price
 */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int baseprice = 5;


        if (addWhippedCream){
            baseprice = baseprice + 1;
        }

        if (addChocolate){
            baseprice = baseprice + 2;
        }

        return quantity * baseprice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method increases the quantity
     */
    public void increment(View view) {
        if(quantity >=100){
            /*
            Use the return keyword to exit from the method - stop the code
             */
            Toast.makeText(getBaseContext(), "That's too many!!!",
            Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method decreases the quantity
     */
    public void decrement(View view) {
        if(quantity == 1){
            /*
            Use the return keyword to exit from the method - stop the code
             */
            Toast.makeText(getBaseContext(), "At least buy one!!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

}