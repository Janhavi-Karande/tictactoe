package factories;

import models.BotDifficultyLevel;
import startegies.BotPlayingStrategy;
import startegies.EasyBotPlayingStrategy;
import startegies.HardBotPlayingStrategy;

public class BotPlayingStrategyFactory {

    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel botDifficultyLevel){
        if(botDifficultyLevel.equals(BotDifficultyLevel.EASY)){
            return new EasyBotPlayingStrategy();
        }
        else{
            return new HardBotPlayingStrategy();
        }
    }
}
