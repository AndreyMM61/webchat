package ru.amm_company.pages;

import java.util.LinkedList;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;
import ru.amm_company.LoginPage;
import ru.amm_company.SessionChat;
import ru.amm_company.message.Message;

public class ChatPage extends WebPage {

	private static final LinkedList<Message> LIST_MESSAGES = new LinkedList<Message>();
//        private static final List<String> LIST_USERS = new ArrayList<String>(); 
        private static final int MAX_MESSAGES = 20;
//	private final Model<String> textNames = new Model<String>("");
        private final TextField<String> newMessage = new TextField<String>("message", new Model<String>(""));
	private InputForm inputForm;
	
       
        private final String name;
        private MarkupContainer messagesContainer;
        
    public ChatPage() {
        this.name = SessionChat.get().getUsername();
        if (name == null) {
            throw new RestartResponseAtInterceptPageException(LoginPage.class);
        }
//        LIST_USERS.add(name);
        init();
    }

    private void init(){
	
        inputForm = new InputForm("inputForm");
	
        add(inputForm);
    }

    public class InputForm extends Form {
            
        public InputForm(String id) {
			super(id);
			
            setDefaultModel(new CompoundPropertyModel(this));
            add(new Label("username", name));
            newMessage.setOutputMarkupId(true);
            add(newMessage);
            add(buildMessagesContainer());
        }
        
        public final void onSubmit() {
            if (newMessage.getModelObject() != null) {
            
                Message chatMessage = new Message(name, newMessage.getModelObject());
                synchronized (LIST_MESSAGES) {
                    if (LIST_MESSAGES.size() >= MAX_MESSAGES) {
                        LIST_MESSAGES.removeFirst();
                    }
                    LIST_MESSAGES.addLast(chatMessage);
                }
                newMessage.setModelObject("");
            }
        }

        private Component buildMessagesContainer() {
            messagesContainer = new WebMarkupContainer("messages");

            final ListView<Message> listView = new ListView<Message>("message", LIST_MESSAGES) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(ListItem<Message> item) {
                    this.modelChanging();

                    Message message = item.getModelObject();

                    Label time = new Label("time", new PropertyModel<String>(message, "time"));
                    item.add(time);

                    Label nickname = new Label("nickname", new PropertyModel<String>(message, "nickname"));
                    item.add(nickname);

                    Label textmsg = new Label("textmsg", new PropertyModel<String>(message, "textmsg"));
                    item.add(textmsg);
                }
            
            };


            messagesContainer.setOutputMarkupId(true);
            messagesContainer.add(listView);

            AjaxSelfUpdatingTimerBehavior ajaxBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5));
            messagesContainer.add(ajaxBehavior);

            return messagesContainer;
        }    
    }
}