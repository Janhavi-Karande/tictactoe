package startegies;

import models.Board;
import models.Move;
import models.Player;

import java.util.List;

public interface BotPlayingStrategy {
    Move makeMove(Board board, List<Player> players);
}
