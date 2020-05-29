/*
 * Copyright (C) 2020 Microservice Systems, Inc.
 * All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package systems.microservice.email.messenger.service;

import javax.mail.*;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = System.getProperties();
        props.setProperty("mail.imap.starttls.enable", "true");
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        try {
            Store store = session.getStore("imap");
            store.connect("mailbox.domoy.ru", 143, "address@host.com", "password");
            Folder folder = store.getFolder("Inbox");
            folder.open(Folder.READ_ONLY);
//            int c = folder.getMessageCount();
            Message[] msgs = folder.search(new ReceivedDateTerm(ComparisonTerm.GE, new Date(System.currentTimeMillis() - 3600000L * 48L)));
//            Message[] msgs = folder.getMessages();
            for (Message msg : msgs) {
                System.out.println(msg.getContent());
            }
            System.out.println(msgs.length);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
