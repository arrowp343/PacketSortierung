import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTools {
    @Test
    public void testGenerateCharPool(){
        char[] charPool = Tools.generateCharPool('A', 'D');
        assertEquals('A', charPool[0]);
        assertEquals('B', charPool[1]);
        assertEquals('C', charPool[2]);
        assertEquals('D', charPool[3]);
    }
    @Test
    public void testConnectCharPool(){
        char[] charPool1 = Tools.generateCharPool('A', 'C');
        char[] charPool2 = Tools.generateCharPool('D', 'F');
        char[] charPool12 = Tools.connectCharPool(charPool1, charPool2);
        assertEquals('A', charPool12[0]);
        assertEquals('B', charPool12[1]);
        assertEquals('C', charPool12[2]);
        assertEquals('D', charPool12[3]);
        assertEquals('E', charPool12[4]);
        assertEquals('F', charPool12[5]);
    }
}