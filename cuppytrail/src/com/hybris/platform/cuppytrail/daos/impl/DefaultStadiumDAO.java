/**
 *
 */
package com.hybris.platform.cuppytrail.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybris.model.StadiumModel;
import com.hybris.platform.cuppytrail.daos.StadiumDAO;


/**
 * @author Shenbagarajan
 *
 */

@Component(value = "stadiumDAO")
public class DefaultStadiumDAO implements StadiumDAO
{
	@Autowired
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<StadiumModel> findStadiums()
	{
		final String queryString = "SELECT {p:" + StadiumModel.PK + "}"//
				+ "FROM {" + StadiumModel._TYPECODE + " AS p}";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(queryString);
		return flexibleSearchService.<StadiumModel> search(flexibleSearchQuery).getResult();
	}

	@Override
	public List<StadiumModel> findStadiumsByCode(final String code)
	{
		final String queryString = "SELECT {p:" + StadiumModel.PK + "}"//
				+ "FROM {" + StadiumModel._TYPECODE + " AS p}"//
				+ "WHERE" + "{p:" + StadiumModel.CODE + "}=?code ";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(queryString);
		flexibleSearchQuery.addQueryParameter("code", code);
		return flexibleSearchService.<StadiumModel> search(flexibleSearchQuery).getResult();
	}
}
