package controllers;

import javax.ws.rs.*;

@Path("/netid")
public class NetidController {
    @GET
    public String printNetID(){
        return "al2386";
    }
}
