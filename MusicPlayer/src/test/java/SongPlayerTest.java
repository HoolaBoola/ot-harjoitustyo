import io.SongPlayer;
import org.junit.Before;

public class SongPlayerTest {
    
    SongPlayer player;
    
    @Before
    public void setUp(){
        player = new SongPlayer();
    }
    
//    @Test
//    public void test1() {
//        player.playSong(new File("testing.mp3"));
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        assertTrue(player.getPosition() > 0);
//    }
}
