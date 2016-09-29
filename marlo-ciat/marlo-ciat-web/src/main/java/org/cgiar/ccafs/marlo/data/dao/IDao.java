/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

/**
 * 
 */
package org.cgiar.ccafs.marlo.data.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Order;
import javax.persistence.metamodel.SingularAttribute;

import com.google.common.base.Optional;

/**
 * Modified by @author nmatovu last on Sep 29, 2016
 *
 */
public interface IDao<T> {
	public T save(T t);

	public T saveOrUpdate(T t);

	public List<T> findAll();

	public <K> T find(K id);

	public <K> List<T> find(SingularAttribute<T, K> property, K value);

	public <K> List<T> find(SingularAttribute<T, K> property, K value,
			Optional<Integer> offset, Optional<Integer> limit,
			Optional<Order> order);

	public void remove(T t);

}
