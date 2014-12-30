/**
 *
 */
package com.hybris.platform.cuppytrail.facades.impl;

import de.hybris.platform.cuppytrail.data.StadiumData;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hybris.model.StadiumModel;
import com.hybris.platform.cuppytrail.facades.StadiumFacade;


/**
 * @author Shenbagarajan
 *
 */
public class DefaultStadiumFacadeIntegrationTest extends ServicelayerTransactionalTest
{
	@Resource
	public StadiumFacade stadiumFacade;
	@Resource
	public ModelService modelService;

	private StadiumModel stadiumModel;
	private final String STADIUM_NAME = "wembley";
	private final Integer STADIUM_CAPACITY = Integer.valueOf(12345);

	@Before
	public void setUp()
	{
		stadiumModel = new StadiumModel();
		stadiumModel.setCode(STADIUM_NAME);
		stadiumModel.setCapacity(STADIUM_CAPACITY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullParameter()
	{
		stadiumFacade.getStadium(null);
	}


	/**
	 * Tests and demonstrates the Facade's methods
	 */
	@Test
	public void testStadiumFacade()
	{
		List<StadiumData> stadiumListData = stadiumFacade.getStadiums();
		Assert.assertNotNull(stadiumListData);
		//	final int size = stadiumListData.size();
		modelService.save(stadiumModel);

		stadiumListData = stadiumFacade.getStadiums();
		Assert.assertNotNull(stadiumListData);
		//	Assert.assertEquals(size, stadiumListData.size());
		//	Assert.assertEquals(STADIUM_NAME, stadiumListData.get(size).getName());
		//	Assert.assertEquals(STADIUM_CAPACITY.toString(), stadiumListData.get(size).getCapacity());

		final StadiumData persistedStadiumData = stadiumFacade.getStadium(STADIUM_NAME);
		Assert.assertNotNull(persistedStadiumData);
		Assert.assertEquals(STADIUM_NAME, persistedStadiumData.getName());
		Assert.assertEquals(STADIUM_CAPACITY.toString(), persistedStadiumData.getCapacity());
	}
}
