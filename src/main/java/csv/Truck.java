package csv;

import config.Configuration;

import java.util.ArrayList;

public class Truck {
    private final String id;
    private Trailer trailer;

    public Truck(Trailer trailer) {
        id = generateId();
        this.trailer = trailer;
    }

    public Truck(ArrayList<String> csv) {
        String id = "";
        trailer = new Trailer(this);
        Pallet[] palletsRight = new Pallet[Configuration.maxPalletsInTruck / 2];
        Pallet[] palletsLeft = new Pallet[Configuration.maxPalletsInTruck / 2];
        for (String csvString : csv) {
            String[] split = csvString.split(",");
            id = split[0];
            try {
                Pallet pallet = new Pallet(split[3]);
                String[] palletStrings = CSV.readFromCSVById("pallet", split[3]);
                for (String palletString : palletStrings) {
                    String[] palletSplit = palletString.split(",");
                    Box box = new Box(CSV.readFromCSVById("box", palletSplit[3])[0]);
                    pallet.addBox(box, Integer.parseInt(palletSplit[1]), Integer.parseInt(palletSplit[2]));
                }
                if (split[1].equals("right"))
                    palletsRight[Integer.parseInt(split[2])] = pallet;
                else
                    palletsRight[Integer.parseInt(split[2])] = pallet;
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        this.id = id;
        trailer.setPalletsRight(palletsRight);
        trailer.setPalletsLeft(palletsLeft);
    }

    public static String generateId() {
        char[] charId = new char[4];
        char[] idCharPool = Tools.connectCharPool(Tools.generateCharPool('a', 'z'), Tools.generateCharPool('0', '9'));
        do {
            for (int i = 0; i < charId.length; i++)
                charId[i] = idCharPool[(int) (Math.random() * idCharPool.length)];
        } while (CSV.checkIfIdAlreadyUsed("pallet", new String(charId)));
        return new String(charId);
    }

    public String getId() {
        return id;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }
}
