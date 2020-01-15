package View;

import Domain.Controller.SystemController;

public class Main {
    public static void main(String[] args){
        SystemController systemController = new SystemController();
        systemController.playGame();


       /* int k=0;


        for (int i=0;i<12;i++){
            for (int j=0;j<4;j++){
                k =(int) (Math.random()*6+1);

                systemController.movePlayerCar(j,k,false);
            }
        }
        
        */

    }
}