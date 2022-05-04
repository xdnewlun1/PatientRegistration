package backend;
import backend.Providers;
import java.util.ArrayList;

public class Insurance {
    private String name, phone, address;
    private int copay, deductable, insurance_id;
    private ArrayList<Providers> provider_list = new ArrayList<Providers>();

    public Insurance(String name, String phone, String address, int copay, int deductable, int insurance_id) {
        this.name = name;
        this.insurance_id = insurance_id;
        this.phone = phone;
        this.address = address;
        this.copay = copay;
        this.deductable = deductable;
    }

    public void addProvider(Providers prov) {
        provider_list.add(prov);
    }
    public ArrayList<Providers> getProviders(){
        return provider_list;
    }
    public String getName(){
        return name;
    }
    public int getInsuranceID() { return insurance_id; }
    public String getPhone(){ return phone; }
    public String getAddress(){ return address; }
    public int getCopay(){ return copay; }
    public int getDeductable(){ return deductable; }
}
