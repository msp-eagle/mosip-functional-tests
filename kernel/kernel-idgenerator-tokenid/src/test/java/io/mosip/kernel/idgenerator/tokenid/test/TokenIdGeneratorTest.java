package io.mosip.kernel.idgenerator.tokenid.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import io.mosip.kernel.idgenerator.tokenid.cache.impl.TokenIdCacheManagerImpl;
import io.mosip.kernel.idgenerator.tokenid.exception.TokenIdGenerationException;
import io.mosip.kernel.idgenerator.tokenid.generator.TokenIdGenerator;
import io.mosip.kernel.idgenerator.tokenid.repository.TokenIdRepository;



/**
 * Test class for TokenIdenerator class
 * 
 * @author M1037462 since 1.0.0
 *
 */
@RunWith(SpringRunner.class)

public class TokenIdGeneratorTest {
	private Integer tokenIdLength;
	
	@InjectMocks
	private TokenIdGenerator tokenIdGenerator;
	
	@Mock
	TokenIdCacheManagerImpl tokenCacheManager;
	
	@Mock
	TokenIdRepository tokenRepo;
	
	String tokenIdLen="tokenIdLength";
	
	
 	
	@Before
	public void setup() {
		try {
			
			InputStream config = getClass().getClassLoader().getResourceAsStream("application.properties");
			Properties propObj = new Properties();
			propObj.load(config);
			String tokenIdLengthString = propObj.getProperty("mosip.kernel.tokenid.length");
			tokenIdLength=Integer.parseInt(tokenIdLengthString);
			MockitoAnnotations.initMocks(this);

			ReflectionTestUtils.setField(this.tokenIdGenerator, tokenIdLen, 36);
			this.tokenIdGenerator.tokenIdGeneratorPostConstruct();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	public void notNullTest() {
		
		assertNotNull(tokenIdGenerator.generateId());
	}

	@Test
	public void tokenIdDigitTest() {
		Integer tokenLength=tokenIdGenerator.generateId().length();
		assertEquals(tokenIdLength, tokenLength);
	}
	
	
	@Test(expected=Exception.class)
	public void tokenIdgeneratorNullTest() {
		
	    this.tokenIdGenerator.saveGeneratedTokenId(null);
		
	}
	
	@Test(expected=TokenIdGenerationException.class)
	public void throwTokenIdgenerationException() {
		throw new TokenIdGenerationException();
	}
	
	
}

