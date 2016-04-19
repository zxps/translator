package org.zx.trans.translator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class YandexTranslator implements Translatable
{
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
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("?key="+config.getTransProp("key"));
            builder.append("&text="+ URLEncoder.encode(item.get(), "UTF-8"));
            builder.append("&lang="+language);
            URI uri = new URI(builder.toString());

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(uri);
            HttpResponse response = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseData = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                responseData.append(line);
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(new InputSource(new StringReader(responseData.toString())));
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("text");
            if (nodes.getLength() > 0 ) {
                Node node = nodes.item(0);
                result = node.getTextContent();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
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

    private String getBaseUrl()
    {
        return "https://translate.yandex.net/api/v1.5/tr/translate";
    }
}
