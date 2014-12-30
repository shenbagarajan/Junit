/**
 *
 */
package com.hybris.platform.cuppytrail.daos;

import java.util.List;

import com.hybris.model.StadiumModel;


/**
 * @author Shenbagarajan
 *
 */
public interface StadiumDAO
{
	List<StadiumModel> findStadiums();

	List<StadiumModel> findStadiumsByCode(String code);
}
