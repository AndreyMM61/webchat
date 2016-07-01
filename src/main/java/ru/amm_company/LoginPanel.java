/*
 * Copyright 2016 mam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.amm_company;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import ru.amm_company.pages.ChatPage;

/**
 *
 * @author mam
 */
public class LoginPanel extends Panel {
        private static final long serialVersionUID = 1L;
        LoginForm loginForm;

        @Override
        protected void onConfigure() {
                super.onConfigure();

                final SessionChat session = SessionChat.get();
                String username = session.getUsername();
                if (username != null) {
                    throw new RestartResponseAtInterceptPageException(ChatPage.class);
                }
        }

        public LoginPanel(String id) {
		super(id);		
		init();
	}
	
	private void init() {
		loginForm = new LoginForm("loginForm");
		add(loginForm);
	}
	
	public class LoginForm extends Form {
		final IModel<String> usernameModel = Model.of("");

                public LoginForm(String id) {
			super(id);
			
			setDefaultModel(new CompoundPropertyModel(this));
			
			add(new TextField("username", usernameModel));
		}

                @Override
		public final void onSubmit() {			
                        super.onSubmit();

                        String username = usernameModel.getObject();
                        SessionChat.get().setUsername(username);
                        setResponsePage(ChatPage.class);
		}
	}
}
