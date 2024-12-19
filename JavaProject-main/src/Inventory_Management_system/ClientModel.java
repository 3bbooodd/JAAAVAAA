package Inventory_Management_system;

public class ClientModel {

    private int ID;
    private String name;
    private String email;
    
    // defualt Constructor
    public ClientModel(){
        ID = 0;
        name = " No Name Providen";
        email = "No Email Providen";
    }

    // Parametarized Constructor
    public ClientModel(String name, String email) {
        this.ID = this.ID + 1;
        this.name = name;
        this.email = email;
    }
    
    // Getter for ID
    public int getID(){
        return ID;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClientModel{name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}
