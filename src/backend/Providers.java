package backend;

public class Providers {
    String fname, lname, credential;
    int provider_id;

    public Providers(String fname, String lname, String credential, int provider_id){
        this.fname = fname;
        this.lname = lname;
        this.credential = credential;
        this.provider_id = provider_id;
    }

    public String getName(){
        return fname + " " + lname + ", " + credential;
    }
}
