package org.zx.trans.translator;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zx.trans.Config;
import org.zx.trans.TransItem;
import org.zx.trans.Translatable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;

public class YandexTranslator extends AbstractTranslator implements Translatable
{
    final String translateGate = "https://translate.yandex.net/api/v1.5/tr/translate";

    private Config config;

    @Override
    public void setConfig(Config c)
    {
        config = c;
    }

    @Override
    public TransItem translate(TransItem item, String language)
    {
        String result = "";
        try {
            StringBuilder builder = new StringBuilder(translateGate);
            builder.append("?key="+config.getTransProp("key"));
            builder.append("&text="+ URLEncoder.encode(item.get(), "UTF-8"));
            builder.append("&lang="+language);
            String response = getResponseText(builder.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("text");
            if (nodes.getLength() > 0 ) {
                Node node = nodes.item(0);
                result = node.getTextContent();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return new TransItem(result, language);
    }
}
