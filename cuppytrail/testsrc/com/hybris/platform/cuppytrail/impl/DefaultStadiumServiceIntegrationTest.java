/**
 *
 */
package com.hybris.platform.cuppytrail.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hybris.model.StadiumModel;
import com.hybris.platform.cuppytrail.service.StadiumService;


/**
 * @author Shenbagarajan
 *
 */
@IntegrationTest
public class DefaultStadiumServiceIntegrationTest extends ServicelayerTransactionalTest
{
	@Resource
	private StadiumService stadiumService;
	@Resource
	private ModelService modelService;
	private StadiumModel stadiumModel;
	private final static String STADIUM_NAME = "wembley";
	private final static Integer STADIUM_CAPACITY = Integer.valueOf(12345);

	@Before
	public void setUp()
	{
		stadiumModel = new StadiumModel();
		stadiumModel.setCode(STADIUM_NAME);
		stadiumModel.setCapacity(STADIUM_CAPACITY);
	}

	@Test(expected = UnknownIdentifierException.class)
	public void testFailBehaviour()
	{
		stadiumService.getStadiumForCode(STADIUM_NAME);
	}

	@Test
	public void testStadiumService()
	{
		List<StadiumModel> stadiumModels = stadiumService.getStadium();
		final int size = stadiumModels.size();
		modelService.save(stadiumModel);
		stadiumModels = stadiumService.getStadium();
		Assert.assertEquals(size + 1, stadiumModels.size());
		Assert.assertEquals("Unexpected stadium found", stadiumModel, stadiumModels.get(stadiumModels.size() - 1));

		final StadiumModel persistedStadiumModel = stadiumService.getStadiumForCode(STADIUM_NAME);
		Assert.assertNotNull("No stadium found", persistedStadiumModel);
		Assert.assertEquals("Different stadium found", stadiumModel, persistedStadiumModel);

	}
}
