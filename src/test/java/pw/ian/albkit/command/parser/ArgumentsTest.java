package pw.ian.albkit.command.parser;

import org.junit.Assert;
import org.junit.Test;

public class ArgumentsTest {
    @Test
    public void test() {
        final Arguments args = new Arguments(new String[] {
                "hello", "bob", "123", "1.445", "yolo", "cool", "-f", "banter",
                "--lol"
        });

        Assert.assertFalse(
                "Arguments seems to think that hello or bob is a boolean. Either that or they were parsed wrong.",
                args.getArgument(0).isBoolean() || args.getArgument(0)
                        .isDouble());
        Assert.assertEquals("Bad parsing on hello!",
                args.getArgument(0).rawString(), "hello");
        Assert.assertEquals("Bad parsing on bob!",
                args.getArgument(1).rawString(), "bob");
        Assert.assertTrue("Bad parsing on 123!", args.getArgument(2).isInt());
        Assert.assertTrue("Bad parsing on 1.445",
                args.getArgument(3).isDouble());

        if (args.length() != 8) {
            Assert.fail("Wrong arguments length!");
        }

        Assert.assertEquals("Error parsing flag '-f'",
                args.getValueFlag("f").getRawValue(), "banter");
        Assert.assertTrue(args.hasNonValueFlag("lol"));
    }
}
