package api;



import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class NetidControllerTest {
    @Path("/netid")
    public static class NetidController {
        @GET
        public String printNetID(){
            return "al2386";
        }
    }

    @Test
    public void netID() throws IOException {
        NetidController t = new NetidController();
        String response = t.printNetID();
        assertEquals("al2386", response);
    }


}