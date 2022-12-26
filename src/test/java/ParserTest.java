import logic.Parser;
import logic.Starter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void test1(){
        File f = new File("src/main/resources/vals.properties");
        Starter s;
        try {
            s = Parser.fromFile(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(s==null){
            return;
        }

    }
}
