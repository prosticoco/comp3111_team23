package com.example.bot.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import src.main.java.com.example.bot.spring.LanguageProcessor;
import src.main.java.com.example.bot.spring.LuisNLP;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KitchenSinkTester.class, PSQLDatabaseEngine.class})
public class KitchenSinkTester {
	
	@Autowired
	private StorageEngine database;
	
//	@Test
	public void CustomerNotFound() throws Exception {
		boolean thrown = false;
		try {
			this.database.getCustomerDetails("A122");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void CustomerFound() throws Exception {
		boolean thrown = false;
		Customer cust = null;

		try {
			cust = this.database.getCustomerDetails("A124");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(cust.getAge()).isEqualTo(17);
		assertThat(cust.getPhone()).isEqualTo(54321256);
		assertThat(cust.getName()).isEqualTo("Chris");

	}
	
	@Test
	public void testLuisQuestion() throws Exception {
		LanguageProcessor languageProcessor = new LuisNLP();
		boolean thrown = false;
		ArrayList<String> result = null;
		
		try {
			result = languageProcessor.processInput("What is the transport to Guangdong?");
		}
		catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("transportToGuangdongQuestion");
	}
}
