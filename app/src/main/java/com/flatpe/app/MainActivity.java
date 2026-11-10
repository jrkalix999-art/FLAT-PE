package com.flatpe.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.*;
import android.graphics.drawable.GradientDrawable;

public class MainActivity extends Activity {

    int purple = Color.rgb(95, 35, 170);
    LinearLayout root;
    TextView status;
    int balance = 25000;

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        return t;
    }

    void addButton(String name, Runnable action) {
        Button b = new Button(this);
        b.setText(name);
        b.setTextColor(Color.WHITE);
        b.setBackground(shape(purple, 30));
        root.addView(b);
        b.setOnClickListener(v -> action.run());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 35, 24, 24);
        root.setBackgroundColor(Color.rgb(247, 244, 252));

        TextView title = text("FLAT PE", 30, purple);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView demo = text("DEMO WALLET • NOT A REAL PAYMENT APP",
                12, Color.RED);
        root.addView(demo);

        TextView card = text(
                "DEMO BALANCE\n₹" + balance + "\n\nSIMULATED MONEY ONLY",
                24, Color.WHITE);
        card.setGravity(Gravity.CENTER);
        card.setPadding(15, 35, 15, 35);
        card.setBackground(shape(purple, 28));
        root.addView(card);

        TextView heading = text("\nQuick Actions", 21, Color.BLACK);
        root.addView(heading);

        status = text(
                "\nNo real payment has been made.\n",
                15, Color.DKGRAY);
        root.addView(status);

        addButton("Send Demo Money", () -> {
            EditText amount = new EditText(this);
            amount.setHint("Enter demo amount (₹)");
            amount.setInputType(2);

            new android.app.AlertDialog.Builder(this)
                .setTitle("DEMO PAYMENT")
                .setMessage("This is only a simulation. No money will move.")
                .setView(amount)
                .setPositiveButton("Simulate", (dialog, which) -> {
                    try {
                        int value = Integer.parseInt(
                            amount.getText().toString());
                        if (value <= 0 || value > balance) {
                            status.setText("Invalid demo amount.");
                        } else {
                            balance -= value;
                            card.setText(
                                "DEMO BALANCE\n₹" + balance +
                                "\n\nSIMULATED MONEY ONLY");
                            status.setText(
                                "DEMO — NOT PAID\n₹" + value +
                                "\nNo real transaction occurred.");
                        }
                    } catch (Exception e) {
                        status.setText("Enter a valid amount.");
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        addButton("Demo Transaction History", () ->
            status.setText(
                "DEMO HISTORY\n\nNo real transactions recorded."));

        addButton("About FLAT PE", () ->
            status.setText(
                "FLAT PE\nIndependent demo wallet.\n" +
                "No real money transfer or UPI processing."));

        ScrollView scroll = new ScrollView(this);
        scroll.addView(root);
        setContentView(scroll);
    }
}
