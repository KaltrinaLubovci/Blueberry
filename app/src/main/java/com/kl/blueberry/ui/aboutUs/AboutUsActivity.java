package com.kl.blueberry.ui.aboutUs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.kl.blueberry.R;
import com.kl.blueberry.data.AboutUs;
import com.kl.blueberry.databinding.AboutUsActivityBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    AboutUsActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.about_us_activity);
        parseXML();
        onClicks();
    }

    private void onClicks() {
        binding.ivBack.setOnClickListener(onClick -> {
            finish();
        });
    }

    private void parseXML() {
        XmlPullParserFactory parseFactory;
        try {
            parseFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parseFactory.newPullParser();
            InputStream is = getAssets().open("aboutUs.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<AboutUs> aboutUs = new ArrayList<>();
        int eventType = parser.getEventType();
        AboutUs aboutUsCurrentRes = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("aboutUs".equals(eltName)) {
                        aboutUsCurrentRes = new AboutUs();
                        aboutUs.add(aboutUsCurrentRes);
                    } else if (aboutUsCurrentRes != null) {
                        if ("title".equals(eltName)) {
                            aboutUsCurrentRes.title = parser.nextText();
                        } else if ("icon".equals(eltName)) {
                            aboutUsCurrentRes.icon = parser.nextText();
                        } else if ("description".equals(eltName)) {
                            aboutUsCurrentRes.description = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }
        printAboutUs(aboutUs);
    }

    private void printAboutUs(ArrayList<AboutUs> aboutUs) {
        StringBuilder builder = new StringBuilder();

        for (AboutUs aboutUs1 : aboutUs) {
            builder.append(aboutUs1.title).append("\n").
                    append(aboutUs1.icon).append("\n").
                    append(aboutUs1.description).append("\n\n");
        }

        binding.tvAboutUs.setText(builder.toString());
    }
}
