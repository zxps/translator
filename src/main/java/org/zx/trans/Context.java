package org.zx.trans;

public class Context
{
    private Translatable translator;

    public Context(Translatable t)
    {
        translator = t;
    }

    public String translate(String word, String source, String result)
    {
        return translator.translate(new TransItem(word, source), result).get();
    }
}
