import network.Client;

import java.util.ArrayList;

public class GameSessionModel {

    private ArrayList<Client> gamePlayers = new ArrayList<Client>();

    private GameSessionState state;

    public GameSessionModel(Client player){
        if(player != null){
            gamePlayers.add(player);
        }
    }

    public GameSessionModel(){

    }

    public boolean addPlayer(Client player){

        if (isGameSessionFull()){
            state = GameSessionState.PLAYING_GAME;
            return false;
        }

        state = GameSessionState.WAITING_FOR_PLAYER;

    this.gamePlayers.add(player);

    return true;
    }

    public Client getOpponent(Client client){
        Client opponentClient;
        for(int i = this.gamePlayers.size(); i > 0; i--){
            opponentClient = this.gamePlayers.get(i);
            if(opponentClient != client){
                return this.gamePlayers.get(i);
            }
        }
        return null;
    }

    public GameSessionState getState(){
        return this.state;
    }


    public boolean isGameSessionFull(){
        return gamePlayers.size() >= 2;

    }
}
