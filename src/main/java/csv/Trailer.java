package csv;

public class Trailer {
    private Pallet[] palletsLeft, palletsRight;
    private Truck truck;

    public Trailer(Truck truck) {
        this.truck = truck;
    }

    public Pallet[] getPalletsLeft() {
        return palletsLeft;
    }

    public void setPalletsLeft(Pallet[] palletsLeft) {
        this.palletsLeft = palletsLeft;
    }

    public Pallet[] getPalletsRight() {
        return palletsRight;
    }

    public void setPalletsRight(Pallet[] palletsRight) {
        this.palletsRight = palletsRight;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
        truck.setTrailer(this);
    }
}