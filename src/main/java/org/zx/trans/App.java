package org.zx.trans;

public class App
{
    public static void main( String[] args )
    {
        Config config = new Config(getConfigPath(args));
        if (args.length < 1) {
            help(config);
            System.exit(-1);
        }
        String word = getArg(args, 0, "");
        if (word.trim().length() < 1) {
            System.out.println("Empty source");
            help(config);
            System.exit(-1);
        }
        String sourceLang = getArg(args, 1, "en");
        String resultLang = getArg(args, 2, "ru");
        Translatable translator = config.getTranslator();
        Context context = new Context(translator);
        System.out.println(context.translate(word, sourceLang, resultLang));
    }

    private static String getArg(String [] args, int index, String defaultValue)
    {
        String result = defaultValue;
        if (args.length <= 1) {
            return result;
        }
        if (args.length >=index + 1) {
            result = args[index];
        }
        if (result.startsWith("--")) {
            result = defaultValue;
        }
        return result;
    }

    private static String getConfigPath(String [] args)
    {
        String path = "./";
        for(int i = 0 ; i < args.length; i++) {
            if (i > 0 && args[i].startsWith("--config=")) {
                path = args[i].split("=")[1];
                break;
            }
        }
        return path;
    }

    private static void help(Config config)
    {
        StringBuffer help = new StringBuffer();
        help.append("Translator cli\n");
        help.append("  Usage:\n");
        help.append("    /path/to/command word [source_lang] [result_lang] [--config=/config/location/]\n");
        help.append("  Default source language: " + config.getProp("trans.lang.source") + "\n");
        help.append("  Default result language: " + config.getProp("trans.lang.result") + "\n");
        help.append("  Default config location: ./\n");

        System.out.println(help);
    }
}
