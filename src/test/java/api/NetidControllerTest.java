package api;



import controllers.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetidControllerTest {

        @Test
        public void netID() {
            NetidController t = new NetidController();
            String response = t.printNetID();
            assertEquals("al2386", response);
    }


}