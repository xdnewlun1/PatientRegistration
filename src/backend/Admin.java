package backend;

public class Admin {
    public int admin_id;
    public boolean logged_in = false;
    public String first_name;
    public String last_name;

    public Admin() {

    }

    public void login(int admin_id, String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.admin_id = admin_id;
        this.logged_in = true;
    }

    public void logout() {
        this.first_name = "";
        this.last_name = "";
        this.admin_id = 0;
        this.logged_in = false;
    }
}
