package pw.ian.albkit.command.parser;

import org.junit.Assert;
import org.junit.Test;
import pw.ian.albkit.command.parser.parameter.Parameter;
import pw.ian.albkit.command.parser.parameter.Params;
import pw.ian.albkit.command.parser.parameter.ParamsBase;

import java.util.Map;

public class ArgumentsTest {
    @Test
    public void test() {
        Arguments args = new Arguments("hello", "bob", "123", "1.445", "yolo",
                "cool", "-f", "banter", "--lol");

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

        ParamsBase base = ParamsBase.fromUsageString("/tree hello <hi> [lol]");
        args = new Arguments("hello", "bob", "trees");
        System.out.println(
                "Parsing: { hello, bob, trees } for usage '/tree hello <hi> [lol]' which produces parameters { hi, lol }");
        args.withParams(base.createParams(args));
        Params params = args.getParams();
        System.out.println("Got params: " + string(params));
        Assert.assertEquals("Not equal!", string(params),
                "{ [ hi, bob ] , [ lol, trees ] }");
        Assert.assertEquals(params.get("hi").get(), "bob");
        Assert.assertTrue(params.valid());
    }

    String string(Params params) {
        StringBuilder bu = new StringBuilder("{ ");
        for (Map.Entry<String, Parameter> entry : params.entries()) {
            bu.append("[ ").append(entry.getKey()).append(", ")
                    .append(entry.getValue().get()).append(" ]").append(" , ");
        }
        bu.setLength(bu.length() - 2);
        return bu.append("}").toString();
    }
}
