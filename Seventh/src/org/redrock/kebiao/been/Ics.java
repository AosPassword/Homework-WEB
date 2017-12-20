package org.redrock.kebiao.been;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNamedNodeMap;
import lombok.Data;

@Data
public class Ics {
    final String begin = "BEGIN:VEVENT\n" +
            "TRANSP:OPAQUE";
    String uid;
    String location;
    String description;
    String dtStart;
    String dtEnd;
    String summary;
    final String end = "\nEND:VEVENT\n";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(begin).append("\nUID:").append(uid)
                .append("\nDTSTART;TZID=Asia/Shanghai:").append(dtStart)
                .append("\nLOCATION:").append(location)
                .append("\nSUMMARY:").append(summary)
                .append("\nDESCRIPTION:").append(description)
                .append("\nDTEND;TZID=Asia/Shanghai:").append(dtEnd)
                .append(end);
        return sb.toString();
    }
}
