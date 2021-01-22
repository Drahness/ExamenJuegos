package self.joanciscar.examenjuegos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ElementsActivity extends MainMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);
        SurfaceViewElements view = new SurfaceViewElements(this);
        setContentView(view);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_elements;
    }
}