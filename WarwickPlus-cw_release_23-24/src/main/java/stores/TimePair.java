package stores;
import java.time.LocalDateTime;

public class TimePair {
    private LocalDateTime timestamp;
    private float rating = 0;

    public TimePair(LocalDateTime inputtimestamp, float ratingadd){
        this.timestamp = inputtimestamp;
        this.rating = ratingadd;
    }

    public float getRating(){
        return rating;
    }
}