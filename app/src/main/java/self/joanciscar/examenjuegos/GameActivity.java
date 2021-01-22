package self.joanciscar.examenjuegos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends MainMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);
        SurfaceViewGame view = new SurfaceViewGame(this);
        setContentView(view);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_elements;
    }
}