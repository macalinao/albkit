package pw.ian.albkit.command.parser;

import org.junit.Assert;
import org.junit.Test;

import pw.ian.albkit.command.parser.parameter.Params;
import pw.ian.albkit.command.parser.parameter.ParamsBase;

public class ArgumentsTest {
    @Test
    public void runTest() {
        // Test number one

        // Test the Arguments class
        final Arguments args = new Arguments(
                "subcommand", "-f", "value", "off", "on");

        Assert.assertEquals("ARG: PARSE", args.getRaw(0), "subcommand");
        Assert.assertEquals("ARG: PARSE", args.getRaw(0), args.get(0).get());
        Assert.assertEquals("ARG: PARSE", args.getRaw(1, false), "off");
        Assert.assertEquals("ARG: PARSE", args.getRaw(2, false), "on");
        Assert.assertEquals("ARG: FLAG", args.getValueFlag("f").getRawValue(), "value");

        // Test the ParamsBase class
        final ParamsBase paramsBase = ParamsBase.fromUsageString(
                "/command subcommand <-f lol> <option1> [optional]");
        Assert.assertEquals("PB: Length", paramsBase.length(), 2);
        // Each required flag counts as two required, hence 3 not 2
        Assert.assertEquals("PB: Req", paramsBase.getAmountRequired(), 1);
        Assert.assertEquals("PB: Opt", paramsBase.getAmountOptional(), 1);
        Assert.assertEquals("PB: B4", 1, paramsBase.getArgsBeforeParams());
        Assert.assertEquals("PB: FLA", 1, paramsBase.getAmountFlags());

        // Test the Params class
        final Params params = args.withParams(
                paramsBase.createParams(args)).getParams();
        Assert.assertEquals("PAR: LKUP", params.get("option1").get(), "off");
        Assert.assertEquals("PAR: LKUP", params.get("optional").get(), "on");
        Assert.assertTrue("PAR: INV", params.valid());

        // Test number two

        // Test the Arguments class
        final Arguments args1 = new Arguments(
                "subcommand", "off", "on");

        Assert.assertEquals("ARG1: PARSE", args1.getRaw(0), "subcommand");
        Assert.assertEquals("ARG1: PARSE", args1.getRaw(0), args.get(0).get());
        Assert.assertEquals("ARG1: PARSE", args1.getRaw(1, false), "off");
        Assert.assertEquals("ARG1: PARSE", args1.getRaw(2, false), "on");

        // Test the ParamsBase class
        final ParamsBase paramsBase1 = ParamsBase.fromUsageString(
                "/command subcommand <-f lol> <option1> [optional]");
        Assert.assertEquals("PB: Length", paramsBase1.length(), 2);
        Assert.assertEquals("PB1: Req", paramsBase1.getAmountRequired(), 1);
        Assert.assertEquals("PB1: Opt", paramsBase1.getAmountOptional(), 1);
        Assert.assertEquals("PB1: B4", 1, paramsBase1.getArgsBeforeParams());
        Assert.assertEquals("PB1: FLA", 1, paramsBase1.getAmountFlags());

        // Test the Params class
        final Params params1 = args1.withParams(
                paramsBase1.createParams(args1)).getParams();
        Assert.assertEquals("PAR1: LKUP", params1.get("option1").get(), "off");
        Assert.assertEquals("PAR1: LKUP", params1.get("optional").get(), "on");
        Assert.assertTrue("PAR1: INV", !params1.valid());
    }
}
