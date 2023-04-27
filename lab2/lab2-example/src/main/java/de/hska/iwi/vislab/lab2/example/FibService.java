package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("fib")
public class FibService {


    @Path("next")
    @POST
	@Produces(MediaType.TEXT_PLAIN)
	public String fiboString() {
        if(FibState.currentIndex == 0 || FibState.currentIndex == 1) {
            String answer = "" + FibState.currentIndex;
            FibState.currentIndex++;
            return answer;
        }
        int n0 = 0, n1 = 1;
        int tempNthTerm;
        for (int i = 2; i <= FibState.currentIndex; i++) {
            tempNthTerm = n0 + n1;
            n0 = n1;
            n1 = tempNthTerm;
        }
        String answer = "" + n1;
        FibState.currentIndex++;
		return answer;
	}

    @Path("reset")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String reset(){
        FibState.currentIndex = 0;
        return "Index has been reset!";
    }

}

