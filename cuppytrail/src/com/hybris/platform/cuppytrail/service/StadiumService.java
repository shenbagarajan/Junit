/**
 *
 */
package com.hybris.platform.cuppytrail.service;

import java.util.List;

import com.hybris.model.StadiumModel;


/**
 * @author Shenbagarajan
 *
 */
public interface StadiumService
{
	List<StadiumModel> getStadium();

	StadiumModel getStadiumForCode(String Code);
}
