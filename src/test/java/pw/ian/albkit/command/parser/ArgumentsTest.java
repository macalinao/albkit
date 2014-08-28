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

        // Test the Params class
        final Params params = args.withParams(
                paramsBase.createParams(args)).getParams();
        Assert.assertEquals("PAR: LKUP", params.get("option1").get(), "off");
        Assert.assertEquals("PAR: LKUP", params.get("optional").get(), "on");
    }
}
