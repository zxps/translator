package org.zx.trans;

public interface Translatable
{

    public void setConfig(Config c);

    public TransItem translate(TransItem item, String language);

}
