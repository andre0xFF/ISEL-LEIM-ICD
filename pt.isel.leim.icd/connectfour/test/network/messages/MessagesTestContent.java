package network.messages;

import java.util.Arrays;

public  class MessagesTestContent {


    public static String getEmptyMessageContent(){
        return "<Message></Message>";
    }

    public static String getAskSignUpMessageContent(String image, String username, char[] pass, String nationality, int age){
        String passval = String.valueOf(pass);
        return String.format("<Message><AskSignUpMessage><image>%s</image><username>%s</username><password>%s</password><nationality>%s</nationality><age>%x</age></AskSignUpMessage></Message>", image, username, passval, nationality, age);
    }
    public static String getAskSignUpMessageContent(){

        return "<Message><AskSignUpMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></AskSignUpMessage></Message>";
    }

    public static String getGiveSignUpAcceptedMessageContent(){
        return "<Message><GiveSignUpAcceptedMessage></GiveSignUpAcceptedMessage></Message>";
    }

    public static String getAskLogInMessageContent(String username, char[] pass){
        String passval = String.valueOf(pass);
        return String.format("<Message><AskLogInMessage><username>%s</username><password>%s</password></AskLogInMessage></Message>", username, passval);
    }
    public static String getAskLogInMessageContent(){
        return "<Message><AskLogInMessage><username>xpto</username><password>1234</password></AskLogInMessage></Message>";
    }
    public static String getGiveLogInAcceptedMessageContent(){
        return "<Message><GiveLogInAcceptedMessage></GiveLogInAcceptedMessage></Message>";
    }

    public static String getGiveGamesStatsMessageContent(GiveGamesStatsMessage.GameStat[] stats){
        StringBuilder message = new StringBuilder("<Message><GiveGamesStatsMessage><GamesStats>");
        for(int i = 0; i < stats.length; i++){
            message.append("<GameStat><id>").append(stats[i].id()).append("</id>").append("<result>").append(stats[i].result()).append("</result>").append("<time>").append(stats[i].time()).append("</time>").append("</GameStat>");

        }

        message.append("</GamesStats></GiveGamesStatsMessage></Message>");

        return message.toString();
    }
    public static String getGiveGamesStatsMessageContent(){
        return "<Message><GiveGamesStatsMessage><GamesStats><GameStat><id>Gamexpto</id><result>win</result><time>12:53:30</time></GameStat><GameStat><id>War2</id><result>Loss</result><time>12:53:30</time></GameStat></GamesStats></GiveGamesStatsMessage></Message>";
    }

    public static String getAskUpdateProfileMessageContent(){
        return "<Message><AskUpdateProfileMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></AskUpdateProfileMessage></Message>";

    }
    public static String getGiveUpdateProfileMessageContent(){
        return "<Message><GiveUpdatedProfileMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></GiveUpdatedProfileMessage></Message>";

    }

    public static String getGameOverMessageContent() {
        return "<Message><GameOverMessage><info>Game Over</info></GameOverMessage></Message>";
    }

    public static String getAskQueueGameMessageContent(){
        return "<Message><AskQueueGameMessage></AskQueueGameMessage></Message>";
    }

    public static String getAskQueueGameCancelMessageContent(){
        return "<Message><AskQueueGameCancelMessage></AskQueueGameCancelMessage></Message>";
    }

    public static String getGiveOpponentFoundMessageContent(){
        return "<Message><GiveOpponentFoundMessage><opponentusername>XpTo</opponentusername></GiveOpponentFoundMessage></Message>";
    }

    public static String getAskGamesStatesMessageContent(){
        return "<Message><AskGamesStatsMessage></AskGamesStatsMessage></Message>";
    }

    public static String getPlayTurnMessageContent(){
        return  "<Message><PlayTurnMessage/></Message>";
    }
}
