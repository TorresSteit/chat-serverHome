package academy.prog;

import jakarta.servlet.http.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/*
    POST(json) -> /add -> AddServlet -> MessageList
    GET -> /get?from=x -> GetListServlet <- MessageList
        <- json[...]
 */

public class AddServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] requestBodyToArray;
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        Message msg = Message.fromJSON(bufStr);
        if (msg != null) {
            if (msg.getText().equalsIgnoreCase("/users")) {
                msgList.getChatMembers(msg.getFrom());
            } else if (msg.getText().toLowerCase().startsWith("/status ")) {
                msgList.setChatMembersStatus(msg.getFrom(), msg.getText().substring("/status ".length()));
            } else {
                msgList.add(msg);
            }
        } else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
    }

    private byte[] requestBodyToArray(HttpServletRequest req) {
        return new byte[0];
    }
}
