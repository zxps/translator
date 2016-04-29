package org.zx.trans;

import com.detectlanguage.DetectLanguage;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.zx.trans.language.Detector;
import org.zx.trans.language.DetectorException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App
{

    public static void main(String[] args) throws IOException
    {
        OptionParser optionParser = new OptionParser(){
            {
                nonOptions().requiresArgument();
                acceptsAll(Arrays.asList("c", "config"), "Config path").
                        withOptionalArg().
                        defaultsTo("./");
                acceptsAll(Arrays.asList("h", "help"), "Show help").forHelp();
            }
        };
        OptionSet options = optionParser.parse(args);
        if (options.has("help") || options.has("h")) {
            help();
            optionParser.printHelpOn(System.out);
            return;
        }
        List arguments = options.nonOptionArguments();
        String resultLang = null;
        String sourceLang = null;
        String text = null;
        if (arguments.size() < 1) {
            System.out.println("Nothing to translate");
            return;
        }
        Config config = new Config((String) options.valueOf("config"));
        if (config.getProp("trans.detector.key") != null) {
            DetectLanguage.apiKey = config.getProp("trans.detector.key");
        }
        text = (String) arguments.get(0);
        if (arguments.size() > 1) {
            resultLang = (String) arguments.get(1);
        }
        if (arguments.size() > 2) {
            sourceLang = (String) arguments.get(2);
        }
        if (sourceLang == null) {
            try{
                sourceLang = new Detector(text).detect().getCode();
            } catch (DetectorException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
        Translatable translator = config.getTranslator();
        Context context = new Context(translator);
        System.out.println(context.translate(text, sourceLang, resultLang));
    }

    private static void help()
    {
        StringBuffer help = new StringBuffer();
        help.append("Translator cli\n\n");
        help.append("Usage: /path/to/translator word LANG [SOURCE] [--option1[--option2[...]]]\n");
        help.append(" LANG    - result language.\n");
        help.append(" SOURCE  - source language. If not specified, \n");
        help.append("           application will try to detect source language automatically, through\n");
        help.append("           language detector api.\n");

        System.out.println(help);
    }
}
