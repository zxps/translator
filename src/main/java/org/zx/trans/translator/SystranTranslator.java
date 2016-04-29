package org.zx.trans.translator;

import org.zx.trans.Config;
import org.zx.trans.TransItem;
import org.zx.trans.Translatable;

/**
 * Translate with
 * https://platform.systran.net/reference/translation
 */
public class SystranTranslator implements Translatable
{

    final static String translateGate = "https://api-platform.systran.net/translation/text/translate";

    private Config config;

    @Override
    public void setConfig(Config c)
    {
        config = c;
    }

    @Override
    public TransItem translate(TransItem item, String language)
    {
        return null;
    }

}
