package org.zx.trans.language;


import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;

import java.io.IOException;
import java.util.List;

public class Detector
{
    private String text;
    private String code;


    public Detector(String text)
    {
        this.text = text;
    }

    public Detector detect() throws DetectorException {
        try {
            List<Result> results = DetectLanguage.detect(this.text);
            if (results.size() < 1) {
                throw new DetectorException("Unable to detect language for {" + this.text + "} text");
            }
            this.code = results.get(0).language;
        } catch (APIError apiError) {
            throw new DetectorException("Unable to detect language for {" + this.text + "} text. " + apiError.getMessage());
        }
        return this;
    }

    public String getCode()
    {
        return this.code;
    }
}
