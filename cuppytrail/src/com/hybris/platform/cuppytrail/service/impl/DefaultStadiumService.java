/**
 *
 */
package com.hybris.platform.cuppytrail.service.impl;

import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.hybris.model.StadiumModel;
import com.hybris.platform.cuppytrail.daos.StadiumDAO;
import com.hybris.platform.cuppytrail.service.StadiumService;


/**
 * @author Shenbagarajan
 *
 */
public class DefaultStadiumService implements StadiumService
{
	private StadiumDAO stadiumDAO;

	@Override
	public List<StadiumModel> getStadium()
	{
		return stadiumDAO.findStadiums();
	}

	@Override
	public StadiumModel getStadiumForCode(final String code) throws AmbiguousIdentifierException, UnknownIdentifierException
	{
		final List<StadiumModel> result = stadiumDAO.findStadiumsByCode(code);
		if (result.isEmpty())
		{
			throw new UnknownIdentifierException("Stadium with code'" + code + "'not found!");
		}
		else if (result.size() > 1)
		{
			throw new AmbiguousIdentifierException("Stadium code'" + code + "' is not unique," + result.size() + "stadiums found!");
		}
		return result.get(0);
	}

	@Required
	public void setStadiumDAO(final StadiumDAO stadiumDAO)
	{
		this.stadiumDAO = stadiumDAO;
	}
}
