package com.example.bot.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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



@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KitchenSinkTester.class, PSQLDatabaseEngine.class, LuisNLP.class, MessageHandler.class, HandlerFactory.class})
public class KitchenSinkTester {

	@Autowired
	private StorageEngine database;
	@Autowired
	private LanguageProcessor languageProcessor;
	@Autowired
	private MessageHandler messageHandler; 

	
	@Test
	public void testGetTourDetailsSuccessful(){
		Tour test = null;
		boolean thrown = false;
		try {
			test = database.getTourDetails("2d001", new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-11"));
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(test.getPrice()).isEqualTo(599);
		assertThat(test.getCapacity()).isEqualTo(20);
		
	}
	
	
	@Test
	public void testGetTourDetailUnsuccessful(){
		boolean thrown = false;
		try {
			database.getTourDetails("2d", new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-11"));
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	
	@Test
	public void testCustomerDetailsFound(){
		boolean thrown = false;
		Customer test = null;
		try {
			test = database.getCustomerDetails("U7284687917ae6c74fdca2ba21f055e78");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(test.getName()).isEqualTo("Ivan");
	}
	
	@Test
	public void testCustomerDetailsNotFound(){
		boolean thrown = false;
		try {
			database.getCustomerDetails("A122");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}

	@Test
	public void generalTourFound() {
		boolean thrown = false;
		GeneralTour gt = null;

		try {
			gt = this.database.getGeneralTourDetails("yangshanhotspringtour");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(gt.getId()).isEqualTo("2d002");
	}

	@Test
	public void generalTourNotFound() {
		boolean thrown = false;
		try {
			database.getGeneralTourDetails("testotur");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}

	@Test
	public void testGetFAQNotFound(){
		boolean thrown = false;

		try {
			database.getFAQResponse("sadasd");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void testGetFAQFound(){
		boolean thrown = false;
		String answer = null;
		try {
			answer = database.getFAQResponse("transporttoguangdong");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(answer).isEqualTo("It is a tour bus");
	}
	
	
	@Test
	public void testLuisQuestion() {
		boolean thrown = false;
		ArrayList<String> result = null;

		try {
			result = languageProcessor.processInput("Book the Shenzhen city tour for two adults on the 23/06/2018. My name is Christopher Lynch.");
		}
		catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("bookTour");
		assertThat(result.get(1)).isEqualTo("builtin.datetimeV2.date:the 23/06/2018");
		assertThat(result.get(2)).isEqualTo("tourType:shenzhen city tour");
		assertThat(result.get(3)).isEqualTo("numberOfAdults:two adults");
		assertThat(result.get(4)).isEqualTo("builtin.encyclopedia.people.person:christopher lynch");
	}
	
	@Test
	public void testGreeting() {
		boolean thrown = false;
		ArrayList<String> result = null;
		
		try {
			result = languageProcessor.processInput("Hello");
		}
		catch(Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("Greeting");
	}
	
//	@Test
//	public void testController(){
//		MessageHandler m = new MessageHandler();
//		String a = m.handleTextContent(new ArrayList<String>(Arrays.asList("none")));
//		assertThat(a).isEqualTo("Excuse me I cannot understand what you are trying to say. We have logged your query. Could you try again?");
//		
//	}
	
	@Test
	public void testNone() {
		boolean thrown = false;
		ArrayList<String> result = null;
			
		try {
			result = languageProcessor.processInput("I like pizza and dogs");
		}
		catch(Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("None");
		assertThat(result.get(1)).isEqualTo("I like pizza and dogs");
	}
	
	@Test
	public void testPositiveConfirmation() {
		boolean thrown = false;
		ArrayList<String> result = null;
			
		try {
			result = languageProcessor.processInput("Y");
		}
		catch(Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("positiveConfirmation");
		assertThat(result.get(1)).isEqualTo("Y");
	}
	
	@Test
	public void testNegativeConfirmation() {
		boolean thrown = false;
		ArrayList<String> result = null;
		try {
			result = languageProcessor.processInput("N");
		}
		catch(Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("negativeConfirmation");
		assertThat(result.get(1)).isEqualTo("N");
	}
	
	@Test
	public void testBooking(){
		ArrayList<String> inputArray = new ArrayList<String>(Arrays.asList(
				"booktour",
				"numberOfAdults:3",
				"numberOfChildren:4",
				"numberOfToddlers:5",
				"builtin.age:34",
				"tourType:yangshanhotspringtour",
				"builtin.encyclopedia.people.person:Ivan"));
		String answer = messageHandler.handleTextContent(inputArray, "test");
		assertThat(answer).isEqualTo(MessageHandler.CONFIRMATION);
	}

	@Test
	public void testAdditionalInformation() {
		boolean thrown = false;
		ArrayList<String> result = null;
			
		try {
			result = languageProcessor.processInput("adults: 5 children: 6");
		}
		catch(Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
		assertThat(result.get(0)).isEqualTo("additionalInformation");
		assertThat(result.get(1)).isEqualTo("numberOfChildren:children : 6");
		assertThat(result.get(2)).isEqualTo("numberOfAdults:adults : 5");
	}

}
