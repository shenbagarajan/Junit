/**
 *
 */
package com.hybris.platform.cuppytrail.daos.impl;

import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.hybris.model.StadiumModel;
import com.hybris.platform.cuppytrail.daos.StadiumDAO;


/**
 * @author Shenbagarajan
 *
 */
public class DefaultStadiumDAOIntegrationTest extends ServicelayerTransactionalTest
{
	@Resource
	private StadiumDAO stadiumDAO;
	@Resource
	private ModelService modelService;
	private static final String STADIUM_NAME = "wembley";
	private static final Integer STADIUM_CAPACITY = Integer.valueOf(12345);

	@Test
	public void stadiumTestDAO()
	{
		List<StadiumModel> stadiumsByCode = stadiumDAO.findStadiumsByCode(STADIUM_NAME);
		Assert.assertTrue("No stadiums should be returned", stadiumsByCode.isEmpty());
		List<StadiumModel> stadiumCapacity = stadiumDAO.findStadiums();
		final int size = stadiumCapacity.size();
		final StadiumModel stadiumModel = new StadiumModel();
		stadiumModel.setCode(STADIUM_NAME);
		stadiumModel.setCapacity(STADIUM_CAPACITY);
		modelService.save(stadiumModel);

		stadiumCapacity = stadiumDAO.findStadiums();
		Assert.assertEquals(size + 1, stadiumCapacity.size());
		Assert.assertEquals("Unexpected stadium found.", stadiumModel, stadiumCapacity.get(stadiumCapacity.size() - 1));

		stadiumsByCode = stadiumDAO.findStadiumsByCode(STADIUM_NAME);
		Assert.assertEquals("Find the one we just saved", 1, stadiumsByCode.size());
		Assert.assertEquals("Check the names", STADIUM_NAME, stadiumsByCode.get(0).getCode());
		Assert.assertEquals("Check the capacity", STADIUM_CAPACITY, stadiumCapacity.get(0).getCapacity());
	}
}
