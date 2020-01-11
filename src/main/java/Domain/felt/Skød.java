package Domain.felt;


    public class Skød extends Felt {
        private int price;
        private int ownedByPlayerId=-1;

        public Skød(String name, int index,String color ,int price, char type, int ownedByPlayerId) {
            super(name,index,color, type);
            this.price=price;
            this.ownedByPlayerId=ownedByPlayerId;
        }

        public int getOwnedByPlayerId() {
            return ownedByPlayerId;
        }

        public void setOwnedByPlayerId(int id){
            this.ownedByPlayerId=id;
        }

        public int getPrice() {
            return price;
        }
    }