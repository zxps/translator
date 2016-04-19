package org.zx.trans;

public class TransItem
{
    private String source;
    private String language;

    public TransItem(String src, String lang)
    {
        source = src;
        language = lang;
    }

    public String getLanguage()
    {
        return language;
    }

    public String get()
    {
        return source;
    }

    public String toString()
    {
        return source;
    }
}
