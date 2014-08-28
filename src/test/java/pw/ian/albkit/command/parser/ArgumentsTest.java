package pw.ian.albkit.command.parser;

import org.junit.Assert;
import org.junit.Test;

import pw.ian.albkit.command.parser.parameter.Params;
import pw.ian.albkit.command.parser.parameter.ParamsBase;

public class ArgumentsTest {
    @Test
    public void runTest() {
        // Test the Arguments class
        final Arguments args = new Arguments(
                "subcommand", "value", "off", "on");

        Assert.assertEquals("ARG: PARSE", args.getRaw(0), "subcommand");
        Assert.assertEquals("ARG: PARSE", args.getRaw(0), args.get(0).get());
        Assert.assertEquals("ARG: PARSE", args.getRaw(1), "value");
        Assert.assertEquals("ARG: PARSE", args.getRaw(2), "off");

        // Test the ParamsBase class
        final ParamsBase paramsBase = ParamsBase.fromUsageString(
                "/command subcommand <lol> <option1> [optional]");
        // Each flag counts as two arguments, hence 4 not 3
        Assert.assertEquals("PB: Length", paramsBase.length(), 3);
        // Each required flag counts as two required, hence 3 not 2
        Assert.assertEquals("PB: Req", paramsBase.getAmountRequired(), 2);
        Assert.assertEquals("PB: Opt", paramsBase.getAmountOptional(), 1);
        Assert.assertEquals("PB: B4", 1, paramsBase.getArgsBeforeParams());

        // Test the Params class
        final Params params = args.withParams(
                paramsBase.createParams(args)).getParams();
        Assert.assertEquals("PAR: LKUP", params.get("option1").get(), "off");
    }
}
