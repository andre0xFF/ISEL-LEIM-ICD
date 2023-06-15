package network.messages;

import java.awt.*;

public record OnTokenDroppedMessage(int column, int row, Color color) implements Message {
}
