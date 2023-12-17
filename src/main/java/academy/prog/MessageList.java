package academy.prog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
	private static final MessageList msgList = new MessageList();

	private final Gson gson;
	private final List<Message> list = new LinkedList<>();
	private final HashMap<String, String> users = new HashMap<>();

	public static MessageList getInstance() {
		return msgList;
	}

	private MessageList() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public synchronized void add(Message m) {
		list.add(m);
		this.setChatMembersStatus(m.getFrom());
	}

	private void setChatMembersStatus(String from) {
	}

	public synchronized String toJSON(int n, String to) {
		if (n >= list.size()) return null;
		return gson.toJson(new JsonMessages(list, n, to));
	}

	public synchronized void getChatMembers(String forUser) {
		this.add(new Message(forUser, "Current chat members:", forUser));
		for (String member : this.users.keySet()) {
			this.add(new Message(forUser, "Member: " + member + " status: " + this.users.get(member), forUser));
		}
	}

	public synchronized void setChatMembersStatus(String member, String status) {
		this.users.put(member, status);
	}
}