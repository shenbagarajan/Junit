/**
 *
 */
package com.hybris.platform.cuppytrail.facades.impl;

import de.hybris.platform.cuppy.model.MatchModel;
import de.hybris.platform.cuppytrail.data.MatchSummaryData;
import de.hybris.platform.cuppytrail.data.StadiumData;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.hybris.model.StadiumModel;
import com.hybris.platform.cuppytrail.facades.StadiumFacade;
import com.hybris.platform.cuppytrail.service.StadiumService;


/**
 * @author Shenbagarajan
 *
 */
public class DefaultStadiumFacade implements StadiumFacade
{
	private StadiumService stadiumService;

	@Override
	public List<StadiumData> getStadiums()
	{
		final List<StadiumModel> stadiumModels = stadiumService.getStadium();
		final List<StadiumData> stadiumFacadeData = new ArrayList<StadiumData>();
		for (final StadiumModel sm : stadiumModels)
		{
			final StadiumData sdf = new StadiumData();
			sdf.setName(sm.getCode());
			sdf.setCapacity(sm.getCapacity().toString());

		}
		return stadiumFacadeData;
	}

	@Override
	public StadiumData getStadium(final String name)
	{
		StadiumModel stadium = null;
		if (name != null)
		{
			stadium = stadiumService.getStadiumForCode(name);
			if (stadium == null)
			{
				return null;
			}
		}
		else
		{
			throw new IllegalArgumentException("Stadium with name" + name + "not found");
		}
		final List<MatchSummaryData> matchSummary = new ArrayList<MatchSummaryData>();
		if (stadium.getMatches() != null)
		{
			final Iterator<MatchModel> matchesIterator = stadium.getMatches().iterator();

			while (matchesIterator.hasNext())
			{
				final MatchModel match = matchesIterator.next();
				final MatchSummaryData summary = new MatchSummaryData();
				final String homeTeam = match.getHomeTeam().getName();
				final String guestTeam = match.getGuestTeam().getName();
				final Date matchDate = match.getDate();
				// If no goals are specified provide a user friendly "-" instead of null
				final String guestGoals = match.getGuestGoals() == null ? "-" : match.getGuestGoals().toString();
				final String homeGoals = match.getHomeGoals() == null ? "-" : match.getHomeGoals().toString();
				summary.setHomeTeam(homeTeam);
				summary.setGuestTeam(guestTeam);
				summary.setDate(matchDate);
				summary.setGuestGoals(guestGoals);
				summary.setHomeGoals(homeGoals);
				final String formatedDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(matchDate);
				final String matchSummaryFormatted = homeTeam + ":( " + homeGoals + " ) " + guestTeam + " ( " + guestGoals + " ) "
						+ formatedDate;
				summary.setMatchSummaryFormatted(matchSummaryFormatted);
				matchSummary.add(summary);
			}
		}
		// Now we can create the StadiumData transfer object
		final StadiumData stadiumData = new StadiumData();
		stadiumData.setName(stadium.getCode());
		stadiumData.setCapacity(stadium.getCapacity().toString());
		stadiumData.setMatches(matchSummary);
		return stadiumData;
	}

	@Required
	public void setStadiumService(final StadiumService stadiumService)
	{
		this.stadiumService = stadiumService;

	}
}
