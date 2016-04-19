package org.zx.trans;

public class App
{
    public static void main( String[] args )
    {
        Config config = new Config();
        if (args.length < 1) {
            help(config);
            System.exit(-1);
        }
        String sourceLang = "en";
        String resultLang = "ru";
        String word = args[0];
        if (word.trim().length() < 1) {
            System.out.println("Empty source");
            System.exit(-1);
        }
        if (args.length >= 2) {
            sourceLang = args[1];
        }
        if (args.length >= 3) {
            resultLang = args[2];
        }
        Translatable translator = config.getTranslator();
        Context context = new Context(translator);
        System.out.println(context.translate(word, sourceLang, resultLang));
    }

    private static void help(Config config)
    {
        StringBuffer help = new StringBuffer();
        help.append("Translator cli\n");
        help.append("  Usage:\n");
        help.append("    /path/to/command word [source_lang] [result_lang]\n");
        help.append("  Default source language: " + config.getProp("trans.lang.source") + "\n");
        help.append("  Default result language: " + config.getProp("trans.lang.result") + "\n");

        System.out.println(help);
    }
}
