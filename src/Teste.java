import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class Teste {

    // NOME DA PASTA ATUALIZADO PARA "Test"
    private static final File BASE = new File("Test");

    private PrintStream consoleStream;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // Nomes dos ficheiros atualizados para usar o sufixo "_training.txt"
    @Test
    public void test01() { test("input1_training.txt", "output1_training.txt"); }

    @Test
    public void test02() { test("input2_training.txt", "output2_training.txt"); }

    @Test
    public void test03() { test("input3_training.txt", "output3_training.txt"); }

    @Test
    public void test04() { test("input4_training.txt", "output4_training.txt"); }

    @Test
    public void test05() { test("input5_training.txt", "output5_training.txt"); }

    @Test
    public void test06() { test("input6_training.txt", "output6_training.txt"); }

    @Test
    public void test07() { test("input7_training.txt", "output7_training.txt"); }

    @Test
    public void test08() { test("input8_training.txt", "output8_training.txt"); }

    @Test
    public void test09() { test("input9_training.txt", "output9_training.txt"); }

    @Test
    public void test10() { test("input10_training.txt", "output10_training.txt"); }

    @Test
    public void test11() { test("input11_training.txt", "output11_training.txt"); }

    @Test
    public void test12() { test("input12_training.txt", "output12_training.txt"); }

    @Test
    public void test13() { test("input13_training.txt", "output13_training.txt"); }

    // Pode adicionar test14, test15, etc., copiando o padrão acima conforme precisar

    @BeforeEach
    public void setup() {
        consoleStream = System.out;
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    public void test(String input, String output) {
        test(new File(BASE, input), new File(BASE, output));
    }

    public void test(File input, File output) {
        consoleStream.println("Testing!");
        consoleStream.println("Input: " + input.getAbsolutePath());
        consoleStream.println("Output: " + output.getAbsolutePath());

        String fullInput, fullOutput;
        try {
            fullInput = Files.readString(input.toPath(), StandardCharsets.UTF_8);
            fullOutput = Files.readString(output.toPath(), StandardCharsets.UTF_8);

            consoleStream.println("INPUT ============");
            consoleStream.println(fullInput);
            consoleStream.println("OUTPUT ESPERADO =============");
            consoleStream.println(fullOutput);
            consoleStream.println("OUTPUT DO SEU CODIGO =============");
        } catch (Exception e) {
            e.printStackTrace(consoleStream);
            fail("Erro a ler o ficheiro! O Java tentou procurar neste caminho exato:\n" + input.getAbsolutePath() + "\nVerifique se o ficheiro existe e se o nome está correto na pasta 'Test'.");
            return;
        }

        try {
            Locale.setDefault(Locale.of("EN", "GB"));
            System.setIn(new FileInputStream(input));

            // Chamada direta para o seu Main
            Main.main(new String[0]);

        } catch (Exception e) {
            e.printStackTrace(consoleStream);
            fail("Erro ao executar o programa (Ocorreu uma exceção dentro do seu Main).");
        } finally {
            var outPrint = outContent.toString(StandardCharsets.UTF_8);
            consoleStream.println(outPrint);

            assertEquals(normalizeOutput(fullOutput), normalizeOutput(outPrint));
        }
    }

    private static String normalizeOutput(String s) {
        return s.replace("\r\n", "\n").trim();
    }
}