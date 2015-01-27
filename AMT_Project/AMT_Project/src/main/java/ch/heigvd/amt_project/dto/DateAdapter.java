package ch.heigvd.amt_project.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public String marshal(Date date) throws Exception {
        String result = dateFormat.format(date);
        return result;
    }

    @Override
    public Date unmarshal(String string) throws Exception {
        Date d = dateFormat.parse(string);
        return dateFormat.parse(string);
    }

}
